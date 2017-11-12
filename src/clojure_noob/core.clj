;;;; Obey comment conventions:

; for inline comment
;; for in-function comment
;;; for between-function comment
;;;; for section header (for outline mode)

(ns clojure-noob.core
  (:gen-class)
)

(defn greeting
  "Perform a greeting"
  [name]
  (str "Hello " name)
)
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
    (inc 1)) ; totally different behavior
)
(println (x-chop "Kanye West" "cross"))
(println (x-chop "Kanye West"))
(println (x-chop))
(println)

;;; rest parameter - last parameter preceeded by & symbol
(defn favorite-things
  "Tell someone what your favorite things are"
  [name & things]
  (str "Hey, " name "! Here are my favorite things: "
    (clojure.string/join ", " things))
)
(println (favorite-things "Mike" "games" "music" "books"))
(println)

;;; destructuring a collection, i.e. vector, list
(defn get-first
  "Get first element from a vector or list"
  [[first-el]]
  first-el
)
(println (get-first [{:a :b} "hello" 5]))
(println (get-first '("hello" {:a :b} 5)))
(let [[one two three & rest] [1 2 3 4 5 6]]
  (println "one: " one)
  (println "two: " two)
  (println "three: " three)
  (println "the rest: " (clojure.string/join ", " rest))
)
(println)

;;; destructuring a map
(defn get-treasure-coordinates
  "Get latitude and longitude from a coordinate"
  [{lat :latitude long :longitude}]
  (println "Treasure lat: " lat)
  (println "Treasure long: " long)
)
(get-treasure-coordinates {:latitude 28.22 :longitude 81.33})
(defn receive-treasure-coordinates
  "Short-hand for getting latitude and longitude from a coordinate while retaining original argument"
  [{:keys [latitude longitude] :as treasure-location}]
  (println "Treasure latitude: " latitude)
  (println "Treasure longitude: " longitude)
  (println "Original argument: " treasure-location)
)
(receive-treasure-coordinates {:latitude 47.39 :longitude 65.17})
(println)


;;; root function
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do
    (println "I'm a little teapot!")
    (println "Hi there!")
  )
)
