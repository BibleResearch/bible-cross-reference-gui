(ns service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [refs :refer [get-cross-refs]]))

(defn ok [body]
  {:status 200 :body body})

(def get-search-results
  {:name ::get-search-results
   :enter (fn [context]
            (let [query (get-in context [:request :query-params :q])
                  cross-refs (get-cross-refs query)]
              (assoc context :response (ok cross-refs))))})

(def get-status
  {:name ::get-status
   :enter (fn [context]
            (assoc context :response (ok {:status "Good"})))})

(def routes
  (route/expand-routes
   #{["/search" :get [http/json-body get-search-results] :route-name :search]
     ["/status" :get [http/json-body get-status] :route-name :status]}))
