package org.dataottam.unicorn.datareader

/**
  * Created by Raghul on 25/01/17.
  */
/**
    The following program can be compiled and run using SBT
        Wrapper scripts have been provided with this
    The following script can be run to compile the code
    ./compile.sh
The following script can be used to run this application in Spark. The
second command line argument of value 1 is very important. This is to flag
the shipping of the kafka jar files to the Spark cluster
./submit.sh com.dataottam.unicorn.datareader 1
  **/

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._


object StreamingApps {
  def main(args: Array[String]) {

    // Log level settings
   // LoggingSettings.setLogLevels()

    // Variables used for creating the Kafka stream
    //The quorum of Zookeeper hosts
    val zooKeeperQuorum = "localhost"

    // Message group name
    val messageGroup = "sfb-consumer-group"

    //Kafka topics list separated by coma if there are multiple topics to be listened on
    val topics = "R3_MERGE"

    //Number of threads per topic
    val numThreads = 1

    // Create the Spark Session and the spark context
    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName(getClass.getSimpleName)
      .getOrCreate()

    // Get the Spark context from the Spark session for creating the streaming context
    val sc = spark.sparkContext

    // Create the streaming context
    val ssc = new StreamingContext(sc, Seconds(10))

    // Set the check point directory for saving the data to recover when there is a crash
      ssc.checkpoint("/tmp")

    // Create the map of topic names
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

    // Create the Kafka stream
    val appLogLines = KafkaUtils.createStream(ssc, zooKeeperQuorum, messageGroup, topicMap).map(_._2)

    // Count each log messge line containing the word ERROR
    val errorLines = appLogLines.filter(line => line.contains("ERROR"))

    // Print the line containing the error
    errorLines.print()

    // Count the number of messages by the windows and print them
    errorLines.countByWindow(Seconds(30), Seconds(10)).print()

    // Start the streaming
    ssc.start()

    // Wait till the application is terminated
    ssc.awaitTermination()
  }
}