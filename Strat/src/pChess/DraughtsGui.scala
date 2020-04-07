/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pChess
import geom._, pCanv._, Colour._, pGrid._

case class DraughtsGui(canv: CanvasPlatform, scen: DraughtsScen) extends CmdBarGui("Draughts")
{
  implicit def grid = scen.grid
  var statusText: String = "Welcome to Chess Gui"
  val darkSquareColour = Brown
  val lightSquareColour = Pink
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  val tiles: GraphicElems = grid.mapPolygons{ (r, p) =>
    val col = ife(r.yPlusC %% 4 == 0, darkSquareColour, lightSquareColour)
    p.fill(col) }

 def bTurn = clickButton("Turn ", _ => {
   repaint()
   thisTop()
 })
  def thisTop(): Unit = reTop(Refs(bTurn, status))
  thisTop()

  def frame = (tiles).gridTrans(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
