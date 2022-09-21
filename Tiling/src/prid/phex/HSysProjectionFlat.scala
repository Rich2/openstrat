/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._, collection.mutable.ArrayBuffer

final case class HSysProjectionFlat(gridSys: HGridSys, panel: Panel) extends HSysProjection with TSysProjectionFlat
{ type GridT = HGridSys
  var pixCScale: Double = gridSys.fullDisplayScale(panel.width, panel.height)
  override def pixRScale: Double = pixCScale * Sqrt3
  override def pixTileScale: Double = pixCScale * 4

  var focus: Vec2 = gridSys.defaultView(pixCScale).vec
  def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems = ife(pixCScale >= minScale, elems, Arr[GraphicElem]())

  override def setView(view: Any): Unit = view match
  {
    case hv: HGView =>
    { pixCScale = hv.cPScale
      focus = hv.vec
      gChild = getGChild
    }
    case d: Double => pixCScale = d
    case _ =>
  }

  var gChild: HGridSys = getGChild
  def setGChild: Unit = gChild = getGChild

  def getGChild : HGridSys = {
    def newBottom: Int = (focus.y / Sqrt3 + panel.bottom / pixRScale - 1).round.toInt.roundUpToEven
    val newTop: Int = (focus.y / Sqrt3 + panel.top / pixRScale + 1).round.toInt.roundDownToEven
    val newLeft: Int = (focus.x + panel.left / pixCScale - 2).round.toInt.roundUpToEven
    val newRight: Int = (focus.x + panel.right / pixCScale + 2).round.toInt.roundDownToEven

    gridSys match
    {
      case hg: HGridReg =>
      { val bt = hg.bottomCenR.max(newBottom)
        val tp = hg.topCenR.min(newTop)
        val lt = hg.leftCenC.max(newLeft)
        val rt = hg.rightCenC.min(newRight)
        deb(s"bt: $bt, tp: $tp, lt: $lt, rt: $rt")
        HGridReg(bt, tp, lt, rt)
      }
      case hi: HGridIrr => hi
      case hs => hs
    }
  }

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
}