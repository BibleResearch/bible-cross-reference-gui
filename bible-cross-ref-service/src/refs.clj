(ns refs
  (:require [csv :refer [csv-data->maps get-csv]]))

(defn match?
  "Determine whether the query matches the cross-ref"
  [query cross-ref]
  (or
   (= query (get cross-ref "a"))
   (= query (get cross-ref "b"))))

(defn find-matches [query cross-ref]
  (if (match? query cross-ref)
    cross-ref
    nil))

(defn get-cross-refs [query]
  (let [cross-refs (csv-data->maps (get-csv "resources/csv/cross-refs.csv"))]
    (remove nil? (map find-matches (repeat query) cross-refs))))
