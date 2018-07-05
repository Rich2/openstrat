/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p305
import geom._
import pDisp._
import pEarth._
//import pGrid._

case class BC305Gui(canv: CanvasPlatform, scen: BCScen) extends EarthGui
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
         
   val fHex: OfETile[BCTile, BCSide] => Disp2 = etog =>
      {
         val tile = etog.tile
         val colour: Colour = tile.colour
         val poly = etog.vertVecs.fillSubj(tile, colour)
         val tileText: CanvObjs = etog.ifScaleCObjs(68,
         {
            val ls: List[String] = List(etog.yxStr, etog.cenLL.toString)                   
            FillText.lines(etog.cen, ls, 10, colour.contrastBW)              
         })         
         Disp2(poly :: Nil, tileText)
      }      
         
   def ls: CanvObjs =
   {
      val gs: Disp2 = scen.grids.displayFold(_.eDisp(this, fHex))
      val as: Disp2 = scen.tops.displayFold(a => a.disp2(this) )
      (gs ++ as).collapse   
   }   
   eTop()
   repaintMap
}