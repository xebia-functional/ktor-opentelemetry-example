# poc-metrics

This project is a example of how to use OpenTelemetry to collect metrics

## How to run

Launch docker compose

```bash
$ cd docker
```

```bash
$ docker compose up -d
```

Execute the servers:

```bash
$ ./gradlew server-a:server
```

```bash
$ ./gradlew server-b:server
```

Call to the endpoint:

```bash
$ curl localhost:8081/hello
```

You can see the metrics on http://localhost:16686/
