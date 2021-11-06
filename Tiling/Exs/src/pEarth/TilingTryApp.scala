/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** Scrap pad console app for the Tiling module. */
object TilingTryApp  extends App
{
  val l2 = 5.east
  val long = Longitude.degs(20)
  val l3 = l2 + long
  debvar(l3)
  val p1 = SaharaWest.polygonLL
  debvar(p1)

  val p2 = p1.addLong(long)
  debvar(p2)


}
