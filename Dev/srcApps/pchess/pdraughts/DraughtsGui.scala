/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import geom._, pgui._, Colour._, prid._, psq._

case class DraughtsGui(canv: CanvasPlatform, scen: DraughtsScen) extends CmdBarGui {
  override def title: String = "Dreughts"

  implicit def grid: SqGrid = scen.gridSys

  statusText = "Welcome to Draughts Gui"
  val darkSquareColour = Brown
  val lightSquareColour = Pink

  /** The number of pixels / 2 displayed per row height. */
  var cPScale: Double = grid.fullDisplayScale(mainWidth, mainHeight)

  def frame: GraphicElems =
  { val tiles: GraphicElems = grid.map { sc => sc.polygonReg.fillActive(sc.checkeredColour(darkSquareColour, lightSquareColour), sc) }
    val pieces = scen.draughts.scSomesFlatMap{ (sc, p) => RArr(Circle(1.2, sc.toPt2Reg).fill(p.colour), CircleActive(Circle(1.2, sc.toPt2Reg), p)) }
    (tiles ++ pieces).slate(-grid.cenVec).scale(cPScale)
 }

  def bTurn: PolygonCompound = simpleButton("Turn "){
    repaint()
    thisTop()
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) => {
      selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    /*case (RightButton, HCenPair(hc1, selectedCounter: Counter), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HStep] = gridSys.stepFind(hc1, hc2)
      newM.foreach { d =>
        if (counterSet.contains(selectedCounter)) moves = moves.replaceA1byA2OrAppend(selectedCounter, hc1.andStep(d))
        else {
          statusText = s"Illegal move You don't have control of $selectedCounter"; thisTop()
        }
      }
      repaint()
    }*/

    case (_, _, h) => deb("Other; " + h.toString)
  }

  def thisTop(): Unit = reTop(RArr(bTurn))
  thisTop()


  def repaint(): Unit = mainRepaint(frame)
  repaint()
}