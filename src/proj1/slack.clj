(ns proj1.slack
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [java-time :as time]))

; this is the #clojure-hacking channel

(def WEBHOOK-URL "https://hooks.slack.com/services/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")

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
  "Reads in the messages from a JSON file"
  []
  (json/read-str (slurp "data/messages.json") :key-fn keyword))

(defn convert-date-time-str
  "Converts a string to a date-time"
  [the-date-time-str]
  (time/local-date-time "yyyy-MM-dd HH:mm:ss" the-date-time-str))

(defn fix-message-val-types
  "Fixes the datatypes for the values in the message, turning them from strings to other things
   side effect is that the message passed in changes"
  [m]
  (update m :scheduled-date-time convert-date-time-str))

(defn get-messages!
  "Gets the messages to be scheduled"
  []
  ;TODO: process the keywords to convert strings to other data types
  ; same as below
  ; (->> (read-messages!)
  ;     :message
  ;     (map fix-message-val-types!))
  (let [msgs    (:message (read-messages!))
        updated (map fix-message-val-types msgs)]
    updated))

  ;(map (fn [x] (update x :scheduled-date-time #(local-date-time "yyyy/MM/dd HH:mm:ss" %)) (read-messages)))
  ;(read-messages))





(defn process-one-message!
  " side effect: post to slack channel "
  [m]
  (fix-message-val-types m)
  (println m))
  ;(send-to-slack! (:text m)))

(defn process-all-messages!
  " read all messages from file
    then send each of them to slack "
  []
  (let [messages (get-messages!)
        message (:message messages)]
    (run! process-one-message! message)))

(defn gen-date-time
  " generates a date time"
  []
  (time/local-date-time "yyyy/MM/dd HH:mm:ss" "2015/10/01 01:02:03"))


#_ (def m (:message (get-messages!)))


