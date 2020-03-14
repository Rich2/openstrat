/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._, Colour._

abstract class CmdBarGui(title: String) extends CanvasPanelled(title)
{
  val barWidth = 30
  val topBar = addPanel(Rectangle.fromTL(canv.width, barWidth, canv.topLeft), true)
  topBar.backColour = Gray
  var statusText: String
  def textBox(str: String, cmd: AnyRef) = Rectangle(10, 25).fillTextParent(cmd, Colour.Gray, str, 15, Colour.White, LeftAlign)
  def status = textBox(statusText, None)
  val mainPanel: Panel = addPanel(Rectangle.fromBL(canv.width, canv.height - barWidth, canv.bottomLeft))
  /**  repaints the top command bar */
   def reTop(commands: Refs[GraphicParent]): Unit = topBar.repaint(displayRowParents(10, commands, topBar.cenLeft))
}

object StdButton
{
  def apply(str: String, cmd: AnyRef) =
      Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5).parentAll(cmd, White, 3, Black, 25, str)
  def turn(num: Int) = apply("Turn" -- num.toString, Turn)    
}

object Turn extends PersistSingleton
{
  def str: String = "Turn"
}