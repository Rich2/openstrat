/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDung
import geom._, pCanv._, pGrid._, SFace._, Colour._

class DungeonGui(canv: CanvasPlatform) extends SquareGridGui[DTileOld, SideOldBare, DungeonGridOld](canv, Dungeon1, "Dungeon")
{ 
  mapPanel.backColour = Black
  var pScale: Double = scaleAlignMin
  var focus: Vec2 = grid.cen
  override def eTop(): Unit = reTop(guButs +- status)
   
  def fSquare: OfSquareReg[DTileOld, SideOldBare, DungeonGridOld] => GraphicElemsOld = tog =>
  { import tog._
    val colour: Colour = tile.colour
    val tv = vertDispVecs.fillActive(colour, tile)
     val sides = ifScaleCObjs(60, ownSideLines.map(_.draw(1, colour.contrastBW)))
    val tText = ifScaleCObj(60, TextGraphic(xyStr, 14, cen, colour.contrastBW))
    val player = ifScaleOptObjsNew(60, tile.charac){charac =>
      val poly1 = Rectangle(1.5).insVerts(1, -0.25 vv 0.5, 0 vv 0.8, 0.25 vv 0.5)
      val poly2 = poly1.scale(tog.tScale / 2.5).rotate(charac.facing.angle).slate(tog.cen)      
      Refs(poly2.fillDrawParentOld(charac, charac.colour, 1), TextGraphic(charac.iden.toString, 16, cen, charac.colour.contrast))
    }
    tv.toArraySeq ++ tText ++ player.toArraySeq ++ sides
  }
  
  def mapObjs: GraphicElems = (ofTilesDisplayFold[OfSquareReg[DTileOld, SideOldBare, DungeonGridOld]](fSquare)).toRefs

  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList//.fHead(Arr(), Arr(_))
      statusText = selected.headToStringElse("Nothing Clicked")
      eTop()
    }
    case (RightButton, Refs1(ch: Character), Refs1(newTile: DTileOld)) if
      adjTileCoodsOfTile(ch.cood).contains(newTile.cood) && ch.canMove(newTile) =>
    { grid.getTile(ch.cood).charac = NoRef
      ch.cood = newTile.cood
      newTile.charac = OptRef(ch)
      repaintMap      
    }
    case (MiddleButton, Refs1(ch: Character), Refs1(newTile: DTileOld)) => optFace(ch.cood, newTile.cood) match
    { case Some(face) => { ch.facing = face; repaintMap }      
      case _ => deb("Middle Button other")
    }
    case _ =>
  }
  eTop()
  mapPanel.repaint(mapObjs)
}
