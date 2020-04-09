package ostrat
package pGrid
import geom._, reflect.ClassTag, Colour.Black

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
  def cStep: Int

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

  /** foreachs over each tile's centre Roord. */
  def foreach(f: Roord => Unit): Unit

  /** Foreach grid Row yi coordinate. */
  def ForeachRow(f: Int => Unit): Unit = iToForeach(yTileMin, yTileMax, 2)(f)

  /** Maps from all tile Roords to an Arr of A. The Arr produced can be accessed by its Roord from this grid Class. */
  def map[A, ArrT <: Arr[A]](f: Roord => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
  { val res = build.newArr(numOfTiles)
    foreach{ roord =>
      build.arrSet(res, index(roord), f(roord))
    }
    res
  }

  /** flatMaps from all tile Roords to an Arr of type ArrT. The elements of this array can not be accessed from this gird class as the TileGrid
   *  structure is lost in the flatMap operation. */
  def flatMap[ArrT <: Arr[_]](f: Roord => ArrT)(implicit build: ArrFlatBuild[ArrT]): ArrT =
  { val buff = build.newBuff(numOfTiles)
    foreach{ roord => build.buffGrowArr(buff, f(roord))}
    build.buffToArr(buff)
  }

  /** flatmaps from all tile Roords to an Arr of type ArrT, removing all duplicate elements. */
  def flatMapNoDupicates[A, ArrT <: Arr[A]](f: Roord => ArrT)(implicit build: ArrBuild[A, ArrT]): ArrT =
  { val buff = build.newBuff(numOfTiles)
    foreach { roord =>
      val newVals = f(roord)
      newVals.foreach{newVal =>
      if (!buff.contains(newVal)) build.buffGrow(buff, newVal) }
    }
    build.buffToArr(buff)
  }

  /** foreachs over each tile centre Vec2. */
  def foreachVec(f: (Roord, Vec2) => Unit): Unit = foreach(r => f(r, roordToVec2Rel(r)))

  def mapPolygons[A, ArrT <: Arr[A]](f: (Roord, Polygon) => A)(implicit build: ArrBuild[A, ArrT]): ArrT =
    map{ roord =>
      val vcs = tileVertRoords(roord)
      val vvs = vcs.map(c => roordToVec2(c))
      f(roord, vvs.toPolygon)
    }

  def activeTiles: Refs[PolyActiveOnly] = map{ roord =>
    val vcs = tileVertRoords(roord)
    val vvs = vcs.map(r => roordToVec2(r))
    vvs.toPolygon.active(roord.toHexTile)
  }

  /** Creates a new uninitialised Arr of the grid length. */
  def newArr[A, AA <: Arr[A]](implicit build: ArrBuild[A, AA]): AA = build.newArr(numOfTiles)

  def newRefs[A <: AnyRef](implicit build: ArrBuild[A, Refs[A]]): Refs[A] = build.newArr(numOfTiles)

  def newArray[A](implicit ct: ClassTag[A]): Array[A] = new Array[A](numOfTiles)

  def newArraySet[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): Array[A] =
  { val res = new Array[A](numOfTiles)
    res.mapInPlace(_ => value)
    res
  }

  def newArrayListSet[A](value: List[A] = Nil): Array[List[A]] =
  { val res = new Array[List[A]](numOfTiles)
    res.mapInPlace(_ => value)
    res
  }

  @deprecated def newRefsSetOld[A <: AnyRef](value: A)(implicit build: ArrBuild[A, Refs[A]]): Refs[A] =
  { val res = build.newArr(numOfTiles)
    res.setAll(value)
    res
  }

  def newTilesRefSet[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): TilesRef[A] =
  { val res = TilesRef[A](numOfTiles)
    res.mutSetAll(value)
    res
  }

  @deprecated def newOptRefsOld[A <: AnyRef](implicit ct: ClassTag[A]): OptRefs[A] = OptRefs(numOfTiles)
  def newTilesOptRef[A <: AnyRef](implicit ct: ClassTag[A]): TilesOptRef[A] = new TilesOptRef(new Array[A](numOfTiles))

  @deprecated def newSideOptRefs[A <: AnyRef](implicit ct: ClassTag[A]): OptRefs[A] = OptRefs(numOfSides)

  def newSideBooleans: SideBooleans = new SideBooleans(new Array[Boolean](numOfSides))

  def cenRoordTexts(textSize: Int = 26) = map(r => TextGraphic(r.ycStr, textSize, roordToVec2(r)))

  def cenSideVertRoordText: Refs[PaintElem] =
  {
    val sideTexts = sidesMap{ r =>  TextGraphic(r.ycStr, 22, roordToVec2(r), Colour.Blue) }
    val vertTexts = vertsMap{ r =>  TextGraphic(r.ycStr, 20, roordToVec2(r), Colour.Red) }
    cenRoordTexts() ++ sideTexts ++ vertTexts
  }

/**************************************************************************************************/
/* Methods that operate on individual tiles. */

  /** Returns the index of an Array from its tile coordinate. */
  @inline final def index(roord: Roord): Int = index(roord.y, roord.c)

  /** Sets element in a flat Tiles Arr according to its Roord. */
  def setTile[A](roord: Roord, value: A)(implicit arr: Arr[A]): Unit = arr.unsafeSetElem(index(roord), value)

  /** Converts Roord to a Vec2. For a square grid this will be a simple 1 to 1 map. */
  def roordToVec2(roord: Roord): Vec2

  /** Returns the index of an Array from its tile coordinate. */
  @inline def index(y: Int, c: Int): Int

  /** This gives the Vec2 of the Roord relative to a position on the grid and then scaled. (roordToVec2Abs(roord) - gridPosn -cen) * scale */
  def roordToVec2Rel(roord: Roord, scale: Double = 1.0, gridPosn: Vec2 = Vec2Z): Vec2 = (roordToVec2(roord) - gridPosn -cen) * scale

  def roordToPolygon(roord: Roord): Polygon = tileVertRoords(roord).map(c => roordToVec2(c)).toPolygon

  /** The Roords of the vertices of a tile, from its centre Roord. */
  def tileVertRoords(roord: Roord): Roords

  /** Method may be removed, probably better to dispatch from the Arr, with the grid as parameter. */
  def setTile[A <: AnyRef](roord: Roord, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(roord), value)

  /** Method may be removed, probably better to dispatch from the Arr, with the grid as parameter. */
  def setTile[A <: AnyRef](xi: Int, yi: Int, value: A)(implicit arr: Refs[A]): Unit = arr.unsafeSetElem(index(xi, yi), value)

  def isTileRoord(r: Roord): Boolean
  def tileExists(r: Roord): Boolean = ???

  /**************************************************************************************************/
  /* Methods that operate on tile sides. */

  def numOfSides: Int = sideRoords.length

  /** Gives all the sideRoords of the grid with out duplicates. */
  def sideRoords: Roords = flatMapNoDupicates[Roord, Roords] { roord => sideRoordsOfTile(roord) }

  def sideRoordToRoordLine(sideRoord: Roord): RoordLine

  final def sideLines : Line2s = flatMap { roord =>
    val c1: Roords = sideRoordsOfTile(roord)
    val c2s: Line2s = c1.map(orig => sideRoordToLine2(orig))
    c2s
  }

  /** This gives the all tile grid lines in a single colour and line width. */
  final def sidesDraw(lineWidth: Double, colour: Colour = Black) = sideLines.draw(lineWidth, colour)

  /** Side Roord to Line2 relative to a position on the grid and then scaled. */
  final def sideRoordToLine2(sideRoord: Roord): Line2 =
    sideRoordToRoordLine(sideRoord).toLine2(roord => roordToVec2(roord))

  def sideRoordsOfTile(tileRoord: Roord): Roords

  def sideIndex(roord: Roord): Int = ???

  def sidesForeach(f: Roord => Unit): Unit = sideRoords.foreach(f)

  /** Maps from each sides roord to an Arr of A. */
  def sidesMap[A, ArrT <: Arr[A]](f: Roord => A)(implicit build: ArrBuild[A, ArrT]) = sideRoords.map(r => f(r))

/**************************************************************************************************/
/* Methods that operate on tile vertices. */

  def vertsMap[A, ArrT <: Arr[A]](f: Roord => A)(implicit build: ArrBuild[A, ArrT]) =
    vertRoords.map(r => f(r))

  def vertRoords: Roords = flatMapNoDupicates[Roord, Roords] { roord => tileVertRoords(roord) }
}