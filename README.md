Build and run the docker container with the following commands:
```Bash
docker build -t pepperz.app .
docker run --env-file ./.env -p 8080:8080 pepperz.app
```