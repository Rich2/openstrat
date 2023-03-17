/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import geom._, prid._, phex._, pgui._

/** Gui for civilisation  game. */
case class CivGui(canv: CanvasPlatform, scen: CivScen) extends HGridSysGui("Civ Rise Game Gui")
{ statusText = "Welcome to Civ Rise."
  implicit val gridSys: HGridSys = scen.gridSys
  val terrs: HCenLayer[VTile] = scen.terrs
  val corners: HCornerLayer = scen.corners
  val lunits: HCenArrLayer[Warrior] = scen.lunits

  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  val proj = gridSys.projection(mainPanel)
  //def view: HGView()
  //proj.setView(viewIn)
  def frame: GraphicElems =
  {
    val sls = proj.sidesDraw()

    val tiles = gridSys.map { hc => hc.polygonReg.fillTextActive(terrs(hc).colour, hc, hc.strComma, 16) }

    def tileFills2: GraphicElems = terrs.projPolyMap(proj, corners) { (poly, t) => poly.fill(t.colour) }

    val unitFills = lunits.gridHeadsMap { (hc, lu) =>
      Rectangle.curvedCornersCentred(1.2, 0.8, 0.3, hc.toPt2Reg).parentAll(lu, lu.colour, 2, lu.colour.contrast, 16, 4.toString)
    }

    tileFills2 ++ (unitFills).slate(-focus).scale(cPScale) +% sls
  }

  def thisTop(): Unit = reTop(proj.buttons)

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }

  thisTop()

  repaint()
}