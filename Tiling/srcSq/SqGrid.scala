/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, Colour.Black, reflect.ClassTag

/** A grid of Squares. A regular rectangle of squares.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
class SqGrid(val bottomCenR: Int, val topCenR: Int, val gridLeftCenC: Int, val gridRightCenC: Int) extends SqGridSys with TGrid
{
  /** Number of rows of tiles. */
  override def numTileRows: Int = (topCenR - bottomCenR + 2).max0 / 2

  /** The number of tiles in each tile row. */
  def tileRowLen: Int = (gridRightCenC - gridLeftCenC + 2).max0 / 2

  override def stepFind(startCen: SqCen, endCen: SqCen): Option[SqStep] = ife(sqCenExists(startCen) & sqCenExists(endCen), scSteps.optFind(_.sqCenDelta == endCen - startCen), None)

  override def flatSqCoordToPt2(sqCoord: SqCoord): Pt2 = Pt2(sqCoord.c, sqCoord.r)

  final override def rightSideC: Int = gridRightCenC + 1
  final override def leftSideC: Int = gridLeftCenC - 1

  override def left: Double = leftSideC
  override def right: Double = rightSideC
  override def top: Double = topSepRow
  override def bottom: Double = bottomSepR
  override def coordCen: SqCoord = SqCoord(rCen, cCen)
  override def yCen: Double = (bottomCenR + topCenR) / 2
  def horrSideLines: LSeg2Arr = iToMap(bottomSepR, topSepRow, 2){ r => LSeg2(leftSideC, r, rightSideC, r) }
  def vertSideLines: LSeg2Arr = iToMap(leftSideC, rightSideC, 2){ c => LSeg2(c, bottomSepR, c, topSepRow) }

  override def sidesForeach(f: SqSep => Unit): Unit = iToForeach(bottomSepR, topSepRow){ r =>
    if (r.isOdd) iToForeach(gridLeftCenC, gridRightCenC, 2)(c => f(SqSep(r, c)))
    else iToForeach(leftSideC, rightSideC, 2)(c => f(SqSep(r, c)))
  }

  /** Fills all the tiles with the same given parameter [[Colour]]. Not sure how useful this method is. */
  def fillTiles(colour: Colour): RArr[PolygonFill] = map(_.fill(colour))

  /** Gives the index into an Arr / Array of Tile data from its tile [[SqCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def layerArrayIndex(sc: SqCen): Int = layerArrayIndex(sc.r, sc.c)

  @inline def layerArrayIndex(r: Int, c: Int): Int = (r - bottomCenR) / 2 * tileRowLen + (c - gridLeftCenC) / 2

  def rowForeach(r: Int)(f: SqCen => Unit): Unit = iToForeach(gridLeftCenC, gridRightCenC, 2)(c => f(SqCen(r, c)))

  def foreach(f: SqCen => Unit): Unit = allRsforeach(rowForeach(_)(f))

  def rowIForeach(r: Int, startCount: Int)(f: (SqCen, Int) => Unit): Int =
  { var count = startCount
    iUntilForeach(tileRowLen) { deltaC =>
      f(SqCen(r, gridLeftCenC + deltaC * 2), count)
      count += 1
    }
    count
  }

  override def defaultView(pxScale: Double = 50): SGView = coordCen.view(pxScale)

  /** The line segments [[LSeg2]]s for the sides of the tiles.
 *
   *  @group SidesGroup */
  override def sideLines: LSeg2Arr = horrSideLines ++ vertSideLines


  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def sqCenExists(r: Int, c:Int): Boolean = r.isEven & c.isEven & r >= bottomCenR & r <= topCenR & c >= gridLeftCenC & c <= gridRightCenC

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