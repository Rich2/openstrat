/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._, pglobe._, pgui._, pEarth._, Colour._

case class HSysProjectionEarth(parent: EGridSys, panel: Panel) extends HSysProjection
{
  override type GridT = EGridSys
  var focus: LatLong = 0 ll 0
  var scale: Length = 4.km
  def gScale: Double = parent.cScale / scale

  //def gScale: Double = gridSys.cScale / scale

  def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems = ife(gScale >= minScale, elems, Arr[GraphicElem]())
  override def setView(view: Any): Unit = view match {
    case hv: HGView => {
      scale = parent.cScale / hv.cPScale
      focus = parent.hCoordLL(hv.hCoord)
    }
    //case d: Double => cPScale = d
    case _ =>
  }

  var gChild: HGridSys = parent

  def setGChid : HGridSys =  parent

  def zoomOut: PolygonCompound = clickButton("-") { _ =>
    scale *= 1.1
    panel.repaint(getFrame())
    //setStatusText(tilePScaleStr)
    //panel.repaint(frame)
    //statusText = tilePScaleStr
    //thisTop()
  }

  def zoomIn: PolygonCompound = clickButton("+") { _ =>
    scale /= 1.1
    panel.repaint(getFrame())
    //setStatusText(tilePScaleStr)
    //panel.repaint(frame)
    //statusText = tilePScaleStr
    //thisTop()
  }


  def goDirn(str: String)(f: Double => Unit): PolygonCompound = clickButton(str) { b =>
    val delta: Int = b.apply(1, 10, 60, 0)
    f(delta)
    panel.repaint(getFrame())//repaint()
    setStatusText(s"focus $focus")
    //thisTop()
  }

  def goNorth: PolygonCompound = goDirn("\u2191") { delta =>
    val newLat: Double = focus.latDegs + ife(true/* northUp */, delta, -delta)
    focus = ife(true/* northUp */, focus.addLat(delta.degsVec), focus.subLat(delta.degsVec))
    // northUp = ife(newLat > 90 | newLat < -90, !northUp, northUp)
  }

  def goSouth: PolygonCompound = goDirn("\u2193") { delta =>
    val newLat: Double = focus.latDegs + ife(true/* northUp */, -delta, delta)
    focus = ife(true/* northUp */, focus.subLat(delta.degsVec), focus.addLat(delta.degsVec))
    //northUp = ife(newLat > 90 | newLat < -90, !northUp, northUp)
  }

  def goEast: PolygonCompound = goDirn("\u2192") { delta => focus = ife(true/* northUp */, focus.addLongVec(delta.degsVec), focus.subLong(delta.degsVec)) }

  def goWest: PolygonCompound = goDirn("\u2190") { delta => focus = ife(true/* northUp */, focus.subLong(delta.degsVec), focus.addLongVec(delta.degsVec)) }
  override val buttons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast)//, focusLeft, focusRight, focusUp, focusDown)
//  val sides0 = sTerrs.truesMap(_.lineSegHC.map(gridSys.hCoordLL(_)))
//
//  def sides1: LineSegM3Arr = sides0.map {
//    _.map(_.toMetres3)
//  }
//
//  def sides2: LineSegM3Arr = sides1.map {
//    _.map(_.fromLatLongFocus(focus))
//  }
//
//  def sides3: LineSegM3Arr = sides2.filter(_.zsPos)
//
//  def sides4: LineSegArr = sides3.map {
//    _.map(_.xy / scale)
//  }

  //def sides: GraphicElems = sides4.map { ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Red) }
  override def tilePolygons: PolygonArr = ???

  override def tileActives: Arr[PolygonActive] = ???

  override def sideLines: LineSegArr = transLineSegM3Arr(parent.sideLineM3s)
  override def innerSideLines: LineSegArr = transLineSegM3Arr(parent.innerSideLineM3s)
  override def outerSideLines: LineSegArr = transLineSegM3Arr(parent.outerSideLineM3s)

  override def transHSides(inp: HSideArr): LineSegArr =
  { val lls: LineSegLLArr = inp.map(_.lineSegHC.map(parent.hCoordLL(_)))
    val m3s = lls.map(_.map(_.toMetres3))
    transLineSegM3Arr(m3s)
  }

  override def transLineSeg(seg: LineSegHC): LineSeg = ???

  override def transOptLineSeg(seg: LineSegHC): Option[LineSeg] = ???

  def transLineSegM3Arr(inp: LineSegM3Arr): LineSegArr =
  { val rotated = inp.fromLatLongFocus(focus)
    val visible = rotated.filter(_.zsPos)
    visible.map(_.xyLineSeg(scale))
  }

  override def transOptCoord(hc: HCoord): Option[Pt2] =
  { val m3 = parent.hCoordLL(hc).toMetres3
    val rotated = m3.fromLatLongFocus(focus)
    val opt = ife(rotated.zPos, Some(rotated.xy), None)
    opt.map(_ / scale)
  }

  override def transCoord(hc: HCoord): Pt2 = {
    val m3 = parent.hCoordLL(hc).toMetres3
    val rotated = m3.fromLatLongFocus(focus)
    rotated.xy / scale
  }

  override def transTile(hc: HCen): Option[Polygon] = {
    val p1 = hc.hVertPolygon.map(parent.hCoordLL(_)).toMetres3.fromLatLongFocus(focus)
    val opt = ife(p1.vert(0).zPos, Some(p1.map(_.xy)), None)
    opt.map(_.map(_ / scale))
  }

  override def hCoordOptStr(hc: HCoord): Option[String] = Some(parent.hCoordLL(hc).degStr)

  val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)
  def irr0: Arr[(EArea2, PolygonM)] = eas.map(_.withPolygonM(focus, true))// northUp))
  def irr1: Arr[(EArea2, PolygonM)] = irr0.filter(_._2.vertsMin3)

  def irrFills = irr1.map { pair =>
    val (d, p) = pair
    val col = d.terr match {
      case w: Water => BlueViolet
      case _ => Colour.LightPink
    }
    p.map(_ / scale).fill(col)
  }

  def irrLines: Arr[PolygonDraw] = irr1.map { a => a._2.map(_ / scale).draw(White) }

  def irrLines2: GraphicElems = ifGScale(2, irrLines)

  def irrNames: Arr[TextGraphic] = irr1.map { pair =>
    val (d, _) = pair
    val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
    TextGraphic(d.name, 12, posn, d.colour.contrastBW)
  }

  def irrNames2: GraphicElems = ifGScale(4, irrNames)

  //val buttons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut, focusLeft, focusRight, focusUp, focusDown)
}