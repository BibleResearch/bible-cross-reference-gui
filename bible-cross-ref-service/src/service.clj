(ns service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [ns-tracker.core :as nst]
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

(def service-map
  {::http/routes routes
   ::http/type   :jetty
   ::http/host   "0.0.0.0"
   ::http/port   3000})

(defn start []
  (http/start (http/create-server service-map)))

(defonce server (atom nil))

(defn- check-namespace-changes [tracker]
  (try
    (doseq [ns-sym (tracker)]
      (require ns-sym :reload)
      (println (str "Reloaded " ns-sym)))
    (catch Throwable e (.printStackTrace e)))
  (Thread/sleep 100))

(defn- start-nstracker []
  (let [tracker (nst/ns-tracker ["src" "resources"])]
    (doto
     (Thread.
      #(while true
         (check-namespace-changes tracker)))
      (.setDaemon true)
      (.start))))

(defn start-dev []
  (reset! server
          (http/start (http/create-server
                       (assoc service-map
                              ::http/join? false))))
  (start-nstracker))

(defn stop-dev []
  (http/stop @server))

(defn restart []
  (stop-dev)
  (start-dev))
