(ns csv
  (:require [clojure.data.csv :as csv])
  (:require [clojure.java.io :as io]))

(defn get-csv [fname]
  "Read given file name as CSV."
  (with-open [reader (io/reader fname)]
    (doall
     (csv/read-csv reader))))

(defn csv-data->maps [csv-data]
  "Convert csv-data to zipmap with the first row as the header."
  (map zipmap
       (->> (first csv-data) ;; First row is the header
            repeat)
       (rest csv-data)))
