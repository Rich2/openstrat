/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import geom._
import pCanv._
import Colour._
import pGrid._
import pStrat._ 

class ZugGui(canv: CanvasPlatform, scen: ZugGrid) extends HexGridGui[ZugTile, ZugSide, ZugGrid](canv)
{
  override var grid: ZugGrid = scen
  override def scaleMin = 10
  override var pScale: Double = scaleAlignMin
  override var focus: Vec2 = grid.cen
  override def eTop(): Unit = reTop(guButs :+ status)
  mapPanel.backColour = Black
  def fHex: OfHexReg[ZugTile, ZugSide, ZugGrid] => GraphicElems = ofh =>
  { import ofh._         
    val colour: Colour = tile.colour         
    
    val tv = tile.terr match
    { case _ : Building => List(vertDispVecs.fillSubj(tile, LightGreen), Square.fill(pScale * 2.5, colour, cen, 1))
      case _ => vertDispVecs.fillSubj(tile, colour, -1) :: Nil        
    }
    
    val tText = ifScaleCObj(60, TextGraphicCen(cen, xyStr, 14, colour.contrastBW, 2))
    
    def action(squad: Squad): GraphicElems = squad.action match
    {
      case Move(coods) =>      
      {
        coods.foldWithPrevious[GraphicElems](squad.cood, Nil){(acc, prevCood, nextCood) =>
          val sideCood = (prevCood + nextCood) / 2
          val l1 = CoodLine(prevCood, sideCood).toLine2(coodToDispVec2).draw(2, scen.getTile(prevCood).contrast, 3)
          val l2 = CoodLine(sideCood, nextCood).toLine2(coodToDispVec2).draw(2, scen.getTile(nextCood).contrast, 3)
          acc :+ l1 :+ l2
        }
      }
      case Fire(target) => List(CoodLine(squad.cood, target).toLine2(coodToDispVec2).draw(2, Red, 3).dashed(20, 20))
      case _ => Nil
    }
    
    val lunit: GraphicElems = tile.lunits match
    {
      case ::(head, _) if tScale > 68 =>
        {
          val counter = UnitCounters.infantry(30, head, head.colour, tile.colour, 4).slate(cen)
          counter :: action(head)
        }
      case _ => Nil   
   }    
   tv ::: tText ::: lunit
 }
    
  def fSide: OfHexSideReg[ZugTile, ZugSide, ZugGrid] => GraphicElems = ofs =>
  { import ofs._    
    ifScaleCObjs(60, side.wall match
      { case true => vertDispLine.draw(6, Gray) :: Nil
        case _ => ifTiles(_.colour == _.colour, (t1, _) => vertDispLine.draw(1, t1.colour.contrastBW))
      }
    )    
  }
    
  def dSides: GraphicElems = ofSidesDisplayFold(fSide)
  def mapObjs: GraphicElems = ofTilesDisplayFold(fHex) ::: dSides//ofHexsDisplayFold(fHex).collapse
     
  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList.fHead(Nil, List(_))
      statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
      eTop()            
    }
    
    case (RightButton, List(squad : Squad), List(newTile: ZugTile)) => scen.zPath(squad.cood, newTile.cood).foreach{l =>
      squad.action = Move(l)
      repaintMap
    }
    
    case (MiddleButton, List(squad : Squad), List(newTile: ZugTile)) => { squad.action = Fire(newTile.cood); repaintMap }
//    (HexGrid.adjTileCoodsOfTile(squad.cood).contains(squad.cood) && squad.canMove(newTile)) =>
//      {
//        val newCood = newTile.cood
//        val oldCood = squad.cood
//        val oldTile = grid.getTile(oldCood)
//        oldTile.lunits = oldTile.lunits.removeFirst(_ == squad)
//        squad.cood = newCood
//        newTile.lunits ::= squad             
//        repaintMap
//      }
      
    case (RightButton, List(squad : Squad), List(newTile: ZugTile)) => deb("No Move" -- squad.cood.toString -- newTile.cood.toString)
    case _ => deb("Other" -- clickList.toString)
  }   
   
  eTop()
  mapPanel.repaint(mapObjs)
}
  