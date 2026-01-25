/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import geom.*, pgui.*, Colour.*, prid.psq.*

case class ChessGui(canv: CanvasPlatform, scen: ChessScen) extends CmdBarGui
{
  override def title: String = "Chess"

  implicit val grid: SqGrid = scen.gridSys
  statusText = "Welcome to Chess Gui"
  //val scale: Double = grid.fullDisplayScale(mainWidth, mainHeight)
  val darkSquareColour = DarkGreen
  val lightSquareColour = LightBlue
  val tiles: GraphicElems = grid.map{sc => sc.polygonReg.fillActive(sc.checkeredColour(darkSquareColour, lightSquareColour), sc) }

  /** The number of pixels / 2 displayed per row height. */
  var cPScale: Double = grid.fullDisplayScale(mainWidth, mainHeight)

  val pieces: GraphicElems = scen.oPieces.scSomesMap((r, p) => p.piece().slate(r.toPt2Reg).fillDraw(p.player.colour, p.player.contrastBW))

  def bTurn: PolygonCompound = simpleButton("Turn "){
    repaint()
    thisTop()
  }

  def thisTop(): Unit = reTop(RArr(bTurn))
  thisTop()
  def frame: RArr[Graphic2Elem] = (tiles ++ pieces).slate(-grid.cenVec).scale(cPScale)
  def repaint(): Unit = mainRepaint(frame)
  repaint()
}