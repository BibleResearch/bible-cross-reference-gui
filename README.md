# Simple Clojure Web App

A simple web app for experimentation in Clojure (specifically using [luminus](https://luminusweb.com/)).


## Usage

To run the app:

```
docker-compose run --service-ports dev
```

Then (once in the dev environment):

```
cd guestbook/
lein run migrate
lein run
```
