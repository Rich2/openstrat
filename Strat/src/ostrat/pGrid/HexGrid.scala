/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, pGrid._,  math.sqrt, reflect.ClassTag

trait HGrid[TileT] extends TGrid[TileT]
{
  type GridT[A] = HGrid[A]
  @inline override def xStep: Int = 4
  override def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)
  override def coodIsTile(x: Int, y: Int): Unit = ifNotExcep(
    x %% 4 == 0 & y %% 4 == 0 | x %% 4 == 2 & y %% 4 == 2,
    x.toString.commaAppend(y.toString) -- "is an invalid Hex tile coordinate")
  override def vertCoodsOfTile(tileCood: Cood): Coods = HexGrid.vertCoodsOfTile(tileCood)
  def cen: Vec2 = (xMax + xMin) / 2 vv (yMin + yMax) * HexGrid.yRatio / 2
  override def sideCoodLine(x: Int, y: Int): CoodLine = HexGrid.vertCoodsOfSide(x, y)

  def tilePolygonReduce(xc: Int, yc: Int, vertoffs: VertOffs): Polygon = //tilePolygon(xc, yc)
  //(, up: Int = 0 upRt: Int = 0, dnRt: Int = 0, down: Int = 0, dnLt: Int = 0, upLt: Int = 0): Polygon =
  {
    import HexGrid.{coodToVec2 => ctv}
 //   val tc: Vec2 = ctv(xc, yc)

   // def getv(x: Int, y: Int, offset: Int): Vec2 =
    //{
     // val aOff: Int = offset.max(0).min(3)
      //(tc * aOff + ctv(x cc y) * (4 - aOff)) / 4
   // }
    var acc: Buff[Double] = Buff(24)
    acc.app2(ctv(xc, yc + 1))
    acc.app2(ctv(xc + 2, yc + 1))
    acc.app2(ctv(xc + 2, yc - 1))
    acc.app2(ctv(xc, yc - 1))
    acc.app2(ctv(xc - 2, yc - 1))
    //  case r => ???    }
    acc.app2(ctv(xc -2, yc + 1))
    new Polygon(acc.toArray)
  }

  def tileDisplayPolygonReduce(x: Int, y: Int, scale: Double, vertOffs: VertOffs, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z): Polygon =
    tilePolygonReduce(x, y, vertOffs).fTrans(v => (v - mapOffset) * scale - displayOffset)
}

/** A Hex tile own the right sides, upRight, Right and DownRight. It owns the Up, UpRight and DownRight Vertices numbers 0, 1 and 2. */
abstract class HexGrid[TileT <: Tile, SideT <: TileSide](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int, val turnNum: Int)
(implicit val evTile: ClassTag[TileT], val evSide: ClassTag[SideT]) extends TileGrid[TileT, SideT]// with HexGrid[TileT]   
{
  override val yRatio: Double = HexGrid.yRatio  

  
  override def xArrLen: Int = (xTileMax - xTileMin) / 4 + 1 //+1 for zeroth tile
  override val yArrLen: Int = yTileMax - yTileMin + 3//+ 1 for lowersides +1 for zeroth tile, + 1 for upper side(s)
  override val arr: Array[TileT] = new Array[TileT](arrLen)
  override def vertCoodsOfTile(tileCood: Cood): Coods = HexGrid.vertCoodsOfTile(tileCood)
  override def sideCoodsOfTile(tileCood: Cood): Coods = HexGrid.sideCoodsOfTile(tileCood)   
  override def xStep: Int = 4
  override def xSideMin: Int = xTileMin - 2
  override def xSideMax: Int = xTileMax + 2

  /** For each Tile's XY in part of a row. */
  final override def rowForeachTilesXY(y: Int, xStart: Int, xEnd: Int, f: (Int, Int) => Unit): Unit =
  {
    val xPt: Int = ife(y %% 4 == 0, 0, 2)
    val xFinalStart = rowTileXStart(y).max(xStart).incrementTill(_ %% 4 == xPt)
    val xFinalEnd = rowTileXEnd(y).min(xEnd).decrementTill(_ %% 4 == xPt)
    (xFinalStart to xFinalEnd by 4).foreach(x => f(x, y))
  }

  def isTile(x: Int, y: Int): Boolean = getTile(x, y) != null   
   
  override def vertCoodLineOfSide(x: Int, y: Int): CoodLine = HexGrid.vertCoodsOfSide(x, y)
  
  override def coodIsTile(x: Int, y: Int): Unit = ifNotExcep(
    x %% 4 == 0 & y %% 4 == 0 | x %% 4 == 2 & y %% 4 == 2,
    x.toString.commaAppend(y.toString) -- "is an invalid Hex tile coordinate")
  
  override def coodIsSide(x: Int, y: Int): Unit = ifNotExcep(
    (x %% 4 == 0 & y %% 4 == 2) | (x %% 4 == 2 & y %% 4 == 0) | (x.isOdd & y.isOdd),
    x.toString.commaAppend (y.toString) -- "is an invalid Hexside tile coordinate")
  
  override def sidesTileCoods(x: Int, y: Int) = ife3(
    (x %% 4 == 0 & y %% 4 == 2) | (x %% 4 == 2 & y %% 4 == 0), (Cood(x -2, y), Cood(x + 2, y)),
    (x %% 4 == 1 & y %% 4 == 1) | (x %% 4 == 3 & y %% 4 == 3), (Cood(x - 1, y - 1), Cood(x + 1, y + 1)),
    (x %% 4 == 1 & y %% 4 == 3) | (x %% 4 == 3 & y %% 4 == 1), (Cood(x - 1, y + 1), Cood(x + 1, y - 1)),
    excep("Invalid Hex Side Coordinate".commaAppend(x.toString, y.toString)))
    
  override def tileDestinguish[A](cood: Cood, v1: A, v2: A, v3: A, v4: A): A =  cood match
  { case Cood(x, y) if x %% 8 == 0 & y %% 4 == 0 => v1
    case Cood(x, y) if x %% 8 == 4 & y %% 4 == 0 => v2
    case Cood(x, y) if x %% 8 == 2 => v3
    case Cood(x, y) if x %% 8 == 6 => v4    
  }
   
  /** Warning needs modification. */
  override def adjTileCoodsOfTile(tileCood: Cood): Coods = HexGrid.adjTileCoodsOfTile(tileCood)
   
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of 
   *  departure and the tile of arrival. */
  def getHCost(startCood: Cood, endCood: Cood): Int =
  { val diff = endCood - startCood
    val x: Int = diff.x.abs
    val y: Int = diff.y.abs
     
    y - x match
    { case 0 => x 
      case n if n > 0 => y 
      case n if n %% 4 == 0 => y - n / 2 //Subtract because n is negative, y being greater than x
      case n => y - n / 2 + 2
    }
  }
}

