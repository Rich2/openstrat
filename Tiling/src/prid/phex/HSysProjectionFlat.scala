/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._, collection.mutable.ArrayBuffer

final case class HSysProjectionFlat(gridSys: HGridSys, panel: Panel) extends HSysProjection with TSysProjectionFlat
{ type GridT = HGridSys
  var pixCScale: Double = gridSys.fullDisplayScale(panel.width, panel.height)
  def pixTileScale: Double = pixCScale * 4

  var focus: Vec2 = gridSys.defaultView(pixCScale).vec
  def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems = ife(pixCScale >= minScale, elems, Arr[GraphicElem]())

  override def setView(view: Any): Unit = view match
  {
    case hv: HGView =>
    { pixCScale = hv.cPScale
      focus = hv.vec
      gChild = setGChid
    }
    case d: Double => pixCScale = d
    case _ =>
  }

  var gChild: HGridSys = setGChid

  def setGChid : HGridSys =  gridSys match
  { case hg: HGridReg =>
    {
      val lt1 = (focus.x + panel.left / pixCScale).toInt - 2
      val lt = hg.leftCenC.max(lt1)
      val rt1 = (focus.x + panel.right / pixCScale).toInt + 2
      val rt = hg.rightCenC.min(rt1)
      val bt1 = (focus.y + panel.bottom / pixCScale).toInt - 2
      val bt = hg.bottomCenR.max(bt1)
      val tp1 = (focus.y + panel.top / pixCScale).toInt + 2
      val tp = hg.topCenR.min(tp1)
      deb(s"bt: $bt, tp: $tp, lt: $lt, rt: $rt")
      HGridReg(bt, tp, lt, rt)
    }

    case hs => hs
  }
  //def window = panel

  override def tiles: PolygonArr = gChild.map(_.hVertPolygon.map(gridSys.hCoordToPt2(_)).slate(-focus).scale(pixCScale))

  override def tileActives: Arr[PolygonActive] =
    gChild.map(hc => hc.hVertPolygon.map(gridSys.hCoordToPt2(_)).slate(-focus).scale(pixCScale).active(hc))

  override def hCenMap(f: (Pt2, HCen) => GraphicElem): GraphicElems = {
    val buff = new ArrayBuffer[GraphicElem]
    gChild.foreach{hc => transOptCoord(hc).foreach(pt => buff.append(f(pt, hc))) }
    new Arr[GraphicElem](buff.toArray)
  }

  override def sides: LineSegArr = gChild.sideLineSegHCs.map(_.map(gridSys.hCoordToPt2(_))).slate(-focus).scale(pixCScale)
  override def innerSides: LineSegArr = gChild.innerSideLineSegHCs.map(_.map(gridSys.hCoordToPt2(_))).slate(-focus).scale(pixCScale)
  override def outerSides: LineSegArr = gChild.outerSideLineSegHCs.map(_.map(gridSys.hCoordToPt2(_))).slate(-focus).scale(pixCScale)

  override def transOptCoord(hc: HCoord): Option[Pt2] = Some(gridSys.hCoordToPt2(hc).slate(-focus).scale(pixCScale))
  override def transCoord(hc: HCoord): Pt2 = gridSys.hCoordToPt2(hc).slate(-focus).scale(pixCScale)

  override def transTile(hc: HCen): Option[Polygon] = Some(hc.polygonReg)

  override def transOptLineSeg(seg: LineSegHC): Option[LineSeg] =
    transOptCoord(seg.startPt).map2(transOptCoord(seg.endPt)){ (p1, p2) => LineSeg(p1, p2) }

  override def transLineSeg(seg: LineSegHC): LineSeg = seg.map(transCoord)

  override def transHSides(inp: HSideArr): LineSegArr = ???//.slate(-focus).scale(cPScale)

  def zoomIn: PolygonCompound = clickButton("+") { _ =>
    pixCScale *= 1.1
    setGChid
    panel.repaint(getFrame())
    setStatusText(pixTileScaleStr)
  }

  def zoomOut: PolygonCompound = clickButton("-") { _ =>
    pixCScale /= 1.1
    setGChid
    panel.repaint(getFrame())
    setStatusText(pixTileScaleStr)
  }

  def focusAdj(uniStr: String)(f: (Vec2, Double) => Vec2): PolygonCompound = clickButton(uniStr) { butt =>
    val delta = butt(1, 10, 100, 0)
    focus = f(focus, pixCScale * delta / 40)
    setGChid
    panel.repaint(getFrame())
    setStatusText(focus.strSemi(2, 2))
  }

  def focusLeft: PolygonCompound = focusAdj("\u2190") { (v, d) =>
    val newX: Double = (v.x - d).max(gridSys.left)
    Vec2(newX, v.y)
  }

  def focusRight: PolygonCompound = focusAdj("\u2192") { (v, d) =>
    val newX: Double = (v.x + d).min(gridSys.right)
    Vec2(newX, v.y)
  }

  def focusUp: PolygonCompound = focusAdj("\u2191") { (v, d) =>
    val newY: Double = (v.y + d).min(gridSys.top)
    Vec2(v.x, newY)
  }

  def focusDown: PolygonCompound = focusAdj("\u2193") { (v, d) =>
    val newY: Double = (v.y - d).max(gridSys.bottom)
    Vec2(v.x, newY)
  }

  val buttons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut, focusLeft, focusRight, focusUp, focusDown)
}