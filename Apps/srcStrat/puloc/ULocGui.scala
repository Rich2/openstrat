/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._, pgui._, Colour._, pStrat.InfantryCounter

/** Graphical user interface for Unit Locator. */
case class ULocGui(canv: CanvasPlatform, var date: MTime, viewIn: EarthView = EarthView(50, 12, 1.2)) extends GlobeGui("The Earth in irregular tiles")
{ /** Scale in km / pixel */
  var scale: Length = viewIn.scale

  /** Scale accounting for whether the display has north up or down. */
  def dirnScale: Length = ife(northUp, scale, -scale)

  val scaleMin: Length = 0.2.kMetres
  val scaleMax: Length = 100.kMetres
  var focus: LatLong = viewIn.latLong

  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)

  val ps1: PolygonLLPairArr[EArea2] = eas.map(ea => PolygonLLPair[EArea2](ea.polygonLL, ea))
  val lc1: LocationLLArr = eas.flatMap(_.places)
  val lc2: PtM3PairArr[Place] = lc1.mapOnA1(_.toMetres3)

  /** This compiles without type annotation. */
  val ps2: PolygonM3PairArr[EArea2] = ps1.polygonMapToPair(_.toMetres3)

  statusText = "Welcome to Unit locations. Click on units to get identity."
  def finds: RArr[LunitState] = unitsAt(date)

  def repaint(): Unit =
  { val ps3: PolygonM3PairArr[EArea2] = ps2.polygonMapToPair(_.fromLatLongFocus(focus))

    val ps4: PolygonM2PairArr[EArea2] = ps3.optMapOnA1{
      case p if p.zAllNonNeg => Some(p.map(_.xy))
      case p if p.zAllNeg => None
      case p =>
      { val newPoly = p.map {
          case v if v.zNeg => (v.xy / v.xyLengthFrom()).toMetres(EarthAvRadius)
          case v => v.xy
        }
        Some(newPoly)
      }
    }

    val ps5 = ps4.polygonMapToPair{ p => p / dirnScale }

    val activeFills: RArr[PolygonCompound] = ps5.pairMap((p, a2) => p.fillActive(a2.colour, a2))

    val sideLines: RArr[PolygonDraw] = ps5.a1Map { _.draw() }

    val areaNames: RArr[TextGraphic] = ps4.a2Map { d =>
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / dirnScale
      TextGraphic(d.name, 10, posn, d.colour.contrastBW)
    }

    val locs1: PtM3PairArr[Place] = lc2.mapOnA1(_.fromLatLongFocus(focus))
    val locs2: PtM3PairArr[Place] = locs1.filterOnA1(_.zPos)
    val locs3: Pt2PairArr[Place] = locs2.mapOnA1(_.xy / scale)

    val locTexts = locs3.map{ p => val col = p.a2.level match { case 1 => DarkBlue; case 2 => DarkGreen; case 3 => Pink }
      p.a1.textAt(p.a2.name, 10, col) }

    def units1: GraphicElems = finds.optMap{ ls =>
      val xyz = ls.loc.toMetres3.fromLatLongFocus(focus)
      if (xyz.zPos){
        val pt = (xyz.xy/scale)
        val res = InfantryCounter.level(50, ls, ls.colour, ls.level).slate(pt)
        Some(res)
      }
      else None
    }

    def units2: GraphicElems = ifScale(8.km, units1)

    def seas: EllipseFill = earth2DEllipse(scale).fill(DarkBlue)

    mainRepaint(seas %: activeFills ++ sideLines ++ areaNames ++ locTexts ++ units2)
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) => {
      selected = cl.headOrNone
      statusText = selectedStr.newLinesToSpaces
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  canv.onScroll = b => { scale = ife(b, (scale / 1.2).max(scaleMin), (scale * 1.2).min(scaleMax)); repaint() }

  def addYearButt: PolygonCompound = timeButt("y+", butt => date.addYears(butt(1, 10, 100, 0)))
  def addMonthButt: PolygonCompound = timeButt("m+", butt => date.addMonths(butt(1, 3, 6, 0)))
  def addDayButt: PolygonCompound = timeButt("d+", butt => date.addDays(butt(1, 3, 10, 0)))
  def subDayButt: PolygonCompound = timeButt("d-", butt => date.subDays(butt(1, 3, 10, 0)))
  def subMonthButt: PolygonCompound = timeButt("m-", butt => date.subMonths(butt(1, 3, 6, 0)))
  def subYearButt: PolygonCompound = timeButt("y-", butt => date.subYears(butt(1, 10, 100, 0)))

  def timeButt(str: String, fNewTime: MouseButton => MTime): PolygonCompound = clickButton(str) { b =>
    date = fNewTime(b)
    repaint()
    thisTop()
  }

  def timeBox: PolygonCompound = textBox(14, date.str3)

  def thisTop(): Unit = reTop(RArr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast, subYearButt, subMonthButt, subDayButt, timeBox, addDayButt,
    addMonthButt, addYearButt))

  repaint()
  thisTop()
}