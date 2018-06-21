/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pEarth
import geom._
import pGrid._

/** 80km hexs. deltaX in HexCood 1 = 20km */   
class EGrid80km[TileT <: Tile] (bounds: Array[Int], name: String, cenLong: Longitude, xOffset: Int,
      xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)(implicit evTile: IsType[TileT]) extends
   EGrid[TileT](bounds, name, cenLong, EGrid80km.scale, xOffset, 300, xTileMin, xTileMax, yTileMin, yTileMax)

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
            case r if overlapRatio < 0.2 => (-xAcc, xAcc + 4)
            case r if overlapRatio < 0.7 => (-xAcc, xAcc)
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
   def apply[TileT <: Tile](fTile: (Int, Int, Terrain) => TileT)(implicit evTile: IsType[TileT]): EGrid80km[TileT] =
      new EGrid80km[TileT](new Array[Int](0), "Empty", 0.east, xOffset = 0, xTileMin = 4, xTileMax = 0, yTileMin = 4, yTileMax = 0)
      
   //def rowDelta(y: Int): Double = ???  
}

class EGFarNorth[TileT <: Tile](name: String, cenLong: Longitude, xOffset: Int, xTileMin: Int, xTileMax: Int)
   (implicit evTile: IsType[TileT])extends EGrid80km[TileT](EGFarNorth.getBounds(xOffset), name, cenLong, xOffset: Int,
         xTileMin: Int, xTileMax: Int, yTileMin = 446, yTileMax = 540)
{
   tileRowsForeach{y => 
      val pair = EGrid80km.tileRowMaxX(y, xOffset)
      setRowStart(y, pair._1)
      setRowEnd(y, pair._2)
   }   
}

object EGFarNorth
{
   def getBounds(xOffset: Int): Array[Int] = EGrid80km.getBounds(xOffset, 446, 540)
      
 }

class EGNorth[TileT <: Tile](bounds: Array[Int], name: String, cenLong: Longitude, xOffset: Int, xTileMin: Int, xTileMax: Int)
   (implicit evTile: IsType[TileT])extends EGrid80km[TileT] (bounds, name, cenLong, xOffset: Int,
         xTileMin: Int, xTileMax: Int, yTileMin = 340, yTileMax = 444)
         
object EGNearNorth
{
   def getBounds(xOffset: Int): Array[Int] = EGrid80km.getBounds(xOffset, 300, 444)
}

