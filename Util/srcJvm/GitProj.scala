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
    |SbtDir/""".stripMargin

  def apply(location: String): Unit =
  {
    fileWrite(location,".gitignore", ignoreStr)
  }
}
