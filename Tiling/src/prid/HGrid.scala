/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, math.sqrt, reflect.ClassTag, collection.mutable.ArrayBuffer

/** A grid of Hexs. The grid may be a regular rectangle of hexs or an irregular grid with variable length rows.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
trait HGrid extends Any with TGrid with HGridBased
{
  final override def left: Double = leftCenCol - 2
  final override def right: Double = rightCenCol + 2
  final override def top: Double = topCenRow * Sqrt3 + 2
  final override def bottom: Double = bottomCenRow * Sqrt3 - 2

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

  /** The conversion factor for c column tile grid coordinates. 1.0 / sqrt(3). */
  override def yRatio: Double = sqrt(3)

  override def coordCen: HCoord

  def defaultView(pxScale: Double = 50): HGridView = coordCen.view(pxScale)

  /** The centre of the hex grid along the Y axis after the yRatio has been applied to the r row value. */
  final override def yCen: Double = rCen * yRatio

  /** foreachs over each [[HCen]] hex tile centre, applying the side effecting function. */
  final def foreach(f: HCen => Unit): Unit = foreachRow(r => rowForeach(r)(f))

  /** foreachs with index over each [[HCen]] hex tile centre, apply the side effecting function. */
  final def iForeach(f: (HCen, Int) => Unit) =
  { var count: Int = 0
    foreachRow{r => count = rowIForeach(r, count)(f) }
  }

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

  /** Is the specified tile centre row empty? */
  final def cenRowEmpty(row: Int): Boolean = rowNumTiles(row) == 0

  /** The start (or by default left column) of the tile centre of the given row. */
  def rowCenLeft(row: Int): Int

  /** The end (or by default right) column number of the tile centre of the given row. */
  def rowCenRight(row: Int): Int

  override def foreachCenCoord(f: TileCoord => Unit): Unit = foreach(f)

  /** The active tiles without any PaintElems. */
  def activeTiles: Arr[PolygonActive] = map(_.active())

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(hc: HCen): Int = arrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array data. */
  def arrIndex(r: Int, c: Int): Int

  /** New immutable Arr of Tile data. */
  final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): HCenArr[A] =
  { val res = HCenArr[A](numTiles)
    res.mutSetAll(value)
    res
  }

  /** New immutable Arr of Tile data. */
  final def newTileArrArr[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrArr[A] =
  { val newArray = new Array[Array[A]](numTiles)
    val init: Array[A] = Array()
    iUntilForeach(0, numTiles)(newArray(_) = init)
    new HCenArrArr[A](newArray)
  }

  /** Creates a new [[HCenArrOfBuff]] An [[HCen] hex tile centre corresponding Arr of empty [[ArrayBuffer]]s of the given or inferred type. */
  final def newHCenArrOfBuff[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrOfBuff[A] = HCenArrOfBuff(numTiles)

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newTileArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrOpt[A] = new HCenArrOpt(new Array[A](numTiles))

  /** Combine adjacent tiles of the same value. */
  /*def combinedPolygons[A <: AnyRef](implicit arr: HCenArr[A]): Arr[(HVertPolygon, A)] =
  {
    implicit def grid: HGrid = this
    if (numCenRows > 0)
    {
      val incomplete: ArrayBuffer[(HVertPolygon, A)] = Buff()
      val complete: ArrayBuffer[(HVertPolygon, A)] = Buff()

      foreachRow { r =>
        var curr: Option[(HVertPolygon, A)] = None
        rowIForeach(r) { (hc, i) =>
          val newValue: A = arr(hc)(this)
          curr match {
            case None => curr = Some((hc.hVertPolygon, newValue))
            case Some((p, a)) if a == newValue =>
            case Some(pair) => incomplete.append(pair)
          }
        }
      }
      ???
    }
    else Arr()
  }*/

  def adjTilesOfTile(tile: HCen): HCens = ???
  def findPath(startRoord: HCen, endRoord: HCen)(fTerrCost: (HCen, HCen) => OptInt): Option[List[HCen]] =
  {
    var open: List[Node] = Node(startRoord, 0, getHCost(startRoord, endRoord), NoRef) :: Nil
    var closed: List[Node] = Nil
    var found: Option[Node] = None

    while (open.nonEmpty & found == None)
    {
      val curr: Node = open.minBy(_.fCost)
      //if (curr.tile.Roord == endRoord) found = true
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
              case Some(node) =>
              case None =>
              { val newNode = Node(tile, newGCost, getHCost(tile, endRoord), OptRef(curr))
                open ::= newNode
                if (tile == endRoord) found = Some(newNode)
              }
            }
          }
        }
      }
    }
    def loop(acc: List[HCen], curr: Node): List[HCen] = curr.parent.fld(acc, loop(curr.tile :: acc, _))

    found.map(endNode =>  loop(Nil, endNode))
  }
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   *  departure and the tile of arrival. */
  def getHCost(startRoord: HCen, endRoord: HCen): Int =
  { val diff = endRoord - startRoord
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

  /** The number of Sides in the TileGrid. Needs reimplementing.
   *  @group SidesGroup */
  def numSides: Int


  override def sideLines: LineSegs = sideCoordLines.map(_.lineSeg)

  /** foreach Hex side's coordinate HSide, calls the effectfull function.
   * @group SidesGroup */
  final def sidesForeach(f: HSide => Unit): Unit = sideRowForeach(r => rowForeachSide(r)(f))

  /** Calls the Foreach procedure on every Hex Side in the row given by the input parameter.
   *  @group */
  def rowForeachSide(r: Int)(f: HSide => Unit): Unit

  /** maps over each Hex Side's coordinate [[HSide]] in the given Row.
   *  @group SidesGroup */
  final def sidesMap[B, ArrT <: SeqImut[B]](f: HSide => B)(implicit build: ArrBuilder[B, ArrT]): ArrT =
  {
    val res: ArrT = build.newArr(numSides)
    var count = 0
    sidesForeach{hs =>
      res.unsafeSetElem(count, f(hs))
      count += 1
    }
    res
  }

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
  def sideCoordLines: Arr[LineSegHC] = sidesMap[LineSegHC, Arr[LineSegHC]](_.coordLine)

  def newSideBooleans: HSideBooleans = new HSideBooleans(new Array[Boolean](numSides))
}

case class Node(val tile: HCen, var gCost: Int, var hCost: Int, var parent: OptRef[Node])
{ def fCost = gCost + hCost
}