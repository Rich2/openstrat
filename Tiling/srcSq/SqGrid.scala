/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, Colour.Black, reflect.ClassTag

/** A grid of Squares. A regular rectangle of squares.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
class SqGrid(val bottomCenR: Int, val topCenR: Int, val leftCenC: Int, val rightCenC: Int) extends SqGridSys with TGrid
{
  /** Number of rows of tiles. */
  override def numTileRows: Int = (topCenR - bottomCenR + 2).max0 / 2

  /** The number of tiles in each tile row. */
  def tileRowLen: Int = (rightCenC - leftCenC + 2).max0 / 2

  override def flatSqCoordToPt2(sqCoord: SqCoord): Pt2 = Pt2(sqCoord.c, sqCoord.r)

  final override def rightSideC: Int = rightCenC + 1
  final override def leftSideC: Int = leftCenC - 1

  override def left: Double = leftSideC
  override def right: Double = rightSideC
  override def top: Double = topSideRow
  override def bottom: Double = bottomSideR
  override def coordCen: SqCoord = SqCoord(rCen, cCen)
  override def yCen: Double = (bottomCenR + topCenR) / 2
  def horrSideLines: LineSegArr = iToMap(bottomSideR, topSideRow, 2){ r => LineSeg(leftSideC, r, rightSideC, r) }
  def vertSideLines: LineSegArr = iToMap(leftSideC, rightSideC, 2){ c => LineSeg(c, bottomSideR, c, topSideRow) }

  override def sidesForeach(f: SqSide => Unit): Unit = iToForeach(bottomSideR, topSideRow){r =>
    if (r.isOdd) iToForeach(leftCenC, rightCenC, 2)(c => f(SqSide(r, c)))
    else iToForeach(leftSideC, rightSideC, 2)(c => f(SqSide(r, c)))
  }

  /** Fills all the tiles with the same given parameter [[Colour]]. Not sure how useful this method is. */
  def fillTiles(colour: Colour): RArr[PolygonFill] = map(_.fill(colour))

  /** Gives the index into an Arr / Array of Tile data from its tile [[SqCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def layerArrayIndex(sc: SqCen): Int = layerArrayIndex(sc.r, sc.c)

  @inline def layerArrayIndex(r: Int, c: Int): Int = (r - bottomCenR) / 2 * tileRowLen + (c - leftCenC) / 2

  def rowForeach(r: Int)(f: SqCen => Unit): Unit = iToForeach(leftCenC, rightCenC, 2)(c => f(SqCen(r, c)))

  def foreach(f: SqCen => Unit): Unit = foreachRow(rowForeach(_)(f))

  def rowIForeach(r: Int, startCount: Int)(f: (SqCen, Int) => Unit): Int =
  { var count = startCount
    iUntilForeach(tileRowLen) { deltaC =>
      f(SqCen(r, leftCenC + deltaC * 2), count)
      count += 1
    }
    count
  }

  override def defaultView(pxScale: Double = 50): SqGridView = coordCen.view(pxScale)

  /** The line segments [[LineSeg]]s for the sides of the tiles.
   *  @group SidesGroup */
  override def sideLines: LineSegArr = horrSideLines ++ vertSideLines


  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def sqCenExists(r: Int, c:Int): Boolean = r.isEven & c.isEven & r >= bottomCenR & r <= topCenR & c >= leftCenC & c <= rightCenC

  /** Finds step from Start[[SqCen]] to target from[[SqCen]].  */
  override def stepEndFind(startSC: SqCen, step: SqStep): Option[SqCen] ={
    val newCen = SqCen(startSC.r + step.tr, startSC.c + step.tc)
    Some(newCen)
  }
}

/** Companion object for the HGridReg class. Contains an applr method that corrects the r and Y minimum and maximum values. */
object SqGrid
{
  /** Corrects the X and Y minimum and maximum values. */
  def apply(bottomCenR: Int, topCenR: Int, leftCenC: Int, rightCenC: Int): SqGrid =
  { val rMin = bottomCenR.roundUpToEven
    val rMax = topCenR.roundDownToEven
    val cMin = leftCenC.roundUpToEven
    val cMax = rightCenC.roundDownToEven
    new SqGrid(rMin, rMax, cMin, cMax)
  }
}