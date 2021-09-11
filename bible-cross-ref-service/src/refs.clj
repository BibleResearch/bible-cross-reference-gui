(ns refs
  (:require [clojure-csv.core :as csv]))

(defn take-csv
  "Takes file name and reads data."
  [fname]
  (csv/parse-csv (slurp fname)))

(defn match?
  "Determine whether the query matches the cross-ref"
  [query cross-ref]
  (or
   (= query (first cross-ref))
   (= query (second cross-ref))))

(defn find-matches [query cross-ref]
  (if (match? query cross-ref)
    cross-ref
    nil))

(defn get-cross-refs [query]
  (let [cross-refs (take-csv "resources/csv/cross-refs.csv")]
    (remove nil? (map find-matches (repeat query) cross-refs))))
