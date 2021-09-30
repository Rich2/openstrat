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

  def text = terrs.mapHC((t, hc) => hc.decText(14, t.contrastBW))

  val rows = terrs.rowCombine
  val areas = rows.map{ hv => hv.polygonReg.fill(hv.value.colour) }
  def players: HCenArrOpt[Player] = scen.oPlayers
  def lunits: Arr[PolygonCompound] = players.mapHCenSomes { (hc, p) =>
    Rect(0.9, 0.6, hc.toPt2).fillDrawTextActive(p.colour, HPlayer(p, hc), p.toString + "\n" + hc.strComma, 24, 2.0)
  }
  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr())//bTurn))
  statusText = s"Game Three. Scenario has ${grid.numCens} tiles."
  thisTop()

  def frame: GraphicElems = (areas ++ lines ++ text ++ lunits).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}