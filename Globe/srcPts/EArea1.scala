/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** A first level area of the Earth, a large area such as North West Europe. */
abstract class EArea1(val name: String, val cen: LatLong) extends GeographicSymbolKey
{ def neighbs: RArr[EArea1] = RArr()
  def a2Arr: RArr[EArea2]
  def disp2(eg: EarthGuiOld): GraphicElems = a2Arr.flatMap(_.display(eg))
  val places: RArr[LocationLL] = RArr()
}

class LocationLL(latMilliSecs: Double, longMilliSecs: Double, nameIn: String) extends LatLongPair[String](latMilliSecs, longMilliSecs, nameIn)
{
  def name: String = a2
}

object LocationLL
{
  def apply(name: String, latDegs: Double, longDegs: Double): LocationLL = new LocationLL(latDegs.degsToMilliSecs, longDegs.degsToMilliSecs, name)
}