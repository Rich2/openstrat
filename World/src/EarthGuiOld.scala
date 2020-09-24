/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pEarth
import geom._, pCanv._

/** Slated for removal. */
abstract class EarthGuiOld(title: String) extends UnfixedMapGui(title)
{
  var focus: LatLong = LatLong.degs(50, 0)
  /** The number of km per pixel  for 1Km on the map. This will normally be much less than 1 */
  var scale: Dist =   14000.km / mapPanelDiameter
  def scaleMin: Dist = 180.km / mapPanelDiameter
  def scaleMax: Dist = 17000.km / mapPanelDiameter
  /** Km / Radian Earth/s Circumference divided by 2 Pi */
  //val kmPerRadian = 40075.0 / Pi2
  val metresPerRadian: Dist = 40075.km / Pi2   
  def viewStr: String = "Focus:" -- focus.degStr -- "Scale: " + scale.kmStr2
  def updateView(): Unit = {repaintMap(); setStatus(viewStr) }
  def setFocus(ll: LatLong): Unit = { focus = ll; updateView() }
  def view: EarthView = EarthView(focus, scale, focusUp) 
  def setView(ev: EarthView): Unit = { focus = ev.latLong; scale = ev.scale; focusUp = ev.up }   
  var focusUp: Boolean = true
  //def focusDown = ! focusUp
  def ifInvScale: Dist = ife(focusUp, scale, -scale)
  def saveNamePrefix: String = "EarthGui"
  def saveName = saveNamePrefix + ".save"
  def loadView(): Unit = canv.fromFileFindForeach(saveName, newView => setView(newView))   
  @inline def polyToGlobedArea(latLongs: LatLongs): GlobedArea = focus.polyToGlobedArea(latLongs)
  def latLongToDist2(ll: LatLong): Dist2 = focus.fromFocusDist2(ll)
  @inline def polyToDist2s(latLongs: LatLongs): Dist2s =  latLongs.pMap(focus.fromFocusDist2)//focus.polyToDist2s(latLongs) 
  val trans: Dist2 => Vec2 = _ / ifInvScale
 //  val transSeq: Dist2s => Vec2s = _.map(trans)
  /** Seems to have a bug */
  def latLongToXY(ll: LatLong): Vec2 = trans(focus.fromFocusDist2(ll))
  def optLatLongToXY(ll: LatLong): Option[Vec2] = focus.optFromFocusDist2(ll).map(trans)
  def optFromFocusDist2(ll: LatLong): Option[Dist2] = focus.optFromFocusDist2(ll)
  def latLongToDist3(ll: LatLong): Dist3 = focus.fromFocusDist3(ll)
  def latLongLineToDist3(inp: LLLineSeg): LineDist3 = focus.fromFocusLineDist3(inp)   
    
  def distDelta(mb: MouseButton): Double = mb(1, 5, 25, 0) * ifInvScale / 400.km             
  def scaleDelta(mb: MouseButton): Double = mb(1.2, 1.8, 3, 1)  
  def inCmd = (mb: MouseButton) => { scale = (scale / scaleDelta(mb)).max(scaleMin); updateView() }
  def outCmd = (mb: MouseButton) => { scale = (scale * scaleDelta(mb)).min(scaleMax); updateView() }
  
  def addLat(radians: Double): Unit =
  { import math.Pi
    Angle.resetRadians(focus.latRadians + radians) match
    {
      //Going over the north Pole from western longitude
      case a if a > PiH && focus.longRadians <= 0 => { focus = LatLong.radians(Pi - a, focus.longRadians + Pi); focusUp = ! focusUp }
      //Going over the north Pole from an eastern longitude
      case a if a > PiH             => { focus = LatLong.radians(Pi - a, focus.longRadians - Pi); focusUp = ! focusUp }
      //Going over the south Pole from western longitude
      case a if a < -PiH && focus.longRadians < 0 => { focus = LatLong.radians(-Pi - a, Pi + focus.longRadians); focusUp = ! focusUp }
      //Going over the south Pole from eastern longitude
      case a if a < -PiH             => { focus = LatLong.radians(-Pi - a, focus.longRadians - Pi); focusUp = ! focusUp }
      case a => focus = LatLong.radians(a, focus.longRadians)
     } 
     repaintMap()
  }
  
  def leftCmd: MouseCmd = mb => setFocus(focus.subLongRadians(distDelta(mb)))
  def rightCmd: MouseCmd = mb => { focus = focus.addLongRadians(distDelta(mb)); updateView() }
  def downCmd: MouseCmd = mb => { addLat(-distDelta(mb)); updateView() }
  def upCmd: MouseCmd = mb => { addLat(distDelta(mb)); updateView() }
  def invCmd: MouseCmd = mb => {focusUp = !focusUp; repaintMap() }
  canv.onScroll = b => { scale = ife(b, (scale / 1.2).max(scaleMin), (scale * 1.2).min(scaleMax)); updateView() }  
      
  val bInv: GraphicBoundedAffine = clickButton("inv", invCmd)
   
  mapPanel.mouseUp = (b, s, v) => { statusText = s.headToStringElse("Nothing Clicked"); eTop() }
   
  def saveCmd = (mb: MouseButton) => { setStatus("Saved"); canv.saveFile(saveName, view.str) }
  def loadCmd = (mb: MouseButton) => { loadView(); updateView() }
  def bSave = clickButton("save", saveCmd)
  def bLoad = clickButton("load", loadCmd)
  def eaButts: Arr[GraphicBoundedAffine] =  Arr(bSave, bLoad)
  def cmd00: MouseCmd = mb => { focus = LatLong0; focusUp = true; updateView() }
  def b00: GraphicBoundedAffine = clickButton("00", cmd00)
  override def eTop(): Unit = reTop(guButs ++ Arr(b00, bInv) ++ eaButts +- status)
     
  def ls: DisplayElems
   
  def hairs = mapPanel.crossHairs(1 , Colour.Red)   
  def blueCircle = Circle(EarthAvDiameter / scale).fill(Colour.DarkBlue)
  def redCircle = Circle(EarthAvDiameter / scale).draw(1, Colour.Red)
  def mapObjs: DisplayElems = ls //:+ hairs
  mapPanel.backColour = Colour.Black
  def repaintMap() = { mapPanel.repaint(mapObjs)}
}