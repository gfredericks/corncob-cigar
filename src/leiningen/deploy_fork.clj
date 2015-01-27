(ns leiningen.deploy-fork
  (:require [clojure.java.shell :as sh]
            [leiningen.deploy]))

(defn ^:private remove-trailing-dot
  [s]
  (cond-> s
          (= \. (last s))
          (subs 0 (dec (count s)))))

(defn ^:private current-git-commit
  []
  (try
    (let [{:keys [out]} (sh/sh "git" "rev-parse" "HEAD")]
      (if (and (string? out)
               (re-matches #"(?s)[0-9a-f]{7}.*" out))
        (subs out 0 7)
        (throw (ex-info "Failed to parse `git rev-parse HEAD`!"
                        {:out out}))))
    (catch Exception e
      (throw (Exception. "Failed to determine current git commit!" e)))))

(defn ^:private append-sha-to-version
  [version sha]
  (let [[_ non-snapshot] (re-matches #"(.*?)(-SNAPSHOT)?" version)]
    (str non-snapshot "-" sha)))

(defn deploy-fork
  "Deploys a forked version of this project. Requires a :deploy-fork
  entry (presumably in your :user profile) with at least the key
  :group-id-prefix. Appends the current commit SHA to the version
  (assumes git)."
  [project]
  (let [{:keys [group-id-prefix]} (:deploy-fork project)
        whole-name (str (:group project) "/" (:name project))]
    (when-not group-id-prefix
      (throw (ex-info "No [:deploy-fork :group-id-prefix] found in project!"
                      {:project project})))
    (-> project
        (update-in [:group] str "." (remove-trailing-dot (str group-id-prefix)))
        (update-in [:description]
                   (fn [s]
                     (format "Temporary forked version of %s.\n\n%s"
                             whole-name s)))
        (update-in [:version] append-sha-to-version (current-git-commit))
        (leiningen.deploy/deploy))))
