/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._

/** Slated for removal. */
abstract class EarthGuiOld(title: String) extends UnfixedMapGui(title)
{
  var focus: LatLong = 50 ll 0
  /** The number of km per pixel  for 1Km on the map. This will normally be much less than 1 */
  var scale: Length =   14000.km / mapPanelDiameter
  def scaleMin: Length = 180.km / mapPanelDiameter
  def scaleMax: Length = 17000.km / mapPanelDiameter
  /** Km / Radian Earth/s Circumference divided by 2 Pi */
  //val kmPerRadian = 40075.0 / Pi2
  val metresPerRadian: Length = 40075.km / Pi2
  def viewStr: String = "Focus:" -- focus.degStr -- "Scale: " + scale.kmStr2
  def updateView(): Unit = {repaintMap(); setStatus(viewStr) }
  def setFocus(ll: LatLong): Unit = { focus = ll; updateView() }
  def view: EarthView = EarthView(focus, scale, focusUp) 
  def setView(ev: EarthView): Unit = { focus = ev.latLong; scale = ev.scale; focusUp = ev.up }   
  var focusUp: Boolean = true
  //def focusDown = ! focusUp
  def ifInvScale: Length = ife(focusUp, scale, -scale)
  def saveNamePrefix: String = "EarthGui"
  def saveName = saveNamePrefix + ".save"
  def loadView(): Unit = canv.fromFileFindForeach(saveName, newView => setView(newView))   
  @inline def polyToGlobedArea(latLongs: PolygonLL): OptEither[PtMetre2Arr, CurveSegDists] = focus.polyToGlobedArea(latLongs)
  def latLongToDist2(ll: LatLong): PtM2 = focus.fromFocusDist2(ll)
  @inline def polyToDist2s(latLongs: PolygonLL): PtMetre2Arr =  latLongs.dataMap(focus.fromFocusDist2)
  val trans: PtM2 => Pt2 = _ / ifInvScale
 //  val transSeq: Dist2s => Vec2s = _.map(trans)
  /** Seems to have a bug */
  def latLongToXY(ll: LatLong): Pt2 = trans(focus.fromFocusDist2(ll))
  def optLatLongToXY(ll: LatLong): Option[Pt2] = focus.optFromFocusDist2(ll).map(trans)
  def optFromFocusDist2(ll: LatLong): Option[PtM2] = focus.optFromFocusDist2(ll)
  def latLongToDist3(ll: LatLong): PtM3 = focus.fromFocusMetres(ll)
  def latLongLineToDist3(inp: LineSegLL): LineSegMetre3 = focus.fromFocusLineDist3(inp)
    
  def distDelta(mb: MouseButton): AngleVec = (mb(1, 5, 25, 0) * ifInvScale / 7.km).degs
  def scaleDelta(mb: MouseButton): Double = mb(1.2, 1.8, 3, 1)  
  def inCmd = (mb: MouseButton) => { scale = (scale / scaleDelta(mb)).max(scaleMin); updateView() }
  def outCmd = (mb: MouseButton) => { scale = (scale * scaleDelta(mb)).min(scaleMax); updateView() }
  
  def addLatRadians(radians: Double): Unit =
  {
    (focus.latRadians + radians) %+- Pi1 match
    {
      //Going over the north Pole from western longitude
      case a if a > PiOn2 && focus.longRadians <= 0 => { focus = LatLong.radians(Pi1 - a, focus.longRadians + Pi1); focusUp = ! focusUp }
      //Going over the north Pole from an eastern longitude
      case a if a > PiOn2             => { focus = LatLong.radians(Pi1 - a, focus.longRadians - Pi1); focusUp = ! focusUp }
      //Going over the south Pole from western longitude
      case a if a < -PiOn2 && focus.longRadians < 0 => { focus = LatLong.radians(-Pi1 - a, Pi1 + focus.longRadians); focusUp = ! focusUp }
      //Going over the south Pole from eastern longitude
      case a if a < -PiOn2             => { focus = LatLong.radians(-Pi1 - a, focus.longRadians - Pi1); focusUp = ! focusUp }
      case a => focus = LatLong.radians(a, focus.longRadians)
     }
     repaintMap()
  }

  def addLat(delta: AngleVec): Unit =
  {
    (focus.latDegs + delta.degs) %+- 180 match
    {
      //Going over the north Pole from western longitude
      case a if a > 90 && focus.longDegs <= 0 => { focus = LatLong.degs(180 - a, focus.longDegs + 180); focusUp = ! focusUp }
      //Going over the north Pole from an eastern longitude
      case a if a > 90             => { focus = LatLong.degs(180 - a, focus.longDegs - 180); focusUp = ! focusUp }
      //Going over the south Pole from western longitude
      case a if a < -90 && focus.longDegs < 0 => { focus = LatLong.degs(-180 - a, 180 + focus.longDegs); focusUp = ! focusUp }
      //Going over the south Pole from eastern longitude
      case a if a < -90             => { focus = LatLong.degs(-180 - a, focus.longDegs - 180); focusUp = ! focusUp }
      case a => focus = LatLong.degs(a, focus.longDegs)
    }
    repaintMap()
  }

  def leftCmd: MouseCmd = mb => setFocus(focus.subLong(distDelta(mb)))
  def rightCmd: MouseCmd = mb => { focus = focus.addLongVec(distDelta(mb)); updateView() }
  def downCmd: MouseCmd = mb => { addLat(-distDelta(mb)); updateView() }
  def upCmd: MouseCmd = mb => { addLat(distDelta(mb)); updateView() }
  def invCmd: MouseCmd = mb => {focusUp = !focusUp; repaintMap() }
  canv.onScroll = b => { scale = ife(b, (scale / 1.2).max(scaleMin), (scale * 1.2).min(scaleMax)); updateView() }  
      
  val bInv = clickButton("inv")(invCmd)
   
  mapPanel.mouseUp = (b, s, v) => { statusText = s.headFoldToString("Nothing Clicked"); eTop() }
   
  def saveCmd = (mb: MouseButton) => { setStatus("Saved"); canv.saveFile(saveName, view.str) }
  def loadCmd = (mb: MouseButton) => { loadView(); updateView() }
  def bSave = clickButton("save")(saveCmd)
  def bLoad = clickButton("load")(loadCmd)
  def eaButts =  Arr(bSave, bLoad)
  def cmd00: MouseCmd = mb => { focus = LatLong0; focusUp = true; updateView() }
  def b00 = clickButton("00")(cmd00)
  override def eTop(): Unit = reTop(guButs ++ Arr(b00, bInv) ++ eaButts +% status)
     
  def ls: GraphicElems
   
  def hairs = mapPanel.crossHairs(1 , Colour.Red)   
  def blueCircle = Circle(EarthAvDiameter / scale).fill(Colour.DarkBlue)
  def redCircle = Circle(EarthAvDiameter / scale).draw(Colour.Red, 1)
  def mapObjs: GraphicElems = ls //:+ hairs
  mapPanel.backColour = Colour.Black
  def repaintMap() = { mapPanel.repaint(mapObjs)}
}