/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._, pGrid._, reflect.ClassTag

/** 80km hexs. deltaX in HexCood 1 = 20km */   
class EGrid80km[TileT <: Tile, SideT <: TileSide] (bounds: Array[Int], name: String, cenLong: Longitude, xOffset: Int,
      xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int)(implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends
   EGrid[TileT, SideT](bounds, name, cenLong, EGrid80km.scale, xOffset, 300, xTileMin, xTileMax, yTileMin, yTileMax, turnNum)
{
   foreachTileRowAll{y => 
      val pair = EGrid80km.tileRowMaxX(y, xOffset)
      setRowStart(y, pair._1)
      setRowEnd(y, pair._2)
   }
   (yTileMin to yTileMax by 2).foreach{ y =>
      val tileMax: Int = rowTileXEnd(y)
      val tileMin: Int = rowTileXStart(y)
      val xSideMax = tileMax + 2
      val xSideMin = tileMin - 2
      
      val rt1: Double = coodToLL(xSideMax, y + 1).long
      val lt1: Double = coodToLL(xSideMin, y + 1).long
      val rt1Adj: Double = lt1 + 30.degreesToRadians
      val rt1New = (rt1 + rt1Adj) / 2
      val lt1Adj = rt1 - 30.degreesToRadians
      val lt1New = (lt1 + lt1Adj) / 2
      setLongitude(xSideMax, y + 1, rt1New)
      setLongitude(xSideMin, y + 1, lt1New)
      
      val rts: Double = coodToLL(xSideMax, y).long
      val lts: Double = coodToLL(xSideMin, y).long
      val rtsAdj: Double = lts + 30.degreesToRadians
      val rtsNew = (rts + rtsAdj) / 2
      val ltsAdj = rts - 30.degreesToRadians
      val ltsNew = (lts + ltsAdj) / 2
      setLongitude(xSideMax, y, rtsNew)
      setLongitude(xSideMin, y, ltsNew)
      
      val rt2: Double = coodToLL(xSideMax, y - 1).long
      val lt2: Double = coodToLL(xSideMin, y - 1).long
      val rt2Adj: Double = lt2 + 30.degreesToRadians
      val rt2New = (rt2 + rt2Adj) / 2
      val lt2Adj = rt2 - 30.degreesToRadians
      val lt2New = (lt2 + lt2Adj) / 2
      setLongitude(xSideMax, y - 1, rt2New)
      setLongitude(xSideMin, y - 1, lt2New)      
   }
   
   override def optTile(x: Int, y: Int): Option[TileT] = ife5(
     y < yTileMin, None,
     y > yTileMax, None,
     x < rowTileXStart(y), None,

     x == rowTileXEnd(y) + 4,
     rightGrid.map { grid =>
        val rs: Int = grid.rowTileXStart(y)
        grid.getTile(rs, y)
     },

     x > rowTileXEnd(y), None,
     Some(getTile(x, y))
   )
}

object EGrid80km
{
   val scale = 20.km
   import pGrid._
   def  coodToLatLong0(inp: Cood): LatLong =
   {
      val adj: Vec2 = HexGrid.coodToVec2(inp.subXY(0, 300))      
      val d2: Dist2 = adj * scale
      val lat: Double = d2.y / EarthPolarRadius         
      val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
      LatLong(lat, longDelta)
   }
   
   def xDelta(y: Int, x: Int): Double = coodToLatLong0(Cood(x, y)).longDegs
   def tileRowMaxX(y: Int, xOffset: Int = 0): (Int, Int) =
   {
      val startX: Int = ife(y %% 4 == 0, 0, 2)
      val hexDelta: Double = xDelta(y, 4)       
      val margin = 15 - hexDelta
      def loop(xAcc: Int): (Int, Int) =
      {
         val newPt = xDelta(y, xAcc)
         val overlapRatio = (newPt - margin) / hexDelta
         newPt match
         {
            case r if r < margin => loop(xAcc + 4)
            //case r if overlapRatio < 0.2 => (-xAcc, xAcc + 4)
            case r if overlapRatio < 0.5 => (-xAcc, xAcc)
            case r => (4 - xAcc, xAcc)
         }
      }
      val (neg, pos) = loop(startX)
      (neg + xOffset , pos + xOffset)      
   }   

   def getBounds(xOffset: Int, yTileMin: Int, yTileMax: Int): Array[Int] =
   {
      
      val bounds = new Array[Int]((yTileMax - yTileMin + 1) * 2)
      (yTileMin to yTileMax by 2).foreach{ y =>
         val p = (y - 446) * 2
         val pair = EGrid80km.tileRowMaxX(y, xOffset)
         bounds(p) = pair._1
         bounds(p + 1) = pair._2
      }
      bounds
   }
}

object E80Empty extends EGridMaker 
{
   def apply[TileT <: Tile, SideT <: TileSide](implicit fTile: (Int, Int, Terrain) => TileT, fSide: (Int, Int, SideTerr) => SideT,
       evTile: ClassTag[TileT], evSide: ClassTag[SideT]):
   EGrid80km[TileT, SideT] =
      new EGrid80km[TileT, SideT](new Array[Int](0), "Empty", 0.east, xOffset = 0, xTileMin = 4, xTileMax = 0, yTileMin = 4, yTileMax = 0, turnNum = 0)
      
   //def rowDelta(y: Int): Double = ???  
}

class EGFarNorth[TileT <: Tile, SideT <: TileSide](name: String, cenLong: Longitude, xOffset: Int, xTileMin: Int, xTileMax: Int)
   (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends 
   EGrid80km[TileT, SideT](EGFarNorth.getBounds(xOffset), name, cenLong, xOffset: Int,
         xTileMin: Int, xTileMax: Int, yTileMin = 446, yTileMax = 540, turnNum = 0)
{
   
}

object EGFarNorth
{
   def getBounds(xOffset: Int): Array[Int] = EGrid80km.getBounds(xOffset, 446, 540)
      
 }

class EGNorth[TileT <: Tile, SideT <: TileSide](bounds: Array[Int], name: String, cenLong: Longitude, xOffset: Int, xTileMin: Int, xTileMax: Int)
   (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends EGrid80km[TileT, SideT] (bounds, name, cenLong, xOffset: Int,
         xTileMin: Int, xTileMax: Int, yTileMin = 340, yTileMax = 444, turnNum = 0)
         
object EGNearNorth
{
   def getBounds(xOffset: Int): Array[Int] = EGrid80km.getBounds(xOffset, 300, 444)
}
