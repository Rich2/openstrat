/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._

/** A second level area */
abstract class Area2(val symName: String, val cen: LatLong, val terr: WTile) extends GeographicSymbolKey
{
   override def toString = name.appendCommas(terr.toString)
   def aStrs: Arr[String] = Arr(name)
   def textScale: Dist = 15.km   
   def latLongs: LatLongs   
   
   def display(eg: EarthGuiOld, fill: Boolean = true): GraphicElems =
   {  
      eg.polyToGlobedArea(latLongs) match
      {
         case SomeA(d2s) =>
         { 
            val v2s: PolygonImp = d2s.pMap(eg.trans)
            val cenXY: Pt2 = eg.latLongToXY(cen)
            val vis1: GraphicElems = ife(fill, Arr(v2s.fillActive(terr.colour, this)), Arr())
            val vis2: GraphicElems = Arr(v2s.draw(terr.colour.redOrPink, 2.0))
            val vis3: GraphicElems =
              if (eg.scale < textScale && fill) TextGraphic.lines(aStrs, 10, cenXY, terr.contrast)
              else Arr()
            (vis1 ++ vis2 ++ vis3)
         }
         case SomeB(curveSegDists) =>
         {
            val cenXY: Pt2 = eg.latLongToXY(cen)
            val curveSegs: ShapeGenOld = curveSegDists.pMap(_.toCurveSeg(eg.trans))
            Arr(PolyCurveParentFull.fill(cenXY, curveSegs, this, terr.colour))
         }
         case NoOptEither => Arr()
      }
   }   
}

object Area2
{
   def apply(symName: String, cen: LatLong, terr: WTile, latLongArgs: LatLong*) = new Area2(symName, cen, terr)
   {      
      val latLongs = LatLongs(latLongArgs: _*)
   }  
}