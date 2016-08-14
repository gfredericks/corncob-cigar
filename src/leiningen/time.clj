(ns leiningen.time
  "Runs a leiningen task and prints how long it ran, E.g.:

  lein time test"
  (:refer-clojure :exclude [time])
  (:require [leiningen.core.main :as main]))

(defn time
  [project & args]
  (let [start-time (System/currentTimeMillis)
        res (main/resolve-and-apply project args)
        run-time (- (System/currentTimeMillis) start-time)]
    (printf "Task ran for %.3f seconds\n" (/ run-time 1000.0))
    (flush)
    res))
