/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pCiv
import geom._
import pGrid._
import pDisp._
import Colour._

class CivGui(canv: CanvDisp) extends HexGridGui[CTile, Civ1.type](canv, Civ1)
{
   override def scaleMin = 10
   //override def eTop(): Unit = reTop(guButs)
   mapPanel.backColour = Colour.Black
   def  fHex: RegHexOfGrid[CTile, Civ1.type] => Disp2 = tog =>
      {
         val tile = tog.tile
         val colour: Colour = tile.colour
         val poly = tog.vertVecs
         val tv = poly.fillSubj(tile, colour)
         val sides = tog.ifScaleCObjs(60, tog.ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
         val tText = tog.ifScaleCObj(60, FillText(tog.cen, tog.xyStr, 14, colour.contrastBW))
         val sett = tog.ifScaleIfCObj(40, tile.settlement, Circle(25).slate(tog.cen).fillFixed(None, Black))
         val lunit: CanvObjs = tile.lunits match
         {
            case ::(head, _) if tog.tScale > 50 =>
               {
                  val maxOffset = tog.grid.coodToVec2(head.dirn.relCood)
                  val gridPosn = tog.cenRelGrid + maxOffset * head.offsetMagnitude
                  val posn = tog.fTrans(gridPosn)
                  val col = head.faction.colour
                  val col2 = col.contrast
               Rect.curved(45, 30, 5, posn).allFixed(head, col, 2, col2, 16, head.movePts.toString) :: Nil
               }
            case _ => Nil
         }
         Disp2(List(tv), tText ++ sett ++ lunit ++ sides)
      }
   def mapObjs: CanvObjs = ofTilesDisplayFold(fHex).collapse// ofHexsDisplayFold(fHex).collapse
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, _) =>
         {            
            selected = clickList.fHead(Nil, (h , _) => List(h))
            statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
            eTop()
         }
      case (RightButton, List(warr : Warrior), List(newTile: CTile)) =>
         {
            //deb("Move")
            val newCood = newTile.cood
            val oldCood = warr.cood
            
            if (HexGrid.tileNeighbs(oldCood).contains(newCood) && warr.movePts > 0)
            {
               warr.dirn = HexDirn.fromNeighbTileCood(newCood - oldCood)
               def out(elapsed: Double, startTime: Double): Unit =
               {
                  warr.offsetMagnitude = elapsed / 200
                  if (warr.offsetMagnitude > 2)
                  {
                     warr.offsetMagnitude = 0
                     warr.dirn = HCen
                     val oldTile = grid.getTile(oldCood)
                     oldTile.lunits = oldTile.lunits.removeFirst(_ == warr)
                     warr.cood = newCood
                     newTile.lunits ::= warr
                     repaintMap
                  }
                  else
                  {
                     repaintMap
                     canv.frame(out, startTime, 15)
                  }
                  
               }
               warr.movePts = (warr.movePts - warr.terrCost(newTile)).max(0)
               canv.frameZero((el, st) => out(el, st), 15)               
            }            
         }      
      case _ => //deb("Mouse other")
   }   
   
//   mapPanel.mouseUp = (a, b, s) =>
//      {
//         //selectedObj = None
//         statusText = s.headOption.fold("Nothing Clicked")(_.toString)
//         eTop()
//      } 
   def turnCmd: MouseButton => Unit = (mb: MouseButton) => {tilesForeach(_.lunits.foreach(_.movePts = 10)); repaintMap }
   val bTurn = button3("T", turnCmd)   
   override def eTop(): Unit = reTop(guButs :+ bTurn :+ status)
   eTop()
   mapPanel.repaint(mapObjs)
}