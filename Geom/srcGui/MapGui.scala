/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._, Colour._

/** A MapGui uses a CanvMulti. It has a main map panel and a top control panel. In addition translates between 2d game coordinates and 2d coordinates
 *  on the canvas. Allowing the canvas to display a moving, scalable 2d view though the Game map. The x coordinate increases from left to right, the
 *  y coordinate increases from bottom to top. */
abstract class MapGui(val title: String) extends CanvasPanelled
{
  val barWidth = 30
  val topPan: Panel = addPanel(Rect.tl(canv.width, barWidth, canv.topLeft), true)
  topPan.backColour = Colour.Gray

  topPan.mouseUp =
  { case (b, RArrHead(MouseButtonCmd(cmd)), _) => cmd.apply(b)
    case (_, l, _) => deb(l.toString)
  }
   
  def textBoxFull(str: String, cmd: AnyRef): PolygonCompound = Rect(75, 25).fillActiveTextAbs(Gray, cmd, str, 15, White, LeftAlign)

  def textBox(str: String, cmd: AnyRef): PolygonCompound = Rect(75, 25).fillActiveTextAbs(Gray, cmd, str, 15, White, LeftAlign)

  /**  repaints the top command bar */
  def reTop(commands: RArr[GraphicBounded]): Unit = topPan.repaint(displayRowGraphics(topPan.cenLeft, commands))
  var statusText = "This is the status text."
  def status = textBoxFull(statusText, None)
  val mapPanel: Panel = addPanel(Rect.bl(canv.width, canv.height - barWidth, canv.bottomLeft))
  def mapPanelDiameter = mapPanel.width.min(mapPanel.height).max(10)   
  def mapObjs: GraphicElems
  def eTop(): Unit
  
  def setStatus(str: String): Unit = { statusText = str; eTop() }
}