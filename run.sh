#!/bin/bash
docker build --no-cache -t t1-services:latest .
docker-compose -f ./docker/docker-compose.yml down --volumes
docker rm -f t1-supplier-db
docker rm -f t1-supplier-app
docker rm -f t1-customer-app
docker-compose -f ./docker/docker-compose.yml build --no-cache
docker-compose -f ./docker/docker-compose.yml up --remove-orphans