(ns service
  (:require [clojure.data.json :as json]
            [io.pedestal.http.route :as route]
            [refs :refer [get-cross-refs]]))

(defn ok [body]
  {:status 200 :body body :headers {"Content-Type" "application/json"}})

(def get-search-results
  {:name ::get-search-results
   :enter (fn [context]
            (println "here")
            (let [query (get-in context [:request :query-params :q])
                  cross-refs (get-cross-refs query)]
              (assoc-in context [:response :body] cross-refs)))})

(def jsonify
  {:name ::jsonify
   :enter (fn [context]
            (assoc context :response (ok (json/write-str (get-in context [:response :body])))))})

(def routes
  (route/expand-routes
   #{["/search" :get [get-search-results jsonify] :route-name :search]}))
