/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

abstract class EGridBaseGui(title: String)  extends HGridSysGui(title)
{ implicit val gridSys: HGridSys
  def terrs: HCenLayer[WTile]
  def sTerrs: HSideLayer[WSide]
  def corners: HCornerLayer
  implicit def proj: HSysProjection

  /*def tileBackFills: GraphicElems = terrs.hcOptMap { (tile, hc) =>
    tile match
    { case li: Coastal =>
      { val res = hc.hVertPolygon.toPolygon(proj.transCoord).fill(li.sideTerrs.colour)
        Some(res)
      }
      case _ => None
    }
  }*/

  def tilePolys: HCenPairArr[Polygon] = proj.hCenPolygons(corners)
  def tileFrontFills: RArr[PolygonFill] = tilePolys.pairMap{ (hc, poly) => poly.fill(terrs(hc)(gridSys).colour) }
  def tileActives: RArr[PolygonActive] = tilePolys.pairMap{ (hc, poly) => poly.active(hc) }
  def sideFills: GraphicElems = sTerrs.midsPolyMap(proj, corners){ (poly, st) => poly.fill(st.colour) }
  def sideActives: GraphicElems = sTerrs.somesPolyMap(proj, corners){ (hs, poly) => poly.active(hs) }

  def lines1: GraphicElems = proj.linksOptMap { hs =>
    def t1: WTile = terrs(hs.tileLt)

    def t2: WTile = terrs(hs.tileRt)

    sTerrs(hs) match {
      case WSideNone if t1.colour == t2.colour => {
        val cs: (HCen, Int, Int) = hs.corners
        val ls1: LineSeg = corners.sideLine(cs._1, cs._2, cs._3)
        Some(ls1.draw(t1.contrastBW))
      }
      //case vs: WSideMid if vs.terr.colour == t2.colour => Some(hs.lineSegHC.lineSeg.draw(t2.contrastBW))
     // case vs: WSideMid if vs.terr.colour == t1.colour => Some(hs.lineSegHC.lineSeg.draw(t1.contrastBW))
      case _ => None
    }
  }

  def lines2: GraphicElems = proj.ifTileScale(50, lines1)

  /*def lines3: GraphicElems = terrs.projHCenFlatMap { (hc, tile) =>
    tile match {
      /*case cst: Coastal => cst.indentedVertexIndexMap { i =>
        val p1: HVAndOffset = corners.cornerV1(hc, i)
        val p2 = hc.vExact(i)
        LineSegHVAndOffset(p1, p2).map(proj.transHVAndOffset).draw(cst.sideTerrs.contrastBW)
      }*/
      case _ => RArr()
    }
  }

  def lines4: GraphicElems = proj.ifTileScale(150, lines3)*/
}
