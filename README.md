# BlockchainSpark

Large scale data processing and analysis of blockchains.

## Setup

1. Install Java.
2. Install SBT - http://www.scala-sbt.org/download.html
3. Install an IDE (eg. IntelliJ IDEA with Scala plugin).

## Build

1. Clone the repository.
2. cd BlockchainSpark
3. sbt compile

## Deployment

We are utilising Heroku platform as a service. Changes pushed to master are automatically deployed so please make sure tests are green before merging.

Heroku app configuration: https://dashboard.heroku.com/apps/blockchainspark

Production URL: http://blockchainspark.herokuapp.com/

## Cassandra
1. Download and install Cassandra
2. Download Datastax Dev center (something like TOAD for Cassandra)
    Or use CQL shell
3. Start Cassandra with CASSANDRA_HOME/bin/cassandra.bat (on windows)

4. Use the CQL statements from cassandra-scripts.cql under resources to
  - create key space 'bitcoin_spark' (equivalent to oracle schema)
  - create table (column family) 'UnconfirmedTransaction'

5. To see the persisted records:

- CQL shell
  - start cql shell CASSANDRA_HOME/bin/cqlsh.bat (on windows)
  - cql>use bitcoin_spark;
  - cql:bitcoin_spark> select * from UnconfirmedTransaction;

- DataStax Dev Centre:
  - Create new connection for running cassandra;
  - select connection and keyspace
  - execute cql: select * from UnconfirmedTransaction;
