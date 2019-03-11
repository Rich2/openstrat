/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import pCanv._, geom._, reflect.ClassTag

/** Base class for displaying single tile grid. */
abstract class TileGridGui[TileT <: Tile, GridT <: TileGridReg[TileT]](title: String) extends UnfixedMapGui(title)
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
  
  def distDelta(mb: MouseButton): Double = mb(1, 5, 25, 0)
  def scaleDelta(mb: MouseButton): Double = mb(1.2, 1.8, 3, 1)
   
  @inline final def foreachTileCood(f: Cood => Unit): Unit = grid.foreachTileCood(f)
  def tilesMap[R: ClassTag](f: TileT => R): Array[R] = grid.tilesMap[R](f)
  def tilesForeach(f: TileT => Unit): Unit = grid.tilesForeach(f)
  def tilesFlatMap[R: ClassTag](f: TileT => Array[R]): Array[R] = grid.tilesFlatMap(f)
  
   
  def inCmd = (mb: MouseButton) => { pScale = (pScale * scaleDelta(mb)).min(scaleMax); updateView }   
  def outCmd = (mb: MouseButton) => { pScale = (pScale / scaleDelta(mb)).max(scaleMin); updateView } 
  def leftCmd: MouseButton => Unit = (mb: MouseButton) =>  {focus = focus.subX(distDelta(mb)); updateView() } 
  def rightCmd: MouseButton => Unit = (mb: MouseButton) => { focus = focus.addX(distDelta(mb)); updateView() }   
  def downCmd: MouseButton => Unit = (mb: MouseButton) => {  focus = focus.subY(distDelta(mb)); updateView }   
  def upCmd: MouseButton => Unit = (mb: MouseButton) => { focus = focus.addY(distDelta(mb)); updateView }
       
  canv.onScroll = b => { pScale = ife(b, (pScale * 1.2).min(scaleMax), (pScale / 1.2).max(scaleMin)); updateView() }
  
  def repaintMap() = { mapPanel.repaint(mapObjs) }  
}