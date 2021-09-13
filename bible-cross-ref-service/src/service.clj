(ns service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [refs :refer [get-cross-refs]]))

(defn ok [body]
  {:status 200 :body body})

(defn not-found []
  {:status 404 :body "Not found\n"})

(defn get-search-results [request]
  (let [query (get-in request [:query-params :q])]
    (ok (str (count (get-cross-refs query))))))

(def routes
  (route/expand-routes
   #{["/search" :get get-search-results :route-name :search]}))
