/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pCiv
import geom._, pGrid._, pCanv._, Colour._

class CivGui(canv: CanvasPlatform) extends HexGridGui[CTile, SideBare, CivGrid](canv, "Civs")
{
  statusText --= "Left click unit to select. Right click on adjacent hex to move."
  override val grid: CivGrid = Civ1
  override def scaleMin = 10 
  var pScale: Double = scaleAlignMin
  var focus: Vec2 = grid.cen
  mapPanel.backColour = Colour.Black
  def  fHex: OfHexReg[CTile, SideBare, CivGrid] => GraphicElems = tog =>
    {
      import tog._        
      val colour: Colour = tile.colour
      //val poly = tog.vertDispVecs
      val tv = vertDispVecs.fillActive(colour, tile, -2)
      val sides = ifScaleCObjs(60, ownSideLines.map(_.draw(1, colour.contrastBW)))
      val tText = ifScaleCObj(60, TextGraphic(xyStr, 14, cen, colour.contrastBW))
      val sett = ifScaleIfCObj(40, tile.settlement, Circle(25).slate(cen).fillFixed(None, Black))
      val lunit: GraphicElems = tile.lunits match
      {
        case ::(head, _) if tog.tScale > 50 =>
        {
          val maxOffset = tog.grid.coodToVec2(head.dirn.relCood)
          val gridPosn = cenRelGrid + maxOffset * head.offsetMagnitude
          val posn = fTrans(gridPosn)
          val fillColour = head.faction.colour                      
          Rectangle.curvedCornersCentred(90, 60, 10, posn).subjAll(head, fillColour, 2, fillColour.contrast, 16, head.movePts.toString) :: Nil   
          //Rectangle.curved(90, 60, 10, posn).allFixed(head, fillColour, 2, fillColour.contrast, 16, head.movePts.toString) :: Nil
        }
        case _ => Nil
       }
       tv ++ tText ++ sett ++ lunit ++ sides
     }
  def mapObjs: GraphicElems = ofHTilesDisplayFold(fHex)// ofHexsDisplayFold(fHex).collapse
  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, _) =>
    {
      deb(selected.toString)
      selected = clickList.fHead(Nil, List(_))
      statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
      eTop()
    }
    
    //If a Warrior is selected and a tile adjacent to the Warrior is right clicked =>
    case (RightButton, List(warr : Warrior), List(newTile: CTile)) =>
      {
        deb("Rt") 
        val newCood = newTile.cood
        val oldCood = warr.cood
        
        if (grid.isTileCoodAdjTileCood(oldCood, newCood) && warr.movePts > 0)
        {
          warr.dirn = HexDirn.fromNeighbTileCood(newCood - oldCood)
          def out(elapsed: Integer, startTime: Integer): Unit =
          {
            warr.offsetMagnitude = elapsed / 600
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
            { repaintMap
              canv.frame(out, startTime, 15)
            }                  
          }
          
          warr.movePts = (warr.movePts - warr.terrCost(newTile)).max(0)
          canv.startFrame((el, st) => out(el, st), 15)               
       }            
    }
    case (RightButton, l, _) => deb(l.toString)   
    case _ => deb("Mouse other")
  }   

  def turnCmd: MB0 = mb => { foreachTileAll(_.lunits.foreach(_.resetMovePts())); repaintMap }
  val bTurn = clickButton("T", turnCmd)   
  override def eTop(): Unit = reTop(guButs :+ bTurn :+ status)
  eTop()
  mapPanel.repaint(mapObjs)
}