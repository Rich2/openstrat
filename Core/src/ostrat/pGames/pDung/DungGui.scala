/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import geom._, pCanv._, pGrid._, Colour._

class DungGui(canv: CanvasPlatform) extends SquareGridGui[DTile, SideBare, DungGrid](canv, Dungeon1)
{ mapPanel.backColour = Black
  override def eTop(): Unit = reTop(guButs :+ status)
   
  def fSquare: OfSquareReg[DTile, SideBare, DungGrid] => Disp2 = tog =>
  { import tog._
    val colour: Colour = tile.colour
    val tv = vertDispVecs.fill(colour)
    val sides = ifScaleCObjs(60, ownSideLines.map(_.draw(1, colour.contrastBW)))
    val tText = ifScaleCObj(60, TextGraphic(cen, xyStr, 14, colour.contrastBW))
    val player = ifScaleOptObjs(60, tile.charac){charac =>
      val poly1 = Rectangle(1.5)
      val poly2 = poly1.insVerts(1, -0.25 vv 0.5, 0 vv 0.8, 0.25 vv 0.5)
      val poly3 = poly2.scale(tog.tScale / 2.5).slate(tog.cen)
      //Rectangle Circle(50).slate(tog.cen).fillDrawFixed(None, charac.colour, 1)
      List(poly3.fillDraw(charac.colour, 1), TextGraphic(cen, charac.iden.toString, 16, charac.colour.contrast))
    }
    Disp2(List(tv), tText ++ player ++ sides)
  }
  def mapObjs: GraphicElems =  ofTilesDisplayFold[OfSquareReg[DTile, SideBare, DungGrid]](
    fSquare
    //)((t: DTile, gr: DungGrid, gui: TileGridGui[DTile, SideBare, DungGrid]) => new OfSquareReg[DTile, SideBare, DungGrid](t, gr, gui)
    ).collapse//ofSquaresDisplayFold(fTile).collapse

  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { debvar(clickList)
      selected = clickList.fHead(Nil, List(_))
      statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
      eTop()
    }
    case (RightButton, List(ch: Character), List(newTile: DTile)) =>
    {
//      val newCood = newTile.cood
//      val oldCood = squad.cood
//      if (HexGrid.adjTileCoodsOfTile(oldCood).contains(newCood) && squad.canMove(newTile))
//      {
//        val oldTile = grid.getTile(oldCood)
//        oldTile.lunits = oldTile.lunits.removeFirst(_ == squad)
//        squad.cood = newCood
//        newTile.lunits ::= squad
//        repaintMap
//      }
    }
    case _ =>
  }
  eTop()
  mapPanel.repaint(mapObjs)
}
