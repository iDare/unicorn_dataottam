package org.dataottam.unicorn.executor

/**
  * Created by ramyasanthanam on 26/01/17.
  */

import org.dataottam.unicorn.kafka._

object ConsumerApp extends App {


  val topic = "R3_MERGE"
  val groupId = "demo-topic-consumer"

  val consumer = new KafkaConsumer(topic, groupId, "localhost:2181")

  while (true) {
    consumer.read() match {
      case Some(message) =>
        println("Getting message.......................  " + message)
        // wait for 100 milli second for another read
        Thread.sleep(100)
      case None =>
        println("Queue is empty.......................  ")
        // wait for 2 second
        Thread.sleep(2 * 1000)
    }

  }

}
