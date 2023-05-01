/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pAltReact
import pgui._, prid._, psq._, geom._

case class AltReacGui(canv: CanvasPlatform, rows: Int, columns: Int) extends SqSysGui("Alternative Reactor")
{
  statusText = "a Welcome alternative to ReactorGui."

  var scen = AltScen.start(rows, columns)
  implicit def gridSys: SqGrid = scen.grid
  focus = gridSys.cenVec
  pixPerC = 32
  def balls: SqCenOptLayer[Balls] = scen.balls

  /** The number of pixels / 2 displayed per row height. */
  val scale = gridSys.fullDisplayScale(mainWidth, mainHeight)

  /** Draws the tiles sides (or edges). */
  val sidesDraw: GraphicElem = gridSys.sidesDraw()

  def ballDisps: GraphicElems = balls.scSomesMap{ (sc, bs) => TextGraphic(bs.num.toString, 18, sc.toPt2Reg, bs.colour) }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn = simpleButton("Turn " + (scen.turn + 1).toString){
    //    val getOrders = moves.mapSomeOnlys(rs => rs)
    //    scen = scen.turn(getOrders)
    //    moves = NoMoves
    //    repaint()
    //    thisTop()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(RArr(bTurn))
  thisTop()
  def frame: GraphicElems = (ballDisps +% sidesDraw).slate(-focus).scale(pixPerC)// ++ moveGraphics2
  //(tiles +- sidesDraw ++ roardTexts ++ lunits ).gridScale(scale)
  repaint()
}