object HexGrid
{
  val yRatio = sqrt(3)
  /** Verts start at Up and follow clockwise */
  val vertCoodsOfTile00: Coods = Coods.ints(0,1,  2,1,  2,-1,  0,-1,  -2,-1,  -2,1)
  def vertCoodsOfTile(x: Int, y: Int): Coods = vertCoodsOfTile(x cc y)
  def vertCoodsOfTile(tileCood: Cood): Coods =
  {
    vertCoodsOfTile00.pMap(_ + tileCood)
  }
  val sideCoodsOfTile00: Coods = Coods.ints(1,1, 2,0, 1,-1, -1,-1, -2,0, -1,1).pMap(p => Cood(p._1, p._2))
  def sideCoodsOfTile(tileCood: Cood): Coods = sideCoodsOfTile00.pMap(tileCood + _)
  val adjTileCoodsOfTile00: Coods = sideCoodsOfTile00.pMap(_ * 2)
  def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)
  def vertCoodsOfSide(sideCood: Cood): CoodLine = vertCoodsOfSide(sideCood.x, sideCood.y)

  def vertCoodsOfSide(x: Int, y: Int): CoodLine = fOrientation(x, y, CoodLine(x - 1, y, x + 1, y), CoodLine(x, y + 1, x, y - 1),
    CoodLine(x + 1, y, x - 1, y))

  /*  (xSideCood %% 4, ySideCood %% 4) match
  { case (0, 2) | (2, 0) => CoodLine(xSideCood, ySideCood + 1, xSideCood, ySideCood - 1)
    case (xr, yr) if xr.isOdd & yr.isOdd => CoodLine(xSideCood - 1, ySideCood, xSideCood + 1, ySideCood)
    case _ => excep("Invalid Hex Cood for a side")
  }*/
  /** Used for regular HexGrids and the regular aspect of irregular Hexgrids */
  def coodToVec2(cood: Cood): Vec2 = coodToVec2(cood.x, cood.y)

  def coodToVec2(x: Int, y: Int): Vec2 =
  {
    def yAdj: Double = y * yRatio
    (x %% 4, y %% 4) match
    { case (xr, yr) if yr.isEven && xr.isEven => Vec2(x, yAdj)
      case (xr, yr) if yr.isEven => throw new Exception("Hex Cood " + x.toString -- y.toString + ", y is even but x is odd. This is an invalid HexCood")
      case (xr, yr) if xr.isOdd  && yr.isOdd => Vec2(x, yAdj)
      case (0, 1) | (2, 3)  =>  Vec2(x, yAdj + yDist /2)
      case (xr, yr) => Vec2(x, yAdj - yDist / 2)
    }
  }
   
  @inline def fOrientation[A](x: Int, y: Int, upRight: => A, rightSide: => A, downRight: => A): A = if3Excep(
    (y.div4Rem1 && x.div4Rem1) || (y.div4Rem3 && x.div4Rem3), upRight,
    (y.isDivBy4 && x.div4Rem2) || (y.div4Rem2 && x.isDivBy4), rightSide,
    (y.div4Rem1 && x.div4Rem3) || (y.div4Rem3 && x.div4Rem1), downRight,
    "invalid Hex Side coordinate: " + x.toString.commaAppend(y.toString))

  def orientationStr(x: Int, y: Int): String = fOrientation(x, y, "UpRight", "Right", "DownRight")
  val yDist =  2 / sqrt(3)
  val yDist2 =  4 / sqrt(3)
  //val yRatio = sqrt(3)
  def yAdj(cood: Cood): Double = cood.y * yRatio
   
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
    Vec2(x, y / yRatio)
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
