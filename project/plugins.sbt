resolvers += "Flyway" at "https://flywaydb.org/repo"

addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")

addSbtPlugin("com.github.tototoshi" % "sbt-slick-codegen" % "1.4.0")

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.15"
