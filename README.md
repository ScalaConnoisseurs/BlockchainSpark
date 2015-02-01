# PoliceSpark

Large scale data processing of police crime statistics.

## Setup

1. Install Java.
2. Install SBT - http://www.scala-sbt.org/download.html
3. Install an IDE (eg. IntelliJ IDEA with Scala plugin).

## Build

1. Clone the repository.
2. cd PoliceSpark
3. sbt compile

## Deployment

We are utilising Heroku platform as a service. Changes pushed to master are automatically deployed so please make sure tests are green before merging.

Heroku app configuration: https://dashboard.heroku.com/apps/policespark

Production URL: http://policespark.herokuapp.com/

## Police API
http://data.police.uk/api/crimes-at-location?date=2012-02&lat=52.629729&lng=-1.131592
