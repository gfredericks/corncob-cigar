(ns leiningen.benchmark-task
  "Run a Leiningen task repeatedly to gather statistics about how long it takes."
  (:require [leiningen.core.main :as main]))

(defn benchmark-task
  "Run a Leiningen task repeatedly to gather statistics about how long it takes.

USAGE: lein benchmark-task 20 test

The task will be run one extra time initially as a warmup, to account
for initial compilation or anything else similar."
  [project num-runs & args]
  (let [num-runs (Long/parseLong num-runs)
        f #(main/resolve-and-apply project args)
        _ (f) ;; warmup, etc.
        runtimes
        (repeatedly num-runs
                    (fn []
                      (let [b (System/currentTimeMillis)]
                        (f)
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
