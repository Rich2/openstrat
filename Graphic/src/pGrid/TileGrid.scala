package ostrat
package pGrid
import geom._

/** A TileGrid is a description of an abstract TileGrid. It contains no data for the elements of any particular TileGrid. The Data for TileGrids is
 *  stored in flat arrays. The TileGrid gives the dimensions of a tileGrid. It has methods to interpret the data in flat Arrays created for that
 *  TileGrid specification. It has methods to map the elements of an Array to the the 2 dimensional geometry of the Tile Grid. On its own a TileGrid
 *  can produce the outlines of the grid, coordinates vector positions and other pure mathematical data. Combined with a simple function it can for
 *  example produce a Chess board. Combined with a 64 length array it can produce a Chess board position. For anything but the most simple games, you
 *  will probably want multiple arrays to describe the game state. The terrain for example may remain invariant, so the terrain data does not need to
 *  be reproduced with every move.
 *
 *  A TileGrid is for use cases where the proportions of the Grid predetermine the proportions of the visual representation, as opposed to a use case
 *  where the proportions of the enclosing space are a factor in determining the proportions of the grid. For example the various grid layouts of the
 *  Stars on the American flag. */
trait TileGrid
{ def numOfRows: Int
  def numOfTiles: Int
  def yTileMin: Int
  def yTileMax: Int
  def coodToVec2(cood: Cood): Vec2
  /** Returns the index of an Array from its tile coordinate. */
  @inline def index(c: Int, y: Int): Int
  def xCen: Double
  def xLeft: Double
  def xRight: Double
  def width: Double = xRight - xLeft
  def top: Double
  def bottom: Double
  def height: Double = top - bottom
  def fullDisplayScale(dispWidth: Double, dispHeight: Double): Double = (dispWidth / width.max(1)).min(dispHeight / height.max(1))

  def sideCoodsAll: Coods = tilesAllFlatUniqueMap[Cood, Coods] { cood => sideCoodsOfTile(cood) }

  /** The centre of the grid by the y coordinate. */
  def yCen: Double = (yTileMin + yTileMax) / 2.0

  def sideCoodToLineRel(sideCood: Cood, relPosn: Vec2): Line2
  def coodToVec2Rel(cood: Cood, gridPosn: Vec2 = cen): Vec2 = coodToVec2(cood) - gridPosn
  def cen = Vec2(xCen, yCen)
  def tilesAllForeach(f: Cood => Unit): Unit
  def sideCoodsOfTile(tileCood: Cood): Coods
  def sideLinesAllRel(offset: Vec2 = cen) : Line2s = sideCoodsAll.map(cood => sideCoodToLineRel(cood, offset))

  def tileRowsAllForeach(f: Int => Unit): Unit = iToForeach(yTileMin, yTileMax, 2)(f)

  def tilesAllMap[A, ArrT <: ArrImut[A]](f: Cood => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
  { val res = build.imutNew(numOfTiles)
    tilesAllForeach{ cood => build.imutSet(res, index(cood), f(cood))}
    res
  }

  def tilesAllFlatMap[ArrT <: ArrImut[_]](f: Cood => ArrT)(implicit build: ArrFlatBuild[ArrT]): ArrT =
  { val buff = build.buffNew(numOfTiles)
    tilesAllForeach{ cood => build.buffGrowArr(buff, f(cood))}
    build.buffToArr(buff)
  }

  def tilesAllFlatUniqueMap[A, ArrT <: ArrImut[A]](f: Cood => ArrT)(implicit build: ArrBuild[A, ArrT]): ArrT =
  { val buff = build.buffNew(numOfTiles)
    tilesAllForeach { cood =>
      val newVals = f(cood)
      newVals.foreach{newVal =>
      if (!buff.contains(newVal)) build.buffGrow(buff, newVal) }
    }
    build.buffToArr(buff)
  }

  def tilesAllVecMap[A, ArrT <: ArrImut[A]](scale: Double, relPosn: Vec2 = cen)(f: Vec2 => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
    tilesAllMap { cood => f(coodToVec2Rel(cood, relPosn) * scale) }

  def tilesAllCoodVecMap[A, ArrT <: ArrImut[A]](scale: Double, relPosn: Vec2 = cen)(f: (Cood, Vec2) => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
    tilesAllMap { cood => f(cood, coodToVec2Rel(cood, relPosn) * scale) }

  def newArr[A, AA <: ArrImut[A]](implicit build: ArrBuild[A, AA]): AA = build.imutNew(numOfTiles)
  def newRefs[A <: AnyRef](implicit build: ArrBuild[A, Refs[A]]): Refs[A] = build.imutNew(numOfTiles)

  def setTile[A <: AnyRef](cood: Cood, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(cood), value)
  def setTile[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(x, y), value)

  def setSomeTile[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: Refs[Option[A]]): Unit = arr.unsafeSetElem(index(x, y), Some(value))
 // def setSome[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: Refs[OptRef[A]]): Unit = arr.unsafeSetElem(index(x, y), OptRef(value))
  /** Returns the index of an Array from its tile coordinate. */
  @inline final def index(cood: Cood): Int = index(cood.c, cood.y)

  def arrSet[A](cood: Cood, value: A)(implicit arr: ArrImut[A]): Unit = arr.unsafeSetElem(index(cood), value)
}