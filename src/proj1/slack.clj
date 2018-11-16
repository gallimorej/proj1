(ns proj1.slack
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))


; this is the #gallimore-stuff channel

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
