/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

abstract class EGridBaseGui(title: String)  extends HGridSysGui(title)
{ implicit val gridSys: HGridSys
  def terrs: LayerHcRefSys[WTile]
  def sTerrs: LayerHSOptSys[WSide, WSideSome]
  def corners: HCornerLayer
  implicit def proj: HSysProjection
  def tilePolys: HCenPairArr[Polygon] = proj.hCenPolygons(corners)
  def tileFills: RArr[PolygonFill] = tilePolys.pairMap{ (hc, poly) => poly.fill(terrs(hc)(gridSys).colour) }
  def tileActives: RArr[PolygonActive] = tilePolys.pairMap{ (hc, poly) => poly.active(hc) }

  def sidePolys: HSidePairArr[Polygon] = proj.hSidePolygons(sTerrs(_).nonEmpty, corners)
  def sideFills: GraphicElems = sidePolys.pairMap { (hSide, poly) => poly.fill(sTerrs(hSide).colour) }

  def sideActives: GraphicElems = sidePolys.pairMap{ (hSide, poly) => poly.active(hSide) }

  def lines1: GraphicElems = proj.linksOptMap { hs =>
    def t1: WTile = terrs(hs.tileLt)

    def t2: WTile = terrs(hs.tileRt)

    sTerrs(hs) match
    {
      case WSideNone if t1.colour == t2.colour =>
      { val ls1: Option[LineSeg] = hs.projCornersSideLine(proj, corners)
        ls1.map(_.draw(lineColour = t1.contrastBW))
      }
      case _: WSideSome if t1.isWater => hs.leftCorners(corners).mapOpt(proj.transOptHVOffset).map(_.draw(lineColour = t1.contrastBW))
      case _: WSideSome if t2.isWater => hs.rightCorners(corners).mapOpt(proj.transOptHVOffset).map(_.draw(lineColour = t2.contrastBW))
      case _ => None
    }
  }

  def lines2: GraphicElems = proj.ifTileScale(50, lines1)

  def hexStrs(f1: HCen => Boolean): RArr[TextFixed] = proj.hCensIfPtFlatMap(f1) { (hc, pt) =>
    val strs: StrArr = StrArr(hc.rcStr32) +% hc.strComma
    TextFixed.lines(strs, 12, pt, terrs(hc).contrastBW)
  }

  def hexStrs2(f1: HCen => Boolean): GraphicElems = proj.ifTileScale(72, hexStrs(f1))
}