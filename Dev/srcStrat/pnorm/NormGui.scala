/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import geom._, prid._, phex._, pgui._

case class NormGui(canv: CanvasPlatform, scenIn: NormScen, viewIn: HGView) extends HGridSysGui("Normandy Gui") {
  var scen: NormScen = scenIn
  implicit val gridSys: HGridSys = scenIn.grid
  val terrs: HCenLayer[Terr] = scen.terrs
  val corners = scen.corners

  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  override def frame: GraphicElems =
  {
    def tileFills: RArr[PolygonFill] = proj.hCensMap { hc =>
      corners.tilePoly(hc).map { hvo => hvo.toPt2(proj.transCoord(_)) }.fill(terrs(hc).colour)
    }

    tileFills
  }

  def thisTop(): Unit = reTop(proj.buttons)
}
