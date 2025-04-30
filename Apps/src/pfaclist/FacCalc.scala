/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pfaclist

/** Not usre what this does. */
class FacCalc(val factions: RArr[Faction], val listLen: Int = 10)
{ val numFactions: Int = factions.length
  val numVote: Int = factions.sumBy(_.weight)
  val facStateInit: IntArr = IntArr.uninitialised(numFactions)

  def assign1Init: RPairElem[Faction, IntArr] = assign1(facStateInit)
  
  def assign1(facStates: IntArr): RPairElem[Faction, IntArr] =
  { val state1: IntArr = facStates.iMap{(i, v0) => v0 + factions(i).weight }
    val indMax: Int = state1.indexOfMax
    state1.mutateElemUnsafe(indMax, _ - numVote)
    RPairElem(factions(indMax), state1)
  }
  
  def assignN(num: Int): RPairArr[Faction, IntArr] =
  { val num2 = num.max0
    var i = 0
    var state = facStateInit
    iRepeatMap(num2){
      val newRes = assign1(state)
      state = newRes.a2
      newRes
    }
  }
}

case class Faction(name: String, weight: Int)
{ override def toString: String = "Faction" + name
}

object Faction
{ def apply(weights: IntArr): RArr[Faction] =  weights.iMap{ (i1, w) => Faction(String.valueOf(('A' + i1).toChar), w) }
  def apply(weights: Int *): RArr[Faction] = apply(weights.toArr)

  extension(thisArr: Arr[Faction])
  { def weightsStr =
    {  val weights = thisArr.map(fac => fac.name + ": " + fac.weight).mkStr("; ")
      "Vote weighting" -- weights + "; Total Votes: " + thisArr.sumBy(_.weight)
    }
  }
}