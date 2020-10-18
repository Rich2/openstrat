/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

class HGridReg protected(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int) extends HGridRegSimple(yTileMin, yTileMax, cTileMin, cTileMax)
{

}

object HGridReg
{
  def apply(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int): HGridReg =
  {
    val yMin = yTileMin.roundUpToEven
    val yMax = yTileMax.roundDownToEven
    val cMin = cTileMin.roundUpToEven
    val cMax = cTileMax.roundDownToEven
    new HGridReg(yMin, yMax, cMin, cMax)
  }
}
