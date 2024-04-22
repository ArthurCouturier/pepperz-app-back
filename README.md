Build and run the docker container with the following commands:
```Bash
docker build -t pepperz.app .
docker run --env-file ./.env -p 8080:8080 pepperz.app
```

dev:
set -o allexport; source .env; set +o allexport
java -jar target/projet-cuisine-0.0.1-SNAPSHOT.jar


mac dev:
set -o allexport; source .env; set +o allexport
cd /Users/arthurcouturier/Documents/GitHub/pepperz-app/pepperz-app-back ; /usr/bin/env /Users/arthurcouturier/.vscode/extensions/redhat.java-1.29.0-darwin-arm64/jre/17.0.10-macosx-aarch64/bin/java @/var/folders/9b/vjnn8l0j2ggbklzkykdmmn_00000gn/T/cp_64z9h6nxwu8zbk7r7sbuwyvge.argfile cout.dev.projetcuisine.PepperzApp