/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._, Colour._

/** A MapGui uses a CanvMulti. It has a main map panel and a top control panel. In addition translates between 2d game coordinates and 2d coordinates
 *  on the canvas. Allowing the canvas to display a moving, scalable 2d view though the Game map. The x coordinate increases from left to right, the
 *  y coordinate increases from bottom to top. */
abstract class MapGui(title: String) extends CanvasPanelled(title)
{
  val barWidth = 30
  val topPan: Panel = addPanel(Rectangle.fromTL(canv.width, barWidth, canv.topLeft), true)
  topPan.backColour = Colour.Gray
  topPan.mouseUp = 
  {
    case (v, b, Refs1(MButtonCmd(cmd))) => cmd.apply(b)
    case t => deb(t.toString)
  }
  def cmdButton(str: String, cmd: AnyRef) = Rectangle.curvedCornersCentred(50, 25, 5).subjAll(cmd, White, 3, Black, 25, str)
  
  def clickButton(str: String, cmd: MB0, backColour: Colour = Colour.White) =
    Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5).subjAll(MButtonCmd(cmd), backColour, 3, backColour.contrastBW, 25, str)
   
  def buttonStd(str: String, cmd: MB0, backColour: Colour = Colour.White) =
    Rectangle.curvedCornersCentred(100, 25, 5).subjAll(cmd, backColour, 3, backColour.contrastBW, 20, str)   
   
  def textBox(str: String, cmd: AnyRef) = Rectangle(10, 25).fillTextSubj(cmd, Colour.Gray, str, 15, Colour.White, LeftAlign)
  /**  repaints the top command bar */
  def reTop(commands: Refs[GraphicSubject]): Unit = topPan.repaintOld(DisplayRowGraphicSubject(10, commands).fromLeft(topPan.cenLeft).toArraySeq)
  var statusText = "This is the status text."
  def status = textBox(statusText, None)
  val mapPanel: Panel = addPanel(Rectangle.fromBL(canv.width, canv.height - barWidth, canv.bottomLeft))
  def mapPanelDiameter = mapPanel.width.min(mapPanel.height).max(10)   
  def mapObjs: GraphicElems
  def eTop(): Unit
  
  def setStatus(str: String): Unit = { statusText = str; eTop() }
}

