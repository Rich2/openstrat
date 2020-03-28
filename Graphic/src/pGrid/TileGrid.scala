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

  /** The centre of the grid in terms of the x Axis. */
  def xCen: Double
  def xLeft: Double
  def xRight: Double
  def width: Double = xRight - xLeft
  def top: Double
  def bottom: Double
  def height: Double = top - bottom

  def fullDisplayScale(dispWidth: Double, dispHeight: Double, padding: Double = 20): Double =
  { def adj(inp : Double): Double =inp match
    { case n if n > 1000 => inp - padding
      case n if n > 500 => inp - padding * inp / 1000.0
      case n if n > 10 => n
      case _ => 10
    }
    (adj(dispWidth) / adj(width).max(1)).min(adj(dispHeight) / height.max(1))
  }

  /** The centre of the grid by the y coordinate. */
  def yCen: Double = (yTileMin + yTileMax) / 2.0

  /** The centre of the grid in Vec2 coordinates. */
  def cen = Vec2(xCen, yCen)

/**************************************************************************************************/
/* Methods that foreach, map, flatMap and fold over all the tiles of the tile grid. */

  /** maps over an array of tile data with each tiles associated centre Vec2. */
  /*def mapArrWithVecs[A, B, BB <: ArrImut[B]](arr: ArrayLike[A])(f: (A, Vec2) => B)(implicit build: ArrBuild[B, BB]): BB =
  {
    val res = build.imutNew(arr.length)
    var count = 0
    foreachTileVec{ v =>
      res.unsafeSetElem(count, f(arr(count), v))
      count += 1
    }
    res
  }*/

  /** foreachs over each tile's centre Cood. */
  def foreachTileCood(f: Cood => Unit): Unit

  /** Foreach grid Row yi coordinate. */
  def tileRowsForeach(f: Int => Unit): Unit = iToForeach(yTileMin, yTileMax, 2)(f)

  /** Maps from all tile Coods to an Arr of A. The Arr produced can be accessed by its Cood from this grid Class. */
  def mapTileCoods[A, ArrT <: ArrImut[A]](f: Cood => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
  { val res = build.imutNew(numOfTiles)
    foreachTileCood{ cood =>
      build.imutSet(res, index(cood), f(cood))
    }
    res
  }

  def mapArrTileOptRefwithVec[A <: AnyRef, B, ArrT <: ArrImut[B]](inp: OptRefs[A], scale: Double, relPosn: Vec2 = Vec2Z)(f: (A, Vec2) => B)
    (implicit build: ArrBuild[B, ArrT]): ArrT =
  {
    val buff = build.buffNew()
    foreachTileCood { cood =>
      val op: OptRef[A] = inp(index(cood))
      op.foreach { a =>
        val v = coodToVec2(cood, scale, relPosn)
        build.buffGrow(buff, f(a, v))
      }
    }
    build.buffToArr(buff)
  }

  /** flatMaps from all tile Coods to an Arr of type ArrT. The elements of this array can not be accessed from this gird class as the TileGrid
   *  structure is lost in the flatMap operation. */
  def flatMapTileCoods[ArrT <: ArrImut[_]](f: Cood => ArrT)(implicit build: ArrFlatBuild[ArrT]): ArrT =
  { val buff = build.buffNew(numOfTiles)
    foreachTileCood{ cood => build.buffGrowArr(buff, f(cood))}
    build.buffToArr(buff)
  }

  /** flatmaps from all tile Coods to an Arr of type ArrT, removing all duplicate elements. */
  def flatUniqueMapTiles[A, ArrT <: ArrImut[A]](f: Cood => ArrT)(implicit build: ArrBuild[A, ArrT]): ArrT =
  { val buff = build.buffNew(numOfTiles)
    foreachTileCood { cood =>
      val newVals = f(cood)
      newVals.foreach{newVal =>
      if (!buff.contains(newVal)) build.buffGrow(buff, newVal) }
    }
    build.buffToArr(buff)
  }

  /** foreachs over each tile centre Vec2. */
  def foreachTileVec(f: Vec2 => Unit): Unit = foreachTileCood(c => f(coodToVec2(c)))

  /** Maps all the Tile centre Vec2 posns to an Arr of type A. The positions are relative to a TileGrid position, which by default is the tileGrid
   * centre. The position is then scaled. */
  def mapTileVecs[A, ArrT <: ArrImut[A]](scale: Double = 1.0, relPosn: Vec2 = Vec2Z)(f: Vec2 => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
    mapTileCoods { cood => f(coodToVec2(cood, scale, relPosn)) }

  /** Maps all the Tile Coods and their respective tile centre Vec2 posns to an Arr of type A. The positions are relative to a TileGrid position,
   *  which by default is the tileGrid centre. The position is then scaled. */
  def mapTileCoodVecs[A, ArrT <: ArrImut[A]](scale: Double = 1.0, relPosn: Vec2 = Vec2Z)(f: (Cood, Vec2) => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
    mapTileCoods { cood => f(cood, coodToVec2(cood, scale, relPosn)) }

  /** Creates a new uninitialised Arr of the grid length. */
  def newArr[A, AA <: ArrImut[A]](implicit build: ArrBuild[A, AA]): AA = build.imutNew(numOfTiles)

  def newRefs[A <: AnyRef](implicit build: ArrBuild[A, Refs[A]]): Refs[A] = build.imutNew(numOfTiles)

  def newOptRefs[A <: AnyRef](implicit ct: ClassTag[A]): OptRefs[A] = OptRefs(numOfTiles)

/**************************************************************************************************/
/* Methods that operate on individual tiles. */

  /** Returns the index of an Array from its tile coordinate. */
  @inline final def index(cood: Cood): Int = index(cood.xi, cood.yi)

  /** Sets element in a flat Arr according to its Cood. */
  def setElem[A](cood: Cood, value: A)(implicit arr: ArrImut[A]): Unit = arr.unsafeSetElem(index(cood), value)

  /** Converts Cood to a Vec2. For a square grid this will be a simple 1 to 1 map. It is called coodToVec2Abs because most of the time, you will want
   * the Vec2 relative to the TileGrid centre. */
  def coodToVec2Abs(cood: Cood): Vec2

  /** Returns the index of an Array from its tile coordinate. */
  @inline def index(c: Int, y: Int): Int

  /** This gives the Vec2 of the Cood relative to a position on the grid and then scaled. (coodToVec2Abs(cood) - gridPosn -cen) * scale */
  def coodToVec2(cood: Cood, scale: Double = 1.0, gridPosn: Vec2 = Vec2Z): Vec2 = (coodToVec2Abs(cood) - gridPosn -cen) * scale

  /** The Coods of the vertices of a tile, from its centre Cood. */
  def tileVertCoods(cood: Cood): Coods



  def setTile[A <: AnyRef](cood: Cood, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(cood), value)
  def setTile[A <: AnyRef](xi: Int, yi: Int, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(xi, yi), value)

  def setTileSome[A <: AnyRef](x: Int, y: Int, value: A)(implicit arr: OptRefs[A]): Unit = arr.setSome(index(x, y), value)

  /** Not sure what this does. */
  def vertsCoodVecMap[A, ArrT <: ArrImut[A]](scale: Double = 1.0, relPosn: Vec2 = Vec2Z)(f: (Cood, Vec2) => A)(implicit build: ArrBuild[A, ArrT]) =
    vertCoods.map(c => f(c, coodToVec2(c, scale, relPosn)))

  /**************************************************************************************************/
  /* Methods that operate on tile sides. */

  /** Gives all the sideCoods of the grid with out duplicates. */
  def sideCoods: Coods = flatUniqueMapTiles[Cood, Coods] { cood => sideCoodsOfTile(cood) }

  def sideCoodToCoodLine(sideCood: Cood): CoodLine

  final def sideLinesAll(scale: Double = 1.0, relPosn: Vec2 = Vec2Z) : Line2s = flatMapTileCoods { cood =>
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

  def sideCoodsOfTile(tileCood: Cood): Coods

  def sidesForeach(f: Cood => Unit): Unit = sideCoods.foreach(f)

  def sidesMapCoodWithVec[A, ArrT <: ArrImut[A]](scale: Double = 1.0, relPosn: Vec2 = Vec2Z)(f: (Cood, Vec2) => A)(implicit build: ArrBuild[A, ArrT]) =
    sideCoods.map(c => f(c, coodToVec2(c, scale, relPosn)))

/**************************************************************************************************/
/* Methods that operate on tile vertices. */

  def vertCoods: Coods = flatUniqueMapTiles[Cood, Coods] { cood => tileVertCoods(cood) }
}