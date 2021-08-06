/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom._, pGrid._

/** This package and module is for Earth maps. In particular the tiling of the whole world in Hex grids, defining the changes over the course of
 *  history. This will be a data orientated module. It will also include terrain types to model terrain, both real and imagined for local maps and
 *  higher scales right up to 0.5 metres per tile However it won't generally include the data for these. The data for the real world will be organised
 *  according to a number of levels, which are likely to change over increasingly shorter historical time frames.
 *
 *  1 Base elevation, relative to 1950 sea level, and relief.
 *  2 Climate.
 *  2 Sea level, shore lines, lake shore lines and river courses.
 *  3 Land-use, both natural and human. */
package object pEarth
{
   /** The North-South divide between Area1s and Grids at 45 degrees north approx. */
   val divN45 = 45.27369792435918.north
   //import HexGrid._
   /** Returns a function for a specific EGrid to convert from gridVec to Latlong */
   def fVec2ToLatLongReg(refLong: Longitude, scale: Metres, xOffset: Int, yOffset: Int = 0): Pt2 => LatLong = inp =>
      {
         val vOffset = HexGridAncient.coodToVec2(xOffset, yOffset)
         val d2: PtMetre2 = (inp - vOffset).toDist2(scale)
         val lat: Double = d2.y / EarthPolarRadius         
         val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
         LatLong.radians(lat, refLong.radians + longDelta)
      }
      
   def vec2ToLatLongReg(inp: Pt2, refLong: Longitude, scale: Metres, xOffset: Int, yOffset: Int = 0): LatLong =
      {
         val vOffset = HexGridAncient.coodToVec2(xOffset, yOffset)
         val d2: PtMetre2 = (inp - vOffset).toDist2(scale)
         val lat: Double = d2.y / EarthPolarRadius         
         val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
         LatLong.radians(lat, refLong.radians + longDelta)
      }   
   
   /** Not necessarily used */   
   def vec2ToLatLong0(inp: Pt2, refLong: Longitude, scale: Metres, yOffset: Int = 0): LatLong =
   {
      val vOffset = HexGridAncient.coodToVec2(0, yOffset)
      val d2: PtMetre2 = (inp - vOffset).toDist2(scale)
      val lat: Double = d2.y / EarthPolarRadius         
      val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
      LatLong.radians(lat, refLong.radians + longDelta)
   }
   
   /** Not necessarily used */
   def  coodToLatLong0(inp: Cood, scale: Metres, yOffset: Int = 0): LatLong =
   {
      val adj: Pt2 = HexGridAncient.coodToVec2(inp.subY(yOffset))
      val d2: PtMetre2 = adj.toDist2(scale)
      val lat = d2.y / EarthPolarRadius         
      val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
      LatLong.radians(lat, longDelta)
   } 
}