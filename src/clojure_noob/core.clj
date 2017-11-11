(ns clojure-noob.core
  (:gen-class))

(defn greeting
  [name]
  (str "Hello " name))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do (println "I'm a little teapot!")
    (println (greeting "Joey"))))
