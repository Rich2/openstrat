/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import reflect.ClassTag, geom._, pglobe._, math.sqrt

/** To be deprecated. A Hex tile own the right sides, upRight, Right and DownRight. It owns the Up, UpRight and DownRight Vertices numbers 0, 1 and 2.
 *  */
abstract class HexGridAncient[TileT <: TileAncient, SideT <: TileSideAncient](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int,
  val yTileMax: Int, val turnNum: Int)(implicit val evTile: ClassTag[TileT], val evSide: ClassTag[SideT]) extends TileGridAncient[TileT, SideT]
{ override val xRatio: Double = HexGridAncient.xRatio
  override def xArrLen: Int = (xTileMax - xTileMin) / 4 + 1 //+1 for zeroth tile
  override val yArrLen: Int = yTileMax - yTileMin + 3//+ 1 for lowersides +1 for zeroth tile, + 1 for upper side(s)
  override val arr: Array[TileT] = new Array[TileT](arrLen)
  override def vertCoodsOfTile(tileCood: Cood): Coods = HexGridAncient.vertCoodsOfTile(tileCood)
  override def sideCoodsOfTile(tileCood: Cood): Coods = HexGridAncient.sideCoodsOfTile(tileCood)
  override def xStep: Int = 4
  override def xSideMin: Int = xTileMin - 2
  override def xSideMax: Int = xTileMax + 2

  /** For each Tile's XY in part of a row. */
  final override def rowForeachTilesXY(y: Int, xStart: Int, xEnd: Int, f: (Int, Int) => Unit): Unit =
  {
    val xPt: Int = ife(y %% 4 == 0, 0, 2)
    val xFinalStart = rowTileXStart(y).max(xStart).roundUpTo(_ %% 4 == xPt)
    val xFinalEnd = rowTileXEnd(y).min(xEnd).roundDownTo(_ %% 4 == xPt)
    (xFinalStart to xFinalEnd by 4).foreach(x => f(x, y))
  }

  def isTile(x: Int, y: Int): Boolean = getTile(x, y) != null

  override def vertCoodLineOfSide(x: Int, y: Int): CoodLine = HexGridAncient.sideCoodToCoodLine(x, y)

  override def coodIsTile(x: Int, y: Int): Unit = ifNotExcep(
    x %% 4 == 0 & y %% 4 == 0 | x %% 4 == 2 & y %% 4 == 2,
    x.commaInts(y) -- "is an invalid Hex tile coordinate")

  override def coodIsSide(x: Int, y: Int): Unit = ifNotExcep(
    (x %% 4 == 0 & y %% 4 == 2) | (x %% 4 == 2 & y %% 4 == 0) | (x.isOdd & y.isOdd),
    x.commaInts(y) -- "is an invalid Hexside tile coordinate")

  override def sidesTileCoods(x: Int, y: Int) = ife3(
    (x %% 4 == 0 & y %% 4 == 2) | (x %% 4 == 2 & y %% 4 == 0), (Cood(x -2, y), Cood(x + 2, y)),
    (x %% 4 == 1 & y %% 4 == 1) | (x %% 4 == 3 & y %% 4 == 3), (Cood(x - 1, y - 1), Cood(x + 1, y + 1)),
    (x %% 4 == 1 & y %% 4 == 3) | (x %% 4 == 3 & y %% 4 == 1), (Cood(x - 1, y + 1), Cood(x + 1, y - 1)),
    excep("Invalid Hex Side Coordinate".commaInts(x, y)))

  override def tileDestinguish[A](cood: Cood, v1: A, v2: A, v3: A, v4: A): A =  cood match
  { case Cood(x, y) if x %% 8 == 0 & y %% 4 == 0 => v1
    case Cood(x, y) if x %% 8 == 4 & y %% 4 == 0 => v2
    case Cood(x, y) if x %% 8 == 2 => v3
    case Cood(x, y) if x %% 8 == 6 => v4
  }

  /** Warning needs modification. */
  override def adjTileCoodsOfTile(tileCood: Cood): Coods = HexGridAncient.adjTileCoodsOfTile(tileCood)

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   *  departure and the tile of arrival. */
  def getHCost(startCood: Cood, endCood: Cood): Int =
  { val diff = endCood - startCood
    val x: Int = diff.xi.abs
    val y: Int = diff.yi.abs

    y - x match
    { case 0 => x
      case n if n > 0 => y
      case n if n %% 4 == 0 => y - n / 2 //Subtract because n is negative, y being greater than x
      case n => y - n / 2 + 2
    }
  }
}

