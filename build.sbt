// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.24" // your current series x.y

ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

// publish website from this branch
//ThisBuild / tlSitePublishBranch := Some("main")

val Scala213 = "2.13.16"
ThisBuild / crossScalaVersions := Seq("2.12.20", Scala213, "3.3.6")
ThisBuild / scalaVersion := Scala213 // the default Scala

// Jetty 12+, for testing, requires Java 17
ThisBuild / githubWorkflowJavaVersions --= List(JavaSpec.temurin("8"), JavaSpec.temurin("11"))
ThisBuild / tlJdkRelease := Some(8)
ThisBuild / startYear := Some(2013)

lazy val root = tlCrossRootProject.aggregate(servlet4, servlet5, servlet6, examplesServlet4, examplesServlet5, examplesServlet6)

val asyncHttpClientVersion = "2.12.3"
val jettyVersion = "12.0.22"
val http4sVersion = "0.22.15"
val munitCatsEffectVersion = "1.0.7"
val disciplineMunitVersion = "1.0.9"
val servletApi6Version = "6.0.0"
val servletApi5Version = "5.0.0"
val servletApi4Version = "4.0.4"

val catsEffectVersion = "2.5.5"
val scalacheckVersion = "1.15.4"

val scalacheckEffectVersion = "1.0.3"

lazy val servlet4 = project
  .in(file("servlet4"))
  .settings(
    name := "http4s-servlet4",
    description := "Portable servlet implementation for http4s servers",
    libraryDependencies ++= Seq(
      "jakarta.servlet" % "jakarta.servlet-api" % servletApi4Version % Provided,
      "org.eclipse.jetty" % "jetty-client" % jettyVersion % Test,
      "org.eclipse.jetty" % "jetty-server" % jettyVersion % Test,
      "org.eclipse.jetty.ee8" % "jetty-ee8-servlet" % jettyVersion % Test,
      "org.http4s" %% "http4s-dsl" % http4sVersion % Test,
      "org.http4s" %% "http4s-server" % http4sVersion,
      "org.typelevel" %% "munit-cats-effect-2" % munitCatsEffectVersion % Test,
      "org.asynchttpclient" % "async-http-client" % asyncHttpClientVersion % Test,
    ),
  )
  .dependsOn(testing % "test->test")

lazy val servlet5 = project
  .in(file("servlet5"))
  .settings(
    name := "http4s-servlet5",
    description := "Portable servlet implementation for http4s servers",
    libraryDependencies ++= Seq(
      "jakarta.servlet" % "jakarta.servlet-api" % servletApi6Version % Provided,
      "org.eclipse.jetty" % "jetty-client" % jettyVersion % Test,
      "org.eclipse.jetty" % "jetty-server" % jettyVersion % Test,
      "org.eclipse.jetty.ee9" % "jetty-ee9-servlet" % jettyVersion % Test,
      "org.http4s" %% "http4s-dsl" % http4sVersion % Test,
      "org.http4s" %% "http4s-server" % http4sVersion,
      "org.typelevel" %% "munit-cats-effect-2" % munitCatsEffectVersion % Test,
      "org.asynchttpclient" % "async-http-client" % asyncHttpClientVersion % Test,
    ),
  )
  .dependsOn(testing % "test->test")

lazy val servlet6 = project
  .in(file("servlet6"))
  .settings(
    name := "http4s-servlet6",
    description := "Portable servlet implementation for http4s servers",
    libraryDependencies ++= Seq(
      "jakarta.servlet" % "jakarta.servlet-api" % servletApi6Version % Provided,
      "org.eclipse.jetty" % "jetty-client" % jettyVersion % Test,
      "org.eclipse.jetty" % "jetty-server" % jettyVersion % Test,
      "org.eclipse.jetty.ee10" % "jetty-ee10-servlet" % jettyVersion % Test,
      "org.http4s" %% "http4s-dsl" % http4sVersion % Test,
      "org.http4s" %% "http4s-server" % http4sVersion,
      "org.typelevel" %% "munit-cats-effect-2" % munitCatsEffectVersion % Test,
      "org.asynchttpclient" % "async-http-client" % asyncHttpClientVersion % Test,
    ),
  )
  .dependsOn(testing % "test->test")

lazy val examplesServlet4 = project
  .in(file("examples-servlet4"))
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(JettyPlugin)
  .settings(
    name := "http4s-servlet-examples-servlet4",
    description := "Examples for http4s-servlet4",
    startYear := Some(2013),
    fork := true,
    Jetty / containerLibs := List("org.eclipse.jetty.ee8" % "jetty-ee8-runner" % jettyVersion),
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "jakarta.servlet" % "jakarta.servlet-api" % servletApi4Version % Provided
    ),
  )
  .dependsOn(servlet4)

lazy val examplesServlet5 = project
  .in(file("examples-servlet5"))
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(JettyPlugin)
  .settings(
    name := "http4s-servlet-examples-servlet5",
    description := "Examples for http4s-servlet5",
    startYear := Some(2013),
    fork := true,
    Jetty / containerLibs := List("org.eclipse.jetty.ee9" % "jetty-ee9-runner" % jettyVersion),
    libraryDependencies ++= Seq(
      "jakarta.servlet" % "jakarta.servlet-api" % servletApi5Version % Provided
    ),
  )
  .dependsOn(servlet5)

lazy val examplesServlet6 = project
  .in(file("examples-servlet6"))
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(JettyPlugin)
  .settings(
    name := "http4s-servlet-examples-servlet6",
    description := "Examples for http4s-servlet6",
    startYear := Some(2013),
    fork := true,
    Jetty / containerLibs := List("org.eclipse.jetty.ee10" % "jetty-ee10-runner" % jettyVersion),
    libraryDependencies ++= Seq(
      "jakarta.servlet" % "jakarta.servlet-api" % servletApi6Version % Provided
    ),
  )
  .dependsOn(servlet6)

lazy val examples = project
  .in(file("examples"))
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(JettyPlugin)
  .settings(
    name := "http4s-servlet-examples",
    description := "Examples for http4s-servlet",
    startYear := Some(2013),
    fork := true,
    Jetty / containerLibs := List("org.eclipse.jetty.ee10" % "jetty-ee10-runner" % jettyVersion),
    libraryDependencies ++= Seq(
      "jakarta.servlet" % "jakarta.servlet-api" % servletApi6Version % Provided
    ),
  )
  .dependsOn(servlet6)

lazy val testing = project
  .in(file("testing"))
  .enablePlugins(NoPublishPlugin)
  .settings(
    name := "http4s-testing",
    description := "Internal utilities for http4s tests",
    startYear := Some(2016),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect-laws" % catsEffectVersion,
      "org.scalacheck" %% "scalacheck" % scalacheckVersion,
      "org.typelevel" %% "munit-cats-effect-2" % munitCatsEffectVersion,
      "org.typelevel" %% "discipline-munit" % disciplineMunitVersion,
      "org.typelevel" %% "scalacheck-effect" % scalacheckEffectVersion,
      "org.typelevel" %% "scalacheck-effect-munit" % scalacheckEffectVersion,
    ).map(_ % Test) ++ Seq("org.http4s" %% "http4s-dsl" % http4sVersion % Test),
  )

lazy val docs = project.in(file("site")).enablePlugins(Http4sOrgSitePlugin)
