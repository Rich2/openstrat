/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import geom._, pgui._, Colour._

/** Simple Solar system model application. */
case class PlanetsGui(val canv: CanvasPlatform) extends MapGui("Planets") with Dist2Gui
{
  statusText = "Choose centreing body."
  val maxOrbit: Length = 3700.mMiles
  var years: Double = 0
  var paused: Boolean = false
  def pausedStr: String = paused.fold("Restart", "Pause")  
  var scale: Length = 0.5.mMiles
  override val scaleMax: Length = 10.mMiles
  override val scaleMin: Length = 0.1.mMiles
  var elapsed: Int = 0

  mapPanel.backColour = Black
  mapPanel.mouseUp = (a, b, s) => deb(s.toString)
  canv.onScroll = b => { scale = ife(b, (scale * 1.2).min(scaleMax), (scale / 1.2).max(scaleMin)) }

  implicit class PlanetExtensions(thisPlanet: Planet)
  {
    def dispColour(planet: Planet): Colour = ???
    def paint(elapsed: Integer): CircleFill = Circle(0.6 * thisPlanet.size, toCanv(thisPlanet.posn(elapsed))).fill(thisPlanet.colour)
  }


  var planetFocus: Planet = Earth
  override def eTop(): Unit = ???
  def fBut(planet: Planet) = clickButtonOld(planet.name, mb => {planetFocus = planet; repaintMap()}, planet.colour)
  def pause = clickButtonStdOld(pausedStr, mb => { deb(pausedStr -- "not implemented yet."); paused = !paused; reTop(cmds)})
   
  def cmds: Arr[GraphicBounded] = zoomable +% pause ++ sunPlus9.map(fBut)
  reTop(cmds)
  
  canv.startFrame((el, st) => out(el, st))
  def mapObjs: Arr[CircleFill] = sunPlus9.map(_.paint(elapsed))
  
  def out(elapsedIn: Integer, startTime: Integer): Unit =
  { //pls.foreach(_.posn(elapsed))
    elapsed = elapsedIn
    mapFocus = planetFocus.posn(elapsed)
    if(!paused)repaintMap()
    canv.frame(out, startTime)           
  }      
}