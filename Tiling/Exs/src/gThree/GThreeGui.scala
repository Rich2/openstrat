/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import pgui._, prid._, phex._, geom._

case class GThreeGui(canv: CanvasPlatform, scenStart: ThreeScen) extends HexMapGui("Game Three Gui")
{ statusText = "Welcome to Game Three."
  val scen = scenStart
  def terrs: HCenArr[Terr] = scen.terrs
  var history: Arr[ThreeScen] = Arr(scen)
  implicit def grid: HGrid = scen.grid
  focus = grid.cenVec

  /** The number of pixels / 2 displayed per row height. */
  var cPScale = grid.fullDisplayScale(mainWidth, mainHeight)

  val lines: Arr[LineSegDraw] = terrs.sideFlatMap((hs, _) => Arr(hs.draw()), (hs, t1, t2 ) => ife(t1 == t2, Arr(hs.draw(t1.contrastBW)), Arr()))

  val rows = terrs.rowCombine
  val hexs = rows.map{ hv => hv.polygonReg.fillActive(hv.value.colour, hv) }
  def units: HCenArrOpt[Lunit] = scen.units

  /** Uses the mapHCen method on units. This takes two functions, the first for when there is no unit in the hex tile. Note how we can access the
   *  data in the separate terrs array by use of the HCen coordinate.  */
  def unitOrTexts: GraphicElems = units.mapHCen{hc => hc.decText(14, terrs(hc).contrastBW) } { (hc, p) =>
    Rect(1.0, 0.66, hc.toPt2).fillDrawTextActive(p.colour, p, p.team.name + "\n" + hc.rcStr, 24, 2.0) }

  def moves: GraphicElems = units.hcSomesFlatMap{ (hc, u) =>
    u.cmds
    Arr()
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn = simpleButton("Turn " + (scen.turn + 1).toString){
    /*val getOrders = moves.mapSomes(rs => rs)
    scen = scen.doTurn(getOrders)
    moves = NoMoves*/
    repaint()
    thisTop()
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    /*case (RightButton, ArrHead(HPlayer(p, hc1)), ArrHead(hc2: HCen)) =>
    { val newM: OptRef[HStep] = hc1.findStep(hc2)
      newM.foldDo{ if (hc1 == hc2) moves = moves.setNone(hc1) }(m => moves = moves.setSome(hc1, m))
      repaint()
    }*/

    case (_, _, h) => deb("Other; " + h.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(bTurn %: navButtons)
  statusText = s"Game Three. Scenario has ${grid.numTiles} tiles."
  thisTop()

  def frame: GraphicElems = (hexs ++ lines ++ unitOrTexts: GraphicElems).slate(-focus).scale(cPScale)
  repaint()
}