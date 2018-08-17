/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pCiv
import geom._
import pGrid._
import pCanv._
import Colour._

class CivGui(canv: CanvasPlatform) extends HexGridGui[CTile, SideBare, CivGrid](canv, Civ1)
{
   override def scaleMin = 10
   //override def eTop(): Unit = reTop(guButs)
   mapPanel.backColour = Colour.Black
   def  fHex: OfHexReg[CTile, SideBare, CivGrid] => Disp2 = tog =>
      {
         import tog._        
         val colour: Colour = tile.colour
         //val poly = tog.vertDispVecs
         val tv = vertDispVecs.fillSubj(tile, colour)
         val sides = ifScaleCObjs(60, ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
         val tText = ifScaleCObj(60, FillText(cen, xyStr, 14, colour.contrastBW))
         val sett = ifScaleIfCObj(40, tile.settlement, Circle(25).slate(cen).fillFixed(None, Black))
         val lunit: CanvObjs = tile.lunits match
         {
            case ::(head, _) if tog.tScale > 50 =>
               {
                  val maxOffset = tog.grid.coodToVec2(head.dirn.relCood)
                  val gridPosn = cenRelGrid + maxOffset * head.offsetMagnitude
                  val posn = fTrans(gridPosn)
                  val fillColour = head.faction.colour
                  //val lineColour = fillcol.contrast
               //Rectangle.curved(90, 60, 10, posn).subjAll  allFixed(head, col, 2, col2, 16, head.movePts.toString) :: Nil   
               Rectangle.curved(90, 60, 10, posn).allFixed(head, fillColour, 2, fillColour.contrast, 16, head.movePts.toString) :: Nil
               }
            case _ => Nil
         }
         Disp2(List(tv), tText ++ sett ++ lunit ++ sides)
      }
   def mapObjs: CanvObjs = ofHTilesDisplayFold(fHex).collapse// ofHexsDisplayFold(fHex).collapse
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, _) =>
         {
            deb(selected.toString)
            selected = clickList.fHead(Nil, (h , _) => List(h))
            statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
            eTop()
         }
      case (RightButton, List(warr : Warrior), List(newTile: CTile)) =>
         {
            deb("Rt")
            val newCood = newTile.cood
            val oldCood = warr.cood
            
            if (HexGrid.adjTileCoodsOfTile(oldCood).contains(newCood) && warr.movePts > 0)
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
      case (RightButton, l, _) => deb(l.toString)   
      case _ => deb("Mouse other")
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