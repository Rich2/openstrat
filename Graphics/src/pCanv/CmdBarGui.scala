/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCanv
import geom._, Colour._

/** A simple 2 panel display, a man panel and a command bar. */
abstract class CmdBarGui(title: String) extends CanvasPanelled(title)
{
  val barWidth = 30
  val topBar = addPanel(Rect.fromTL(canv.width, barWidth, canv.topLeft), true)
  topBar.backColour = Gray
  var statusText: String
  
  def textBox(str: String, cmd: AnyRef) =
    Rect.applyOld(10, 25).parentFillText(cmd, Colour.Gray, str, 15, Colour.White, LeftAlign)
    
  def status = textBox(statusText, None)
  val mainPanel: Panel = addPanel(Rect.fromBL(canv.width, canv.height - barWidth, canv.bottomLeft))
  def mainRepaint(els: Arr[DisplayElem]): Unit = mainPanel.repaint(els)
  def mainRepaints(els: DisplayElem*): Unit = mainPanel.repaints(els: _*)
  def mainWidth = mainPanel.width
  def mainHeight = mainPanel.height
  /**  repaints the top command bar */
  def reTop(commands: Arr[DisplayBounded]): Unit = topBar.repaint(displayRowGraphics(topBar.cenLeft, commands))
  def mainMouseUp: (MouseButton, List[Any], Vec2) => Unit = mainPanel.mouseUp
  def mainMouseUp_= (f: (MouseButton, List[Any], Vec2) => Unit): Unit = { mainPanel.mouseUp = f }
  var selected: List[Any] = Nil
  topBar.mouseUp =
    { case (b, List(MouseButtonCmd(cmd)), _) => cmd.apply(b)
    case (_, l, _) => deb(l.toString)
    }
}

object StdButton
{
  def apply(str: String, cmd: AnyRef) =
      Rect.curvedCornersCentred(str.length.max(2) * 17, 25, 5).parentAll(cmd, White, 3, Black, 25, str)
  def turn(num: Int) = apply("Turn" -- num.toString, Turn)    
}

object Turn extends PersistSingleton
{ def str: String = "Turn"
}