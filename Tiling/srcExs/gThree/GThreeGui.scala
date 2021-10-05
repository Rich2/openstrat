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

  val lines: Arr[LineSegDraw] = terrs.sideFlatMap((hs, _) => Arr(hs.draw()), (hs, t1, t2 ) => ife(t1 == t2, Arr(hs.draw(t1.contrastBW)), Arr()))

  val rows = terrs.rowCombine
  val hexs = rows.map{ hv => hv.polygonReg.fill(hv.value.colour) }
  def units: HCenArrOpt[Lunit] = scen.units

  /** Uses the mapHCen method on units. This takes two functions, the first for when there is no unit in the hex tile. Note how we can access the
   *  data in the separate terrs array by use of the HCen coordinate.  */
  def unitOrTexts: GraphicElems = units.mapHCen{hc => hc.decText(14, terrs(hc).contrastBW) } { (hc, p) =>
    Rect(1.0, 0.66, hc.toPt2).fillDrawTextActive(p.colour, p, p.team.name + "\n" + hc.rcStr, 24, 2.0) }

  def moves: GraphicElems = units.flatMapHCenSomes{(hc, u) =>
    u.cmds
    Arr()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr())//bTurn))
  statusText = s"Game Three. Scenario has ${grid.numTiles} tiles."
  thisTop()

  def frame: GraphicElems = (hexs ++ lines ++ unitOrTexts: GraphicElems).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}