/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import geom._, reflect.ClassTag, Colour._

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
trait TileGridOld
{
  /** Number of rows of tiles. This will be different to the number of rows of sides and and will be different to the number of rows of vertices for
   * HexGrids. */
  def numOfTileRows: Int

  /** The number of Rows of Sides. */
  @inline final def numOfSideRows: Int = numOfTileRows * 2 + 1

  /** The number of Rows of vertices. */
  @inline final def numOfVertRows: Int = numOfTileRows + 1

  /** The total number of Tiles in the tile Grid. */
  def numOfTiles: Int

  /** The bottom Tile Row of the TileGrid. the y value. */
  def yTileMin: Int

  /** The top Tile Row of the The TileGrid. the y value. */
  def yTileMax: Int

  /** The bottom Side Row of this TileGrid. The y value. */
  @inline final def ySideMin: Int = yTileMin - 1

  /** The top Side Row of this TileGrid. The y value. */
  @inline final def ySideMax: Int = yTileMax + 1

  /** The top Row of Tile vertices. */
  @inline final def yVertMax: Int = yTileMax + 1

  /** The bottom row of Tile vertices. */
  @inline final def yVertMin: Int = yTileMin - 1

  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  def cTileMin: Int

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  def cTileMax: Int

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
  {
    def adj(inp : Double): Double =inp match
    { case n if n > 1000 => inp - padding
      case n if n > 500 => inp - padding * inp / 1000.0
      case n if n > 10 => n
      case _ => 10
    }
    (adj(dispWidth) / adj(width).max(1)).min(adj(dispHeight) / height.max(1))
  }

  /** The centre of the grid by the y coordinate. */
  def yCen: Double = (yTileMin + yTileMax) / 2.0

  /** The centre point of the grid in Vec2 coordinates. */
  def cenPt = Pt2(xCen, yCen)

  /** The centre of the grid in Vec2 coordinates. */
  def cenVec = Vec2(xCen, yCen)

  /**************************************************************************************************/
  /* Methods that foreach, map, flatMap and fold over all the tiles of the tile grid. */

  /** foreachs over each tile's centre Roord. */
  final def foreach(f: Roord => Unit): Unit = foreachRow(y => rowForeachTile(y)(f))

  /** Foreach grid Row y coordinate. */
  final def foreachRow(f: Int => Unit): Unit = iToForeach(yTileMin, yTileMax, 2)(f)

  /** foreachs over each Tile's Roord in the given Row. The row is specified by its y value. */
  def rowForeachTile(y: Int)(f: Roord => Unit): Unit

  /** Maps from all tile Roords to an Arr of A. The Arr produced can be accessed by its Roord from this grid Class. */
  final def map[A, ArrT <: ArrBase[A]](f: Roord => A)(implicit build: ArrBuilder[A, ArrT]): ArrT =
  { val res = build.newArr(numOfTiles)
    foreach{ roord =>
      build.arrSet(res, arrIndex(roord), f(roord))
    }
    res
  }

  /** Maps from all tile Roords with index to an Arr of A. The Arr produced can be accessed by its Roord from this grid Class. */
  final def iMap[A, ArrT <: ArrBase[A]](f: (Roord, Int) => A)(implicit build: ArrBuilder[A, ArrT]): ArrT =
  { val res = build.newArr(numOfTiles)
    var i = 0
    foreach{ roord =>
      build.arrSet(res, arrIndex(roord), f(roord, i))
      i += 1
    }
    res
  }

