/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import pCanv._, geom._, reflect.ClassTag

/** Gui for display of a single regular complex TileGrid */
abstract class TileGridGui[TileT <: Tile, SideT <: GridElem, GridT <: TileGridReg[TileT, SideT]](title: String) extends
  UnfixedMapGui(title)//TileGridGui[TileT, SideT, GridT](title)
{
  val grid: GridT
  /** number of pixels per grid unit */
  def scaleMin: Double = mapPanelDiameter / grid.diagLength
  def scaleMax: Double = 1000
  def scaleAlignMin: Double = (mapPanel.width / grid.width).min(mapPanel.height / grid.height)
  /** The number of pixels per x-grid unit. There are 2 x-grid units between complex square tiles and 4 between complex hex tiles. */
  var pScale: Double
  //The number of pixels per tile, from tile centre to tile centre
  final def tileScale: Double = pScale / grid.xStep
  var focus: Vec2 //= grid.cen
  statusText = "Use middle and right mouse buttons on view buttons for greater deltas."
  def viewStr: String = focus.str2 -- pScale.str1
  def updateView(): Unit = {repaintMap; setStatus(viewStr) }
  override def eTop(): Unit = reTop(guButs :+ status)
  def focusStr2 = grid.cen.str2
  val fTrans: Vec2 => Vec2 = v => (v - focus) * pScale
  def coodToDisp(cood: Cood): Vec2 = fTrans(grid.coodToVec2(cood))
  
  def distDelta(mb: MouseButton): Double = mb(1, 5, 25, 0)
  def scaleDelta(mb: MouseButton): Double = mb(1.2, 1.8, 3, 1)
  
  /** For all Tiles call side effecting function on the Tile's Cood. */ 
  @inline final def forallTilesCood(f: Cood => Unit): Unit = grid.forallTilesCood(f)
  
  /** For all Tiles call side effecting function on the Tile's XY Cood. */
  @inline final def forallTilesXY(f: (Int, Int) => Unit): Unit = grid.forallTilesXY(f)  
  
  /** For all Tiles call side effecting function on the Tile. */
  def forallTiles(f: TileT => Unit): Unit = grid.forallTiles(f)
  
  /** Map all Tiles to Array with function. */
  def allTilesMap[B: ClassTag](f: TileT => B): Array[B] = grid.allTilesMap[B](f)
  
  /** Map all Tiles to an Array with function and flatten into Single Array. */
  def allTilesFlatMap[R: ClassTag](f: TileT => Array[R]): Array[R] = grid.allTilesFlatMap(f)
  
  /** Map all tiles Cood to a List. */
  @inline final def allTilesCoodMapList[A](f: Cood => A): List[A] = grid.allTilesCoodMapList(f)  
   
  def inCmd = (mb: MouseButton) => { pScale = (pScale * scaleDelta(mb)).min(scaleMax); updateView }   
  def outCmd = (mb: MouseButton) => { pScale = (pScale / scaleDelta(mb)).max(scaleMin); updateView } 
  def leftCmd: MouseButton => Unit = (mb: MouseButton) =>  {focus = focus.subX(distDelta(mb)); updateView() } 
  def rightCmd: MouseButton => Unit = (mb: MouseButton) => { focus = focus.addX(distDelta(mb)); updateView() }   
  def downCmd: MouseButton => Unit = (mb: MouseButton) => {  focus = focus.subY(distDelta(mb)); updateView }   
  def upCmd: MouseButton => Unit = (mb: MouseButton) => { focus = focus.addY(distDelta(mb)); updateView }
       
  canv.onScroll = b => { pScale = ife(b, (pScale * 1.2).min(scaleMax), (pScale / 1.2).max(scaleMin)); updateView() }
  
  def repaintMap() = { mapPanel.repaint(mapObjs) }  
  
  
  
  
  def ofTilesFold[OfT <: OfTile[TileT, SideT, GridT], R](f: OfT => R, fSum: (R, R) => R, emptyVal: R)(implicit oftFactory: (TileT, GridT,
      TileGridGui[TileT, SideT, GridT]) => OfT) =
  {
    var acc: R = emptyVal
    forallTilesCood{ tileCood =>
      val newOft = oftFactory(grid.getTile(tileCood), grid, this)
      val newRes: R = f(newOft)
      acc = fSum(acc, newRes)
    }
    acc
  }
   
  def ofSidesFold[OfS <: OfSide[TileT, SideT, GridT], R](f: OfS => R, fSum: (R, R) => R, emptyVal: R)(implicit ofsFactory: (SideT, GridT,
      TileGridGui[TileT, SideT, GridT]) => OfS) =
  {    
    var acc: R = emptyVal
    grid.forallSidesCood{ tileCood =>
      val newOfs = ofsFactory(grid.getSide(tileCood), grid, this)
      val newRes: R = f(newOfs)
      acc = fSum(acc, newRes)
    }
    acc
  }
   
  def ofTilesDisplayFold[OfT <: OfTile[TileT, SideT, GridT]](f: OfT => GraphicElems)(implicit oftFactory: (TileT, GridT,
      TileGridGui[TileT, SideT, GridT]) => OfT): GraphicElems = ofTilesFold[OfT, GraphicElems](f, _ ++ _, Nil)(oftFactory)
         
  def ofSidesDisplayFold[OfT <: OfSide[TileT, SideT, GridT]](f: OfT => GraphicElems)(implicit ofsFactory: (SideT, GridT,
      TileGridGui[TileT, SideT, GridT]) => OfT): GraphicElems = ofSidesFold[OfT, GraphicElems](f, _ ++ _, Nil)(ofsFactory)
 
  @inline def adjTileCoodsOfTile(tileCood: Cood): Coods = grid.adjTileCoodsOfTile(tileCood)
}