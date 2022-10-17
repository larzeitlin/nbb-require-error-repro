(ns e2e.login-test
  {:clj-kondo/config '{:lint-as {promesa.core/let clojure.core/let
                                 promesa.core/doseq clojure.core/doseq}}}
  (:require
   ["playwright$default"]
   [clojure.test :as t :refer [deftest is async]]
   [e2e.config]
   [e2e.util]
   [promesa.core :as p]))

(deftest login
  (async
   done
   (p/do
     (p/doseq [account-username e2e.config/test-account-usernames]
       (p/let [browser (.launch e2e.config/browser-type #js {:headless true})
               context (.newContext browser)
               page (.newPage context)]

         (println (str account-username ": Logging in"))
         (.goto page e2e.config/dashboard-url)
         (.fill (.locator page "[id=email]") account-username)
         (.fill (.locator page "[id=password]") e2e.config/password)
         (.click (.locator page "[id=btn-login]"))

         (println (str account-username ": Dashboard loading"))
         (.waitForSelector page "[id=map-container]")
         (e2e.util/is-visible? (.locator page "[id=map-container]"))

         (println (str account-username ": Switch to Explorer"))
         (.click (.locator page "text=Explorer"))
         (.click (.locator page "text=I accept the terms of service"))
         (.click (.locator page "text=Continue"))
         (.click (.locator page "text=No thanks"))
         (e2e.util/is-visible? (.locator page "[id=viewer]"))

         (println (str account-username ": Return to Dashboard"))
         (.click (.locator page "text=Dashboard"))

         (println (str account-username ": Logging out"))
         (.click (.locator page "[id=menu-button-7]"))
         (.click (.locator page "text=Sign out"))
         (.waitForSelector page "text=Welcome to")
         (e2e.util/is-visible? (.locator page "text=Welcome to"))
         (.close browser)))
     (done))))

(t/run-tests 'e2e.login-test)
