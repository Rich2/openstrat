/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._

/** A first level area of the world, a large area such as North West Europe. Still not sure about the name. */
abstract class WldArea1(val symName: String, val cen: LatLong) extends GeographicSymbolKey
{ def neighbs: Arr[WldArea1] = Arr()
  def a2Arr: Arr[WldArea2]
  def fill: Boolean = true
  def disp2(eg: EarthGuiOld): GraphicElems = a2Arr.flatMap(_.display(eg, fill))
}
