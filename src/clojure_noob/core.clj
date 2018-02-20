;;;; Obey comment conventions:

; for inline comment
;; for in-function comment
;;; for between-function comment
;;;; for section header (for outline mode)

(ns clojure-noob.core
  (:gen-class))


(defn greeting "Perform a greeting" [name]
  (str "Hello " name))

(println (greeting "Joey"))
(println)

;;; arity overloading - a different behavior runs based on number of arguments passed
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type] ; 2-arity function (2 parameters)
    (str "I " chop-type " chop " name "! Take that!"))
  ([name] ; 1-arity function (1 parameters)
    (x-chop name "karate")) ; function calling itself
  ([] ; 0-arity function (0 parameters)
    (inc 1))) ; totally different behavior

(println (x-chop "Kanye West" "cross"))
(println (x-chop "Kanye West"))
(println (x-chop))
(println)

;;; rest parameter - last parameter preceeded by & symbol
(defn favorite-things
  "Tell someone what your favorite things are"
  [name & things]
  (str "Hey, " name "! Here are my favorite things: "
    (clojure.string/join ", " things)))

(println (favorite-things "Mike" "games" "music" "books"))
(println)

;;; destructuring a collection, i.e. vector, list
(defn get-first
  "Get first element from a vector or list"
  [[first-el]]
  first-el)

(println (get-first [{:a :b} "hello" 5]))
(println (get-first '("hello" {:a :b} 5)))
(let [[one two three & rest] [1 2 3 4 5 6]]
  (println "one: " one)
  (println "two: " two)
  (println "three: " three)
  (println "the rest: " (clojure.string/join ", " rest)))

(println)

;;; destructuring a map
(defn get-treasure-coordinates
  "Get latitude and longitude from a coordinate"
  [{lat :latitude long :longitude}]
  (println "Treasure lat: " lat)
  (println "Treasure long: " long))

(get-treasure-coordinates {:latitude 28.22 :longitude 81.33})
(defn receive-treasure-coordinates
  "Short-hand for getting latitude and longitude from a coordinate while retaining original argument"
  [{:keys [latitude longitude] :as treasure-location}]
  (println "Treasure latitude: " latitude)
  (println "Treasure longitude: " longitude)
  (println "Original argument: " treasure-location))

(receive-treasure-coordinates {:latitude 47.39 :longitude 65.17})
(println)

;;; anonymous functions
((fn [x] (* x 3)) 8)
((fn [x y] (* x 3 y)) 5 4)
;;; anonymous functions - shorthand
;;; % indicates the argument passed to the function
(#(* % 6) 7)
;;; if your anonymous function takes multiple arguments
;;; you can distinguish them like this: %1, %2, %3, etc
(#(str %1 " and " %2 " are delicious") "cornbread" "yams")

;;; closures and returning functions
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))
(println (inc3 7))
(println)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                              {:name "left-eye" :size 1}
                              {:name "left-ear" :size 1}
                              {:name "mouth" :size 1}
                              {:name "nose" :size 1}
                              {:name "neck" :size 2}
                              {:name "left-shoulder" :size 3}
                              {:name "left-upper-arm" :size 3}
                              {:name "chest" :size 10}
                              {:name "back" :size 10}
                              {:name "left-forearm" :size 3}
                              {:name "abdomen" :size 6}
                              {:name "left-kidney" :size 1}
                              {:name "left-hand" :size 2}
                              {:name "left-knee" :size 2}
                              {:name "left-thigh" :size 4}
                              {:name "left-lower-leg" :size 3}
                              {:name "left-achilles" :size 1}
                              {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
    :size (:size part)})

(defn create-set-matching-parts
  [part]
  (set [part (matching-part part)]))

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
          (into final-body-parts (create-set-matching-parts part)))))))

; (symmetrize-body-parts asym-hobbit-body-parts)

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (create-set-matching-parts part)))
          []
          asym-body-parts))

; (better-symmetrize-body-parts asym-hobbit-body-parts)

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand-int body-part-size-sum)]
    (loop [[part & remaining] sym-parts
          accumulated-size (:size part)]
      (println (str "target: " target " accumulated-size: " accumulated-size " part: " part))
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(hit asym-hobbit-body-parts)

; (def body-part-size
;   (reduce + (map :size (better-symmetrize-body-parts asym-hobbit-body-parts))))

; (println (rand body-part-size))

(def human-consumption  [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])

(defn unify-diet-data
  [human critter]
  {:human human
    :critter critter})
(map unify-diet-data human-consumption critter-consumption)

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(sum [3 4 5])
(avg [3 4 5])
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))
(stats [3 4 10])
(stats [80 1 44 13 6])

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
    {:alias "Spider-Man" :real "Peter Parker"}
    {:alias "Santa" :real "Your mom"}
    {:alias "Easter Bunny" :real "Your dad"}])

(map :alias identities)
(map :real identities)

;;; root function
(defn -main [& args]
  (do
    (println "I'm a little teapot!")
    (println "Hi there!")))

(take 3 [1 2 3 4 5 6 7 8 9 10])
(drop 3 [1 2 3 4 5 6 7 8 9 10])

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
    {:month 1 :day 2 :human 5.1 :critter 2.0}
    {:month 2 :day 1 :human 4.9 :critter 2.1}
    {:month 2 :day 2 :human 5.0 :critter 2.5}
    {:month 3 :day 1 :human 4.2 :critter 3.3}
    {:month 3 :day 2 :human 4.0 :critter 3.8}
    {:month 4 :day 1 :human 3.7 :critter 3.9}
    {:month 4 :day 2 :human 3.7 :critter 3.6}])

; get everything before March
(take-while #(< (:month %) 3) food-journal)
; get everything after February
(drop-while #(< (:month %) 3) food-journal)

; get just February
(take-while #(< (:month %) 3)
  (drop-while #(< (:month %) 2) food-journal))

; `filter` can end up processing all of your data, which isn’t always necessary.
; Because `food-journal` is already sorted by date, we know that `drop-while`
; will return the data we want without having to examine any of
; the data we won’t need. Therefore, `drop-while` can be more efficient.
(filter #(> (:month %) 2) food-journal)

; `some` checks to see whether a collection contains any values that test true
(some #(> (:critter %) 5) food-journal) ; nil
(some #(> (:critter %) 3) food-journal) ; true

; uses `and` to first check whether the condition (> (:critter %) 3) is true,
; and then returns the entry when the condition is indeed true
(some #(and (> (:critter %) 3) %) food-journal)

; `sort` sorts elements in ascending order
(sort [3 1 4 2])

; for more complicated sorts, use `sort-by`
(sort-by count ["aaa", "c", "bb"])

; `concat` appends the members of one sequence to the end of another
(concat [1 2] [3 4])

; lazy seqs
(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
    1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
    2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
    3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
    (not (:has-pulse? record))
      record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
    (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0))

(time (def mapped-details (map vampire-related-details (range 0 1000000))))

(time (first mapped-details))

; infinite seqs
(concat (take 8 (repeat "na")) ["Batman!"])

(take 3 (repeatedly (fn [] (rand-int 10))))







