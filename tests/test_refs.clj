(ns test-refs
  (:require [clojure.test :refer [deftest is]])
  (:require [refs :refer [get-cross-refs]]))

(deftest test-refs-with-valid-query
  (let [test-data (get-cross-refs "Gen.1.1")]
    (is (.contains (map #(get % "b") test-data) "Isa.65.17"))
    (is (= (keys (first test-data)) '("a" "b" "description" "sources" "date-added")))
  )
)

(deftest test-refs-with-invalid-query
  (is (= (get-cross-refs "Foo") '()))
)
