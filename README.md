
all api neet bearer token
for get token you should login with this api and use this response as bearer token 


curl --location --request POST 'localhost:8080/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"admin",
    "password":"admin"
}'
