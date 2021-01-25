docker-compose up --force-recreate --build -d

printf '\nWaiting for the health of the service.'
until $(curl --output /dev/null --silent --head --fail http://localhost:8085/actuator/health); do
    printf '.'
    sleep 5
done
printf '\nDone\n'