package ostrat
package pGrid

/** A TileGrid is a description of an abstract TileGrid. It contains no data for the elements of any particular TileGrid. The Data for TileGrids is
 *  stored in flat arrays. The TileGrid gives the dimensions of a tileGrid. It has methods to interpret the data in flat Arrays created for that
 *  TileGrid specification. It has methods to map the elements of an Array to the the 2 dimensional geometry of the Tile Grid. On its own a TileGrid
 *  can produce the outlines of the grid, coordinates vector positions and other pure mathematical data. Combined with a simple function it can for
 *  example produce a Chess board. Combined with a 64 length array it can produce a Chess board position. For anything but the most simple games, you
 *  will probably want multiple arrays to describe the game state. The terrain for example may remain invariant, so the terrain data does not need to
 *  be reproduced with every move.*/
trait TileGrid
{
  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio form column coordinate to x. */
  def cTileMin: Int

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio form column coordinate to x. */
  def cTileMax: Int

  def yTileMin: Int
  def yTileMax: Int
  def numOfTiles: Int

  /** Returns the index of an Array from its tile coordinate. */
  @inline def index(c: Int, y: Int): Int

  def tilesAllForeach(f: Cood => Unit): Unit

  def tilesAllMap[A, ArrT <: ArrImut[A]](f: Cood => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
  { val res = build.imutNew(numOfTiles)
    tilesAllForeach{ cood => build.imutSet(res, index(cood), f(cood))}
    res
  }

  def tilesAllFlatMap[ArrT <: ArrImut[_]](f: Cood => ArrT)(implicit build: ArrArrBuild[ArrT]): ArrT =
  { val buff = build.buffNew(numOfTiles)
    tilesAllForeach{ cood => build.buffGrowArr(buff, f(cood))}
    build.buffToArr(buff)
  }

  def newArr[A, AA <: ArrImut[A]](implicit build: ArrBuild[A, AA]): AA = build.imutNew(numOfTiles)
  def newRefs[A <: AnyRef](implicit build: ArrBuild[A, Refs[A]]): Refs[A] = build.imutNew(numOfTiles)

  def setTile[A <: AnyRef](cood: Cood, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(cood), value)
  def setTile[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(x, y), value)

  def setSomeTile[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: Refs[Option[A]]): Unit = arr.unsafeSetElem(index(x, y), Some(value))
  /** Returns the index of an Array from its tile coordinate. */
  @inline final def index(cood: Cood): Int = index(cood.c, cood.y)

  def arrSet[A](cood: Cood, value: A)(implicit arr: ArrImut[A]): Unit = arr.unsafeSetElem(index(cood), value)
}