/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import geom._, pgui._, Colour._, prid._, psq._

case class DraughtsGui(canv: CanvasPlatform, scen: DraughtsScen) extends CmdBarGui("Draughts")
{
  implicit def grid: SqGrid = scen.grid
  statusText = "Welcome to Draughts Gui"
  val darkSquareColour = Brown
  val lightSquareColour = Pink

  /** The number of pixels / 2 displayed per row height. */
  var cPScale: Double = grid.fullDisplayScale(mainWidth, mainHeight)

  val tiles: GraphicElems =grid.map{sc => sc.polygonReg.fillActive(sc.checkeredColour(darkSquareColour, lightSquareColour), sc) }

  val pieces: Arr[CircleFill] = scen.draughts.scSomesMap((r, d) => Circle(1.2, r.toPt2).fill(d.colour))
  def bTurn: PolygonCompound = simpleButton("Turn "){
    repaint()
    thisTop()
  }

  def thisTop(): Unit = reTop(Arr(bTurn))
  thisTop()

  def frame: GraphicElems = (tiles ++ pieces).slate(-grid.cenVec).scale(cPScale)
  def repaint(): Unit = mainRepaint(frame)
  repaint()
}