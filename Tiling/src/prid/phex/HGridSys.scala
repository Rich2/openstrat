/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag, Colour.Black

/** System of hex tile grids. Can be a single [[HGrid]] or a system of multiple hex tile grids. */
trait HGridSys extends Any with TGridSys
{
  final override lazy val numTiles: Int =
  { var i = 0
    foreach(_ => i += 1)
    i
  }

  /** The number of sides in the hex grid system. */
  final lazy val numSides: Int =
  { var i = 0
    sidesForeach(_ => i += 1)
    i
  }

  /** The number of inner sides in the hex grid system. */
  final lazy val numInnerSides: Int =
  { var i = 0
    innerSidesForeach(_ => i += 1)
    i
  }

  /** The number of outer sides in the hex grid system. */
  final lazy val numOuterSides: Int =
  { var i = 0
    outerSidesForeach(_ => i += 1)
    i
  }

  /** The conversion factor for c column tile grid coordinates. 1.0 / sqrt(3). */
  final override def yRatio: Double = 3.sqrt

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCenExists(hc: HCen): Boolean = hCenExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCenExists(r: Int, c:Int): Boolean
  def coordCen: HCoord
  def hCenSteps(hCen: HCen): HDirnArr

  def unsafeStepEnd(startCen: HCen, step: HDirn): HCen

  //def stepEnd(startCen: HCen, step: HStep): Option[HCen]

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  final def findStep(startR: Int, startC: Int, endR: Int, endC: Int): Option[HDirn] = findStep(HCen(startR, startC), HCen(endR, endC))

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  def findStep(startHC: HCen, endHC: HCen): Option[HDirn]

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  def findStepEnd(startHC: HCen, step: HDirn): Option[HCen]
  def hCoordToPt2(hCoord: HCoord): Pt2

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(hc: HCen): Int = arrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array SeqDef data. */
  def arrIndex(r: Int, c: Int): Int
  def rowCombine[A <: AnyRef](data: HCenDGrid[A], indexingGrider: HGridSys): Arr[HCenRowValue[A]]
  def adjTilesOfTile(tile: HCen): HCenArr

  //def findPathHC(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[LinePathHC] = findPathList(startCen, endCen)(fTerrCost).map(_.toLinePath)

  def findPath(startCen: HCen, endCen: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[HCenArr] = findPathList(startCen, endCen)(fTerrCost).map(_.toArr)

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
      val neighbs: HCenArr = adjTilesOfTile(curr.tile).filterNot(tile => closed.exists(_.tile == tile))
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
  def getHCost(startCen: HCen, endCen: HCen): Int

  /** foreachs over each [[HCen]] hex tile centre, applying the side effecting function. */
  def foreach(f: HCen => Unit): Unit

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  def iForeach(f: (HCen, Int) => Unit): Unit

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit

  /** Creates a new [[HCenBuffDGrid]] An [[HCen] hex tile centre corresponding Arr of empty [[ArrayBuffer]]s of the given or inferred type. */
  final def newHCenArrOfBuff[A <: AnyRef](implicit ct: ClassTag[A]): HCenBuffDGrid[A] = HCenBuffDGrid(numTiles)

  /** Maps over the [[HCen]] hex centre tile coordinates. B is used rather than A as a type parameter, as this method maps from HCen => B,
   *  corresponding to the standard Scala map function of A => B. */
  final def map[B, ArrB <: SeqImut[B]](f: HCen => B)(implicit build: ArrBuilder[B, ArrB]): ArrB =
  { val res = build.newArr(numTiles)
    iForeach((hCen, i) => res.unsafeSetElem(i, f(hCen)))
    res
  }

  /** flatMaps from all hex tile centre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   *  TileGrid structure is lost in the flatMap operation. */
  final def flatMap[ArrT <: SeqImut[_]](f: HCen => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numTiles)
    foreach{ hCen => build.buffGrowArr(buff, f(hCen))}
    build.buffToBB(buff)
  }

  /** New hex data grid. */
  final def newHCenDGrid[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): HCenDGrid[A] =
  { val res: HCenDGrid[A] = HCenDGrid[A](numTiles)
    res.mutSetAll(value)
    res
  }

