(ns bible-cross-ref-gui.routes.cross-ref
  (:require
   [bible-cross-ref-gui.layout :as layout]
   [bible-cross-ref-gui.middleware :as middleware]
   [clojure-csv.core :as csv]
   [clojure.java.io :as io]))

(defn take-csv
  "Takes file name and reads data."
  [fname]
    (csv/parse-csv (slurp (io/resource fname))))

(defn get-data [{:keys [flash] :as request}]
  (layout/render request "data.html" (merge {:data (take-csv "csv/cross-refs.csv")}
                                            (select-keys flash [:name :message :errors]))))

(defn cross-ref-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/data" {:get get-data}]])
