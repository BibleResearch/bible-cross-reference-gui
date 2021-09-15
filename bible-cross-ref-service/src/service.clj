(ns service
  (:require [clojure.data.json :as json]
            [io.pedestal.http.route :as route]
            [refs :refer [get-cross-refs]]))

(defn ok [body]
  {:status 200 :body body})

(defn get-search-results [request]
  )
    ;; (ok (get-cross-refs query))))

(def get-search-results
  {:name ::get-search-results
   :enter (fn [context]
            (let [query (get-in context [:request :query-params :q])]
              (assoc context :response {:body (get-cross-refs query)})))})

(def format-results
  {:name ::format-results
   :enter (fn [context]
            (let [body (get-in context [:response :body])]
              (update-in context [:response :body] (TODO: CONVERT ARRAY TO MAP HERE...))))})

(def echo
  {:name ::echo
   :enter (fn [context]
            (println (str "a: " (:response context)))
            context)})

(def jsonify
  {:name ::jsonify
   :leave (fn [context]
            (update context :response (json/write-str (get-in context [:response :body]))))})

(def routes
  (route/expand-routes
   #{["/search" :get [jsonify echo get-search-results format-results] :route-name :search]
     ["/echo" :get echo]}))
