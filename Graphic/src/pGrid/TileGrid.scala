package ostrat
package pGrid
import geom._, reflect.ClassTag

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

  /** Converts Cood to a Vec2. For a square grid this will be a simple 1 to 1 map. It is called coodToVec2Abs because most of the time, you will want
   * the Vec2 relative to the TileGrid centre. */
  def coodToVec2Abs(cood: Cood): Vec2
  /** Returns the index of an Array from its tile coordinate. */
  @inline def index(c: Int, y: Int): Int

  /** The centre of the grid in termsof the x Axis. */
  def xCen: Double
  def xLeft: Double
  def xRight: Double
  def width: Double = xRight - xLeft
  def top: Double
  def bottom: Double
  def height: Double = top - bottom
  def fullDisplayScale(dispWidth: Double, dispHeight: Double): Double = (dispWidth / width.max(1)).min(dispHeight / height.max(1))

  /** Gives all the sideCoods of the grid with out duplicates. */
  def sideCoods: Coods = tilesAllFlatUniqueMap[Cood, Coods] { cood => sideCoodsOfTile(cood) }

  /** The centre of the grid by the y coordinate. */
  def yCen: Double = (yTileMin + yTileMax) / 2.0

  //def sideCoodToLineRel(sideCood: Cood, scale: Double, relPosn: Vec2): Line2

  def sideCoodToCoodLine(sideCood: Cood): CoodLine

  final def sideLinesAll(scale: Double = 1.0, relPosn: Vec2 = Vec2Z) : Line2s = tilesAllFlatMap { cood =>
    val c1: Coods = sideCoodsOfTile(cood)
    val c2s: Line2s = c1.map(orig => sideCoodToLine(orig, scale, relPosn))
    c2s
  }

  /** This gives the tile grid lines in a single colour and line width. */
  def sideLinesAllDraw(scale: Double, lineWidth: Double = 2.0, colour: Colour = Colour.Black, relPosn: Vec2): LinesDraw =
    LinesDraw(sideLinesAll(scale, relPosn), lineWidth, colour)

  /** Side Cood to Line2 relative to a position on the grid and then scaled. */
  final def sideCoodToLine(sideCood: Cood, scale: Double = 1.0, relPosn: Vec2 = Vec2Z): Line2 =
    sideCoodToCoodLine(sideCood).toLine2(cood => (coodToVec2Abs(cood) -relPosn -cen) * scale)

  /** This gives the Vec2 of the Cood relative to a position on the grid and then scaled. (coodToVec2Abs(cood) - gridPosn -cen) * scale */
  def coodToVec2(cood: Cood, gridPosn: Vec2 = Vec2Z, scale: Double = 1.0): Vec2 = (coodToVec2Abs(cood) - gridPosn -cen) * scale

  def cen = Vec2(xCen, yCen)
  def tilesAllForeach(f: Cood => Unit): Unit
  def sideCoodsOfTile(tileCood: Cood): Coods

  def tileRowsAllForeach(f: Int => Unit): Unit = iToForeach(yTileMin, yTileMax, 2)(f)

  def tilesAllMap[A, ArrT <: ArrImut[A]](f: Cood => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
  { val res = build.imutNew(numOfTiles)
    tilesAllForeach{ cood =>
      build.imutSet(res, index(cood), f(cood))
    }
    res
  }

  def tilesSomeMap[A <: AnyRef, B, ArrT <: ArrImut[B]](inp: OptRefs[A])(f: (A, Vec2) => B)(implicit build: ArrBuild[B, ArrT]): ArrT =
  {
    val buff = build.buffNew()

    tilesAllForeach { cood =>
      val op: OptRef[A] = inp(index(cood))
      op.foreach ( a => build.buffGrow(buff, f(a, Vec2Z)))
      }
    build.buffToArr(buff)
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

  /** Maps all the Tile centre Vec2 posns to an Arr of type A. The positions are relative to a TileGrid position, which by default is the tileGrid
   * centre. The position is then scaled. */
  def tilesVecMap[A, ArrT <: ArrImut[A]](scale: Double = 1.0, relPosn: Vec2 = Vec2Z)(f: Vec2 => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
    tilesAllMap { cood => f(coodToVec2(cood, relPosn, scale)) }

  /** Maps all the Tile Coods and their respective tile centre Vec2 posns to an Arr of type A. The positions are relative to a TileGrid position,
   *  which by default is the tileGrid centre. The position is then scaled. */
  def tilesCoodVecMap[A, ArrT <: ArrImut[A]](scale: Double = 1.0, relPosn: Vec2 = Vec2Z)(f: (Cood, Vec2) => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
    tilesAllMap { cood => f(cood, coodToVec2(cood, relPosn, scale)) }

  /** Creates a new uninitialised Arr of the grid length. */
  def newArr[A, AA <: ArrImut[A]](implicit build: ArrBuild[A, AA]): AA = build.imutNew(numOfTiles)

  def newRefs[A <: AnyRef](implicit build: ArrBuild[A, Refs[A]]): Refs[A] = build.imutNew(numOfTiles)

  def setTile[A <: AnyRef](cood: Cood, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(cood), value)
  def setTile[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(x, y), value)

  def newOptRefs[A <: AnyRef](implicit ct: ClassTag[A]): OptRefs[A] = OptRefs(numOfTiles)
  //def setSomeTile[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: Refs[Option[A]]): Unit = arr.unsafeSetElem(index(x, y), Some(value))
  def setTileSome[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: OptRefs[A]): Unit = arr.setSome(index(x, y), value)
  /** Returns the index of an Array from its tile coordinate. */
  @inline final def index(cood: Cood): Int = index(cood.xi, cood.yi)

  def arrSet[A](cood: Cood, value: A)(implicit arr: ArrImut[A]): Unit = arr.unsafeSetElem(index(cood), value)
}