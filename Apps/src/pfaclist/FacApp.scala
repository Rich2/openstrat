/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pfaclist

object FacApp
{
  def main(args: Array[String]): Unit =
  {
    val fc = new FacCalc(Faction(5, 4, 3))
    println(fc.assign1Init)
  }
}