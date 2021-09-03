(ns bible-cross-ref-service.handler-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :refer :all]
   [ring.middleware.anti-forgery :as af :refer :all]
   [bible-cross-ref-service.handler :refer :all]
   [bible-cross-ref-service.middleware.formats :as formats]
   [muuntaja.core :as m]
   [mount.core :as mount]))

(defn parse-json [body]
  (m/decode formats/instance "application/json" body))

(use-fixtures
  :once
  (fn [f]
    (mount/start #'bible-cross-ref-service.config/env
                 #'bible-cross-ref-service.handler/app-routes)
    (f)))

(deftest test-routes
  (testing "main route w/o query"
    (let [response ((app) (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "main route w/ unfound query"
    (let [response ((app) (request :post "/" {:query "Foo"}))]
      (is (= 200 (:status response)))))

  (testing "main route w/ found query"
    (let [response ((app) (request :post "/" {:query "Gen.1.1"}))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response ((app) (request :get "/invalid"))]
      (is (= 404 (:status response))))))
