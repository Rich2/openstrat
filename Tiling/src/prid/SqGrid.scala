/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

import scala.reflect.ClassTag

class SqGrid(val rTileMin: Int, val rTileMax: Int, val cTileMin: Int, val cTileMax: Int) extends TGrid
{
  /** Number of rows of tiles. */
  override def numOfTileRows: Int = (rTileMax - rTileMin + 2).atLeast0 / 2

  /** The number of tiles in each tile row. */
  def tileRowLen: Int = (cTileMax - cTileMin + 2).atLeast0 / 2

  /** The total number of Tiles in the tile Grid. */
  override def numOfTiles: Int = numOfTileRows * tileRowLen

  override def xRatio: Double = 1
  override def xCen: Double = (cTileMin + cTileMax) / 2
  override def width: Double = ???
  override def height: Double = ???

  def sideLines: LineSegs = ???

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newTileArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): SqArrOpt[A] = new SqArrOpt(new Array[A](numOfTiles))

}

/** Companion object for the HGridReg class. Contains an applr method that corrects the r and Y minimum and maximum values. */
object SqGrid
{
  /** Corrects the X and Y minimum and maximum values. */
  def apply(rTileMin: Int, rTileMax: Int, cTileMin: Int, cTileMax: Int): SqGrid =
  {
    val rMin = rTileMin.roundUpToEven
    val rMax = rTileMax.roundDownToEven
    val cMin = cTileMin.roundUpToEven
    val cMax = cTileMin.roundDownToEven

    new SqGrid(rMin, rMax, cMin, cMax)
  }
}