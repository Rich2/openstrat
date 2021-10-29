/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCanv
import geom._, Colour._

/** A simple 2 panel display, a man panel and a command bar. */
abstract class CmdBarGui(title: String) extends CanvasPanelled(title)
{
  val barWidth = 30
  val topBar = addPanel(Rect.tl(canv.width, barWidth, canv.topLeft), true)
  topBar.backColour = Colour.Cornsilk

  /** The text for the status field of the command bar. */
  var statusText: String = ""
  
  def textBox(str: String, cmd: AnyRef) = Rect(40, 25).fillDrawTextActive(Gray, cmd, str, 15, 2, White, LeftAlign)
  
  val mainPanel: Panel = addPanel(Rect.bl(canv.width, canv.height - barWidth, canv.bottomLeft))
  def mainRepaint(els: Arr[GraphicElem]): Unit = mainPanel.repaint(els)
  def mainRepaints(els: GraphicElem*): Unit = mainPanel.repaints(els: _*)
  def mainWidth = mainPanel.width
  def mainHeight = mainPanel.height

  /**  repaints the top command bar */
  def reTop(commands: Arr[GraphicBounded]): Unit =
  {
    val a = displayRowGraphics(topBar.cenLeft, commands)
    val sp = ife(a.empty, topBar.cenLeft,a.last.boundingRect.cen.addX(80))
    val st = TextGraphic(statusText, 15, sp, Black, LeftAlign)
    topBar.repaint(a +- st)
  }
  def mainMouseUp: (MouseButton, Arr[AnyRef], Pt2) => Unit = mainPanel.mouseUp
  def mainMouseUp_= (f: (MouseButton, Arr[AnyRef], Pt2) => Unit): Unit = { mainPanel.mouseUp = f }
  var selected: Arr[AnyRef] = Arr()
  topBar.mouseUp =
    { case (b, ArrHead(MouseButtonCmd(cmd)), _) => cmd.apply(b)
      case (_, l, _) => deb(l.toString)
    }
}

object StdButton
{
  def apply(str: String, cmd: AnyRef) =
      Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5).parentAll(cmd, White, 3, Black, 25, str)

  def turn(num: Int) = apply("Turn" -- num.toString, Turn)    
}

object Turn extends ShowSingleton
{ override def typeStr: String = "Turn.type"
  def str: String = "Turn"
}