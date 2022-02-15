/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import geom._, pgui._, Colour._, java.time._

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
  val fixedClock = Clock.fixed(Instant.ofEpochSecond(1234567890L), ZoneOffset.ofHours(0))
  debvar(fixedClock)
  val date = LocalDateTime.now(fixedClock)
  deb(date.getMonth.toString)

  mapPanel.backColour = Black
  mapPanel.mouseUp = (a, b, s) => deb(s.toString)
  canv.onScroll = b => { scale = ife(b, (scale * 1.2).min(scaleMax), (scale / 1.2).max(scaleMin)) }

  implicit class PrimaryBodyExtensions(thisBody: SSPrimaryBody)
  {
    def dispColour: Colour = thisBody match {
    case Mercury => Gray
    case Venus => White
    case Earth => Blue
    case Mars => Red
    case Jupiter => Orange
    case Saturn => Gold
    case Uranus => BrightSkyBlue
    case Neptune => LightGreen
    case Pluto => SandyBrown
    case _ => Yellow
    }

    def posn(elapsed: Integer): PtM2 = thisBody match
    {
      case p: Planet =>
      { val auRatio: Double = p.avSunDist / Earth.avSunDist
        Pt2.circlePtClockwise(0.001 * elapsed / math.sqrt(auRatio.cubed)).toMetres(p.avSunDist)
      }
      case _ => PtM2(0.metres, 0.metres)
    }

    def paint(elapsed: Integer): CircleFill = Circle(0.6 * dispSize, toCanv(thisBody.posn(elapsed))).fill(thisBody.dispColour)

    def dispSize: Double = thisBody match {
      case Sun => 14
      case _ => 10
    }
  }

  var focus: SSPrimaryBody = Earth
  override def eTop(): Unit = {}
  def fBut(body: SSPrimaryBody) = clickButtonOld(body.name, mb => {focus = body; repaintMap()}, body.dispColour)

  def pause: PolygonCompound = clickButton(pausedStr){ mb => deb(pausedStr -- "not implemented yet.");
    paused = !paused; reTop(cmds)
  }
   
  def cmds: Arr[GraphicBounded] = zoomable +% pause ++ sunPlus9.map(fBut)
  reTop(cmds)
  
  canv.startFrame((el, st) => out(el, st))
  def mapObjs: Arr[CircleFill] = sunPlus9.map(_.paint(elapsed))
  
  def out(elapsedIn: Integer, startTime: Integer): Unit =
  { elapsed = elapsedIn
    mapFocus = focus.posn(elapsed)
    if(!paused)repaintMap()
    canv.frame(out, startTime)           
  }      
}