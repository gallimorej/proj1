(defproject proj1 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.9.1"]
                 [org.clojure/data.json "0.2.6"]
                 [clojure.java-time "0.3.2"]]
  :plugins [[com.jakemccrary/lein-test-refresh "LATEST"]]                 
  :main proj1.slack)
