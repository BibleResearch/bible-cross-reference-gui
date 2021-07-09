(ns bible-cross-ref-gui.routes.cross-ref
  (:require
   [bible-cross-ref-gui.layout :as layout]
   [bible-cross-ref-gui.middleware :as middleware]
   [clojure-csv.core :as csv]
   [ring.util.http-response :as response]
   [clojure.java.io :as io]))

(defn take-csv
  "Takes file name and reads data."
  [fname]
  (csv/parse-csv (slurp (io/resource fname))))

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

(defn get-search-results [query]
  (let [cross-refs (take-csv "csv/cross-refs.csv")]
      (remove nil? (map find-matches (repeat query) cross-refs))))

(defn show-search-results [{:keys [flash] :as request}]
  (let [query (get-in request [:params :query])
        results (if (not-empty query)
                  (get-search-results query)
                  ())]
    (layout/render request "search.html"
                   (merge {:results results
                           :query query}
                          (select-keys flash [:name :message :errors])))))

(defn cross-ref-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/search" {:get show-search-results
               :post show-search-results}]])
