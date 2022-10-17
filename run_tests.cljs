(ns e2e.run-tests
  (:require [nbb.core :refer [load-file]]))

(load-file "login_test.cljs")
(load-file "map_test.cljs")
