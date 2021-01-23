host="0.0.0.0"
port="8081"

java -jar wiremock-standalone-2.14.0.jar --port $port #--verbose &> wiremock.log 2>&1 &
echo "Wiremock started on host $host and port $port. PID : $!"
