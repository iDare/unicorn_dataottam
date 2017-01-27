package org.dataottam.unicorn.executor

/**
  * Created by ramyasanthanam on 26/01/17.
  */
import org.dataottam.unicorn.kafka._

object ProducerApp extends App {

  val topic = "R3_MERGE"

  val producer = new KafkaProducer("localhost:9092")

  val batchSize = 100

  (1 to 1000000).toList.map(no => "Message " + no).grouped(batchSize).foreach { message =>
    println("Sending message batch size " + message.length)
    producer.send(topic, message)
  }

}