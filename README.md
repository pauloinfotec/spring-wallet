# Wallet Microservice
A simple wallet microservice running on the JVM that manages users' money.
This service will support operations for creating, depositing, withdrawing and list transactions of a specific user.

## Description
A simple Rest API to access wallet accounts with the current balance of a user. The balance can be modified by registering transactions on the account, either debit transactions (removing funds) or credit transactions (adding funds).
A debit transaction will only succeed if there are sufficient funds on the wallet.
It is also possible to fetch the users wallets and get current balance.

## Implementation
Due to the short implementation time of this application (about 6h for implementation and 2h for documentation) a simple GenAI prompt in Chatgpt was used to drive development:
```
Implement a wallet microservice using spring boot, java 17 and postgres with controllers to make deposit, withdraw, list transaction for each user and to guarantee that all transaction will be atomics. Write also unit tests for each funcionality.
```

## API requirements and running instructions
1. Java 17
2. Maven 3 to build the application
3. Download and install PostgreSQL server (version 9.6 or higher). It is available here: https://www.postgresql.org/download/
4. Connect to the Postgres server and create database walletdb.
5. You will need to run some scripts so that it be able to test application:
   ```
   CREATE TABLE public.user
   (
    id bigserial NOT NULL,
    username character varying(128) NOT NULL,
    balance numeric(20,2) NOT NULL,
    CONSTRAINT uk_username UNIQUE (username),
    CONSTRAINT user_pkey PRIMARY KEY (id)
   );

   CREATE TABLE public.transaction
   (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    amount numeric(20,2) NOT NULL,
    type character varying(32) NOT NULL,
    date_time timestamp without time zone NOT NULL,
    CONSTRAINT transaction_pkey PRIMARY KEY (id)
   );

   ALTER TABLE public.transaction ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.user(id);
   ```
6. You can run the application executing this command from the root folder:
   ```
   mvn spring-boot:run
   ```
7. To check that application started successfully go to:
   ```
   http://localhost:8080/actuator/health
   ```
8. You will see this response:
   ```
   {
     "status": "UP"
   }
   ```
## Endpoints
You can see below request examples for all endpoints that can be imported on postman:
```
{
   "info":{
      "_postman_id":"d89d6ae6-77d8-4460-8fbc-d6ad6d359a78",
      "name":"Wallet",
      "schema":"https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
      "_exporter_id":"2960346"
   },
   "item":[
      {
         "name":"Create",
         "request":{
            "method":"POST",
            "header":[
               
            ],
            "body":{
               "mode":"raw",
               "raw":"",
               "options":{
                  "raw":{
                     "language":"json"
                  }
               }
            },
            "url":{
               "raw":"http://localhost:8080/api/wallet/create?username=paulo",
               "protocol":"http",
               "host":[
                  "localhost"
               ],
               "port":"8080",
               "path":[
                  "api",
                  "wallet",
                  "create"
               ],
               "query":[
                  {
                     "key":"username",
                     "value":"paulo"
                  }
               ]
            }
         },
         "response":[
            
         ]
      },
      {
         "name":"Deposit",
         "request":{
            "method":"POST",
            "header":[
               
            ],
            "body":{
               "mode":"raw",
               "raw":"",
               "options":{
                  "raw":{
                     "language":"json"
                  }
               }
            },
            "url":{
               "raw":"http://localhost:8080/api/wallet/deposit?username=paulo&amount=5",
               "protocol":"http",
               "host":[
                  "localhost"
               ],
               "port":"8080",
               "path":[
                  "api",
                  "wallet",
                  "deposit"
               ],
               "query":[
                  {
                     "key":"username",
                     "value":"paulo"
                  },
                  {
                     "key":"amount",
                     "value":"5"
                  }
               ]
            }
         },
         "response":[
            
         ]
      },
      {
         "name":"Withdraw",
         "request":{
            "method":"POST",
            "header":[
               
            ],
            "body":{
               "mode":"raw",
               "raw":"",
               "options":{
                  "raw":{
                     "language":"json"
                  }
               }
            },
            "url":{
               "raw":"http://localhost:8080/api/wallet/withdraw?username=paulo&amount=1",
               "protocol":"http",
               "host":[
                  "localhost"
               ],
               "port":"8080",
               "path":[
                  "api",
                  "wallet",
                  "withdraw"
               ],
               "query":[
                  {
                     "key":"username",
                     "value":"paulo"
                  },
                  {
                     "key":"amount",
                     "value":"1"
                  }
               ]
            }
         },
         "response":[
            
         ]
      },
      {
         "name":"Balance",
         "protocolProfileBehavior":{
            "disableBodyPruning":true
         },
         "request":{
            "method":"GET",
            "header":[
               
            ],
            "body":{
               "mode":"raw",
               "raw":"",
               "options":{
                  "raw":{
                     "language":"json"
                  }
               }
            },
            "url":{
               "raw":"http://localhost:8080/api/wallet/balance?username=paulo",
               "protocol":"http",
               "host":[
                  "localhost"
               ],
               "port":"8080",
               "path":[
                  "api",
                  "wallet",
                  "balance"
               ],
               "query":[
                  {
                     "key":"username",
                     "value":"paulo"
                  }
               ]
            }
         },
         "response":[
            
         ]
      },
      {
         "name":"Transactions",
         "protocolProfileBehavior":{
            "disableBodyPruning":true
         },
         "request":{
            "method":"GET",
            "header":[
               
            ],
            "body":{
               "mode":"raw",
               "raw":"",
               "options":{
                  "raw":{
                     "language":"json"
                  }
               }
            },
            "url":{
               "raw":"http://localhost:8080/api/wallet/transactions?username=paulo",
               "protocol":"http",
               "host":[
                  "localhost"
               ],
               "port":"8080",
               "path":[
                  "api",
                  "wallet",
                  "transactions"
               ],
               "query":[
                  {
                     "key":"username",
                     "value":"paulo"
                  }
               ]
            }
         },
         "response":[
            
         ]
      }
   ]
}
```
You can also access API Documentation with this URL:
```
http://localhost:8080/swagger-ui/index.html
```

## Technologies
- PostgreSQL database, which has good concurrency support, also has ACID compliance
- Spring Boot, including Spring Data JPA for JPA based repositories
- Logback with SLf4j for logging
- Lombok to make clean code
- Actuator so that we can make health checks (if you deploy application on Kubernetes cluster)
- OpenAPI dependency to help with API Documentation
- You can see in the root folder a file called *project.toml* that sets JVM version so that you can deploy this application on Google Cloud with following command:
  ```
  gcloud run deploy --source .
  ```
- This project also has a *Dockerfile* if you want to build an image and deploy to a Kubernetes cluster.

## Application aspects
1. Transactions on service and repository level ensure atomicity
2. Identifiers for entities and primary keys in the db ensure idempotency
3. Scalability: This can be solved by deploying wallet microservice application to a Kubernetes cluster.

## Features Implemented
1. Create a Wallet
2. Retrieve Balance
3. Deposit Funds
4. Withdraw Funds
5. Retrieve all transations of a specific user

## Features Not Implemented
1. Retrieve the balance of a user's wallet at a specific point in the past
2. Transfer money between user wallets
3. Security (can be implemented using Spring Security with JWT)
