#!/usr/bin/env bash

mkdir msa
cd msa

spring init \
--boot-version=2.5.6 \
--build=gradle \
--java-version=11 \
--packaging=jar \
--name=product-service \
--package-name=com.msa.core.product \
--groupId=com.msa.core.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-service

spring init \
--boot-version=2.5.6 \
--build=gradle \
--java-version=11 \
--packaging=jar \
--name=review-service \
--package-name=com.msa.core.review \
--groupId=com.msa.core.review \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
review-service

spring init \
--boot-version=2.5.6 \
--build=gradle \
--java-version=11 \
--packaging=jar \
--name=recommendation-service \
--package-name=com.msa.core.recommendation \
--groupId=com.msa.core.recommendation \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
recommendation-service

spring init \
--boot-version=2.5.6 \
--build=gradle \
--java-version=11 \
--packaging=jar \
--name=product-composite-service \
--package-name=com.msa.composite.product \
--groupId=com.msa.composite.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-composite-service

cd ..