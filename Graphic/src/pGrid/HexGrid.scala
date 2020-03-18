/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, math.sqrt

trait HexGrid extends TileGrid

object HexGrid
{ /* converts Grid c to x. */
  val xRatio = 1.0 / sqrt(3)
  /** Verts start at Up and follow clockwise */
  val vertCoodsOfTile00: Coods = Coods(0 cc 1,  2 cc 1,  2 cc -1,  0 cc -1,  -2 cc -1,  -2 cc 1)
  def vertCoodsOfTile(x: Int, y: Int): Coods = vertCoodsOfTile(x cc y)
  def vertCoodsOfTile(tileCood: Cood): Coods = vertCoodsOfTile00.pMap(_ + tileCood)
  val sideCoodsOfTile00: Coods = Coods(1 cc 1, 2 cc 0, 1 cc -1, -1  cc -1, -2 cc 0, -1 cc 1)
  //val sideCoodsOfTile00List: List[Cood] = Coods(1 cc 1, 2 cc 0, 1 cc -1, -1  cc -1, -2 cc 0, -1 cc 1)
  def sideCoodsOfTile(tileCood: Cood): Coods = sideCoodsOfTile00.pMap(tileCood + _)
  val adjTileCoodsOfTile00: Coods = sideCoodsOfTile00.pMap(_ * 2)
  def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)

  def sideCoodToLineRel(sideCood: Cood, relPosn: Vec2): Line2 = sideCoodToCoodLine(sideCood).toLine2(c => coodToVec2(c) -relPosn)
  def sideCoodToLine(sideCood: Cood): Line2 = sideCoodToCoodLine(sideCood).toLine2(coodToVec2)
  def sideCoodToCoodLine(sideCood: Cood): CoodLine = sideCoodToCoodLine(sideCood.c, sideCood.y)

  def sideCoodToCoodLine(x: Int, y: Int): CoodLine = fOrientation(x, y, CoodLine(x - 1, y, x + 1, y), CoodLine(x, y + 1, x, y - 1),
    CoodLine(x + 1, y, x - 1, y))

  def coodToVec2Rel(cood: Cood, relPosn: Vec2): Vec2 = coodToVec2(cood.c, cood.y) -relPosn

  /** Used for regular HexGrids and the regular aspect of irregular Hexgrids */
  def coodToVec2(cood: Cood): Vec2 = coodToVec2(cood.c, cood.y)

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
  val v0 = Vec2(x0, y0)
   
  @inline def x1 = 2
  @inline def y1 = yDist
  /** The Up Right vertice */
  val v1 = Vec2(x1, y1)
   
  @inline def x2 = 2
  @inline def y2 = -yDist
  /** Down Right vertice */
  val v2 = Vec2(x2, y2)
   
  @inline def x3 = 0
  @inline def y3 = -yDist2
  /** The Down vertice */
  val v3 = Vec2(x3, y3)
   
  @inline def x4 = -2
  @inline def y4 = -yDist
  /** The Down Left vertice */
  val v4 = Vec2(x4, y4)
    
  @inline def x5 = -2
  @inline def y5 = yDist
  /** The up left vertice */
  val v5 = Vec2(x5, y5)
   
  val verts: Seq[Vec2] = Seq(v0, v1, v2, v3, v4,  v5)
  val cenVerts: Seq[Vec2] = Seq(Vec2Z, v0, v1, v2, v3, v4, v5)
             
  val triangleFan = Seq(Vec2Z, v0, v5, v4, v3, v2, v1)

  def latLong(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2, gridScale: Dist): LatLong =
  { val lat = (pt.y * gridScale + xyOffset.y) / EarthPolarRadius + latLongOffset.lat
    val long = (pt.x * gridScale + xyOffset.x) / (EarthEquatorialRadius * math.cos(lat)) + latLongOffset.long
    LatLong(lat, long)
  }

  def latLongToCood(latLong: LatLong, latLongOffset: LatLong, xyOffset: Dist2, gridScale: Dist): Vec2 =
  { val y: Double = ((latLong.lat - latLongOffset.lat) * EarthPolarRadius - xyOffset.y) / gridScale
    val x: Double = ((latLong.long - latLongOffset.long) * EarthEquatorialRadius * math.cos(latLong.lat) - xyOffset.x) / gridScale
    Vec2(x * xRatio, y)
  }

  def latLongU(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2): LatLong = latLong(pt, latLongOffset, xyOffset, Dist(gridU))
  def latLongV(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2): LatLong = latLong(pt, latLongOffset, xyOffset, Dist(gridV))
  def latLongW(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2): LatLong = latLong(pt, latLongOffset, xyOffset, Dist(gridW))
   
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
   
  def gridToGridDist(i: Int): Dist  = Dist(i / 32.0)
  val gridDistU: Dist = gridToGridDist(gridU)
  val gridDistV: Dist = gridToGridDist(gridV)
  val gridDistW: Dist = gridToGridDist(gridW)
  val gridDistX: Dist = gridToGridDist(gridX)
  val gridDistY: Dist = gridToGridDist(gridY)
}