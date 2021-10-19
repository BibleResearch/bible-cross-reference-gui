(ns test-csv
  (:require [clojure.test :refer [deftest is]])
  (:require [csv :refer [get-csv]]))

(deftest get-csv-valid-name
  (is (not-empty (get-csv "resources/csv/cross-refs.csv"))))

(deftest get-csv-invalid-names
  (is (thrown? java.io.FileNotFoundException (get-csv "foo.csv")))
  (is (thrown? java.io.FileNotFoundException (get-csv ""))))
