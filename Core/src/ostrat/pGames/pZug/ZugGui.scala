/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import geom._
import pDisp._
import Colour._
import pGrid._
import pStrat._ 

class ZugGui(canv: CanvasPlatform) extends HexGridGui[ZugTile, ZugSide, ZugGrid](canv, Zug1)
{
   //deb(scaleMin.toString)
   override def scaleMin = 10
   override def eTop(): Unit = reTop(guButs :+ status)
   mapPanel.backColour = Black
   def fHex: OfHexReg[ZugTile, ZugSide, ZugGrid] => Disp2 = ofh =>
      {
         import ofh._         
         val colour: Colour = tile.colour
         val poly: Vec2s = vertVecs
         val tv = poly.fillSubj(tile, colour)
         val sides = ifScaleCObjs(60, ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
         val tText = ifScaleCObj(60, FillText(cen, xyStr, 14, colour.contrastBW))
         val lunit = tile.lunits match
         {
            case ::(head, _) if tScale > 68 => Some(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(cen))
            case _ => None   
         }         
         Disp2(List(tv), tText ++ lunit ++ sides)
      }
   def fSide: OfHexSideReg[ZugTile, ZugSide, ZugGrid] => Disp2 = ofs => {
      import ofs._
      val line = ifScaleIfCObj(60, side.wall, LineDraw(vertLine, 6, Colour.Gray))      
      Disp2(Nil, line)
   }
   def dSides: Disp2 = ofSidesDisplayFold(fSide)//(OfHexSideReg.implicitBuilder(_, _, _))
     
   def mapObjs: CanvObjs = (ofTilesDisplayFold(fHex) ++ dSides ).collapse//ofHexsDisplayFold(fHex).collapse
     
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, _) =>
         {
            //deb(v.toString)
            selected = clickList.fHead(Nil, (h , _) => List(h))
            statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
            eTop()            
         }
      case (RightButton, List(squad : Squad), List(newTile: ZugTile)) =>
         {
            val newCood = newTile.cood
            val oldCood = squad.cood
            if (HexGrid.tileNeighbs(oldCood).contains(newCood) && squad.canMove(newTile))
            {
               val oldTile = grid.getTile(oldCood)
               oldTile.lunits = oldTile.lunits.removeFirst(_ == squad)
               squad.cood = newCood
               newTile.lunits ::= squad             
               repaintMap
            }            
         }      
      case _ => 
   }   
   eTop()
   mapPanel.repaint(mapObjs)
}
  