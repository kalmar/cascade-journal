# Cascade Rock Climbing Club Journal App

## Building

To build whole components just run

```
mvn clean install
```


Then to run docker rest service do following

```bash
pushd journal-service

# pull Postgres image
docker image pull postgres:10.4-alpine

# build docker image for journal app
mvn dockerfile:build

# deploy
docker stack deploy -c stack.yml journalapp

popd
```

Rest will be available at [http://0.0.0.0:8080/](http://0.0.0.0:8080/)
