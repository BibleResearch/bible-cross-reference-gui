(ns server
  (:require [io.pedestal.http :as http]
            [ns-tracker.core :as nst]
            [service :refer [routes]]))

(def service-map
  {::http/routes routes
   ::http/type   :jetty
   ::http/host   "0.0.0.0"
   ::http/port   3000})

(defn start []
  (http/start (http/create-server service-map)))

(defonce server (atom nil))

(defn stop-dev []
  (http/stop @server))

(defn start-dev []
  (reset! server
          (http/start (http/create-server
                       (merge service-map
                              {::http/join? false
                               ;; all origins are allowed in dev mode
                               ::http/allowed-origins {:creds true :allowed-origins (constantly true)}
                               ;; Content Security Policy (CSP) is mostly turned off in dev mode
                               ::http/secure-headers {:content-security-policy-settings {:object-src "'none'"}}})))))

(defn restart-dev []
  (stop-dev)
  (start-dev))

(defn- check-namespace-changes [tracker]
  (try
    (doseq [ns-sym (tracker)]
      (require ns-sym :reload)
      (restart-dev)
      (println (str "Reloaded " ns-sym)))
    (catch Throwable e (.printStackTrace e)))
  (Thread/sleep 100))

(defn start-nstracker []
  (let [tracker (nst/ns-tracker ["src" "resources"])]
    (doto
     (Thread.
      #(while true
         (check-namespace-changes tracker)))
      (.setDaemon true)
      (.start))))

;; (defn -main [& [port]]
;;   (let [port (Integer. (or port (env :port) 5000))]
;;     (jetty/run-jetty (site #'app) {:port port :join? false})))

(defn -main []
  (start))
