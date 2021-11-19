/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSpace
import geom._, pgui._, Colour._

/** Simple Solar system model application. */
case class Planets(val canv: CanvasPlatform) extends MapGui("Planets") with Dist2Gui
{
  statusText = "Choose centreing body."
  val maxOrbit: Metres = 3700.mMiles.metres
  var years: Double = 0
  var paused: Boolean = false
  def pausedStr: String = paused.fold("Restart", "Pause")  
  var scale = 0.5.mMiles.metres
  override val scaleMax: Metres = 10.mMiles.metres
  override val scaleMin: Metres = 100000.miles.metres
  val earthDist = 93.mMiles
  /** Years per second */
   
  mapPanel.backColour = Black
  mapPanel.mouseUp = (a, b, s) => deb(s.toString)
  canv.onScroll = b => { scale = ife(b, (scale * 1.2).min(scaleMax), (scale / 1.2).max(scaleMin)) }

  class Planet(val dist: Metres, val colour: Colour, val name: String)
  {
    var posn: PtMetre2 = PtMetre2(dist, 0.metres)
    //Gets the angle and the multiplies by the scala. (* dist) at end
    
    def move(elapsed: Integer): Unit =
    { val auRatio = dist / earthDist        
      posn = Pt2.circlePtClockwise(0.001 * elapsed / math.sqrt(auRatio.cubed)).toMetres(dist)
    }

    def size = 10
    def paint = Circle(0.6 * size, toCanv(posn)).fill(colour)
    override def toString = name
  }

  object Planet
  {
    def miles(miles: Miles, colour: Colour, name: String): Planet = new Planet(miles.metres, colour, name)
    def mMiles(mMiles: Metres, colour: Colour, name: String): Planet = new Planet(mMiles, colour, name)
  }
  
  val mercury = Planet.mMiles(36.mMiles, Colour.Gray, "Mercury")
  val venus = Planet.mMiles(67.2.mMiles , White, "Venus" )
  val earth = Planet.mMiles(earthDist, Blue, "Earth")
  val mars = Planet.mMiles(141.6.mMiles , Red, "Mars")
  val jupiter = Planet.mMiles(483.6.mMiles , Orange, "Jupiter")
  val saturn = Planet.mMiles(886.7.mMiles , Gold, "Saturn")
  val uranus = Planet.mMiles(1784.0.mMiles , Colour.BrightSkyBlue, "Uranus")
  val neptune = Planet.mMiles(2794.4.mMiles , LightGreen, "Neptune")
  val pluto = Planet.mMiles(3674.5.mMiles, Colour.SandyBrown, "Pluto")
  
  object Sun extends Planet(0.mMiles, Yellow, "Sun")
  { override def move(elapsed: Integer): Unit = {}
    override val size = 14
  }
  
  val pls: Arr[Planet] = Arr(Sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto)
  var planetFocus: Planet = earth
  override def eTop(): Unit = ???
  def fBut(planet: Planet) = clickButtonOld(planet.name, mb => {planetFocus = planet; repaintMap()}, planet.colour)
  def pause = clickButtonStdOld(pausedStr, mb => { deb(pausedStr -- "not implemented yet."); paused = !paused; reTop(cmds)})
   
  def cmds: Arr[GraphicBounded] = zoomable +% pause ++ pls.map(fBut)
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