/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import pCanv._, prid._, geom._

case class GThreeGui(canv: CanvasPlatform, scenStart: ThreeScen) extends CmdBarGui("Game Three Gui")
{ override var statusText: String = "Welcome to Game Three."
  val scen = scenStart
  var history: Arr[ThreeScen] = Arr(scen)
  implicit def grid: HGrid = scen.grid
  /** The number of pixels / 2 displayed per row height. */
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  def hexStrs: Arr[TextGraphic] = grid.rcTexts

  def frame: GraphicElems = (grid.sidesDraw() +: hexStrs).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
