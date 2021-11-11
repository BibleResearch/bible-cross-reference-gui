# Bible Cross Reference Service

An [API and GUI](https://bible-cross-ref-service.herokuapp.com/) (built with [pedestal](http://pedestal.io/)) for interacting with cross references throughout the Bible.

## Local Development

### REPL/Service

To run the app:

```
docker-compose run --service-ports dev
```

Then (once in the dev environment):

```
lein repl
```

This will drop you into a repl where you can start the server by running:

```
(require 'server)
(server/start-dev)
(server/start-nstracker)
```

This will start the server at [http://localhost:5000](http://localhost:5000) and will auto-reload when there is a change in the `src` or `resources` directory.

### Tests

To run tests and coverage, run:

```shell
lein cloverage -e server
```
