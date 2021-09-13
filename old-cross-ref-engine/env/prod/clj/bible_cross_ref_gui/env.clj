(ns bible-cross-ref-service.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[bible-cross-ref-service started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[bible-cross-ref-service has shut down successfully]=-"))
   :middleware identity})
