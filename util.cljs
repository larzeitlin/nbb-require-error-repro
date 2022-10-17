(ns e2e.util
  (:require
   [clojure.test :as t :refer [is]]
   [promesa.core :as p]))

;; Debugging

(def continue (atom nil))

(defn pause []
  (js/Promise.
   (fn [resolve]
     (reset! continue resolve))))

(defmacro defp
    "Define var when promise is resolved"
    [binding expr]
    `(-> ~expr (.then (fn [val]
                        (def ~binding val)))))

;; Tests

(defn is-visible? [loc]
  (-> loc
      (.isVisible)
      (p/then #(is % (str loc " not visible")))))
