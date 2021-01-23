This "wiremock" directory contains a set of tools that would allow you to spin up a mock JSON API and consume the relevant in any language you wish.

./start.sh to start mock server
./stop.sh to stop mock server

### List of all products
**GET http://localhost:8081/products**

### Product information
**GET http://localhost:8081/products/{productId}**

**Note:** The productIds of all items can be retrieved via the  **/products** call
