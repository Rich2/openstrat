/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._, Colour._

/** This trait exists to provide text fpr a status bar in a graphical user interface when selected. */
trait Selectable extends Any
{ /** The [[String]] to be displayed in the status bar in a GUI when selected. */
  def selectStr: String
}

/** A simple 2 panel display, a man panel and a command bar. */
trait CmdBarGui extends CanvasPanelled
{ val barThickness = 30
  val topBar = addPanel(Rect.tl(canv.width, barThickness, canv.topLeft), true)
  topBar.backColour = Colour.Cornsilk

  /** The text for the status field of the command bar. */
  var statusText: String = ""

  def textButton(str: String, cmd: AnyRef): PolygonCompound = Rect(40, 25).fillActiveDrawText(Gray, cmd, str, 15, 2, White, Colour.Black, LeftAlign)

  val mainPanel: Panel = addPanel(Rect.bl(canv.width, canv.height - barThickness, canv.bottomLeft))
  def mainRepaint(els: RArr[GraphicElem]): Unit = mainPanel.repaint(els)
  def mainRepaints(els: GraphicElem*): Unit = mainPanel.repaints(els: _*)
  def mainWidth: Double = mainPanel.width
  def mainHeight: Double = mainPanel.height

  /**  repaints the top command bar */
  def reTop(commands: RArr[GraphicBounded]): Unit =
  { val a = displayRowGraphics(topBar.cenLeft, commands)
    val sp = ife(a.empty, topBar.cenLeft,a.last.boundingRect.cen.addX(80))
    val st = TextFixed(statusText, 15, sp, Black, LeftAlign)
    topBar.repaint(a +% st)
  }

  canv.onScroll = b => mainPanel.onScroll(b)
  def mainMouseUp: (MouseButton, RArr[Any], Pt2) => Unit = mainPanel.mouseUp
  def mainMouseUp_= (f: (MouseButton, RArr[Any], Pt2) => Unit): Unit = { mainPanel.mouseUp = f }

  /** Selected takes the type Any, but the value None means nothing selected. */
  var selected: Any = None

  def selectedStr: String = selected match
  { case None => "Nothing selected"
    case sel: Selectable => sel.selectStr
    case sw: Showed => sw.str
    case a => a.toString
  }

  topBar.mouseUp =
  { case (b, RArrHead(MouseButtonCmd(cmd)), _) => cmd.apply(b)
    case (_, l, _) => deb(l.toString)
  }
}

object StdButton
{
  def apply(str: String, cmd: AnyRef) =
      Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5).parentAll(cmd, White, 3, Black, 25, str)

  def turn(num: Int) = apply("Turn" -- num.toString, Turn)    
}

object Turn extends ShowSimpled
{ override def typeStr: String = "Turn.type"
  override def str: String = "Turn"
}