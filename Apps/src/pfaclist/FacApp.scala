/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pfaclist

object FacApp
{
  def main(args: Array[String]): Unit =
  {
    val factions = Faction(5, 4, 3, 2)
    val fc = new FacCalc(factions)
    val res = fc.assignN(12)
    val lines = res.iMap{ (i, pair) =>
      val acc = pair.a2.iMap{(i, votes) => factions(i).name + ": " + votes }.mkStr("; ")
      (i + 1).str -- pair.a1.toString + ": " + acc
    }
    val output = lines.mkStr("\n")
    println(factions.weightsStr)
    println(output)
  }
}