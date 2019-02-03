(ns proj1.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [hiccup.core :refer :all])
  (:gen-class))

; https://learnxinyminutes.com/docs/compojure/

(defn body []
  (html
    [:div
     [:h1 "Greetings!!!"]
     [:p "Hello from Jeff's and Gene's Slack Scheduler."]]))



(defn splash []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (body)})

(defroutes app
           (GET "/" []
                (splash))
           (route/resources "/resources")
           (ANY "*" []
                (route/not-found (slurp (io/resource "404.html")))))

(def handler
  (-> app))
      ;(wrap-defaults site-defaults)))
  ;(site #'app))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty handler {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

