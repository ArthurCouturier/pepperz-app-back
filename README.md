Build and run the docker container with the following commands:
```Bash
docker build -t pepperz.app .
docker run --env-file ./.env -p 8080:8080 pepperz.app
```

dev:
set -o allexport; source .env; set +o allexport
java -jar target/projet-cuisine-0.0.1-SNAPSHOT.jar
