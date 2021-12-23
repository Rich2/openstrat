/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

object GitProj
{
  val ignoreStr =
  """project/project/
    |target/
    |DocProj/
    |DevData/
    |bin/
    |out/
    |SbtDir/
    |.*/""".stripMargin

  def sbtStr(projName: String): String =
    s"""
      |name := "$projName"
      |scalaVersion := "3.1.0"
      |Compile/scalaSource := baseDirectory.value / "src"
      |scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8")
      |""".stripMargin

  def mainStr(projName: String) =
  s"""
      |object ${projName}App extends App{
      |println("Hello from $projName")
      |}
      |""".stripMargin

  def apply(path: String, projName: String): Unit =
  {
    val fullPath = path / projName
    fileWrite(fullPath,".gitignore", ignoreStr)
    fileWrite(fullPath,projName + ".sbt", sbtStr(projName))
    fileWrite(fullPath / "src",projName + "App.scala", mainStr(projName))
  }
}
