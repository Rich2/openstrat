/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import geom._, pCanv._, Colour._, pGrid._, pStrat._ 

class ZugGui(canv: CanvasPlatform, game: ZGame, player: ZPlayer) extends HexGridGui[ZugTileOld, ZugSideOld, ZugGridOld](canv, "ZugFuhrer")
{  
  statusText --= "Left click to select, middle to set to fire, right to set to Move."
  val scen = game.getScen(player)
  override val grid: ZugGridOld = scen
  override def scaleMin = 10
  override var pScale: Double = scaleAlignMin
  override var focus: Vec2 = grid.cen
  mapPanel.backColour = Black
  
  def fHex: OfHexReg[ZugTileOld, ZugSideOld, ZugGridOld] => GraphicElems = ofh =>
  { import ofh._         
    val colour: Colour = tile.colour         
    
    val tv: GraphicElems = tile.terr match
    { case _ : Building => vertDispVecs.fillActive(LightGreen, tile) +- Square.fill(pScale * 1.6, colour, cen)
      case _ => vertDispVecs.fillActive(colour, tile)
    }
    
    val tText = ifScaleCObj(60, TextGraphicCen(yxStr, 14, cen, colour.contrastBW, 2))
    
    def action(squad: Squad): GraphicElems = squad.action match
    {
      case Move(coods) =>      
      {
        coods.foldWithPrevious[GraphicElems](squad.cood, Refs()){ (acc, prevCood, nextCood) =>
          val sideCood = (prevCood + nextCood) / 2
          val l1 = CoodLine(prevCood, sideCood).toLine2(coodToDispVec2).draw(2, scen.getTile(prevCood).contrast)
          val l2 = CoodLine(sideCood, nextCood).toLine2(coodToDispVec2).draw(2, scen.getTile(nextCood).contrast)
          acc +- l1 +- l2
        }
      }
      case Fire(target) => Refs(CoodLine(squad.cood, target).toLine2(coodToDispVec2).draw(2, Red).dashed(20, 20))
      case _ => Refs()
    }
    
    val lunit: GraphicElems = tile.lunits match
    {
      case s if tScale > 68 & s.nonEmpty =>
      {
        val counter = UnitCounters.infantry(30, s.head, s.head.colour, tile.colour).slate(cen)
        Refs(counter) ++ action(s.head)
      }
      case _ => Refs()
    }    
    (tv ++ tText ++ lunit)//.toArraySeq
  }
    
  def fSide: OfHexSideReg[ZugTileOld, ZugSideOld, ZugGridOld] => GraphicElems = ofs =>
  { import ofs._    
    ifScaleCObjs(60, side.wall match
      { case true => Refs(vertDispLine.draw(6, Gray))
        case _ => ifTiles(_.colour == _.colour, (t1, _) => vertDispLine.draw(1, t1.colour.contrastBW))
      }
    )    
  }
    
  def dSides: GraphicElems = ofSidesDisplayFold(fSide)
  def mapObjs: GraphicElems = ofTilesDisplayFold(fHex) ++ dSides
     
  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList //.fHead(Arr(), Arr(_))
      statusText = selected.headToStringElse("Nothing Clicked")
      eTop()            
    }
    
    case (RightButton, List(squad : Squad), List(newTile: ZugTileOld)) => scen.zPath(squad.cood, newTile.cood).foreach{ l =>
      squad.action = Move(Coods(l :_*))
      repaintMap
    }
    
    case (MiddleButton, List(squad : Squad), List(newTile: ZugTileOld)) => { squad.action = Fire(newTile.cood); repaintMap }
    
    case (RightButton, List(squad : Squad), List(newTile: ZugTileOld)) => deb("No Move" -- squad.cood.toString -- newTile.cood.toString)
    
    case _ => deb("Other" -- clickList.toString)
  }   
  def turnCmd: MouseCmd = mb => {}
  val bTurn = clickButton("T", turnCmd)   
  override def eTop(): Unit = reTop(guButs +- bTurn +- status)
  eTop()
  mapPanel.repaint(mapObjs)
}
  