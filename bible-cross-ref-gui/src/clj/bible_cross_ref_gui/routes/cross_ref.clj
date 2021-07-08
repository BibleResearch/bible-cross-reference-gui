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

(defn get-search-results [query]
  (let [cross-refs (take-csv "csv/cross-refs.csv")]
    (for [ref cross-refs]
      (if (or 
           (= query (first ref))
           (= query (second ref)))
          ref))))

(defn show-search-results [{:keys [flash] :as request}]
  (let [query (get-in request [:params :query])]
    (layout/render request "search.html"
                   (merge {:results (get-search-results query)
                           :query query}
                          (select-keys flash [:name :message :errors])))))

(defn cross-ref-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/search" {:get show-search-results
               :post show-search-results}]])
