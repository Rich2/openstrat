package ostrat
package pGrid

/** A TileGrid is a description of an abstract TileGrid. It contains no data for the elements any TileGrid. The Data for TileGrids is stored in flat
 * arrays. The TileGrid gives the dimensions of a tileGrid. It has methods to interpret the data in flat Arrays created for that TileGrid
 * specification. It has methods to map the elements of an Array to the the 2 dimensional geometry of the Tile Grid. On its own a TileGrid can produce the
 * outlines of the grid, coordinates vector positions and other pure mathematical data. Combined with a simple function it can for example produce a
 * Chess board. Combined with a 64 length array it can produce a Chess board position. For anything but the most simple games, you will probably want
 * multiple arrays to describe the game state. The terrain for example may remain invariant, so the terrain data does not need to be reproduced with
 * every move.*/
trait TileGrid
{ def xTileMin: Int
  def xTileMax: Int
  def yTileMin: Int
  def yTileMax: Int
  def numOfTiles: Int

  /** Returns the index of an Array from its tile coordinate. */
  @inline def index(x: Int, y: Int): Int

  def allTilesForeach(f: Cood => Unit): Unit

  def newArr[A, AA <: ArrImut[A]](implicit build: ArrBuild[A, AA]): AA = build.imutNew(numOfTiles)
  def newRefs[A <: AnyRef](implicit build: ArrBuild[A, Refs[A]]): Refs[A] = build.imutNew(numOfTiles)

  /** Returns the index of an Array from its tile coordinate. */
  @inline final def index(cood: Cood): Int = index(cood.x, cood.y)

  def arrSet[A](cood: Cood, value: A)(implicit arr: ArrImut[A]): Unit = arr.unsafeSetElem(index(cood), value)
}
