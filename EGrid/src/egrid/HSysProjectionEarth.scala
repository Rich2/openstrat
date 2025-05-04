/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._, pglobe._, pgui._, pEarth._, Colour._

/** Creates a 2D projection from hex grids projected on on to the Earth */
case class HSysProjectionEarth(parent: EGridSys, panel: Panel) extends HSysProjection
{
  override type SysT = EGridSys
  var focus: LatLongDirn = LatLongDirn.degs(0, 0)
  var irrOn: Boolean = false
  
  /** Is the map orientation north up? */
  def northUp: Boolean = focus.dirn.northUp

  /** Is the map orientation south up? */
  def southUp: Boolean = focus.dirn.southUp
  
  def metresPerPixel: LengthMetric = parent.cScale / pixelsPerC

  def setMetresPerPixel(value: Metres): Unit = pixelsPerC = parent.cScale / value

  override def pixelsPerTile: Double = pixelsPerC * 4
  override def ifTileScale(minScale: Double, elems: => GraphicElems): GraphicElems = ife(pixelsPerTile >= minScale, elems, RArr[Graphic2Elem]())

  override def setView(view: Any): Unit = view match
  {
    case hv: HGView =>
    { pixelsPerC = hv.pixelsPerC
      focus = parent.hCoordLLDirn(hv.hCoord, hv.nthSth)
    }
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
  }

  def goSouth: PolygonCompound = goDirn("\u2193") { delta =>
    val newLat: Double = focus.latDegs + ife(northUp, -delta, delta)
    focus = ife(northUp, focus.subLat(delta.degsVec), focus.addLat(delta.degsVec))
  }

  def goEast: PolygonCompound = goDirn("\u2192") { delta => focus = ife(northUp, focus.addLongVec(delta.degsVec), focus.subLong(delta.degsVec)) }

  def goWest: PolygonCompound = goDirn("\u2190") { delta => focus = ife(northUp, focus.subLong(delta.degsVec), focus.addLongVec(delta.degsVec)) }

  def showIrr: PolygonCompound = clickButton("L") { b =>
    irrOn = !irrOn
    panel.repaint(getFrame())
    setStatusText(ife(irrOn, "Irregular borders on", "Irregular borders off"))
  }

  override val buttons: RArr[PolygonCompound] = RArr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast, showIrr)

  override def tilePolygons: PolygonGenArr =
  { val r1: PolygonHCArr = gChild.map(_.hVertPolygon)
    r1.optMap{phc => phc.optMap(transOptCoord(_)) }
  }

  override def hCenPtMap(f: (HCen, Pt2) => Graphic2Elem): GraphicElems = gChild.map{ hc => f(hc, transCoord(hc)) }
  override def hCenSizedMap(hexScale: Double = 20)(f: (HCen, Pt2) => Graphic2Elem): GraphicElems = ifTileScale(hexScale, hCenPtMap(f))

  override def tileActives: RArr[PolygonActive] = gChild.map(p => p.hVertPolygon.map(transCoord(_)).active(p))

  override def sideLines: LSeg2Arr = transLineSegM3Arr(parent.sideLineM3s)
  override def innerSideLines: LSeg2Arr = transLineSegM3Arr(parent.innerSideLineM3s)
  override def outerSideLines: LSeg2Arr = transLineSegM3Arr(parent.outerSideLineM3s)

  override def transHSeps(inp: HSepArr): LSeg2Arr =
  { val lls: LineSegLLArr = inp.map(_.lineSegHC.map(parent.hCoordLL(_)))
    val m3s = lls.map(_.map(_.toMetres3))
    transLineSegM3Arr(m3s)
  }

  /** Transforms the line segment from [[HCoord]] space to [[Pt2]] space. */
  override def transLineSeg(seg: LSegHC): LSeg2 = seg.map(transCoord(_))

  /** Optionally Transforms the line segment from [[HCoord]] space to [[Pt2]] space. */
  override def transOptLineSeg(seg: LSegHC): Option[LSeg2] = seg.mapOpt(transOptCoord(_).rotate180If(southUp))

  def transLineSegM3Arr(inp: LineSegM3Arr): LSeg2Arr =
  { val rotated = inp.fromLatLongFocus(focus)
    val visible = rotated.filter(_.zsPos)
    visible.map(_.xyLineSeg(metresPerPixel))
  }

  override def transOptCoord(hc: HCoord): Option[Pt2] =
  { val m3 = parent.hCoordLL(hc).toMetres3
    val rotated = m3.fromLatLongFocus(focus)
    val opt = ife(rotated.zNonNeg, Some(rotated.xy.rotate180If(southUp)), None)
    opt.map(_.mapGeom2(metresPerPixel))
  }

  override def transCoord(hc: HCoord): Pt2 =
  { val m3 = parent.hCoordLL(hc).toMetres3
    val rotated = m3.fromLatLongFocus(focus)
    val r1: PtM2 = rotated.xy.rotate180If(southUp)
    r1.mapGeom2(metresPerPixel)
  }

  override def transOptHVOffset(hvo: HvOffset): Option[Pt2] =
  { val m3 = hvo.toPtM3(hCoord => parent.hCoordLL(hCoord).toMetres3)(parent)
    val rotated = m3.fromLatLongFocus(focus)
    val opt = ife(rotated.zNonNeg, Some(rotated.xy.rotate180If(southUp)), None)
    opt.map(_.mapGeom2(metresPerPixel))
  }

  override def transTile(hc: HCen): Option[Polygon] =
  { val p1 = hc.hVertPolygon.map(parent.hCoordLL(_)).toMetres3.fromLatLongFocus(focus)
    val opt: Option[PolygonM2Gen] = ife(p1.vert(0).zNonNeg, Some(p1.map(_.xy)), None)
    opt.map{poly => poly.map(_.rotate180If(southUp).mapGeom2(metresPerPixel)) }
  }

  override def hCoordOptStr(hc: HCoord): Option[String] = Some(parent.hCoordLL(hc).degStr)

  val eas: RArr[EarthPoly] = earthAllRegions.flatMap(_.ePolys)
  def irr0: RArr[(EarthPoly, PolygonM2Gen)] = eas.map(_.withPolygonM2(focus))
  def irr1: RArr[(EarthPoly, PolygonM2Gen)] = irr0.filter(_._2.vertsMin3)

  def irrFills: RArr[PolygonFill] = irr1.map { pair =>
    val (ea, p) = pair
    val col = ea.terr match
    { case wh if wh.colour == White => White
      case SeaIceWinter => SeaIceWinter.colour
      case w: Water => BlueViolet

      case _ => LightPink
    }
    p.map(_.mapGeom2(metresPerPixel)).fill(col)
  }

  def irrLines: RArr[PolygonDraw] = irr1.map { a => a._2.map(_.mapGeom2(metresPerPixel)).draw(lineColour = Violet) }

  def irrLines2: GraphicElems = ifTileScale(8, ife(irrOn, irrLines, RArr()))

  def irrActives = irr1.map { a => a._2.map(_.mapGeom2(metresPerPixel)).active(a._1) }

  def irrActives2: GraphicElems = ifTileScale(8, ife(irrOn, irrActives, RArr()))

  def irrNames: RArr[TextFixed] = irr1.map { pair =>
    val (d, _) = pair
    val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy.rotate180If(southUp).mapGeom2(metresPerPixel)
    TextFixed(d.name, 12, posn, d.colour.contrastBW)
  }

  def irrNames2: GraphicElems = ifTileScale(16, irrNames)
}