(ns leiningen.benchmark-task
  "Run a Leiningen task repeatedly to gather statistics about how long
it takes. E.g.:

  lein benchmark-task 20 test"
  (:require [leiningen.core.main :as main]))

(defn benchmark-task
  [project num-runs & args]
  (let [num-runs (Long/parseLong num-runs)
        runtimes
        (repeatedly num-runs
                    (fn []
                      (let [b (System/currentTimeMillis)]
                        (main/resolve-and-apply project args)
                        (- (System/currentTimeMillis) b))))
        avg (/ (reduce + runtimes) (double num-runs))
        stddev (Math/sqrt
                (reduce + (for [runtime runtimes
                                :let [diff (- runtime avg)]]
                            (/ (* diff diff) (double num-runs)))))]
    (printf "Ran task %d times, runtime was mean of %.2fms, standard deviation of %.2fms.\n"
            num-runs
            avg
            stddev)
    (flush)))
