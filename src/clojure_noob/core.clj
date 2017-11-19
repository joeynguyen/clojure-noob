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

(symmetrize-body-parts asym-hobbit-body-parts)

;;; root function
(defn -main [& args]
  (do
    (println "I'm a little teapot!")
    (println "Hi there!")))
