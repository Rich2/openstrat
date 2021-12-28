/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, reflect.ClassTag

/** A grid of Squares. A regular rectangle of squares.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
final class SqGrid(val bottomCenRow: Int, val topCenRow: Int, val leftCenCol: Int, val rightCenCol: Int) extends TGrid
{
  /** Number of rows of tiles. */
  override def numTileRows: Int = (topCenRow - bottomCenRow + 2).max0 / 2

  /** The number of tiles in each tile row. */
  def tileRowLen: Int = (rightCenCol - leftCenCol + 2).max0 / 2

  /** The total number of Tiles in the tile Grid. */
  override def numTiles: Int = numTileRows * tileRowLen

  def rGridMin: Int = bottomCenRow - 1
  def rGridMax: Int = topCenRow + 1
  def cGridMin: Int = leftCenCol - 1
  def cGridMax: Int = rightCenCol + 1

  override def coordCen: SqCenOrSide = SqCenOrSide(rCen, cCen)
  override def yRatio: Double = 1
  override def yCen: Double = (bottomCenRow + topCenRow) / 2
  override def width: Double = cGridMax - cGridMin
  override def height: Double = rGridMax - rGridMin

  def horrSideLines: LineSegs = iToMap(rGridMin, rGridMax, 2){ r => LineSeg(cGridMin, r, cGridMax, r) }
  def vertSideLines: LineSegs = iToMap(cGridMin, cGridMax, 2){ c => LineSeg(c, rGridMin, c, rGridMax) }
  def sideLines: LineSegs = horrSideLines ++ vertSideLines


  /** Gives the index into an Arr / Array of Tile data from its tile [[SqCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(sc: SqCen): Int = arrIndex(sc.r, sc.c)

  @inline def arrIndex(r: Int, c: Int): Int = (r - bottomCenRow) / 2 * tileRowLen + (c - leftCenCol) / 2
  /** New immutable Arr of Tile data. */
  /*final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): SqcenArr[A] =
  { val res = HcenArr[A](numOfTiles)
    //res.mutSetAll(value)
    //res
    ???
  }*/

  /** New immutable Arr of Tile data. */
  final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): SqCenArr[A] =
  { val res = SqCenArr[A](numTiles)
    //res.mutSetAll(value)
    //res
    res
  }

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newTileArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): SqCenArrOpt[A] = new SqCenArrOpt(new Array[A](numTiles))

  def rowForeach(r: Int)(f: SqCen => Unit): Unit = iToForeach(leftCenCol, rightCenCol, 2)(c => f(SqCen(r, c)))

  def foreach(f: SqCen => Unit): Unit = foreachRow(rowForeach(_)(f))

  final def iForeach(f: (SqCen, Int) => Unit, startCount: Int = 0): Unit =
  { var count: Int = startCount
    foreachRow{r => count = rowIForeach(r, count)(f) }
  }

  def rowIForeach(r: Int, startCount: Int)(f: (SqCen, Int) => Unit): Int =
  {
    var count = startCount
    iToForeach(0, tileRowLen) { i =>
      f(SqCen(r, leftCenCol + i * 2), i)
      count += 1
    }
    count
  }

  override def foreachCenCoord(f: TileCoord => Unit): Unit = foreach(f)

  final def newTileBuffArr[A <: AnyRef](implicit ct: ClassTag[A]): SqCenArrBuff[A] = SqCenArrBuff(numTiles)
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
    val cMax = cTileMax.roundDownToEven

    new SqGrid(rMin, rMax, cMin, cMax)
  }
}