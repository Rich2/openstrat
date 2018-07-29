/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import pDisp._
import geom._

/** Gui for display of a single regular TileGrid */
trait TileGridGui[TileT <: GridElem, SideT <: GridElem, GridT <: TileGridReg[TileT, SideT]] extends UnfixedMapGui//RectangleGui
{
   def grid: GridT//TileGrid[TileT]
   /** number of pixels per grid unit */
   def scaleMin: Double = mapPanelDiameter / grid.diagLength
   def scaleMax: Double = 1000
   def scaleAlignMin: Double = mapPanel.width / grid.width min mapPanel.height / grid.height
   /** The number of pixels per x-grid unit. There are 2 x-grid units between square tiles and 4 between hex tiles. */
   var pScale: Double = scaleAlignMin
   var focus: Vec2 = grid.cen
   def viewStr: String = focus.str2 -- pScale.str1
   def updateView(): Unit = {repaintMap; setStatus(viewStr) }
   override def eTop(): Unit = reTop(guButs :+ status)
   def focusStr2 = grid.cen.str2
   val fTrans: Vec2 => Vec2 = v => (v - focus) * pScale
   
   //def updateView(): Unit = {repaintMap; setStatus("Hello") }//viewStr) }
   def distDelta(mb: MouseButton): Double = mb(1, 5, 25, 0)
   def scaleDelta(mb: MouseButton): Double = mb(1.2, 1.8, 3, 1)
   
   //The number of pixels per tile, from tile centre to tile centre
   def ptScale: Double   
   
   def inCmd = (mb: MouseButton) => { pScale = (pScale * scaleDelta(mb)).min(scaleMax); updateView }   
   def outCmd = (mb: MouseButton) => { pScale = (pScale / scaleDelta(mb)).max(scaleMin); updateView } 
   def leftCmd: MouseButton => Unit = (mb: MouseButton) =>  {focus = focus.subX(distDelta(mb)); updateView() } 
   def rightCmd: MouseButton => Unit = (mb: MouseButton) => { focus = focus.addX(distDelta(mb)); updateView() }   
   def downCmd: MouseButton => Unit = (mb: MouseButton) => {  focus = focus.subY(distDelta(mb)); updateView }   
   def upCmd: MouseButton => Unit = (mb: MouseButton) => { focus = focus.addY(distDelta(mb)); updateView }
       
   canv.onScroll = b => { pScale = ife(b, (pScale * 1.2).min(scaleMax), (pScale / 1.2).max(scaleMin)); updateView() }   
   
   def tilesMap[R](f: TileT => R): List[R] = grid.tilesMap[R](f)
   def tilesForeach(f: TileT => Unit): Unit = grid.tilesForeach(f)
   def tilesFlatMap[R](f: TileT => List[R]): List[R] = grid.tilesFlatMap(f)
   
   def ofTilesFold[OfT <: OfTile[TileT, SideT, GridT], R](f: OfT => R, fSum: (R, R) => R, emptyVal: R)
   (implicit oftFactory: (TileT, GridT, TileGridGui[TileT, SideT, GridT]) => OfT) =
   {
      var acc: R = emptyVal
      grid.tileCoodForeach{ tileCood =>
         val newOft = oftFactory(grid.getTile(tileCood), grid, this)
         val newRes: R = f(newOft)
         acc = fSum(acc, newRes)
      }
      acc
   }
   
   def ofSidesFold[OfS <: OfSide[TileT, SideT, GridT], R](f: OfS => R, fSum: (R, R) => R, emptyVal: R)
   (implicit ofsFactory: (SideT, GridT, TileGridGui[TileT, SideT, GridT]) => OfS) =
   {
      var acc: R = emptyVal
      grid.sideCoodForeach{ tileCood =>
         val newOfs = ofsFactory(grid.getSide(tileCood), grid, this)
         val newRes: R = f(newOfs)
         acc = fSum(acc, newRes)
      }
      acc
   }
   
   def ofTilesDisplayFold[OfT <: OfTile[TileT, SideT, GridT]](f: OfT => Disp2)(implicit
         oftFactory: (TileT, GridT, TileGridGui[TileT, SideT, GridT]) => OfT): Disp2 = ofTilesFold[OfT, Disp2](f, _ ++ _, Disp2.empty)(oftFactory)
         
   def ofSidesDisplayFold[OfT <: OfSide[TileT, SideT, GridT]](f: OfT => Disp2)(implicit
         ofsFactory: (SideT, GridT, TileGridGui[TileT, SideT, GridT]) => OfT): Disp2 = ofSidesFold[OfT, Disp2](f, _ ++ _, Disp2.empty)(ofsFactory)      
//   
//   /** These are pretty horrible need looking at */
//   def tilesVertFlatMap[R](f: (TileT, Seq[Vec2]) => Seq[R]): Seq[R] = 
//      grid.tilesFlatMap(tile => f(tile, grid.tileVertCoods(tile.cood).map(grid.coodToVec2).fTrans(fTrans)))
//   /** These are pretty horrible need looking at */
//   def tilesVertMap[R](f: (TileT, Cood, Seq[Vec2]) => R): Seq[R] =
//      grid.tilesMap(tile => f(tile, tile.cood, grid.tileVertCoods(tile.cood).map(grid.coodToVec2).fTrans(fTrans)))
//   /** These are pretty horrible need looking at */   
//   def tilesVertDisplayFold(f: (TileT, Cood, Seq[Vec2]) => Disp2) = 
//      grid.tileAndCoodsDisplayFold((tile, cood) => f(tile, cood, grid.tileVertCoods(tile.cood).map(grid.coodToVec2).fTrans(fTrans)))
//   
   def repaintMap() = { mapPanel.repaint(mapObjs)} 
 //  override def rectMap: RectMap = grid
   //val gridScale: Dist = grid.scale
//   val yRatio: Double

}