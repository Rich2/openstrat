/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import geom._, pgui._, Colour._, prid._, psq._, pGrid._, proord._

case class ChessGui(canv: CanvasPlatform, scen: ChessScen) extends CmdBarGui("Chess")
{ implicit val grid: SqGrid = scen.grid
  statusText = "Welcome to Chess Gui"
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val darkSquareColour = DarkGreen
  val lightSquareColour = LightBlue
  val tiles: GraphicElems = Arr()/*grid.mapRPolygons{ (r, p) =>
    val col = ife(r.yPlusC %% 4 == 0, darkSquareColour, lightSquareColour)
    val yStr: String = ('A' + r.y / 2 - 1).toChar.toString
    val cStr: String = ('0' + r.c / 2).toChar.toString
    p.fillText(col, yStr + cStr, 20) }*/

  val pieces: GraphicElems = Arr()// = scen.pieces.mapSomeWithRoords((r, p) => p.piece().slate(r.gridPt2).fillDraw(p.player.colour, p.player.contrastBW))

  def bTurn = simpleButton("Turn "){
    repaint()
    thisTop()
  }

  def thisTop(): Unit = reTop(Arr(bTurn))
  thisTop()
  def frame = (tiles ++ pieces)//.gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}