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

Once both servers are up and healthy, run the client:

```bash
$ ./gradlew :client:client
```

You can see the metrics on 

* Jaeger: http://localhost:16686/
* Zipkin: http://localhost:9411/zipkin/
