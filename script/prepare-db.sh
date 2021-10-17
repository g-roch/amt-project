#!/bin/bash

FILE=application/src/main/resources/application.properties

sed -i /^spring.datasource/d $FILE

echo "spring.datasource.url=$SQL_URL" >> $FILE
echo "spring.datasource.username=$SQL_USER" >> $FILE
echo "spring.datasource.password=$SQL_PASSWORD" >> $FILE
echo "spring.datasource.driver-class-name=$SQL_DRIVER" >> $FILE
