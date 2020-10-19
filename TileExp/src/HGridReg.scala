/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import reflect.ClassTag

class HGridReg(val yTileMin: Int, val yTileMax: Int, val cTileMin: Int, val cTileMax: Int) extends HGrid
{
  /** Gives the index into an Arr / Array of Tile data from its tile Roord. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(hc: HCen): Int = arrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile Roord. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */

  @inline def arrIndex(y: Int, c: Int): Int =
  {
    val thisRow: Int = y %% 4 match
    { case 2 => (c - cRow2sMin) / 4
      case 0 => (c - cRow0sMin) / 4
    }
    val y2s: Int = ((y - yRow2sMin).divRoundUp(4) * row2sTileLen).atMost0
    val y0s: Int = ((y - yRow0sMin).divRoundUp(4) * row0sTileLen).atMost0
    y0s + y2s + thisRow
  }
  /** Minimum c for Rows where y.Div4Rem2. */
  def cRow2sMin: Int = cTileMin.roundUpTo(_.div4Rem2)

  /** Maximum c for Rows where y.Div4Rem2. */
  def cRow2sMax: Int = cTileMax.roundDownTo(_.div4Rem2)

  def row2sTileLen = ((cRow2sMax - cRow2sMin + 4) / 4).max(0)

  /** Minimum c for Rows where y.Div4Rem0. */
  def cRow0sMin: Int = cTileMin.roundUpTo(_.div4Rem0)

  /** Maximum x for Rows where y.Div4Rem0. */
  def cRow0sMax: Int = cTileMax.roundDownTo(_.div4Rem0)

  def row0sTileLen = ((cRow0sMax - cRow0sMin + 4) / 4).max(0)
  def yRow2sMin: Int = yTileMin.roundUpTo(_.div4Rem2)
  def yRow2sMax: Int = yTileMax.roundDownTo(_.div4Rem2)
  /** Number of Rows where y.Div4Rem2. */
  def numOfRow2s: Int = ((yRow2sMax - yRow2sMin + 4) / 4).max(0)

  def yRow0sMin: Int = yTileMin.roundUpTo(_.div4Rem0)
  def yRow0sMax: Int = yTileMax.roundDownTo(_.div4Rem0)
  /** The y coordinate of the bottom row of this grid divided by 4 leaves remainder of 2. */
  def bottomRowIs2: Boolean = yTileMin.div4Rem2

  /** The y coordinate of the bottom row of this grid divided by 4 leaves remainder of 0. */
  def bottomRowIs0: Boolean = yTileMin.div4Rem0

  /** The y coordinate of the top row of this grid divided by 4 leaves remainder of 2. */
  def topRowIs2: Boolean = yTileMin.div4Rem2

  /** The y coordinate of the top row of this grid divided by 4 leaves remainder of 0. */
  def topRowIs0: Boolean = yTileMin.div4Rem0

  /** Number of Rows where y.Div4Rem0. */
  def numOfRow0s: Int = ((yRow0sMax - yRow0sMin + 4) / 4).max(0)

  override def numOfTiles: Int = numOfRow2s * row2sTileLen + numOfRow0s * row0sTileLen
  def cCen: Double = (cTileMin + cTileMax) / 2.0
  /** New Tile immutable Tile Arr of Opt data values. */
  final def newHexArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): HexArrOpt[A] = new HexArrOpt(new Array[A](numOfTiles))
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