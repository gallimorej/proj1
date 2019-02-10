(ns proj1.worker
  (:require [proj1.slack :as slack])
  (:gen-class))

(defn -main [& args]
  (slack/process-all-messages!))
