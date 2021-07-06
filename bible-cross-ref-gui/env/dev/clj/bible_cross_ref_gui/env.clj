(ns bible-cross-ref-gui.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [bible-cross-ref-gui.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[bible-cross-ref-gui started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[bible-cross-ref-gui has shut down successfully]=-"))
   :middleware wrap-dev})
