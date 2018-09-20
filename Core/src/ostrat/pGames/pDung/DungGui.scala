/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import geom._, pCanv._, pGrid._, Colour._

class DungGui(canv: CanvasPlatform) extends SquareGridGui[DTile, SideBare, DungGrid](canv, Dungeon1)
{  
   mapPanel.backColour = Black
   override def eTop(): Unit = reTop(guButs :+ status)
   
   def fSquare: OfSquareReg[DTile, SideBare, DungGrid] => Disp2 = tog =>
      {
         import tog._         
         val colour: Colour = tile.colour        
         val tv = vertDispVecs.fill(colour)
         val sides = ifScaleCObjs(60, ownSideLines.map(_.draw(1, colour.contrastBW)))
         val tText = ifScaleCObj(60, TextGraphic(cen, xyStr, 14, colour.contrastBW))
         val player = ifScaleOptObj(60, tile.charac)(charac => Circle(50).slate(tog.cen).fillDrawFixed(None, charac.colour, 1)    )
         Disp2(List(tv), tText ++ player ++ sides)
      }
   def mapObjs: GraphicElems =  ofTilesDisplayFold[OfSquareReg[DTile, SideBare, DungGrid]](
         fSquare
         //)((t: DTile, gr: DungGrid, gui: TileGridGui[DTile, SideBare, DungGrid]) => new OfSquareReg[DTile, SideBare, DungGrid](t, gr, gui)
               ).collapse//ofSquaresDisplayFold(fTile).collapse   
  
   eTop()
   mapPanel.repaint(mapObjs)
}
