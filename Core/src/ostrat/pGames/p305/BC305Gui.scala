/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p305
import geom._
import pDisp._
import pEarth._

case class BC305Gui(canv: CanvasPlatform, scen: BcScen) extends EarthGui
{
   override def saveNamePrefix = "BC305"
   override def scaleMax: Dist = 14000.km / mapPanelDiameter
   scale = scaleMax
   var lat: Latitude = 20.north
   var long: Longitude = 20.east
   val maxLat = 70.north
   val minLat = 0.north
   //def focus: LatLong = lat * long

   val tops: Seq[Area1] = EarthAreas.oldWorld
//   override def eTop(): Unit = reTop(Seq(bIn, bOut, bLeft, bRight,
//         bDown, bUp, bInv, status))
//   /** 4 methods below are incorrect */
//   def leftCmd: MouseButton => Unit = (mb: MouseButton) =>
//      { long = Longitude((long.radians - distDelta(mb)).min(100)); updateView() }  
//   def rightCmd: MouseButton => Unit = (mb: MouseButton) => 
//      { long = Longitude((long.radians + (distDelta(mb)).max(70.N.radians))); updateView }   
//   def downCmd: MouseButton => Unit = (mb: MouseButton) =>
//      { lat = Latitude((lat.radians - distDelta(mb)).min(0)); updateView() }   
//   def upCmd: MouseButton => Unit = (mb: MouseButton) =>
//      { lat = Latitude((lat.radians + distDelta(mb)).max(0)); updateView() } 
         
   val fHex: OfETile[BcTile, ESideOnly] => Disp2 = etog =>
      {
         import etog._         
         val colour: Colour = tile.colour
         val poly = vertDispVecs.fillSubj(tile, colour)
         val tileText: CanvObjs = ifScaleCObjs(68,
         {
            val ls: List[String] = List(yxStr, cenLL.toString)                   
            FillText.lines(cen, ls, 10, colour.contrastBW)              
         })         
         Disp2(poly :: Nil, tileText)
      }
   def fSide: OfESide[BcTile, ESideOnly] => Disp2 = ofs => {
      import ofs._
      val line = ifScaleCObjs(60, side.terr match
            {
         case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour,
               (t1, _) => LineDraw(vertLine, 1, t1.colour.contrastBW))
         case Straits => LineDraw(vertLine, 6, Colour.Blue) :: Nil
         })      
      Disp2(Nil, line)   
   }   
         
   def ls: CanvObjs =
   {
      val gs: Disp2 = scen.grids.displayFold(_.eDisp2(this, fHex, fSide))
      val as: Disp2 = scen.tops.displayFold(a => a.disp2(this) )
      (gs ++ as).collapse   
   }   
   eTop()
   loadView 
   repaintMap
}