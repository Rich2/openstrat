/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import pCanv._, prid._, geom._

case class GThreeGui(canv: CanvasPlatform, scenStart: ThreeScen) extends CmdBarGui("Game Three Gui")
{ statusText = "Welcome to Game Three."
  val scen = scenStart
  def terrs: HCenArr[Terr] = scen.terrs
  var history: Arr[ThreeScen] = Arr(scen)
  implicit def grid: HGrid = scen.grid
  /** The number of pixels / 2 displayed per row height. */
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val lines = grid.sidesDraw()
  def text = grid.map{ hc => hc.rcText() }

  val rows = terrs.rowCombine.map{ hv => hv.polygonReg.fill(hv.value.colour) }

  def frame: GraphicElems = (rows +- lines ++ text).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}