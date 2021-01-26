# supermarket solution

## Requirements
- npm
- docker
- docker-compose

## First Steps
First of all, it is necessary to run the *start.sh* file which will be responsible for lifting the docker-compose with the images of the wiremock and the developed backend. *There is no need to start the wiremock* as I put it in a Docker container and it will upload together with a PostgreSQL database and the backend application I developed. After uploading the containers, a local section will start with our application, which can be accessed through the url *http://localhost:8080*.

## Details
### Backend
For the backend it was decided to use the Java language together with the SpringBoot framework, thus reflecting my knowledge of the language. I tried to apply a design patern with adapters, totally decoupling our application from the communication layer to search for products, for that we used the ProductAdapter Interface as a port. This positively impacts a data source migration, having to respect the contract defined in that interface. The design strategy standard was also used which, by means of a contract, defines which calculation will be applied to the flow record. For this we use the contract informed in the interface WiremockPromotionStrategy that is used in the class WiremockAdapter.

I used Swagger to facilitate my table tests, with it I did several simulations *http://localhost:8085/swagger-ui*. With it I was also able to do the API documentation. 

For communication with the database I used JPA and to make queries use JPQL standards.

Several unit tests were carried out, covering 100% of the application's service package and 75% of the adapter. Only tests were not carried out for controllers, configurations and classes that are responsible only for the use of some library. As I am using the gradle, when building all tests are performed, thus guaranteeing this coverage for application startup.

### Frontend
For the front I used the JavaScript language together with the chosen Vue.js framework, thus bringing in this project some of my knowledge in this framework. In it we have 4 views and 2 components. The views represent the screens (Login, Home, Checkout and History) and the two components were placed to represent my knowledge in componentization. Both components control information from the basket that is in the Home view. I also put a user control just in order to store the basket that has not been finalized and bringing it in case the user does not finalize the order and returns at another time.

### Flow
When opening the main link *http://localhost:8080* the application will open the identification screen, where the user will inform his identification. If it is the first time that the user has entered his / her identification, the application will display an alert informing that the identification was not found and that to register, just click the login button again.This action will take you to the Home page.

On the home screen the user will have access to an upper bar that has a fake logo on the left and on the right the user and basket information. When hovering over the user's information, the application will open the menu with options for redirecting to the closed order history or exit with logout. When hovering the mouse over the cart, a menu will open with the cart information and a button for redirecting to the checkout. The basket symbol will also contain information on the total quantity of items ordered. Just below the top bar, the available products will be displayed. The addition to the cart can initially be done by the button contained in each product, or if the customer prefers, he can add or remove it in the displayed basket.

When the customer finishes choosing products, by hovering the mouse over the cart, he / she will be able to see the summary of what he / she has bought, being able to add or remove items. Below the items, an order confirmation button will be displayed that will take the customer to the checkout screen. When you click on the checkout button, your totalizers will be displayed. At the bottom of the summary, two buttons will be found, which give the option to finalize the order or continue shopping.