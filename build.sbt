name := "unicorn"

version := "1.0"

// scalaVersion := "2.11.8"

scalaVersion := "2.10.4"

scalacOptions += "-deprecation"


// val sparkVersion = "2.2.0-SNAPSHOT"
val sparkVersion = "2.0.2"

resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/",
  "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven",
  "Will's bintray" at "https://dl.bintray.com/willb/maven/"
 // "commons-beanutils" at "https://mvnrepository.com/artifact/commons-beanutils/commons-beanutils-core"
)

// retrieveManaged := true

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.0.0-preview",
 // "commons-beanutils" %% "commons-beanutils-core" % "1.8.0"
  "dibbhatt" % "kafka-spark-consumer" % "1.0.8"
// "org.apache.spark" % "spark-sql-kafka-0-10_2.10" % sparkVersion,
// "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.0.0-preview"
)