object HexGridAncient
{ /* converts Grid c to x. */
  val xRatio = 1.0 / sqrt(3)
  /** Verts start at Up and follow clockwise */
  val vertCoodsOfTile00: Coods = Coods(0 cc 1,  2 cc 1,  2 cc -1,  0 cc -1,  -2 cc -1,  -2 cc 1)
  def vertCoodsOfTile(x: Int, y: Int): Coods = vertCoodsOfTile(x cc y)
  def vertCoodsOfTile(tileCood: Cood): Coods = vertCoodsOfTile00.dataMap(_ + tileCood)
  val sideCoodsOfTile00: Coods = Coods(1 cc 1, 2 cc 0, 1 cc -1, -1  cc -1, -2 cc 0, -1 cc 1)
  //val sideCoodsOfTile00List: List[Cood] = Coods(1 cc 1, 2 cc 0, 1 cc -1, -1  cc -1, -2 cc 0, -1 cc 1)
  def sideCoodsOfTile(tileCood: Cood): Coods = sideCoodsOfTile00.dataMap(tileCood + _)
  val adjTileCoodsOfTile00: Coods = sideCoodsOfTile00.dataMap(_ * 2)
  def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.dataMap(tileCood + _)

  def sideCoodToLine(sideCood: Cood): LineSeg = sideCoodToCoodLine(sideCood).toLine2(coodToVec2)
  def sideCoodToCoodLine(sideCood: Cood): CoodLine = sideCoodToCoodLine(sideCood.xi, sideCood.yi)
  //override def sideCoodToCoodLine(sideCood: Cood): ostrat.pGrid.CoodLine = HexGrid.sideCoodToCoodLine(sideCood)

  def sideCoodToCoodLine(x: Int, y: Int): CoodLine = fOrientation(x, y, CoodLine(x - 1, y, x + 1, y), CoodLine(x, y + 1, x, y - 1),
    CoodLine(x + 1, y, x - 1, y))

  /** Used for regular HexGrids and the regular aspect of irregular Hexgrids */
  def coodToVec2(cood: Cood): Pt2 = coodToPt2(cood.xi, cood.yi)

  def coodToPt2(c: Int, y: Int): Pt2 =
  { def x: Double = c * xRatio
    (c %% 4, y %% 4) match
    { case (xr, yr) if yr.isEven && xr.isEven => Pt2(x, y)
    case (_, yr) if yr.isEven => throw new Exception("Hex Cood " + c.toString -- y.toString + ", y is even but x is odd. This is an invalid HexCood")
    case (xr, yr) if xr.isOdd  && yr.isOdd => Pt2(x, y)
    case (0, 1) | (2, 3)  =>  Pt2(x, y + yDist /2)
    case _ => Pt2(x, y - yDist / 2)
    }
  }

  def coodToVec2(c: Int, y: Int): Vec2 =
  { def x: Double = c * xRatio
    (c %% 4, y %% 4) match
    { case (xr, yr) if yr.isEven && xr.isEven => Vec2(x, y)
    case (_, yr) if yr.isEven => throw new Exception("Hex Cood " + c.toString -- y.toString + ", y is even but x is odd. This is an invalid HexCood")
    case (xr, yr) if xr.isOdd  && yr.isOdd => Vec2(x, y)
    case (0, 1) | (2, 3)  =>  Vec2(x, y + yDist /2)
    case _ => Vec2(x, y - yDist / 2)
    }
  }

  @inline def fOrientation[A](x: Int, y: Int, upRight: => A, rightSide: => A, downRight: => A): A = if3Excep(
    (y.div4Rem1 && x.div4Rem1) || (y.div4Rem3 && x.div4Rem3), upRight,
    (y.div4Rem0 && x.div4Rem2) || (y.div4Rem2 && x.div4Rem0), rightSide,
    (y.div4Rem1 && x.div4Rem3) || (y.div4Rem3 && x.div4Rem1), downRight,
    "invalid Hex Side coordinate: " + x.toString.appendCommas(y.toString))

  def orientationStr(x: Int, y: Int): String = fOrientation(x, y, "UpRight", "Right", "DownRight")
  /** The previous value was 2 / sqrt(3). */
  val yDist = 2.0 / 3

