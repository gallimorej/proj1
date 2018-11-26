(ns proj1.slack
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))


; this is the #clojure-hacking channel

(def WEBHOOK-URL "https://hooks.slack.com/services/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")

(defn send-to-slack
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

(defn read-messages
  "Reads in the messages from a JSON file"
  []
  (json/read-str (slurp "data/messages.json") :key-fn keyword))

(defn process-message
  [messages]
  (if (not (empty? messages))
    (do
      (send-to-slack ((first messages) :text))
      (process-message (rest messages)))))

(defn process-messages
  []
  (let [messages (read-messages)]
    (process-message (messages :message))))


    ;;(send-to-slack (messages :message)))))

