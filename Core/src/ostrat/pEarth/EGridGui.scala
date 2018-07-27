/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._
import pGrid._

class EGridGui(canv: pDisp.CanvasPlatform, eg: EGrid[TerrOnly, ESideOnly]) extends HexGridGui[TerrOnly, ESideOnly, EGrid[TerrOnly, ESideOnly]](canv, eg)
{
   override def eTop(): Unit = reTop(guButs :+ status)
   eTop
   mapPanel.backColour = Colour.Black
   override def viewStr: String = "focus: " - focusStr2 -- ptScale.str1 -- ptScale.str1
   def fHex: OfHex[TerrOnly, ESideOnly, EGrid[TerrOnly, ESideOnly]] => Disp2 = tog => 
      {
         val tile = tog.tile
         val colour: Colour = tile.colour
         val poly = tog.vertVecs
         val tv = poly.fill(colour)
         val sides = tog.ifScaleCObjs(60, tog.ownSideLines.map(line => LineDraw(line, 1, colour.contrastBW)))
         val tText = tog.ifScaleCObjs(68,
               {
            val ls: List[String] = List(tog.yxStr, tog.grid.getLL(tog.cood).toString)//cenLL.toString)                   
            FillText.lines(tog.cen, ls, 10, colour.contrastBW) 
            //FillText(tog.cen, tog.xyStr, 14, colour.contrastBW)
            
               })
         Disp2(List(tv), sides ++ tText)         
      }
   
   override def mapObjs: CanvObjs = ofTilesDisplayFold(fHex)(OfHex.apply[TerrOnly, ESideOnly, EGrid[TerrOnly, ESideOnly]]).collapse//ofHexsDisplayFold(fHex).collapse
   repaintMap
}