  /** The previous value was 4 /  sqrt(3). */
  val yDist2 = 4.0 / 3

  @inline def x0 = 0
  @inline def y0 = yDist2
  /** The Up vertice */
  val v0 = Pt2(x0, y0)

  @inline def x1 = 2
  @inline def y1 = yDist
  /** The Up Right vertice */
  val v1 = Pt2(x1, y1)

  @inline def x2 = 2
  @inline def y2 = -yDist
  /** Down Right vertice */
  val v2 = Pt2(x2, y2)

  @inline def x3 = 0
  @inline def y3 = -yDist2
  /** The Down vertice */
  val v3 = Pt2(x3, y3)

  @inline def x4 = -2
  @inline def y4 = -yDist
  /** The Down Left vertice */
  val v4 = Pt2(x4, y4)

  @inline def x5 = -2
  @inline def y5 = yDist
  /** The up left vertice */
  val v5 = Pt2(x5, y5)

  val verts: Seq[Pt2] = Seq(v0, v1, v2, v3, v4,  v5)
  val cenVerts: Seq[Pt2] = Seq(Pt2Z, v0, v1, v2, v3, v4, v5)

  val triangleFan = Seq(Pt2Z, v0, v5, v4, v3, v2, v1)

  def latLong(pt: Pt2, latLongOffset: LatLong, xyOffset: PtMetre2, gridScale: Metres): LatLong =
  { val lat = (pt.y * gridScale + xyOffset.y) / EarthPolarRadius + latLongOffset.latRadians
    val long = (pt.x * gridScale + xyOffset.x) / (EarthEquatorialRadius * math.cos(lat)) + latLongOffset.longRadians
    LatLong.radians(lat, long)
  }

  def latLongToCood(latLong: LatLong, latLongOffset: LatLong, xyOffset: PtMetre2, gridScale: Metres): Pt2 =
  { val y: Double = ((latLong.latRadians - latLongOffset.latRadians) * EarthPolarRadius - xyOffset.y) / gridScale
    val x: Double = ((latLong.longRadians - latLongOffset.longRadians) * EarthEquatorialRadius * math.cos(latLong.latRadians) - xyOffset.x) / gridScale
    Pt2(x * xRatio, y)
  }

  def latLongU(pt: Pt2, latLongOffset: LatLong, xyOffset: PtMetre2): LatLong = latLong(pt, latLongOffset, xyOffset, Metres(gridU))
  def latLongV(pt: Pt2, latLongOffset: LatLong, xyOffset: PtMetre2): LatLong = latLong(pt, latLongOffset, xyOffset, Metres(gridV))
  def latLongW(pt: Pt2, latLongOffset: LatLong, xyOffset: PtMetre2): LatLong = latLong(pt, latLongOffset, xyOffset, Metres(gridW))

  val gridA: Int = 1//3.125cm
  val gridB: Int = 2//6.25cm
  val gridC: Int = 4//12.5cm
  val gridD: Int = 8//0.25m
  val gridE: Int = 16//0.5m
  val gridF: Int = 32//1m
  val gridG: Int = 64//2m
  val gridH: Int = 128//4m
  val gridI: Int = 256//8m
  val gridJ: Int = 512//16m
  val gridK: Int = 1024//32m
  val gridL: Int = 2048//64m
  val gridM: Int = 4096//128m
  val gridN: Int = 8192//256m
  val gridO: Int = 16384//512m
  val gridP: Int = 32768//1.024km
  val gridQ: Int = 65536//2.048km
  val gridR: Int = 131072//4.096km
  val gridS: Int = 262144//8.192km
  val gridT: Int = 524288//16.384km
  val gridU: Int = 1048576//32.768km
  val gridV: Int = 2097152//65.536km
  val gridW: Int = 4194304//131.072km
  val gridX: Int = 8388608//266.144km
  val gridY: Int = 16777216//524.288km
  val gridZ: Int = 33554432//1048.576km

  def gridToGridDist(i: Int): Metres  = Metres(i / 32.0)
  val gridDistU: Metres = gridToGridDist(gridU)
  val gridDistV: Metres = gridToGridDist(gridV)
  val gridDistW: Metres = gridToGridDist(gridW)
  val gridDistX: Metres = gridToGridDist(gridX)
  val gridDistY: Metres = gridToGridDist(gridY)
}