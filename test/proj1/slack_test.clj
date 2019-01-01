(ns proj1.slack-test
  (:require [clojure.test :refer :all]
            [proj1.slack :refer :all]
            [clojure.spec.alpha :as spec]))

(deftest a-slack-test
  (testing "Dummy test for now"
    (is (= 0 0))
    (is (not (= 0 1)))))

(deftest valid-message-test
  (testing "Valid message test"
    (is (spec/valid? :proj1.slack/message {:scheduled-date-time "2019-01-01 00:00:00" :text "The message  text"}))))

(deftest invalid-message-test
  (testing "Invalid message test"
    (is (not (spec/valid? :proj1.slack/message {:scheduled-date-time "2019-01-01 00:00:00"})))
    (is (not (spec/valid? :proj1.slack/message {:text "The message text"})))))

;TODO add some test for the date format and conversion

