--create keyspace
create keyspace if not exists bitcoin_spark with  replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

-- Work in progress, as of now only persisting following 4 attributes

CREATE TABLE UnconfirmedTransaction (
  id uuid,
  version int,
  size int,
  hash text,
  PRIMARY KEY (id)
);