(ns user
  "Userspace functions you can run by default in your local REPL."
  (:require
   [bible-cross-ref-gui.config :refer [env]]
    [clojure.pprint]
    [clojure.spec.alpha :as s]
    [expound.alpha :as expound]
    [mount.core :as mount]
    [bible-cross-ref-gui.core :refer [start-app]]
    [bible-cross-ref-gui.db.core]
    [conman.core :as conman]
    [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(add-tap (bound-fn* clojure.pprint/pprint))

(defn start
  "Starts application.
  You'll usually want to run this on startup."
  []
  (mount/start-without #'bible-cross-ref-gui.core/repl-server))

(defn stop
  "Stops application."
  []
  (mount/stop-except #'bible-cross-ref-gui.core/repl-server))

(defn restart
  "Restarts application."
  []
  (stop)
  (start))

(defn restart-db
  "Restarts database."
  []
  (mount/stop #'bible-cross-ref-gui.db.core/*db*)
  (mount/start #'bible-cross-ref-gui.db.core/*db*)
  (binding [*ns* (the-ns 'bible-cross-ref-gui.db.core)]
    (conman/bind-connection bible-cross-ref-gui.db.core/*db* "sql/queries.sql")))

(defn reset-db
  "Resets database."
  []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate
  "Migrates database up for all outstanding migrations."
  []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback
  "Rollback latest database migration."
  []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration
  "Create a new up and down migration file with a generated timestamp and `name`."
  [name]
  (migrations/create name (select-keys env [:database-url])))


