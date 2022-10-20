## run docker file
```docker-compose -f ./docker/docker-compose.yml  up ```

##start mock-server 

``` nodejs ./mock-server/index.js```

##start ecommerce app, rum Application

##send request

```curl --location --request POST 'http://localhost:8080/checkout' \
--header 'Content-Type: application/json' \
--data-raw '{       "customerId":2,
"order":{
    "items":[{"name":"item","id":1},{"name":"item","id":2}]}} '```
