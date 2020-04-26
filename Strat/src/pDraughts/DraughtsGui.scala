/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDraughts
import geom._, pCanv._, Colour._, pGrid._

case class DraughtsGui(canv: CanvasPlatform, scen: DraughtsScen) extends CmdBarGui("Draughts")
{
  implicit def grid = scen.grid
  var statusText: String = "Welcome to Draughts Gui"
  val darkSquareColour = Brown
  val lightSquareColour = Pink
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  val tiles: GraphicElemFulls = grid.mapRPolygons{ (r, p) =>
    val col = ife(r.yPlusC %% 4 == 0, darkSquareColour, lightSquareColour)
    p.fill(col) }

  val pieces = scen.draughts.mapSomes((r, d) => Circle(0.75, r.gridVec2).fill(d.colour))
  val crosses = scen.draughts.flatMapSomes((r, d) => Cross(0.01).slate(r.gridVec2))//.fill(d.colour))

 def bTurn = clickButton("Turn ", _ => {
   repaint()
   thisTop()
 })
  def thisTop(): Unit = reTop(Arr(bTurn, status))
  thisTop()

  def frame = (tiles ++ pieces ++ crosses).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}