package org.dataottam.unicorn.datareader

/**
  * Created by ramyasanthanam on 26/01/17.
  */

import org.apache.spark
import java.util.HashMap

import org.apache.spark.sql.{Row, SparkSession, SQLContext}
// import org.elasticsearch.spark.rdd.EsSpark
import org.apache.spark.streaming.kafka._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

/**
  * Created by Raghul on 22/01/17.
  */
object kafkaRead {
  def main(args: Array[String]) {


    val sparkcluster = SparkSession
      .builder()
      .master("local[*]")
      .appName("kafka-source-reader")
      .getOrCreate()

    import sparkcluster.implicits._

    val topic = "R3_MERGE"
    val brokerPath = "kafka.bootstrap.servers"
    val source="kafka"

    val inputstream = sparkcluster.readStream
      .format("kafka")
      .option("subscribe", topic)
      .option(brokerPath, "localhost:9092")
      .load()

    val keyValueString = inputstream.selectExpr("CAST(key AS STRING)", "CAST( value AS STRING)").as[(String, String)]

    val wordCounts = keyValueString.map(_._2.split(" ")).groupBy("value").count()

    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    println("running")

    query.awaitTermination()
  }
}

