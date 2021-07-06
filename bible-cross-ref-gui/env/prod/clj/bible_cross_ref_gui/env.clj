(ns bible-cross-ref-gui.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[bible-cross-ref-gui started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[bible-cross-ref-gui has shut down successfully]=-"))
   :middleware identity})
