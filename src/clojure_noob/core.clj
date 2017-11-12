(ns clojure-noob.core
  (:gen-class))

(defn greeting
  [name]
  (str "Hello " name))

; arity overloading - a different behavior runs based on number of arguments passed
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type] ; 2-arity function (2 parameters)
    (str "I " chop-type " chop " name "! Take that!"))
  ([name] ; 1-arity function (1 parameters)
    (x-chop name "karate")) ; function calling itself
  ([] ; 0-arity function (0 parameters)
    (inc 1))) ; totally different behavior

; rest parameter - last parameter preceeded by & symbol
(defn favorite-things
  "Tell someone what your favorite things are"
  [name & things]
  (str "Hey, " name "! Here are my favorite things: "
    (clojure.string/join ", " things)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do
    (println "I'm a little teapot!")
    (println (greeting "Joey"))
    (println (x-chop "Kanye West" "cross"))
    (println (x-chop "Kanye West"))
    (println (x-chop))
    (println (favorite-things "Mike" "games" "music" "books"))
  ))
