/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pfaclist

class FacCalc(val listLen: Int, val factions: RArr[Faction])
{
  val numFactions: Int = factions.length
  val numVote: Int = factions.sumBy(_.weight)
  
  def assign1(facStates: IntArr): (Faction, IntArr) =
  { val state1: IntArr = facStates.iMap{(i, v0) => v0 + factions(i).weight }
    val indMax: Int = state1.indexOfMax
    state1.mutateElemUnsafe(indMax, _ - numVote)
    (factions(indMax), state1)
  }

  def run: RPairArr[Int, String] =
  {
    var facState: IntArr = factions.map(_ => 0)
    val res = RPairBuff[Int, String]()
    res.toArr
  }
}

case class Faction(name: String, weight: Int)


object Faction
{
  def apply(weights: IntArr): RArr[Faction] =  weights.iMap{ (i1, w) => Faction(('A' + i1).toString, w) }

}