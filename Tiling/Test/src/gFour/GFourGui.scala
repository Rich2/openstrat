/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour
import pgui._, prid._, phex._, geom._, gPlay._

case class GFourGui(canv: CanvasPlatform, scenStart: FourScen) extends HGridFlatGui("Game Three Gui")
{ statusText = "Welcome to Game Four."
  val scen = scenStart
  def terrs: HCenDGrid[Terr] = scen.terrs
  var history: Arr[FourScen] = Arr(scen)
  implicit def gridSys: HGridSysFlat = scen.gridSys
  focus = gridSys.cenVec

  /** The number of pixels / 2 displayed per row height. */
  var cPScale: Double = gridSys.fullDisplayScale(mainWidth, mainHeight)

  val lines: Arr[LineSegDraw] = terrs.sideFlatMap((hs, _) => Arr(hs.draw()), (hs, t1, t2 ) => ife(t1 == t2, Arr(hs.draw(t1.contrastBW)), Arr()))

  val rows: Arr[HCenRowValue[Terr]] = terrs.rowCombine
  debvar(rows.length)
  val hexs: Arr[PolygonCompound] = rows.map{ hv => hv.polygonReg.fillActive(hv.value.colour, hv) }
  def units: HCenOptDGrid[Lunit] = scen.units

  /** Uses the mapHCen method on units. This takes two functions, the first for when there is no unit in the hex tile. Note how we can access the
   *  data in the separate terrs array by use of the HCen coordinate.  */
  def unitOrTexts: GraphicElems = units.mapHCen{hc => hc.decText(14, terrs(hc).contrastBW) } { (hc, p) =>
    Rect(1.6, 1.2, hc.toPt2Reg).fillDrawTextActive(p.colour, p, p.team.name + "\n" + hc.rcStr, 24, 2.0) }

  def moveGraphics: GraphicElems = units.hcSomesMap{ (hc, u) => LineSegHC(hc, hc.unsafeStep(u.cmds(0))).lineSeg.draw(units.unSafeApply(hc).colour)}

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = simpleButton("Turn " + (scen.turn + 1).toString){
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

    case (RightButton, AnyArrHead(HPlayer(hc1, _)), hits) => hits.findHCenForEach{ hc2 =>
     val newM: Option[HDirn] = gridSys.findStep(hc1, hc2)
      //newM.fold{ if (hc1 == hc2) moves = moves.setNone(hc1) }(m => moves = moves.setSome(hc1, m))
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(bTurn %: navButtons)
  statusText = s"Game Four. Scenario has ${gridSys.numTiles} tiles."
  thisTop()

  def frame: GraphicElems = (hexs ++ lines ++ unitOrTexts: GraphicElems).slate(-focus).scale(cPScale)
  repaint()
}