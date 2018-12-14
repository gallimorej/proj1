(ns proj1.mongodb
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb MongoOptions WriteResult ServerAddress]))

(def uri (format "mongodb://%s:%s@ds113630.mlab.com:13630/%s"
                 "slackadmin"
                 "dY4pY4r5w#"
                 "slack-scheduler"))

(defonce db (atom nil))

; connection to monggodb
(reset! db
        (try
          (mg/connect-via-uri uri)
          (catch Exception e (str "caught exception: "
                                  (.getMessage e))
                             nil)))