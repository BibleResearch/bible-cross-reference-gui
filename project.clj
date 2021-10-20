(defproject clojure-getting-started "1.0.0-SNAPSHOT"
  :description "Demo Clojure web app"
  :url "http://clojure-getting-started.herokuapp.com"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.3"]
    [org.clojure/data.csv "1.0.0"]
    [io.pedestal/pedestal.service "0.5.7"]
    [io.pedestal/pedestal.route "0.5.7"]
    [io.pedestal/pedestal.jetty "0.5.7"]
    [ns-tracker/ns-tracker "0.4.0"]
    [org.slf4j/slf4j-simple "1.7.28"]
    [selmer/selmer "1.12.6"]
    [environ "1.1.0"]]
  :min-lein-version "2.0.0"
  :source-paths ["src"]
  :test-paths ["tests"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :plugins [[environ/environ.lein "0.3.1"] [lein-cloverage "1.2.2"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "bible-cross-ref-service.jar"
  :profiles {:production {:env {:production true}}
             :uberjar {:aot :all}})
