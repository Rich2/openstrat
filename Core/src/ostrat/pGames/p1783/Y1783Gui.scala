/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p1783
import geom._
import pEarth._
import pCanv._
import pStrat._
//import pGrid._

case class Y1783Gui(canv: CanvasPlatform, scen: NapScen) extends EarthAllGui
{
   override def saveNamePrefix = "Y1783"
   /** The distance per pixel. This will normally be much greater than than 1 */
   scale = 5.12.km   
   focus = 59.17 ll 0.0
   val fHex: OfETile[NTile, ESideOnly] => Disp2 = etog =>
      {
         import etog._         
         val colour: Colour = tile.colour
         val poly = vertDispVecs.fillSubj(tile, colour)       
         val textU: CanvElems = etog.ifScaleCObjs(68, tile.lunits match
         {
            case ::(head, _) if tScale > 68 => List(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(cen))               
            case _ =>
            {
            val ls: List[String] = List(yxStr, cenLL.toString)                   
            FillText.lines(cen, ls, 10, colour.contrastBW)
            }
         })         
         Disp2(List(poly),textU)
      }
      def fSide: OfESide[NTile, ESideOnly] => Disp2 = ofs => {
      import ofs._
      val line = ifScaleCObjs(60, side.terr match
            {
         case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour,
               (t1, _) => LineDraw(vertDispLine, 1, t1.colour.contrastBW))
         case Straits => LineDraw(vertDispLine, 6, Colour.Blue) :: Nil
         })      
      Disp2(Nil, line)
   } 
      
   def ls: CanvElems =
   {
      val gs: Disp2 = scen.grids.displayFold(_.eDisp2(this, fHex, fSide))
      val as: Disp2 = scen.tops.displayFold(a => a.disp2(this) )
      (gs ++ as).collapse   
   }
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, _) =>{
         selected = clickList.fHead(Nil, List(_))
         //deb("Sel" -- selected.head.getClass.toString)
         }
      case (RightButton, List(c : Corps), List(tile: NTile)) =>
         { 
           // deb("move")
             scen.tile(c.cood).lunits = scen.tile(c.cood).lunits.removeFirst(_ == c)
              tile.lunits = c :: tile.lunits
              c.cood = tile.cood
             repaintMap  
         }
      case (RightButton, List(c : Corps), clickList) => //deb(clickList.map(_.getClass.toString).toString)  
      case _ => 
   }    
   eTop()   
   loadView   
   repaintMap   
}