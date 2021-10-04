(ns service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [refs :refer [get-cross-refs]]
            [selmer.parser :as selmer]))

;; todo: use a ring response instead of this function
(defn ok [body]
  {:status 200 :body body})

(def get-search-results
  {:name ::get-search-results
   :enter (fn [context]
            (let [query (get-in context [:request :query-params :q])
                  cross-refs (get-cross-refs query)]
              (assoc context :response (ok cross-refs))))})

(defn show-home
  [request]
  (ok
    (selmer/render-file "html/home.html"
      (if (= (:request-method request) "get")
        {}
        (let [query (get-in request [:params "query"])]
          {:results (get-cross-refs query)
          :query query})))))

(def routes
  (route/expand-routes
   #{["/" :get [http/html-body show-home] :route-name :home-get]
     ["/" :post [http/html-body (body-params/body-params) show-home] :route-name :home-post]
     ["/api/search" :get [http/json-body get-search-results] :route-name :api-search]}))
