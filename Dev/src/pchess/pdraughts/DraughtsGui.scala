/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import geom._, pgui._, Colour._, prid._, psq._

case class DraughtsGui(canv: CanvasPlatform, scen: DraughtsScen) extends CmdBarGui("Draughts")
{
  implicit def grid: SqGrid = scen.grid
  statusText = "Welcome to Draughts Gui"
  val darkSquareColour = Brown
  val lightSquareColour = Pink
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  /** The number of pixels / 2 displayed per row height. */
  var cPScale: Double = grid.fullDisplayScale(mainWidth, mainHeight)

  val tiles: GraphicElems =grid.map{sc => sc.polygonReg.fillActive(sc.checkeredColour(darkSquareColour, lightSquareColour), sc) }
//    grid.mapRPolygons{ (r, p) =>
//    val col = ife(r.yPlusC %% 4 == 0, darkSquareColour, lightSquareColour)
//    p.fill(col) }

  //val pieces = scen.draughts.mapSomeWithRoords((r, d) => Circle(0.7, r.gridPt2).fill(d.colour))
  def bTurn = simpleButton("Turn "){
    repaint()
    thisTop()
  }

  def thisTop(): Unit = reTop(Arr(bTurn))
  thisTop()

  def frame: GraphicElems = (tiles/* ++ pieces*/).slate(-grid.cenVec).scale(cPScale)//.gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}