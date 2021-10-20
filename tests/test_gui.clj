(ns test-gui
  (:require [io.pedestal.test :refer [response-for]])
  (:require [io.pedestal.http])
  (:require [server :refer [service-map]])
  (:require [clojure.test :refer [deftest is]]))

(def service (:io.pedestal.http/service-fn (io.pedestal.http/create-servlet service-map)))

(defn- response-contains
  "Return whether or not the response from the given URL contains the given regex pattern."
  ([pattern url]
   (not-empty (re-find pattern (:body (response-for service :get url)))))
  ([pattern url verb]
   (not-empty (re-find pattern (:body (response-for service verb url))))))

(deftest test-home
  (is (response-contains #"Search" "/")) ;; base URL
  (is (response-contains #"No results for \"<b>Foo</b>\"" "/?q=Foo")) ;; search w/o results
  (is (response-contains #"Found \d+ cross-references for \"<b>Gen.1.1</b>\"" "/?q=Gen.1.1")) ;; search w/ results
)

(deftest test-invalid-pages
  (is (response-contains #"Not Found" "/foo")) ;; non-existent URL
)
