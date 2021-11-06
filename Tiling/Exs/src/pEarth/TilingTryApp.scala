/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** Scrap pad console app for the Tiling module. */
object TilingTryApp  extends App
{
  val p1 = SaharaWest.polygonLL
  debvar(p1)
  val long = Longitude.degs(20)
  val p2 = p1.llLongAdd(long)
  debvar(p2)
}