  /** flatMaps from all tile Roords to an Arr of type ArrT. The elements of this array can not be accessed from this gird class as the TileGrid
   *  structure is lost in the flatMap operation. */
  final def flatMap[ArrT <: ArrBase[_]](f: Roord => ArrT)(implicit build: SeqFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numOfTiles)
    foreach{ roord => build.buffGrowArr(buff, f(roord))}
    build.buffToArr(buff)
  }

  /** flatmaps from all tile Roords to an Arr of type ArrT, removing all duplicate elements. */
  final def flatMapNoDupicates[A, ArrT <: ArrBase[A]](f: Roord => ArrT)(implicit build: ArrBuilder[A, ArrT]): ArrT =
  { val buff = build.newBuff(numOfTiles)
    foreach { roord =>
      val newVals = f(roord)
      newVals.foreach{newVal =>
        if (!buff.contains(newVal)) build.buffGrow(buff, newVal) }
    }
    build.buffToArr(buff)
  }

  /** foreachs over each tile's Roord and its centre Vec2. */
  final def foreachRVec(f: (Roord, Pt2) => Unit): Unit = foreach(r => f(r, roordToVec2Rel(r)))

  /** maps over each tile's Roord and its Polygon. */
  final def mapRPolygons[A, ArrT <: ArrBase[A]](f: (Roord, PolygonGen) => A)(implicit build: ArrBuilder[A, ArrT]): ArrT =
    map { roord =>
      val vcs = tileVertRoords(roord)
      val vvs = vcs.map(c => roordToPt2(c))
      f(roord, vvs.toPolygon)
    }

  /** The active tiles without any PaintElems. */
  def activeTiles: Arr[PolygonActive]

  /** New mutable Array of Tile data. All tiles set to an initial value. */
  final def newTileArray[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): Array[A] =
  { val res = new Array[A](numOfTiles)
    res.mapInPlace(_ => value)
    res
  }

  /** New mutable Array of Tile data Lists. */
  final def newTileArrayList[A](value: List[A] = Nil): Array[List[A]] =
  { val res = new Array[List[A]](numOfTiles)
    res.mapInPlace(_ => value)
    res
  }

  /** New immutable Arr of Tile data. */
  final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): TilesArr[A] =
  { val res = TilesArr[A](numOfTiles)
    res.mutSetAll(value)
    res
  }

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newTileArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): TilesArrOpt[A] = new TilesArrOpt(new Array[A](numOfTiles))

  /** New immutable Arr of Side Boolean data. */
  final def newTileBooleans: TileBooleans = new TileBooleans(new Array[Boolean](numOfTiles))

  def cenRoordTexts(textSize: Int = 26, colour: Colour = Black): Arr[TextGraphic] = map(r => TextGraphic(r.ycStr, textSize, roordToPt2(r), colour))

  final def cenRoordIndexTexts(textSize: Int = 26, colour: Colour = Black): Arr[TextGraphic] =
    iMap((r, i) => TextGraphic(i.str + ": " + r.ycStr, textSize, roordToPt2(r)))

  /** Quick method to give the Tile, Side and Vertex Roord Text Grahics. */
  final def cenSideVertRoordText: Arr[GraphicAffineElem] = cenRoordTexts() ++ sideRoordTexts() ++ vertRoordTexts()

  /**************************************************************************************************/
  /* Methods that operate on individual tiles. */

  /** Sets element in a flat Tiles Arr according to its Roord. */
  def setTile[A](roord: Roord, value: A)(implicit arr: ArrBase[A]): Unit = arr.unsafeSetElem(arrIndex(roord), value)

  /** Converts Roord to a Vec2. For a square grid this will be a simple 1 to 1 map. */
  def roordToPt2(roord: Roord): Pt2

  /** Converts Roord, input as y and components, to a Vec2. For a square grid this will be a simple 1 to 1 map. */
  def roordToPt2(y: Int, c: Int): Pt2 = roordToPt2(y rr c)

  /** Converts Roord to a Vec2. For a square grid this will be a simple 1 to 1 map. */
  def roordToVec(roord: Roord): Vec2 =
  { val r = roordToPt2(roord)
    Vec2(r.x, r.y)
  }

  /** Converts Roord, input as y and components, to a Vec2. For a square grid this will be a simple 1 to 1 map. */
  def roordToVec(y: Int, c: Int): Vec2 = roordToVec(y rr c)


  /** Gives the index into an Arr / Array of Tile data from its tile Roord. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(r: Roord): Int = arrIndex(r.y, r.c)

  /** Gives the index into an Arr / Array of Tile data from its tile Roord. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline def arrIndex(y: Int, c: Int): Int

  /** This gives the Vec2 of the Roord relative to a position on the grid and then scaled. (roordToVec2Abs(roord) - gridPosn -cen) * scale */
  def roordToVec2Rel(roord: Roord, scale: Double = 1.0, gridPosn: Pt2 = Pt2Z): Pt2 = (roordToPt2(roord).invSlate(gridPosn) - cenVec).scale(scale)

  def roordToPolygon(roord: Roord): Polygon = tileVertRoords(roord).map(c => roordToPt2(c)).toPolygon

  /** The Roords of the vertices of a tile, from its centre Roord. */
  def tileVertRoords(roord: Roord): Roords

  /** Method may be removed, probably better to dispatch from the Arr, with the grid as parameter. */
  def setTile[A <: AnyRef](roord: Roord, value: A)(implicit arr: Arr[A]): Unit = arr.unsafeSetElem(arrIndex(roord), value)

  /** Method may be removed, probably better to dispatch from the Arr, with the grid as parameter. */
  def setTile[A <: AnyRef](xi: Int, yi: Int, value: A)(implicit arr: Arr[A]): Unit = arr.unsafeSetElem(arrIndex(xi, yi), value)

  def isTileRoord(r: Roord): Boolean

  /** Tests whether the Tile exists within the TileGrid. Will throw on an invalid Tile Roord. */
  final def tileExists(r: Roord): Boolean = tileExists(r.y, r.c)

  /** Tests whether the Tile exists within the TileGrid. Will throw on an invalid Tile Roord. */
  def tileExists(y: Int, c: Int): Boolean

  /**************************************************************************************************/
  /* Methods that operate on tile sides. */

  /** foreach side's Roords, calls the effectful function. */
  final def sidesForeach(f: Roord => Unit): Unit = sideRowForeach(y => rowForeachSide(y)(f))

  /** Maps from each sides Roord to an ArrBase of A. */
  def sidesMap[A, ArrT <: ArrBase[A]](f: Roord => A)(implicit build: ArrBuilder[A, ArrT]) =
  { val res = build.newArr(numOfSides)
    var count = 0
    sidesForeach{r => build.arrSet(res, count, f(r)); count += 1 }
    res
  }

  /** Maps from each sides Roord to an ArrBase of A. */
  def sidesIMap[A, ArrT <: ArrBase[A]](f: (Roord, Int) => A)(implicit build: ArrBuilder[A, ArrT]) =
  { val res = build.newArr(numOfSides)
    var count = 0
    sidesForeach{r => build.arrSet(res, count, f(r, count)); count += 1 }
    res
  }

  /** Foreachs over each Row of Sides. Users will not normally need to use this method directly. */
  def sideRowForeach(f: Int => Unit) : Unit = iToForeach(yTileMin - 1, yTileMax + 1)(f)

  def sideInnerRowForeach(f: Int => Unit) : Unit = iToForeach(yTileMin, yTileMax)(f)

  /** foreachs over each Side's Roord in the given Row. Users will not normally need to access this method directly. */
  def rowForeachSide(y: Int)(f: Roord => Unit): Unit

  /** The number of Sides in the TileGrid.*/
  final val numOfSides: Int =
  { var count = 0
    sidesForeach(r => count += 1)
    count
  }

  /** The number of tile vertices in the TileGrid. */
  final val numOfVerts: Int =
  { var count = 0
    vertsForeach(r => count += 1)
    count
  }

  /** Gives all the sideRoords of the grid with out duplicates. */
  def sideRoords: Roords = sidesMap(r => r)

  def sideRoordToRoordLine(sideRoord: Roord): RoordLine

  final def sideLines : LineSegs = flatMap { roord =>
    val c1: Roords = sideRoordsOfTile(roord)
    val c2s: LineSegs = c1.map(orig => sideRoordToLine2(orig))
    c2s
  }

  /** This gives the all tile grid lines in a single colour and line width. */
  final def sidesDraw(lineWidth: Double, colour: Colour = Black) = sideLines.draw(lineWidth, colour)

  /** Side Roord to Line2 relative to a position on the grid and then scaled. */
  final def sideRoordToLine2(sideRoord: Roord): LineSeg =
    sideRoordToRoordLine(sideRoord).toLine2(roord => roordToPt2(roord))

  def sideRoordsOfTile(tileRoord: Roord): Roords

  def sideRoordTexts(textSize: Int = 22, colour: Colour = Blue): Arr[TextGraphic] = sidesMap{ r => TextGraphic(r.ycStr, textSize, roordToPt2(r), colour) }

  def sideRoordIndexTexts(textSize: Int = 26, colour: Colour = Blue): Arr[TextGraphic] =
    sidesIMap((r, i) => TextGraphic(i.str + ": " + r.ycStr, textSize, roordToPt2(r), colour))

  /** The index from a Side Roord into an Arr of Side data. */
  def sideArrIndex(y: Int, c: Int): Int

  /** The index from a Side Roord into an Arr of Side data. */
  @inline final def sideArrIndex(roord: Roord): Int = sideArrIndex(roord.y, roord.c)


  /**************************************************************************************************/
  /* Methods that operate on tile vertices. */

  /** foreach vertex's Roord, calls the effectful function. */
  final def vertsForeach(f: Roord => Unit): Unit = vertRowForeach(y => rowForeachVert(y)(f))

  /** Foreach row of vertices apply the effctful function taking the y dimension as parameter. */
  final def vertRowForeach(f: Int => Unit) : Unit = iToForeach(yTileMin - 1, yTileMax + 1, 2)(f)

  /** foreach Vertice's Roord in the vertex row applies the effectful function. */
  def rowForeachVert(y: Int)(f: Roord => Unit): Unit

  /** maps from each Vertex's Roord to a value of type A. Returns a specialiased immutable Arr. */
  def vertsMap[A, ArrT <: ArrBase[A]](f: Roord => A)(implicit build: ArrBuilder[A, ArrT]) =
  { val res = build.newArr(numOfVerts)
    var count = 0
    vertsForeach{r => build.arrSet(res, count, f(r)); count += 1 }
    res
  }

  /** Maps from each vertex's Roord with index, to a specialised Arr of type A. */
  def vertsIMap[A, ArrT <: ArrBase[A]](f: (Roord, Int) => A)(implicit build: ArrBuilder[A, ArrT]) =
  { val res = build.newArr(numOfVerts)
    var count = 0
    vertsForeach{r => build.arrSet(res, count, f(r, count)); count += 1 }
    res
  }

  def vertRoords: Roords = vertsMap(r => r)

  def vertRoordTexts(fontSize: Int = 20, colour: Colour = Red) = vertsMap{ r => TextGraphic(r.ycStr, fontSize, roordToPt2(r), colour) }

  def vertRoordIndexTexts(textSize: Int = 20, colour: Colour = Red): Arr[TextGraphic] =
    vertsIMap((r, i) => TextGraphic(i.str + ": " + r.ycStr, textSize, roordToPt2(r), colour))

  /** New immutable Arr of Side Boolean data. */
  def newSideBooleans: SideBooleans = new SideBooleans(new Array[Boolean](numOfSides))

  /** New immutable Arr of vertex Int data. */
  def newVertInts: VertInts = new VertInts(new Array[Int](numOfVerts))
}