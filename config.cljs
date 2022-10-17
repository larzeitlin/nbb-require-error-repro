(ns e2e.config
  (:require ["playwright$default" :as pw]))

(def browser-type pw/firefox) ;; or pw/chromium

(def password #_js/process.env.DASHBOARD_TEST_PASSWORD)
(def dashboard-url #_js/process.env.DASHBOARD_URL_STAGING)

(def test-account-usernames ["testcos@gybe.eco"
                             "testnc@gybe.eco"
                             "testwf@gybe.eco"])
