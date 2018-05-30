/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pWW2
import geom._
import pEarth._
import pDisp._
import pStrat._
//import pGrid._

case class WWIIGui(canv: CanvDisp, scen: WWIIScen) extends EarthAllGui
{
 
   focusUp = true
   override def saveNamePrefix = "WW2"
   val fHex: ETileOfGrid[W2Tile] => Disp2 = etog =>
      {
         val tile = etog.tile
         val colour: Colour = tile.colour
         val poly = etog.vertVecs.fillSubj(tile, colour)
         val sides = etog.ifScaleCObjs(60, etog.ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
         val textOrUnit: CanvObjs = etog.ifScaleCObjs(68, tile.lunits match {
            case ::(head, _) if etog.tScale > 68 => Seq(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(etog.cen))
            case _ => 
            {
              val ls: Seq[String] = List(etog.xyStr, etog.cenLL.toString)                   
              FillText.lines(etog.cen, ls, 10, colour.contrastBW)                  
            }
         })
         Disp2(Seq(poly), sides ++ textOrUnit)
      }
   def ls: CanvObjs = 
   {
      val gs: Disp2 = scen.grids.displayFold(_.eDisp(this, fHex))
      val as: Disp2 = scen.tops.displayFold(a => a.disp2(this) )
//      val b: CanvObjs = scen.sidesMap{s =>
//          val cl = scen.sideCoodVertCoods(s.cood)
//          val ln = cl.toLine2(cood => fTrans(grid.coodToVec2(cood)))          
//          LineDraw(ln, 1, Colour.Black)
//      }
      (as ++ gs).collapse// + b  
   }   
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, _) =>
         {
            selected = clickList.fHead(Nil, (h , _) => List(h))         
            statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
            eTop()
         }
      case (RightButton, List(army : Army), List(t: W2Tile)) =>
         {
            scen.tile(army.cood).lunits = scen.tile(army.cood).lunits.removeFirst(_ == army)
            t.lunits = army :: t.lunits
            army.cood = t.cood
            repaintMap  
         }      
      case _ => 
   }
   eTop()
   canvSaverDo(loadView)
   repaintMap
}
