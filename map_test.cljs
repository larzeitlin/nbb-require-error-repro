(ns e2e.map-test
  {:clj-kondo/config '{:lint-as {promesa.core/let clojure.core/let
                                 e2e.util/defp clojure.core/def
                                 promesa.core/doseq clojure.core/doseq}}}
  (:require
   ["playwright$default"]
   [clojure.test :as t :refer [deftest is async]]
   [e2e.config]
   [e2e.util]
   [promesa.core :as p]))

(deftest map-card-interactions
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
         (e2e.util/is-visible? (.locator page ".ol-unselectable.ol-layers"))

         (println (str account-username ": Map areas / cards interaction"))
         (p/let [count-cards (.count (.locator page  "data-testid=area-card"))
                 area-cards (.locator page "data-testid=area-card")]
           (p/doseq [n (range count-cards)]
             (.hover (.nth area-cards n))
             (.waitForSelector page "[id=popup]")
             (e2e.util/is-visible? (.locator page "[id=popup]"))))
         (.close browser)))
     (done))))

(t/run-tests 'e2e.map-test)
