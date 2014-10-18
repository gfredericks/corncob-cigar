(ns leiningen.vcs-tag-no-sign
  (:require [leiningen.core.eval :as eval]))

(defn vcs-tag-no-sign
  "Creates an unsigned git tag in the format
  <project-name>-<version>."
  [{:keys [name root version]}]
  (binding [eval/*dir* root]
    (let [tag (str name "-" version)]
      (eval/sh "git" "tag" tag))))
