(ns leiningen.gsub-version
  "Updates the version string in various files."
  (:require [clojure.string :as s])
  (:import java.util.regex.Pattern))

(defn gsub-version
  "Updates all the version references to this project in the
  files listed in the :gsub-version/files entry."
  [project]
  (let [full-project-name (if (= (:name project) (:group project))
                            (format "(?:%s/)?%s"
                                    (Pattern/quote (:group project))
                                    (Pattern/quote (:name project)))
                            (format "%s/%s"
                                    (Pattern/quote (:group project))
                                    (Pattern/quote (:name project))))
        version-regex
        (re-pattern (format "(\\[%s \")[^\\s\\]]+(\"\\])" full-project-name))]
    (doseq [f (:gsub-version/files project)]
      (-> (slurp f)
          (s/replace version-regex
                     (fn [[_ before after]]
                       (str before (:version project) after)))
          (->> (spit f))))))
