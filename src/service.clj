(ns service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [refs :refer [get-cross-refs]]
            [selmer.parser :as selmer]))

;; todo: use a ring response instead of this function
(defn ok [body]
  {:status 200 :body body})

(defn get-query [request]
  (get-in request [:query-params :q]))

(defn get-search-results [request]
  (let [query (get-query request)]
    (get-cross-refs query)))

(defn show-api-results [request]
  ;; todo: check to see if there is a query param before trying to get results
  (ok (get-search-results request)))

(defn show-home
  [request]
  (ok
    (selmer/render-file "html/home.html"
      (let [query (get-query request)]
        (if (not-empty query)
        {:results (get-search-results request)
         :query query}
        {})))))

(def routes
  (route/expand-routes
   #{["/" :get [http/html-body show-home] :route-name :home-get]
     ["/api/search" :get [http/json-body show-api-results] :route-name :api-search]}))
