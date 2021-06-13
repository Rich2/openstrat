/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSpace
import geom._, pCanv._, Colour._

/** Simple Solar system model application. */
case class Planets(val canv: CanvasPlatform) extends MapGui("Planets") with Dist2Gui
{
  statusText = "Choose centreing body."
  val maxOrbit: Metre = 3700.millionMiles
  var years: Double = 0
  var paused: Boolean = false
  def pausedStr: String = paused.fold("Restart", "Pause")  
  var scale = 0.5.millionMiles
  override val scaleMax: Metre = 10.millionMiles
  override val scaleMin: Metre = 100000.miles
  val earthDist = 93.millionMiles
  /** Years per second */
   
  mapPanel.backColour = Black
  mapPanel.mouseUp = (a, b, s) => deb(s.toString)
  canv.onScroll = b => { scale = ife(b, (scale * 1.2).min(scaleMax), (scale / 1.2).max(scaleMin)) }
  case class Planet(dist: Metre, colour: Colour, name: String)
  {
    var posn: Pt2M = Pt2M(dist, 0.metre)
    //Gets the angle and the multiplies by the scala. (* dist) at end
    
    def move(elapsed: Integer): Unit =
    { val auRatio = dist / earthDist        
      posn = Pt2.circlePtClockwise(0.001 * elapsed / math.sqrt(auRatio.cubed)).toDist2(dist)
    }

    def size = 10
    def paint = Circle(0.6 * size, toCanv(posn)).fill(colour)
    override def toString = name
  }
  
  val mercury = Planet(36.millionMiles, Colour.Gray, "Mercury")
  val venus = Planet(67.2.millionMiles , White, "Venus" )      
  val earth = Planet(earthDist, Blue, "Earth")  
  val mars = Planet(141.6.millionMiles , Red, "Mars")
  val jupiter = Planet(483.6.millionMiles , Orange, "Jupiter")
  val saturn = Planet(886.7.millionMiles , Gold, "Saturn")
  val uranus = Planet(1784.0.millionMiles , Colour.BrightSkyBlue, "Uranus")
  val neptune = Planet(2794.4.millionMiles , LightGreen, "Neptune")
  val pluto = Planet(3674.5.millionMiles, Colour.SandyBrown, "Pluto")
  
  object Sun extends Planet(0.millionMiles, Yellow, "Sun")
  { override def move(elapsed: Integer): Unit = {}
    override val size = 14
  }
  
  val pls: Arr[Planet] = Arr(mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto, Sun)
  var planetFocus: Planet = earth
  override def eTop(): Unit = ???
  def fBut(planet: Planet) = clickButtonOld(planet.name, mb => {planetFocus = planet; repaintMap()}, planet.colour)
  def pause = clickButtonStdOld(pausedStr, mb => { deb(pausedStr -- "not implemented yet."); paused = !paused; reTop(cmds)})
   
  def cmds: Arr[GraphicBounded] = zoomable +- pause ++ pls.map(fBut)
  reTop(cmds)
  
  canv.startFrame((el, st) => out(el, st))
  def mapObjs = pls.map(_.paint)
  
  def out(elapsed: Integer, startTime: Integer): Unit =
  { pls.foreach(_.move(elapsed))
    mapFocus = planetFocus.posn
    if(!paused)repaintMap()
    canv.frame(out, startTime)           
  }      
}