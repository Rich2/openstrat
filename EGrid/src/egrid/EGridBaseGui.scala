/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._, egrid._

abstract class EGridBaseGui(title: String)  extends HGridSysGui(title)
{ implicit val gridSys: HGridSys
  def terrs: HCenLayer[WTile]
  def sTerrs: HSideLayer[WSide]
  def corners: HCornerLayer
  implicit val proj: HSysProjection

  def tileBackFills: GraphicElems = terrs.hcOptMap { (tile, hc) =>
    tile match
    { case li: Coastal =>
      { val res = hc.hVertPolygon.toPolygon(proj.transCoord).fill(li.sideTerrs.colour)
        Some(res)
      }
      case _ => None
    }
  }
}
