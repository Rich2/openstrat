/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pEarth
import Colour.Black
import pDisp._

abstract class EarthGui extends pGrid.GridUserGui
{   
   var focus: LatLong = LatLong.deg(50, 0)   
   /** The number of km per pixel  for 1Km on the map. This will normally be much less than 1 */
   var scale: Dist =   15500.km / mapPanelDiameter
   def scaleMin: Dist = 400.km / mapPanelDiameter 
   def scaleMax: Dist = 17000.km / mapPanelDiameter
   /** Km / Radian Earth/s Circumference divided by 2 Pi */
   //val kmPerRadian = 40075.0 / Pi2
   val metresPerRadian: Dist = 40075.km / Pi2   
   def viewStr: String = "Focus:" -- focus.degStr -- "Scale: " - scale.kmStr2
   def updateView(): Unit = {repaintMap; setStatus(viewStr) }
   def setFocus(ll: LatLong): Unit = { focus = ll; updateView }
   def view: EarthView = EarthView(focus, scale, focusUp) 
   def setView(ev: EarthView): Unit = { focus = ev.latLong; scale = ev.scale; focusUp = ev.up }   
   var focusUp: Boolean = true
   //def focusDown = ! focusUp
   def ifInvScale: Dist = ife(focusUp, scale, -scale)
   def saveNamePrefix: String = "EarthGui"
   def saveName = saveNamePrefix - ".save"
   
   def loadView(): Unit =
   {      
      val mStr = canv.load(saveName)
      val res: EMon[Seq[Statement]] = mStr.flatMap(g=> ParseTree.fromString(g))
      val res1 = res.flatMap(_.findType[EarthView])
      res1.fold(errs => setStatus(errs.toString), newView =>  setView(newView) )                         
   }
   
   @inline def polyToGlobedArea(latLongs: LatLongs): GlobedArea = focus.polyToGlobedArea(latLongs)
   @inline def polyToDist2s(latLongs: LatLongs): Dist2s =  latLongs.pMap(focus.fromFocusDist2)//       focus.polyToDist2s(latLongs) 
   val trans: Dist2 => Vec2 = _ / ifInvScale
 //  val transSeq: Dist2s => Vec2s = _.map(trans)
   def latLongToXY(ll: LatLong): Vec2 = trans(focus.fromFocusDist2(ll))
   def optLatLongToXY(ll: LatLong): Option[Vec2] = focus.optFromFocusDist2(ll).map(trans)
   def optFromFocusDist2(ll: LatLong): Option[Dist2] = focus.optFromFocusDist2(ll)
   def latLongToDist3(ll: LatLong): Dist3 = focus.fromFocusDist3(ll)
   def latLongLineToDist3(inp: LatLongLine): LineDist3 = focus.fromFocusLineDist3(inp)   
    
   def distDelta(mb: MouseButton): Double = mb(1, 5, 25, 0) * ifInvScale / 400.km             
   def scaleDelta(mb: MouseButton): Double = mb(1.2, 1.8, 3, 1)  
   def inCmd = (mb: MouseButton) => { scale = (scale / scaleDelta(mb)).max(scaleMin); updateView }   
   def outCmd = (mb: MouseButton) => { scale = (scale * scaleDelta(mb)).min(scaleMax); updateView }    
   def addLat(radians: Double): Unit =
   {
      import math.Pi
      Angle.reset(focus.lat + radians) match
      {
         //Going over the north Pole from western longitude
         case a if a > PiH && focus.long <= 0 => { focus = LatLong(Pi - a, focus.long + Pi); focusUp = ! focusUp }
         //Going over the north Pole from an eastern longitude
         case a if a > PiH             => { focus = LatLong(Pi - a, focus.long - Pi); focusUp = ! focusUp }
         //Going over the south Pole from western longitude
         case a if a < -PiH && focus.long < 0 => { focus = LatLong(-Pi - a, Pi + focus.long); focusUp = ! focusUp }
         //Going over the south Pole from eastern longitude
         case a if a < -PiH             => { focus = LatLong(-Pi - a, focus.long - Pi); focusUp = ! focusUp }
         case a => focus = LatLong(a, focus.long)
      } 
      repaintMap()
   }
   def leftCmd: MouseButton => Unit = (mb: MouseButton) => setFocus(focus.subLong(distDelta(mb))) 
   def rightCmd: MouseButton => Unit = (mb: MouseButton) => { focus = focus.addLong(distDelta(mb)); updateView() }   
   def downCmd: MouseButton => Unit = (mb: MouseButton) => { addLat(-distDelta(mb)); updateView }   
   def upCmd: MouseButton => Unit = (mb: MouseButton) => { addLat(distDelta(mb)); updateView }   
   def invCmd = () => {focusUp = !focusUp; repaintMap() }    
   canv.onScroll = b => { scale = ife(b, (scale / 1.2).max(scaleMin), (scale * 1.2).min(scaleMax)); updateView() }  
      
   val bInv = button1("inv", invCmd)      
   
   mapPanel.mouseUp = (a, b, s) => { statusText = s.headOption.fold("Nothing Clicked")(_.toString)
         eTop() }  
   
   def saveCmd = (mb: MouseButton) => { setStatus("Saved"); canv.save(saveName, view.persist) }
   def loadCmd = (mb: MouseButton) => { loadView; updateView() }  
   def bSave = button3("save", saveCmd)
   def bLoad = button3("load", loadCmd)
   def eaButts: Seq[ShapeSubj] =  List(bSave, bLoad)     
   def cmd00 = () => { focus = LatLong0; focusUp = true; updateView }
   def b00 = button1("00", cmd00) 
  override def eTop(): Unit = reTop(guButs ++ Seq(b00, bInv) ++ eaButts :+ status)   
     
   def ls: Seq[CanvObj[_]] 
   
   def hairs = mapPanel.crossHairs(1 , Colour.Red)   
   def blueCircle = Circle.segs(EarthAvDiameter / scale).fill(Colour.DarkBlue)
   def redCircle = Circle.segs(EarthAvDiameter / scale).draw(1, Colour.Red)
   def mapObjs: CanvObjs = ls //:+ hairs      
   mapPanel.backColour = Black
   def repaintMap() = { mapPanel.repaint(mapObjs)}  
}
