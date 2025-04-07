/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pfaclist

class FacCalc(val listLen: Int, val factions: RArr[Faction])
{
  def run: RPairArr[Int, String] =
  {
    var facState: IntArr = factions.map(_ => 0)
    val res = RPairBuff[Int, String]()
    ???
  }
}

case class Faction(name: String, weight: Int)

object Faction
{
  def apply(weights: IntArr): RArr[Faction] =  weights.iMap{ (i1, w) => Faction(('A' + i1).toString, w) }

}