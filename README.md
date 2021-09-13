# Bible Cross Reference Service

An API and GUI (built in [Clojure](https://clojure.org/) using [pedestal](http://pedestal.io/)) for interacting with cross references throughout the Bible.

## Usage

To run the app:

```
docker-compose run --service-ports dev
```

Then (once in the dev environment):

```
cd bible-cross-ref-service/
clj
```

This will drop you into a repl where you can start the server by running:

```
(require 'server)
(server/start-dev)
```
