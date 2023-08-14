/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import geom._, prid._, phex._, pgui._

case class NormGui(canv: CanvasPlatform, scenIn: NormScen, viewIn: HGView) extends HGridSysGui("Normandy Gui") {
  var scen: NormScen = scenIn
  implicit val gridSys: HGridSys = scen.grid
  val terrs: HCenLayer[Tile] = scen.terrs
  val corners: HCornerLayer = scen.corners

  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  override def frame: GraphicElems =
  {
    def tileFills: RArr[PolygonFill] = proj.hCenMap { hc =>
      corners.tilePoly(hc).map { hvo => hvo.toPt2(proj.transCoord(_)) }.fill(terrs(hc).colour)
    }
    def tileFills2 = terrs.projPolyMap(proj, corners){(poly, t) => poly.fill(t.colour) }

    def lines1: GraphicElems = proj.linksOptMap { hs =>
      val hc1 = hs.tileLt
      val t1 = terrs(hc1)

      def t2 = terrs(hs.tileRt)

      if (/*sTerrs(hs) | */t1 != t2) None
      else
      { val cs: (HCen, Int, Int) = hs.cornerNums
        val ls1 = corners.sideLineHVAndOffset(cs._1, cs._2, cs._3)
        val ls2 = ls1.map(hva => hva.toPt2(proj.transCoord(_)))
        Some(ls2.draw(lineColour = t1.contrastBW))
      }
    }

    def lines2: GraphicElems = proj.ifTileScale(50, lines1)

    tileFills2 ++ lines2
  }

  def thisTop(): Unit = reTop(proj.buttons)

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  thisTop()
  mainRepaint(frame)
}