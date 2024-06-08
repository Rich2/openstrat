/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._, Colour._

object EarthTryApp extends App
{
  deb("Hello Earth App")
  val ll1 = LatLong.degs(85, 5)
  val mm1: PtM3 = ll1.toMetres3
  debvar(mm1.kmStr)
  val ll2 = LatLong.degs(85, 175)
  val mm2: PtM3 = ll2.toMetres3
  debvar(mm2.kmStr)
  val mm3 = ll2.fromFocusMetres(LatLong.degs(10, 0))
  debvar(mm3.kmStr)
  val mm4 = ll2.fromFocusMetres(LatLong.degs(40, 0))
  debvar(mm4.kmStr)
  val mm5 = ll2.fromFocusMetres(LatLong.degs(50, 0))
  debvar(mm5.kmStr)
  val mm6 = ll2.toMetres3.rotateX(-44.degsVec)
  debvar(mm6.kmStr)
}