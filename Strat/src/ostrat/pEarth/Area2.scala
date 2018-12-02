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
   
   def display(eg: EarthGui, fill: Boolean = true): GraphicElems = 
   {  
      eg.polyToGlobedArea(latLongs) match
      {
         case GlobedAll(d2s) =>
         { 
            val v2s: Polygon = d2s.pMap(eg.trans)// eg.transSeq(d2s)
            val cenXY: Vec2 = eg.latLongToXY(cen)
            val vis1: GraphicElems = ife(fill, List(v2s.fillSubj(this, terr.colour)), Nil)
            val vis2: GraphicElems = List(v2s.draw(2.0, terr.colour.redOrPink)).
               ifAppendList(eg.scale < textScale && fill, TextGraphic.lines(cenXY, aStrs, 10, terr.contrast))
            vis1 ::: vis2
         }
         case GlobedSome(curveSegDists) =>
         {
            val cenXY: Vec2 = eg.latLongToXY(cen)
            val curveSegs: Shape = curveSegDists.pMap(_.toCurveSeg(eg.trans))
            List(ShapeSubj.fill(cenXY, curveSegs, this, terr.colour))
         }
         case GlobedNone => Nil
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