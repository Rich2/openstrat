/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._

abstract class Area2(val sym: Symbol, val cen: LatLong, val terr: Terrain) extends GeographicSymbolKey
{
   override def toString = name.commaAppend(terr.toString)
   def aStrs = List(name)
   def textScale: Dist = 15.km   
   def latLongs: LatLongs   
   
   def display(eg: EarthGui, fill: Boolean = true): Disp2 = 
   {  
      eg.polyToGlobedArea(latLongs) match
      {
         case GlobedAll(d2s) =>
         { 
            val v2s: Vec2s = d2s.pMap(eg.trans)// eg.transSeq(d2s)
            val cenXY: Vec2 = eg.latLongToXY(cen)
            val vis1: CanvElems = ife(fill, List(v2s.fillSubj(this, terr.colour)), Nil)
            val vis2: CanvElems = List(v2s.draw(2.0, terr.colour.redOrPink)).
               ifAppendList(eg.scale < textScale && fill, TextGraphic.lines(cenXY, aStrs, 10, terr.colourContrast))
            Disp2(vis1, vis2)
         }
         case GlobedSome(s) =>
         {
            val cenXY: Vec2 = eg.latLongToXY(cen)
            Disp2.vp(ShapeSubj.fill(cenXY, s.map(_.toVec2s(eg.trans)), this, terr.colour))()
         }
         case GlobedNone => Disp2.empty
      }
   }   
}

object Area2
{
   def apply(sym: Symbol, cen: LatLong, terr: Terrain, latLongArgs: LatLong*) = new Area2(sym, cen, terr)
   {      
      val latLongs = LatLongs(latLongArgs: _*)
   }  
}