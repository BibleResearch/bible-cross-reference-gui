(ns test-api
  (:require [io.pedestal.test :refer [response-for]])
  (:require [test-gui :refer [service]])
  (:require [clojure.test :refer [deftest is]]))

(deftest test-api-search
  (is (= (:body (response-for service :get "/api/search")) "[]")) ;; base URL
  ;; todo: write more tests here...
  ;; (is (response-contains #"No results for \"<b>Foo</b>\"" "/?q=Foo")) ;; search w/o results
  ;; (is (response-contains #"Found \d+ cross-references for \"<b>Gen.1.1</b>\"" "/?q=Gen.1.1")) ;; search w/ results
)
