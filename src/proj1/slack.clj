(ns proj1.slack
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.spec.alpha :as spec]
            [java-time :as time]
            [proj1.mongodb :as mongodb])
  (:gen-class))

; this is the #clojure-hacking channel

(def WEBHOOK-URL "https://hooks.slack.com/services/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")

(spec/def ::scheduled-date-time string?)

(spec/def ::text string?)

(spec/def
  ::message
  (spec/keys
    :req-un
    [::scheduled-date-time ::text]))

(defn send-to-slack!
  "Sends a simple message to slack using an 'incoming webhook'.
   url will be of form: https://myapp.slack.com/services/hooks/incoming-webhook?token=mytoken .
   (Exact url you should use will appear on the slack integration page)
   text will be any valid message.
   This implementation could be expanded if you wanted to specify channel, username, etc.
   For more information see:
   https://my.slack.com/services/new/incoming-webhook . (You'll need a slack account
   to see that)"
  ([text]
   (client/post WEBHOOK-URL {:form-params {:payload (json/write-str {:text text})}}))
  ([url text]
   (client/post url {:form-params {:payload (json/write-str {:text text})}})))

(defn read-messages!
  "Reads in the messages from the default JSON file or specified mongodb"
  ([]
   (json/read-str (slurp "data/messages.json") :key-fn keyword))
  ([db]
   (let [msgs (mongodb/get-documents!)]
     {:message (into [] msgs)})))

(defn convert-date-time-str
  "Converts a string to a date-time"
  [the-date-time-str]
  (time/local-date-time "yyyy-MM-dd HH:mm:ss" the-date-time-str))

(defn fix-message-val-types
  "Fixes the datatypes for the values in the message, turning them from strings to other things
   side effect is that the message passed in changes"
  [m]
  (assoc m :java-time convert-date-time-str))

(defn get-messages
  "Gets the messages to be scheduled"
  [messages]
  ; same as below
  ; (->> (read-messages!)
  ;     :message
  ;     (map fix-message-val-types!))
  (let [msgs    (:message messages)
        updated (map fix-message-val-types msgs)]
    {:message updated}))

  ;(map (fn [x] (update x :scheduled-date-time #(local-date-time "yyyy/MM/dd HH:mm:ss" %)) (read-messages)))
  ;(read-messages))

(defn load-messages!
  []
  (let [msgs (:message (read-messages!))]
    (mongodb/load-documents! msgs)))

(defn process-one-message!
  " side effect: post to slack channel "
  [m]
  ;TODO throw an exception if it's an invalid message
  (if (spec/valid? ::message m)
    (println m)
    (println (str "Invalid message: " m))))
    ;(send-to-slack! (:text m))))

(defn process-all-messages!
  " read all messages from file
    then send each of them to slack "
  []
  (let [messages (get-messages (read-messages! mongodb/db))
        message (:message messages)]
    (run! process-one-message! message)))

(defn gen-date-time
  " generates a date time"
  []
  (time/local-date-time "yyyy/MM/dd HH:mm:ss" "2015/10/01 01:02:03"))


(defn -main [& args]
  (process-all-messages!))

#_ (def m (:message (get-messages!)))


