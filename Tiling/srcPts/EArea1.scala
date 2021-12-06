/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** A first level area of the Earth, a large area such as North West Europe. */
abstract class EArea1(val name: String, val cen: LatLong) extends GeographicSymbolKey
{ def neighbs: Arr[EArea1] = Arr()
  def a2Arr: Arr[EArea2]
  def disp2(eg: EarthGuiOld): GraphicElems = a2Arr.flatMap(_.display(eg))
}