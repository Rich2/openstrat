/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import geom._, pgui._, Colour._, java.time._

/** Simple Solar system model application. */
case class PlanetsGui(val canv: CanvasPlatform) extends MapGui("Planets")// with Length2Gui
{
  val margin = 35
  var mapFocus: PtM2 = PtM2(0.metres, 0.metres)

  val bZoomIn: PolygonCompound = clickButton("+")(zoomInCmd)
  val bZoomOut: PolygonCompound = clickButton("-")(zoomOutCmd)

  def zoomInCmd: MouseButton => Unit = mb =>  { scale = (scale / 1.5).max(scaleMin); repaintMap() }
  def zoomOutCmd: MouseButton => Unit = mb => { scale = (scale * 1.5).min(scaleMax); repaintMap() }

  /** Translates a point from map position to Canvas Display position */
  def toCanv(mapPoint: PtM2): Pt2 = (mapPoint - mapFocus) / scale

  /** Translates an array of map points to an array of Canvas Display positions */
  def arrCanv(inp: PtM2Arr): Polygon = inp.mapPolygon(toCanv(_))

  final def repaintMap(): Unit = mapPanel.repaint(mapObjs)

  def reFocus(newFocus: PtM2): Unit =
  { mapFocus = newFocus
    repaintMap()
  }

  statusText = "Choose centreing body."
  val maxOrbit: Metres = 3700.mMiles
  var years: Double = 0
  var paused: Boolean = false
  def pausedStr: String = paused.fold("Restart", "Pause")  
  var scale: Metres = 0.5.mMilesDepr
  val scaleMax: Metres = 10.mMiles
  val scaleMin: Metres = 0.1.mMilesDepr
  var elapsed: Int = 0
  val nowt: Instant = Instant.now()
  //val num = 1234567890L
  val num = Long.MinValue / 293 // -3111462345678901L
  val fixedClock = Clock.fixed(Instant.ofEpochSecond(num), ZoneOffset.ofHours(0))
  debvar(fixedClock)
  val date = LocalDateTime.now(fixedClock)
  deb(date.getMonth.toString)

  mapPanel.backColour = Black
  mapPanel.mouseUp = (a, b, s) => deb(s.toString)
  canv.onScroll = b => { scale = ife(b, (scale * 1.2).min(scaleMax), (scale / 1.2).max(scaleMin)) }

  implicit class PrimaryBodyExtensions(thisBody: SSPrimaryBody)
  {
    def posn(elapsed: Integer): PtM2 = thisBody match
    {
      case p: Planet =>
      { val auRatio: Double = p.avSunDist / Earth.avSunDist
        Pt2.circlePtClockwise(0.001 * elapsed / math.sqrt(auRatio.cubed)).toMetres(p.avSunDist)
      }
      case _ => PtM2(0.metres, 0.metres)
    }

    def paint(elapsed: Integer): CircleFill = Circle(0.6 * dispSize, toCanv(thisBody.posn(elapsed))).fill(thisBody.colour)

    def dispSize: Double = thisBody match {
      case Sun => 14
      case _ => 10
    }
  }

  var focus: SSPrimaryBody = Earth
  override def eTop(): Unit = {}
  def fBut(body: SSPrimaryBody): PolygonCompound = simpleButton(body.name, body.colour){focus = body; repaintMap()}

  def pause: PolygonCompound = clickButton(pausedStr){ mb => deb(pausedStr -- "not implemented yet.");
    paused = !paused; reTop(cmds)
  }
   
  def cmds: RArr[GraphicBounded] = RArr(bZoomIn, bZoomOut, pause) ++ sunPlus9.map(fBut)
  reTop(cmds)
  
  canv.startFrame((el, st) => out(el, st))
  def mapObjs: RArr[CircleFill] = sunPlus9.map(_.paint(elapsed))
  
  def out(elapsedIn: Integer, startTime: Integer): Unit =
  { elapsed = elapsedIn
    mapFocus = focus.posn(elapsed)
    if(!paused)repaintMap()
    canv.frame(out, startTime)           
  }      
}