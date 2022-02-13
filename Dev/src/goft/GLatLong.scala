/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package goft
import geom._, pglobe._

case class GLatLong(latMilliSecs: Double, longMilliSecs: Double) extends LatLongBase
{
  override def equatorialRadius: Length = ???

  override def polarRadius: Length = ???
}