/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

class HGridRegSimple(val yTileMin: Int, val yTileMax: Int, val cTileMin: Int, val cTileMax: Int) extends HGrid
{
  def numOfRow2s: Int = ???
  def numOfRow0s: Int = ???
  def cCen: Double = (cTileMin + cTileMax) / 2.0
}

object HGridRegSimple
{
  def apply(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int): HGridRegSimple =
  {
    val yMin = yTileMin.roundUpToEven
    val yMax = yTileMax.roundDownToEven
    val cMin = cTileMin.roundUpToEven
    val cMax = cTileMax.roundDownToEven
    new HGridRegSimple(yMin, yMax, cMin, cMax)
  }
}