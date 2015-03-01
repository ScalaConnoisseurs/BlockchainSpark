package persistence.bitcoin

import com.datastax.driver.core.{ProtocolOptions, Cluster}
import com.typesafe.config.ConfigFactory
import scala.util.Try
import com.datastax.driver.core.Cluster
import com.datastax.driver.core.ProtocolOptions

trait CassandraCluster {
  def cluster: Cluster
}

trait CassandraConfig extends CassandraCluster {

  val config = ConfigFactory.load()
  lazy val host = Try(config.getString("cassandra-db.host")).getOrElse("127.0.0.1")
  lazy val port = Try(config.getInt("cassandra-db.port")).getOrElse(9042)
  lazy val cassandraPersist = Try(config.getBoolean("cassandra-db.persist")).getOrElse(false)

  lazy val cluster: Cluster =
      Cluster.builder().
        addContactPoints(host).
        withCompression(ProtocolOptions.Compression.SNAPPY).
        withPort(port).
        build()
}
