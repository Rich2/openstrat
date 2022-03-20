/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, reflect.ClassTag

/** A grid of Squares. A regular rectangle of squares.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
class SqGrid(val bottomCenR: Int, val topCenRow: Int, val leftCenC: Int, val rightCenC: Int) extends TGrid
{
  /** Number of rows of tiles. */
  override def numTileRows: Int = (topCenRow - bottomCenR + 2).max0 / 2

  /** The number of tiles in each tile row. */
  def tileRowLen: Int = (rightCenC - leftCenC + 2).max0 / 2

  /** The total number of Tiles in the tile Grid. */
  override def numTiles: Int = numTileRows * tileRowLen

  def leftSideCol: Int = leftCenC - 1
  def rightSideCol: Int = rightCenC + 1

  override def left: Double = leftSideCol
  override def right: Double = rightSideCol
  override def top: Double = topSideRow
  override def bottom: Double = bottomSideRow

  override def coordCen: SqCenOrSide = SqCenOrSide(rCen, cCen)
  override def yRatio: Double = 1
  override def yCen: Double = (bottomCenR + topCenRow) / 2
  override def width: Double = rightSideCol - leftSideCol
  override def height: Double = topSideRow - bottomSideRow

  def horrSideLines: LineSegs = iToMap(bottomSideRow, topSideRow, 2){ r => LineSeg(leftSideCol, r, rightSideCol, r) }
  def vertSideLines: LineSegs = iToMap(leftSideCol, rightSideCol, 2){ c => LineSeg(c, bottomSideRow, c, topSideRow) }
  def sideLines: LineSegs = horrSideLines ++ vertSideLines

  /** The active tiles without any PaintElems. */
  def activeTiles: Arr[PolygonActive] = map(_.active())

  /** Fills all the tiles with the same given parameter [[Colour]]. Not sure how useful this method is. */
  def fillTiles(colour: Colour): Arr[PolygonFill] = map(_.fill(colour))

  /** Gives the index into an Arr / Array of Tile data from its tile [[SqCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(sc: SqCen): Int = arrIndex(sc.r, sc.c)

  @inline def arrIndex(r: Int, c: Int): Int = (r - bottomCenR) / 2 * tileRowLen + (c - leftCenC) / 2
  /** New immutable Arr of Tile data. */
  /*final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): SqcenArr[A] =
  { val res = HcenArr[A](numOfTiles)
    //res.mutSetAll(value)
    //res
    ???
  }*/

  /** Maps over the [[SqCen]] hex centre tile coordinates. B is used rather than A as a type parameter, as this method maps from HCen => B,
   *  corresponding to the standard Scala map function of A => B. */
  final def map[B, ArrB <: SeqImut[B]](f: SqCen => B)(implicit build: ArrBuilder[B, ArrB]): ArrB =
  { val res = build.newArr(numTiles)
    iForeach((sqCen, i) => res.unsafeSetElem(i, f(sqCen)))
    res
  }

  /** New immutable Arr of Tile data. */
  final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): SqCenArr[A] =
  { val res = SqCenArr[A](numTiles)
    //res.mutSetAll(value)
    //res
    res
  }

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newTileArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): SqCenArrOpt[A] = new SqCenArrOpt(new Array[A](numTiles))

  def rowForeach(r: Int)(f: SqCen => Unit): Unit = iToForeach(leftCenC, rightCenC, 2)(c => f(SqCen(r, c)))

  def foreach(f: SqCen => Unit): Unit = foreachRow(rowForeach(_)(f))

  final def iForeach(f: (SqCen, Int) => Unit): Unit =
  { var i: Int = 0
    foreachRow{ r => i = rowIForeach(r, i)(f) }
  }

  final def iForeach(startCount: Int)(f: (SqCen, Int) => Unit): Unit =
  { var i: Int = startCount
    foreachRow{r => i = rowIForeach(r, i)(f) }
  }

  def rowIForeach(r: Int, startCount: Int)(f: (SqCen, Int) => Unit): Int =
  { var count = startCount
    iUntilForeach(0, tileRowLen) { deltaC =>
      f(SqCen(r, leftCenC + deltaC * 2), count)
      count += 1
    }
    count
  }

  override def foreachCenCoord(f: TileCoord => Unit): Unit = foreach(f)

  final def newTileBuffArr[A <: AnyRef](implicit ct: ClassTag[A]): SqCenArrBuff[A] = SqCenArrBuff(numTiles)

  /** Creates a new [[HCenArrOfBuff]] An [[HCen] hex tile centre corresponding Arr of empty [[ArrayBuffer]]s of the given or inferred type. */
  final def newSqCenArrOfBuff[A <: AnyRef](implicit ct: ClassTag[A]): SqCenArrOfBuff[A] = SqCenArrOfBuff(numTiles)

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def sqCenExists(sc: SqCen): Boolean = sqCenExists(sc.r, sc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def sqCenExists(r: Int, c:Int): Boolean = r.isEven & c.isEven & r >= bottomCenR & r <= topCenRow & c >= leftCenC & c <= rightCenC
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