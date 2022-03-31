/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A grid of Hexs. The grid may be a regular rectangle of hexs or an irregular grid with variable length rows.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
trait HGrid extends Any with TGrid with HGriderFlat
{
  final override def left: Double = leftCenC - 2
  final override def right: Double = rightCenC + 2
  final override def top: Double = topCenR * yRatio + 4.0/Sqrt3
  final override def bottom: Double = bottomCenR * yRatio - 4.0/Sqrt3

  final override def leftSideC: Int = leftCenC - 2
  final override def rightSideC: Int = leftCenC + 2

  /** The number of tile centre rows where r %% 4 == 0.  */
  def numRow0s: Int

  /** The number of tile centre rows where r %% 4 == 2.  */
  def numRow2s: Int

  /** The number of tile centres in the given row. */
  def rowNumTiles(row: Int): Int

  /** Carries out the procedure function on each [[HCen]] hex tile centre coordinate in the given tile row. This method is defined here rather than on
   *  TileGrid so it can take the specific narrow [[HCen]] parameter to the foreach function. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit

  /** Carries out the procedure function on each [[HCen]] hex tile centre coordinate and an index counter in the given tile row. This method is
   *  defined here rather than on TileGrid so it can take the specific narrow [[HCen]] parameter to the foreach function. */
  def rowIForeach(r: Int, count: Int = 0)(f: (HCen, Int) => Unit): Int

  override def coordCen: HCoord

  def defaultView(pxScale: Double = 50): HGridView = coordCen.view(pxScale)

  override def hCoordToPt2(hCoord: HCoord): Pt2 = hCoord.toPt2Reg

  /** The centre of the hex grid along the Y axis after the yRatio has been applied to the r row value. */
  final override def yCen: Double = rCen * yRatio

  /** foreachs over each [[HCen]] hex tile centre, applying the side effecting function. */
  final override def foreach(f: HCen => Unit): Unit = foreachRow(r => rowForeach(r)(f))

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  final override def iForeach(f: (HCen, Int) => Unit): Unit =
  { var count: Int = 0
    foreachRow{r => count = rowIForeach(r, count)(f) }
  }

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  final override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count: Int = init
    foreachRow{r => count = rowIForeach(r, count)(f) }
  }

  /** Is the specified tile centre row empty? */
  final def cenRowEmpty(row: Int): Boolean = rowNumTiles(row) == 0

  /** The start (or by default left column) of the tile centre of the given row. */
  def rowLeftCenC(row: Int): Int

  /** The end (or by default right) column number of the tile centre of the given row. */
  def rowRightCenC(row: Int): Int

  override def foreachCenCoord(f: TCoord => Unit): Unit = foreach(f)

  override def polygons: Arr[Polygon] = map(_.polygonReg)

  /** The active tiles without any PaintElems. */
  override def activeTiles: Arr[PolygonActive] = map(_.active())

  override def hCenSteps(hCen: HCen): HStepArr = HStep.full.filter(st => hCenExists(hCen.r + st.r, hCen.c + st.c))

  override def unsafeStep(startCen: HCen, step: HStep): HCen ={
    val endCen = HCen(startCen.r + step.r, startCen.c + step.c)
    if (hCenExists(endCen)) endCen else excep("Illegal end hex in unsafeStep method.")
  }

  override def findStepHC(startHC: HCen, step: HStep): Option[HCen] =
  { val endHC = HCen(startHC.r + step.r, startHC.c + step.c)
    if (hCenExists(startHC) & hCenExists(endHC)) Some(endHC) else None
  }

  override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ife(hCenExists(startHC) & hCenExists(endHC), hcSteps.optFind(_.hCenDelta == endHC - startHC), None)

  def findPathHC(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[LinePathHC] = findPathList(startCen, endCen)(fTerrCost).map(_.toLinePath)

  def findPath(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[HCens] = findPathList(startCen, endCen)(fTerrCost).map(_.toImut)

  /** Finds path from Start hex tile centre to end tile centre given the cost function parameter. */
  def findPathList(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[List[HCen]] =
  {
    var open: List[HNode] = HNode(startCen, 0, getHCost(startCen, endCen), NoRef) :: Nil
    var closed: List[HNode] = Nil
    var found: Option[HNode] = None

    while (open.nonEmpty & found == None)
    {
      val curr: HNode = open.minBy(_.fCost)
      open = open.filterNot(_ == curr)
      closed ::= curr
      val neighbs: HCens = adjTilesOfTile(curr.tile).filterNot(tile => closed.exists(_.tile == tile))
      neighbs.foreach { tile =>
        fTerrCost(curr.tile, tile) match {
          case NoInt =>
          case SomeInt(nc) if closed.exists(_.tile == tile) =>
          case SomeInt(nc) => {
            val newGCost = nc + curr.gCost

            open.find(_.tile == tile) match {
              case Some(node) if newGCost < node.gCost => node.gCost = newGCost; node.parent = OptRef(curr)
              case Some(_) =>
              case None =>
              { val newNode = HNode(tile, newGCost, getHCost(tile, endCen), OptRef(curr))
                open ::= newNode
                if (tile == endCen) found = Some(newNode)
              }
            }
          }
        }
      }
    }
    def loop(acc: List[HCen], curr: HNode): List[HCen] = curr.parent.fld(acc, loop(curr.tile :: acc, _))

    found.map(endNode =>  loop(Nil, endNode))
  }

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   *  departure and the tile of arrival. */
  def getHCost(startCen: HCen, endCen: HCen): Int =
  { val diff = endCen - startCen
    val c: Int = diff.c.abs
    val y: Int = diff.r.abs

    y - c match
    { case 0 => c
    case n if n > 0 => y
    case n if n %% 4 == 0 => y - n / 2 //Subtract because n is negative, y being greater than x
    case n => y - n / 2 + 2
    }
  }

  /* Methods that operate on Hex tile sides. ******************************************************/

  override def sideLines(implicit grider: HGriderFlat): LineSegs = sideCoordLines.map(_.lineSeg)

  /** foreach Hex side's coordinate HSide, calls the effectfull function.
   * @group SidesGroup */
  final override def sidesForeach(f: HSide => Unit): Unit = sideRowForeach(r => rowForeachSide(r)(f))

  /** Calls the Foreach procedure on every Hex Side in the row given by the input parameter.
   *  @group */
  def rowForeachSide(r: Int)(f: HSide => Unit): Unit

  /** maps over each Hex Side's coordinate [[HSide]] in the given Row.
   *  @group SidesGroup */
  final def sidesFlatMap[ArrT <: SeqImut[_]](f: HSide => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  {
    val buff = build.newBuff()// newArr(numSides)
    sidesForeach{hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToBB(buff)
  }

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def sideArrIndex(hc: HSide): Int = sideArrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array data. */
  def sideArrIndex(r: Int, c: Int): Int

  /** Array of indexs for Side data Arrs giving the index value for the start of each side row. */
  def sideRowIndexArray: Array[Int]

  /** The Hex Sides of the Hex Grid defined in integer constructed [[LineSegHC.]].
   *
   *  @group SidesGroup */
  def sideCoordLines: LineSegHCs = sidesMap[LineSegHC, LineSegHCs](_.coordLine)

  def newSideBooleans: HSideBooleans = new HSideBooleans(new Array[Boolean](numSides))
}

/** Hex grid path finding node. */
case class HNode(val tile: HCen, var gCost: Int, var hCost: Int, var parent: OptRef[HNode])
{ def fCost = gCost + hCost
}