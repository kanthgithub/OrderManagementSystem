# Order Management System

# API Specifications:

- You can access swagger page here:
    http://localhost:8081/swagger-ui.html


# H2 Console:
  http://localhost:8081/h2-console

    - JDBC Url: jdbc:h2:file:./src/main/db/assigment-db
    - username: sa
    - password: <blank>



### Installing

A step by step series of examples that tell you how to get a development env running


```
mvn clean install
```

### Run Application

```
mvn spring-boot:run
```

### Entrypoint to Application

```
com.yieldBroker.yieldBrokerApplication
```



### Data-Model

```
Data-Model was modified from the skeleton setup

Entity:

Package: com.yieldBroker.entity
Order: Plain Old Java Object representation of Orders Table (YB_ORDER)


model:
Objects to accept data from & send to client

package: com.yieldBroker.model

OrderBook: POJO containing buyOrders and sellOrders seggrgated
OrderBookModel: OrderBook contains list of Orders
OrderModel: POJO to carry informartion to serverside/ from server-side for order-handling (Place Order)

```

### Controller

```
package containing classes with end-points for clients to interact with OrderManagementSystem

Class: com.yieldBroker.controller.OrderBookController
```

### Service

```
package containing service layer of order management system

Interface: com.yieldBroker.service.OrderService

Class: com.yieldBroker.service.OrderServiceImpl
```


### Repository

```
package containing the repository classes to interact/transact with database (H2)

Interface: com.yieldBroker.repository.OrderRepository

Class: com.yieldBroker.repository.OrderRepositoryImpl
```


## Running the tests

```
mvn test
```

### Break down into Unit tests

```
Test Class: com.yieldBroker.OrderBookControllerIntegrationTest

Purpose: Repository Methods - interaction/transaction with H2 Database (In-Memory)

Comments: H2 is an in-Memory and initialized/destroyed per-test execution

Test Methods:

- should_Store_Order_Successfully

```

```
Test Class: com.yieldBroker.service.OrderServiceImplTest

Purpose: Service Methods - interaction/transaction with H2 Database (In-Memory)

Comments: H2 is an in-Memory and initialized/destroyed per-test execution

Test Methods:

- test_Store_Order
- test_Cancel_Order
- test_Get_Order_Book

```

```
Test Class: com.yieldBroker.controller.OrderBookControllerTest

Purpose: Controller Methods - interaction/transaction with Order Book Service

Comments: H2 is an in-Memory and initialized/destroyed per-test execution

Test Methods:

- shouldBeEmpty
- shouldBeBuyOrderAfterPlaceOrder
- shouldBeEmptyAfterPlaceAndCancel
- test_Place_Order
- test_Cancel_Order
- test_Get_Order_Book

```


### Break down into end to end tests


```
Test Class: com.yieldBroker.OrderBookControllerIntegrationTest

Test Methods:

- test_Place_Order_Successfully
- test_Place_Order_With_Bad_Request
- test_Cancel_Order_Successfully
- test_Get_OrderBook_Successfully_And_Verify_Content

```

# Design Improvements:

```
- CQRS Seggregation of Order Handling and Order Query

- Reactive Java and Spring-5 reactive library can be used to query/store in reactive-way

- Controller to make use of Mono and Flux

- Event Driven Micro-Services where Orders are managed via events

- A Kafka layer acting as message broker/message bus to store events

- Specific partitions on kafka for placeOrder and cancelOrder

- A special in-memory View to be constructed/updated upon receiving order events

  - In Memory view listens to Order Events and update the OrderBook Tree/Graph Structure

  - OrderBook Query MicroService to work on querying this view

  - Scalable to web-scale with no impact on Database (H2)

```

# API Gateway , Service Management & Latency Improvement
```
- Zuul-2.0 , OAuth-2.0 & JWT , Eureka-Client, Routing for Building API-Gateway
- Eureka-Server to register microservices
- Inter MicroServices communication on Google ProtoBuf to reduce latency
- can Use gRPC for micro-Service communication on Protobuf dataformat
```

# DevOps Improvements:

```
 - Add Docker configuration
 - enable/configure Piplelines for Continuous Build, Delivery & Deployment
 - Use AWS - RDS (Relational Database As a Service) for scalable feature
 - Push Docker image to AWS and run from AWS
```

