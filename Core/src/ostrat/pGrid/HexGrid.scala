/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A Hex tile own the right sides, upRight, Right and DownRight. It owns the Up, UpRight and DownRight Vertices numbers 0, 1 and 2. */
abstract class HexGrid[TileT <: GridElem, SideT <: GridElem](xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
(implicit evTile: IsType[TileT], evSide: IsType[SideT]) extends TileGrid[TileT, SideT](xTileMin, xTileMax, yTileMin, yTileMax)   
{   
   override def tileVertCoods(cenCood: Cood): Coods = HexGrid.tileVertCoods(cenCood)
   //override def newCoodT(x: Int, y: Int): HexCood = HexCood.apply(x, y)
   override def xStep: Int = 4
   //override lazy val xSideMin: Int = xTileMin - 2
   //override lazy val xSideMax: Int = xTileMax + 2 
   override def xToInd(x: Int): Int = x / 2 - xTileMin / 2
   override def xArrLen: Int = xTileMax / 2 - xTileMin / 2 + 2 //+1 for zeroth tile, +1 for right side
   override lazy val yRatio: Double = HexGrid.yRatio
   def vertMargin = 0.7
   override def left: Double = xTileMin - 2.2
   override def right: Double = xTileMax + 2.2
   def bottom: Double = (yTileMin - 2) * yRatio - vertMargin
   def top: Double = (yTileMax + 2) * yRatio + vertMargin
   def fTiles[D](f: (TileT, D) => Unit, data: (Int, Int, D)*) = data.foreach(tr => f(getTile(tr._1, tr._2), tr._3))      
   
   def isTile(x: Int, y: Int): Boolean = getTile(x, y) != null
   override def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)
   //def evenRows: Range = yTileMin until yTileMax by 2
   //def tileNeibs
   override def sideVertCoods(x: Int, y: Int): CoodLine = HexGrid.sideVertCoods(x, y)
   override def coodIsTile(x: Int, y: Int): Unit = Unit match
   {
      case _ if x %% 4 == 0 & y %% 4 == 0 =>
      case _ if x %% 4 == 2 & y %% 4 == 2 =>
      case _ => excep(x.toString.commaAppend(y.toString) -- "is an invalid Hex tile coordinate")   
   }
   override def coodIsSide(x: Int, y: Int): Unit = Unit match
   {
      case _ if x %% 4 == 0 & y %% 4 == 2 =>
      case _ if x %% 4 == 2 & y %% 4 == 0 =>
      case _ if x.isOdd & y.isOdd =>   
      case _ => excep(x.toString.commaAppend(y.toString) -- "is an invalid Hexside tile coordinate")   
   }
   override def sidesTileCoods(x: Int, y: Int): (Cood, Cood) = Unit match
   {
      case _ if (x %% 4 == 0 & y %% 4 == 2) | (x %% 4 == 2 & y %% 4 == 0)  => (Cood(x -2, y), Cood(x + 2, y))
      case _ if (x %% 4 == 1 & y %% 4 == 1) | (x %% 4 == 3 & y %% 4 == 3) =>  (Cood(x - 1, y - 1), Cood(x + 1, y + 1))
      case _ if (x %% 4 == 1 & y %% 4 == 3) | (x %% 4 == 3 & y %% 4 == 1) => (Cood(x - 1, y + 1), Cood(x + 1, y - 1))
      case _ => excep("Invalid Hex Side Coordinate".commaAppend(x.toString, y.toString))
   }
   
}

object HexGrid
{
   /** Verts start at Up and follow clockwise */
   val tile00VertCoods: Coods = Coods.xy(0,1,  2,1,  2,-1,  0 ,-1,  -2,-1,  -2,1)
   def tileVertCoods(inp: Cood): Coods = tile00VertCoods.pMap(inp + _)
   //def sideVertCoods(inp: Cood): Coods = 
   val tile00Neighbs: List[Cood] = List(2 -> 2, 4 -> 0, 2 -> -2, -2 -> -2, -4 -> 0, -2 -> 2).map(p => Cood(p._1, p._2))
   def tileNeighbs(inp: Cood): List[Cood] = tile00Neighbs.map(inp + _)  
   
