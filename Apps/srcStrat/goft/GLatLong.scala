/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package goft
import geom._, pglobe._

/** LatLong for Game of Thrones world. */
case class GLatLong(latMilliSecs: Double, longMilliSecs: Double)// extends LatLongBase
{
 def equatorialRadius: Length = ???

 def polarRadius: Length = ???
}