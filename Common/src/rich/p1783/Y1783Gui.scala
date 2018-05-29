/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package p1783
import geom._
import pEarth._
import pDisp._
import pStrat._

case class Y1783Gui(canv: CanvDisp, scen: NapScen) extends EarthAllGui
{
   override def saveNamePrefix = "Y1783"
   /** The distance per pixel. This will normally be much greater than than 1 */
   //scale = 2.71.km   
   //focus = 40.24 ll -5.66
   val fHex: ETileOfGrid[NTile] => Disp2 = etog =>
      {
         val tile = etog.tile
         val colour: Colour = tile.colour
         val poly = etog.vertVecs.fillSubj(tile, colour)
         val sides = etog.ifScaleCObjs(60, etog.ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
         val textU: CanvObjs = etog.ifScaleCObjs(68, tile.lunits match
         {
            case ::(head, _) if etog.tScale > 68 => Seq(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(etog.cen))               
            case _ =>
            {
            val ls: Seq[String] = Seq(etog.yxStr, etog.cenLL.toString)                   
            FillText.lines(etog.cen, ls, 10, colour.contrastBW)
            }
         })         
         Disp2(Seq(poly), sides ++ textU)// ++ lunit)
      }
   def ls: CanvObjs =
   {
      val gs: Disp2 = scen.grids.displayFold(_.eDisp(this, fHex))
      val as: Disp2 = scen.tops.displayFold(a => a.disp2(this) )
      (gs ++ as).collapse   
   }
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, _) =>{
         selected = clickList.fHead(Nil, (h , _) => List(h))
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
   canvSaverDo(loadView)
   repaintMap   
}