/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._, pglobe._, pgui._, pEarth._, Colour._

case class HSysProjectionEarth(parent: EGridSys, panel: Panel) extends HSysProjection
{
  override type SysT = EGridSys
  var focus: LatLong = 0 ll 0
  var northUp: Boolean = true
  def metresPerPixel: Length = parent.cScale / pixelsPerC

  def setMetresPerPixel(value: Length): Unit = pixelsPerC = parent.cScale / value

  override def pixelsPerTile: Double = pixelsPerC * 4
  override def ifTileScale(minScale: Double, elems: => GraphicElems): GraphicElems = ife(pixelsPerTile >= minScale, elems, RArr[GraphicElem]())

  override def setView(view: Any): Unit = view match
  {
    case hv: HGView => {
      pixelsPerC = hv.pixelsPerC
      focus = parent.hCoordLL(hv.hCoord)
    }
    //case d: Double => cPScale = d
    case _ =>
  }

  var gChild: HGridSys = parent

  def setGChild: Unit = { gChild = parent }

  def goDirn(str: String)(f: Double => Unit): PolygonCompound = clickButton(str) { b =>
    val delta: Int = b.apply(1, 10, 60, 0)
    f(delta)
    panel.repaint(getFrame())
    setStatusText(s"focus ${focus.str}")
  }

  def goNorth: PolygonCompound = goDirn("\u2191") { delta =>
    val newLat: Double = focus.latDegs + ife(northUp, delta, -delta)
    focus = ife(northUp, focus.addLat(delta.degsVec), focus.subLat(delta.degsVec))
    // northUp = ife(newLat > 90 | newLat < -90, !northUp, northUp)
  }

  def goSouth: PolygonCompound = goDirn("\u2193") { delta =>
    val newLat: Double = focus.latDegs + ife(northUp, -delta, delta)
    focus = ife(northUp, focus.subLat(delta.degsVec), focus.addLat(delta.degsVec))
    //northUp = ife(newLat > 90 | newLat < -90, !northUp, northUp)
  }

  def goEast: PolygonCompound = goDirn("\u2192") { delta => focus = ife(true/* northUp */, focus.addLongVec(delta.degsVec), focus.subLong(delta.degsVec)) }

  def goWest: PolygonCompound = goDirn("\u2190") { delta => focus = ife(true/* northUp */, focus.subLong(delta.degsVec), focus.addLongVec(delta.degsVec)) }
  override val buttons: RArr[PolygonCompound] = RArr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast)//, focusLeft, focusRight, focusUp, focusDown)

  override def tilePolygons: PolygonArr = ???

  override def hCenPtMap(f: (HCen, Pt2) => GraphicElem): GraphicElems = gChild.map{ hc => f(hc, transCoord(hc)) }
  override def hCenSizedMap(hexScale: Double = 20)(f: (HCen, Pt2) => GraphicElem): GraphicElems = ifTileScale(hexScale, hCenPtMap(f))

  override def tileActives: RArr[PolygonActive] = gChild.map(p => p.hVertPolygon.map(transCoord(_)).active(p))

  override def sideLines: LineSegArr = transLineSegM3Arr(parent.sideLineM3s)
  override def innerSideLines: LineSegArr = transLineSegM3Arr(parent.innerSideLineM3s)
  override def outerSideLines: LineSegArr = transLineSegM3Arr(parent.outerSideLineM3s)

  override def transHSides(inp: HSideArr): LineSegArr =
  { val lls: LineSegLLArr = inp.map(_.lineSegHC.map(parent.hCoordLL(_)))
    val m3s = lls.map(_.map(_.toMetres3))
    transLineSegM3Arr(m3s)
  }

  /** Transforms the line segment from [[HCoord]] space to [[Pt2]] space. */
  override def transLineSeg(seg: LineSegHC): LineSeg = seg.map(transCoord(_))

  /** Optionally Transforms the line segment from [[HCoord]] space to [[Pt2]] space. */
  override def transOptLineSeg(seg: LineSegHC): Option[LineSeg] = seg.mapOpt(transOptCoord(_))

  def transLineSegM3Arr(inp: LineSegM3Arr): LineSegArr =
  { val rotated = inp.fromLatLongFocus(focus)
    val visible = rotated.filter(_.zsPos)
    visible.map(_.xyLineSeg(metresPerPixel))
  }

  override def transOptCoord(hc: HCoord): Option[Pt2] =
  { val m3 = parent.hCoordLL(hc).toMetres3
    val rotated = m3.fromLatLongFocus(focus)
    val opt = ife(rotated.zPos, Some(rotated.xy), None)
    opt.map(_ / metresPerPixel)
  }

  override def transCoord(hc: HCoord): Pt2 =
  { val m3 = parent.hCoordLL(hc).toMetres3
    val rotated = m3.fromLatLongFocus(focus)
    rotated.xy / metresPerPixel
  }

  override def transTile(hc: HCen): Option[Polygon] =
  { val p1 = hc.hVertPolygon.map(parent.hCoordLL(_)).toMetres3.fromLatLongFocus(focus)
    val opt = ife(p1.vert(0).zPos, Some(p1.map(_.xy)), None)
    opt.map(_.map(_ / metresPerPixel))
  }

  override def hCoordOptStr(hc: HCoord): Option[String] = Some(parent.hCoordLL(hc).degStr)

  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)
  def irr0: RArr[(EArea2, PolygonM2)] = eas.map(_.withPolygonM2(focus, true))// northUp))
  def irr1: RArr[(EArea2, PolygonM2)] = irr0.filter(_._2.vertsMin3)

  def irrFills: RArr[PolygonFill] = irr1.map { pair =>
    val (ea, p) = pair
    val col = ea.terr match {
      case w: Water => BlueViolet
      case _ if ea == pOceans.Artic => White// LightSkyBlue.average(White).average(White)
      case _ => LightPink
    }
    p.map(_ / metresPerPixel).fill(col)
  }

  def irrLines: RArr[PolygonDraw] = irr1.map { a => a._2.map(_ / metresPerPixel).draw(lineColour = Violet) }

  def irrLines2: GraphicElems = ifTileScale(8, irrLines)

  def irrNames: RArr[TextGraphic] = irr1.map { pair =>
    val (d, _) = pair
    val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / metresPerPixel
    TextGraphic(d.name, 12, posn, d.colour.contrastBW)
  }

  def irrNames2: GraphicElems = ifTileScale(16, irrNames)
}