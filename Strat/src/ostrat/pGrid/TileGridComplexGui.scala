/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import pCanv._, geom._

/** Gui for display of a single regular TileGrid */
abstract class TileGridComplexGui[TileT <: Tile, SideT <: GridElem, GridT <: TileGridComplexReg[TileT, SideT]](title: String) extends
  TileGridGui[TileT, GridT](title)
{
  var grid: GridT//TileGrid[TileT]
  /** number of pixels per grid unit */
  def scaleMin: Double = mapPanelDiameter / grid.diagLength
  def scaleMax: Double = 1000
  def scaleAlignMin: Double = mapPanel.width / grid.width min mapPanel.height / grid.height
  /** The number of pixels per x-grid unit. There are 2 x-grid units between square tiles and 4 between hex tiles. */
  var pScale: Double //= scaleAlignMin
  var focus: Vec2 //= grid.cen
  statusText = "Use middle and right mouse buttons on view buttons for greater deltas."
  def viewStr: String = focus.str2 -- pScale.str1
  def updateView(): Unit = {repaintMap; setStatus(viewStr) }
  override def eTop(): Unit = reTop(guButs :+ status)
  def focusStr2 = grid.cen.str2
  val fTrans: Vec2 => Vec2 = v => (v - focus) * pScale   
 
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
  
  def ofTilesFold[OfT <: OfTile[TileT, SideT, GridT], R](f: OfT => R, fSum: (R, R) => R, emptyVal: R)(implicit oftFactory: (TileT, GridT,
      TileGridComplexGui[TileT, SideT, GridT]) => OfT) =
  {
    var acc: R = emptyVal
    grid.tileCoodForeach{ tileCood =>
      val newOft = oftFactory(grid.getTile(tileCood), grid, this)
      val newRes: R = f(newOft)
      acc = fSum(acc, newRes)
    }
    acc
  }
   
  def ofSidesFold[OfS <: OfSide[TileT, SideT, GridT], R](f: OfS => R, fSum: (R, R) => R, emptyVal: R)(implicit ofsFactory: (SideT, GridT,
      TileGridComplexGui[TileT, SideT, GridT]) => OfS) =
  {
    var acc: R = emptyVal
    grid.sideCoodForeach{ tileCood =>
      val newOfs = ofsFactory(grid.getSide(tileCood), grid, this)
      val newRes: R = f(newOfs)
      acc = fSum(acc, newRes)
    }
    acc
  }
   
  def ofTilesDisplayFold[OfT <: OfTile[TileT, SideT, GridT]](f: OfT => GraphicElems)(implicit oftFactory: (TileT, GridT,
      TileGridComplexGui[TileT, SideT, GridT]) => OfT): GraphicElems = ofTilesFold[OfT, GraphicElems](f, _ ++ _, Nil)(oftFactory)
         
  def ofSidesDisplayFold[OfT <: OfSide[TileT, SideT, GridT]](f: OfT => GraphicElems)(implicit ofsFactory: (SideT, GridT,
      TileGridComplexGui[TileT, SideT, GridT]) => OfT): GraphicElems = ofSidesFold[OfT, GraphicElems](f, _ ++ _, Nil)(ofsFactory)         

  def repaintMap() = { mapPanel.repaint(mapObjs) } 
  @inline def adjTileCoodsOfTile(tileCood: Cood): Coods = grid.adjTileCoodsOfTile(tileCood)
}
