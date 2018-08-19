/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pWW2
import geom._
import pEarth._
import pCanv._
import pStrat._

case class WWIIGui(canv: CanvasPlatform, scen: WWIIScen) extends EarthAllGui
{   
   deb("Beginning WWIIGui")
   focusUp = true
   override def saveNamePrefix = "WW2"
   val fHex: OfETile[W2Tile, W2Side] => Disp2 = etog =>
      {
         import etog._         
         val colour: Colour = tile.colour
         val poly = etog.vertDispVecs.fillSubj(tile, colour)
         //val sides = etog.ifScaleCObjs(60, ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
         val textOrUnit: CanvElems = ifScaleCObjs(68, tile.lunits match {
            case ::(head, _) if tScale > 68 => List(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(cen))
            case _ => 
            {
              val ls: List[String] = List(xyStr, cenLL.toString)                   
              FillText.lines(cen, ls, 10, colour.contrastBW)                  
            }
         })
         Disp2(List(poly), textOrUnit)
      }
   def fSide: OfESide[W2Tile, W2Side] => Disp2 = ofs => {
      import ofs._
      val line = ifScaleCObjs(60, side.terr match
            {
         case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour,
               (t1, _) => LineDraw(vertDispLine, 1, t1.colour.contrastBW))
         case Straits => LineDraw(vertDispLine, 6, Colour.Blue) :: Nil
         })      
      Disp2(Nil, line)
   } 
   
   //def dSides: Disp2 = ofSidesDisplayFold(fSide)//(OfHexSideReg.implicitBuilder(_, _, _))
      
   def ls: CanvElems = 
   {
      val gs: Disp2 = scen.grids.displayFold(_.eDisp2(this, fHex, fSide))
      val as: Disp2 = scen.tops.displayFold(a => a.disp2(this) )
      (as ++ gs).collapse// + b  
   }   
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, _) =>
         {
            selected = clickList.fHead(Nil, List(_))         
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
   scale = 1.08.km
   eTop()
   loadView
   repaintMap
}
