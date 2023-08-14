/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

abstract class EGridBaseGui(title: String)  extends HGridSysGui(title)
{ implicit val gridSys: HGridSys
  def terrs: HCenLayer[WTile]
  def sTerrs: HSideOptLayer[WSide, WSideSome]
  def corners: HCornerLayer
  implicit def proj: HSysProjection

  def tilePolys: HCenPairArr[Polygon] = proj.hCenPolygons(corners)
  def tileFills: RArr[PolygonFill] = tilePolys.pairMap{ (hc, poly) => poly.fill(terrs(hc)(gridSys).colour) }
  def tileActives: RArr[PolygonActive] = tilePolys.pairMap{ (hc, poly) => poly.active(hc) }

  //def sidePolys: GraphicElems = proj.hSidePolygons(corners)
  def sideFills: GraphicElems = sTerrs.somePolyMap(proj, corners){ (st, poly) => poly.fill(st.colour) }

  def sideActives: GraphicElems = sTerrs.someOnlyHSPolyMap(proj, corners){ (hs, poly) => poly.active(hs) }

  def lines1: GraphicElems = proj.linksOptMap { hs =>
    def t1: WTile = terrs(hs.tileLt)

    def t2: WTile = terrs(hs.tileRt)

    sTerrs(hs) match
    { case WSideNone if t1.colour == t2.colour => {
        val cs: (HCen, Int, Int) = hs.corners
        val ls1: LineSeg = corners.sideLine(cs._1, cs._2, cs._3)
        Some(ls1.draw(lineColour = t1.contrastBW))
      }
      case _: WSideSome if t1.isWater => Some(hs.leftCorners(corners).map(proj.transHVOffset).draw(lineColour = t1.contrastBW))
      case _: WSideSome if t2.isWater => Some(hs.rightCorners(corners).map(proj.transHVOffset).draw(lineColour = t2.contrastBW))
      case _ => None
    }
  }

  def lines2: GraphicElems = proj.ifTileScale(50, lines1)

  def hexStrs(f1: HCen => Boolean): RArr[TextGraphic] = proj.hCensIfPtFlatMap(f1) { (hc, pt) =>
    val strs: StrArr = StrArr(hc.rcStr32) +% hc.strComma
    TextGraphic.lines(strs, 12, pt, terrs(hc).contrastBW)
  }

  def hexStrs2(f1: HCen => Boolean): GraphicElems = proj.ifTileScale(72, hexStrs(f1))
}