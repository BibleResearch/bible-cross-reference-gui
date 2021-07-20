(ns bible-cross-ref-service.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [bible-cross-ref-service.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[bible-cross-ref-service started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[bible-cross-ref-service has shut down successfully]=-"))
   :middleware wrap-dev})
