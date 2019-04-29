scalaVersion := "2.12.8"

val dbUser = System.getProperty("db.user", "test")
val dbPass = System.getProperty("db.pass", "test")
val dbUrl = System.getProperty("db.url", "jdbc:mysql://localhost:3306/test")

enablePlugins(FlywayPlugin)
flywayUrl := dbUrl
flywayUser := dbUser
flywayPassword := dbPass

enablePlugins(CodegenPlugin)
slickCodegenDatabaseUrl := dbUrl
slickCodegenDatabaseUser := dbUser
slickCodegenDatabasePassword := dbPass
slickCodegenDriver := slick.jdbc.MySQLProfile
slickCodegenJdbcDriver := "com.mysql.cj.jdbc.Driver"
slickCodegenOutputPackage := "org.opal.db.model"
slickCodegenOutputDir := file("src/main/scala")

lazy val core = (project in file("."))
  .settings(
    organization := "org.opal",
    name := "db-model",
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.3.0",
      "mysql" % "mysql-connector-java" % "8.0.15"
    )
  )



