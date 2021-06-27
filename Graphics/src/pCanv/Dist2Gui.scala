/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCanv
import geom._

/** Why is the called a Dist2LikeGui not a Dist2 Gui */
trait Dist2Gui extends MapGui
{
  /** The Distance represented by one pixel width / height on the screen */
  var scale: Metres
  val margin = 35
   
  //(canv.width.subMin(margin, 20) / mapWidth).min(canv.height.subMin(margin, 20) / mapHeight)
  def scaleMax: Metres
  def scaleMin: Metres //= scaleAlignedMin.min(10.millionMiles)

  var mapFocus: Pt2M = Pt2M(0.km, 0.km)
  //@inline def setFocus(x: Distouble, y: Double): Unit = mapFocus = Vec2(x, y)
  
  def scaleAlignedMin: Metres = ??? //mapPanelDiameter / mapWidth.max(mapHeight).max(0.000001)
  def scaleRotatedMin: Metres = ??? //(mapWidth.squared + mapHeight.squared) / mapWidth.max(mapHeight).max(0.000001)
   
  val bZoomIn = clickButtonOld("+", zoomInCmd)
  val bZoomOut = clickButtonOld("-", zoomOutCmd)
  val zoomable: Arr[GraphicActive] = Arr(bZoomIn, bZoomOut)

  val bMapRotateClockwise = clickButtonOld("\u21BB", MouseButton => { rotation -= AngleVec(20); repaintMap() } )
  val bMapRotateAntiClockwise = clickButtonOld("\u21BA", Mousebutton => { rotation += AngleVec(20); repaintMap() } )
  // val focusAdjust = Seq(bFocusLeft, bFocusRight, bFocusUp, bFocusDown, bMapRotateClockwise, bMapRotateAntiClockwise)

  def zoomInCmd: MouseButton => Unit = mb =>  { scale = (scale / 1.5).max(scaleMin); repaintMap() }
  def zoomOutCmd: MouseButton => Unit = mb => { scale = (scale * 1.5).min(scaleMax); repaintMap() }

  /** Translates a point from map position to Canvas Display position */
  def toCanv(mapPoint: Pt2M): Pt2 = (mapPoint - mapFocus).rotate(rotation) / scale
   
  /** Translates a point from Canvas Display position back to Map position */
  def invCanv(canvPoint: Pt2): Pt2 = ??? //(canvPoint / scale).rotate(-rotation) + mapFocus

  /** Translates an array of map points to an array of Canvas Display positions */
  def arrCanv(inp: Pt2MArr): PolygonImp = inp.pMap(toCanv(_))

  final def repaintMap(): Unit =
  { val o2 = mapObjs
    mapPanel.repaint(o2)
  }
   
  def reFocus(newFocus: Pt2M): Unit =
  { mapFocus = newFocus
    repaintMap()
  }

  def adjFocus(adj: Pt2M): Unit = reFocus(mapFocus + adj)
  var rotation: AngleVec = Deg0

  implicit class ImpVec2InCanvasMap(thisVec2: Pt2)
  { def mapRotate: Pt2 = thisVec2.rotate(rotation)
    def antiMapRotate: Pt2 = thisVec2.rotate(- rotation)
  }
}