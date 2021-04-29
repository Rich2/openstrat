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

  val lines = terrs.sideMap( (hs, _) => hs.coordLine.toLine2.draw(),
    (hs, t1, t2 ) => ife(t1 == t2, hs.coordLine.toLine2.draw(t1.contrastBW), GraphicNone))

  def text = terrs.mapHC((t, hc) => hc.decText(14, t.contrastBW))

  val rows = terrs.rowCombine
  val areas = rows.map{ hv => hv.polygonReg.fill(hv.value.colour) }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr())//bTurn))
  statusText = s"Game Three. Scenario has ${grid.numOfTiles} tiles."
  thisTop()

  def frame: GraphicElems = (areas ++ lines ++ text).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}