  /** New immutable Arr of Tile data. */
  final def newHCenArrDGrid[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrDGrid[A] =
  { val newArray = new Array[Array[A]](numTiles)
    val init: Array[A] = Array()
    iUntilForeach(0, numTiles)(newArray(_) = init)
    new HCenArrDGrid[A](newArray)
  }

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newHCenOptDGrid[A <: AnyRef](implicit ct: ClassTag[A]): HCenOptDGrid[A] = new HCenOptDGrid(new Array[A](numTiles))

  def polygons: Arr[Polygon]

  /** The active tiles without any PaintElems. */
  def activeTiles: Arr[PolygonActive]

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def sideArrIndex(hc: HSide): Int = sideArrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array data. */
  def sideArrIndex(r: Int, c: Int): Int

  /** foreach Hex side's coordinate HSide, calls the effectfull function.
   * @group SidesGroup */
  def sidesForeach(f: HSide => Unit): Unit
  def innerSidesForeach(f: HSide => Unit): Unit
  def outerSidesForeach(f: HSide => Unit): Unit

  /** maps over each Hex Side's coordinate [[HSide]] in the hex grid system. */
  final def sidesMap[B, ArrT <: SeqImut[B]](f: HSide => B)(implicit build: ArrBuilder[B, ArrT]): ArrT =
  { val res: ArrT = build.newArr(numSides)
    var i = 0
    sidesForeach{hs => res.unsafeSetElem(i, f(hs)); i += 1 }
    res
  }

  /** maps over each the grid systems inner side's coordinate [[HSide]]. */
  final def innerSidesMap[B, ArrT <: SeqImut[B]](f: HSide => B)(implicit build: ArrBuilder[B, ArrT]): ArrT =
  { val res: ArrT = build.newArr(numInnerSides)
    var i = 0
    innerSidesForeach{hs => res.unsafeSetElem(i, f(hs)); i += 1 }
    res
  }

  /** maps over each the grid systems outer side's coordinate [[HSide]]. */
  final def outerSidesMap[B, ArrT <: SeqImut[B]](f: HSide => B)(implicit build: ArrBuilder[B, ArrT]): ArrT =
  { val res: ArrT = build.newArr(numOuterSides)
    var i = 0
    outerSidesForeach{hs => res.unsafeSetElem(i, f(hs)); i += 1 }
    res
  }

  /** flatMaps over each Hex Side's coordinate [[HSide]]. */
  final def sidesFlatMap[ArrT <: SeqImut[_]](f: HSide => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff()
    sidesForeach{hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToBB(buff)
  }

  /** flatMaps  over each inner hex Side's coordinate [[HSide]].. */
  final def innerSidesFlatMap[ArrT <: SeqImut[_]](f: HSide => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff()
    innerSidesForeach{hs => build.buffGrowArr(buff, f(hs)) }
    build.buffToBB(buff)
  }

  final def sides: HSideArr = sidesMap(hs => hs)

  /** The line segments of the sides defined in [[HCoord]] vertices. */
  def sideLineSegHCs: LineSegHCArr = sidesMap(_.lineSegHC)
  /** The line segments of the inner sides defined in [[HCoord]] vertices. */

  def innerSideLineSegHCs: LineSegHCArr = innerSidesMap[LineSegHC, LineSegHCArr](_.lineSegHC)
  def outerSideLineSegHCs: LineSegHCArr = outerSidesMap[LineSegHC, LineSegHCArr](_.lineSegHC)

  /** The line segments [[LineSeg]]s for the sides of the tiles. */
  final def sideLines(implicit grider: HGridSys): LineSegArr = sideLineSegHCs.map(_.lineSeg)

  /** The line segments [[LineSeg]]s for the inner sides. */
  final def innerSideLines(implicit grider: HGridSys): LineSegArr = innerSideLineSegHCs.map(_.lineSeg)

  /** The line segments [[LineSeg]]s for the inner sides. */
  final def outerSideLines(implicit grider: HGridSys): LineSegArr = outerSideLineSegHCs.map(_.lineSeg)

  /** This gives the all tile grid lines in a single colour and line width. */
  final def sidesDraw(colour: Colour = Black, lineWidth: Double = 2.0)(implicit grider: HGridSys): LinesDraw = sideLines.draw(lineWidth, colour)

  /** Draws the inner side lines in a single colour and line width. */
  final def innerSidesDraw(colour: Colour = Black, lineWidth: Double = 2.0)(implicit grider: HGridSys): LinesDraw =
    innerSideLines.draw(lineWidth, colour)

  /** Draws the inner side lines in a single colour and line width. */
  final def outerSidesDraw(colour: Colour = Black, lineWidth: Double = 2.0)(implicit grider: HGridSys): LinesDraw =
    outerSideLines.draw(lineWidth, colour)

  def newSideBools: HSideBoolDGrid = new HSideBoolDGrid(new Array[Boolean](numSides))

  def defaultView(pxScale: Double = 50): HGView
}