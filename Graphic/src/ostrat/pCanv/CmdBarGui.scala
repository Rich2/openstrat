/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._, Colour._

abstract class CmdBarGui(title: String) extends CanvasMulti(title)
{
  val barWidth = 30
  val topPan = addPanel(Rectangle.fromTL(canv.width, barWidth, canv.topLeft), true)
  topPan.backColour = Gray
  var statusText: String
  def textBox(str: String, cmd: AnyRef) = Rectangle(10, 25).fillTextSubj(cmd, Colour.Gray, str, 15, Colour.White, TextLeft)
  def status = textBox(statusText, Unit)
  val mainPanel: Panel = addPanel(Rectangle.fromBL(canv.width, canv.height - barWidth, canv.bottomLeft))
}