   def coodToVec2(cood: Cood): Vec2 = xyToVec2(cood.x, cood.y)
   def xyToVec2(x: Int, y: Int): Vec2 =
   {
      def yAdj: Double = y * yRatio
      (x % 4, y % 4) match 
      {
         case (xr, yr) if yr.isEven && xr.isEven => Vec2(x, yAdj)
         case (xr, yr) if yr.isEven => throw new Exception("HexCood, y is even but x is odd. This is an invalid HexCood")
         case (xr, yr) if xr.isOdd  && yr.isOdd => Vec2(x, yAdj)      
         case (0, 1) | (2, 3)  =>  Vec2(x, yAdj + yDist /2)
         case (xr, yr) => Vec2(x, yAdj - yDist / 2)
      }
   }
   
   @inline def fOrientation[A](x: Int, y: Int, upRight: => A, rightSide: => A, downRight: => A): A = Unit match
   {
      case _ if (y.div4Rem1 && x.div4Rem1) || (y.div4Rem3 && x.div4Rem3) => upRight
      case _ if (y.isDivBy4 && x.div4Rem2) || (y.div4Rem2 && x.isDivBy4) => rightSide      
      case _ if (y.div4Rem1 && x.div4Rem3) || (y.div4Rem3 && x.div4Rem1) => downRight
      case _ => excep("invalid Hex Side coordinate: " - x.toString.commaAppend(y.toString))
   }
   def orientationStr(x: Int, y: Int): String = fOrientation(x, y, "UpRight", "Right", "DownRight")
   def sideVertCoods(cood: Cood): CoodLine = sideVertCoods(cood.x, cood.y)
   def sideVertCoods(x: Int, y: Int): CoodLine = (x %% 4, y %% 4) match
   {
      case (0, 2) | (2, 0) => CoodLine(x, y + 1, x, y - 1)
      case (xr, yr) if xr.isOdd & yr.isOdd => CoodLine(x - 1, y, x + 1, y)
      case _ => excep("Invalid Hex Cood for a side")
   }
   
   import math.sqrt
     
   val yDist =  2 / sqrt(3) 
   val yDist2 =  4 / sqrt(3)
   val yRatio = sqrt(3)
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
   
   //   implicit class HexCoodPersist(tc: HexCood) extends PersistCompound
//   {
//      override def persistMems: Seq[Persist] = Seq(tc.x, tc.y)
//      override def persistName: String = "HexCood"
//   }
//   implicit object HexCoodPBuilder extends PBuilder2[HexCood, Int, Int]
//   {
//      override def persistType = "HexCood"
//      override def isType(obj: Any): Boolean = obj.isInstanceOf[HexCood]
//      override def apply = HexCood.apply  
//   }
   def latLong(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2, gridScale: Dist): LatLong =
   {
      val lat = (pt.y * gridScale + xyOffset.y) / EarthPolarRadius + latLongOffset.lat 
      val long = (pt.x * gridScale + xyOffset.x) / (EarthEquatorialRadius * math.cos(lat)) + latLongOffset.long
      LatLong(lat, long)      
   }
   def latLongToCood(latLong: LatLong, latLongOffset: LatLong, xyOffset: Dist2, gridScale: Dist): Vec2 =
   {
      val y: Double = ((latLong.lat - latLongOffset.lat) * EarthPolarRadius - xyOffset.y) / gridScale
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
//   def vertsCen(cen: Vec2, scale: Double = 0): Seq[Vec2] = verts.map(cen + _ * scale)  
//   val vertsTL: Seq[Vec2] = verts.slate(VertXDist2, 2)
//   val vertsBL: Seq[Vec2] = verts.slate(VertXDist2, -2)  
}

