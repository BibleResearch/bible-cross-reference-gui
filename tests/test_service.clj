(ns test-service
  (:require [io.pedestal.test :refer [response-for]])
  (:require [io.pedestal.http])
  (:require [server :refer [service-map]])
  (:require [clojure.test :refer [deftest is]]))

(def service (:io.pedestal.http/service-fn (io.pedestal.http/create-servlet service-map)))

(deftest get-home
  (is (re-find #"Search" (:body (response-for service :get "/")))))
