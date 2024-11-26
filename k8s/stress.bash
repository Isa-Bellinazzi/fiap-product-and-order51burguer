#!/bin/bash

url="http://localhost:8080/swagger-ui/index.html"

for i in {1..10000}
do
    curl -s $url > /dev/null
    sleep 0.1
done
