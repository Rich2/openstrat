/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utiljvm._
object UtilExsJvmApp extends App
{ deb("Starting ExsJvmApp")
  val circ: geom.Circle = geom.Circle(10)
  debvar(circ)
  fileWrite("/ldat/Temp", "Util.pom", new OpenStratPom("rutil", "0.3.2").out())
}