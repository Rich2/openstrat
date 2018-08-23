/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._
import Colour._

/** A MapGui uses a CanvLike and in addition translates between 2d game coordinates and 2d coordinates on the canvas. Allowing the canvas
 *  to display a moving, scalable 2d view though the Game map. The x coordinate increases from left to right, the y coordinate increases from
 *  bottom to top. */
trait MapGui extends CanvasMulti
{   
   val barWidth = 30
   val topPan = addPanel(Rectangle.tL(canv.topLeft, canv.width, barWidth), true)
   topPan.backColour = Colour.Gray
   def button1(str: String, cmd: AnyRef) = Rectangle.curved(50, 25, 5).subjAll(cmd, White, 3, Black, 25, str)
   def button3(str: String, cmd: MouseButton => Unit) =
      Rectangle.curved(str.length.max(2) * 17, 25, 5).subjAll(MButtonCmd(cmd), White, 3, Black, 25, str)
   def buttonStd(str: String, cmd: AnyRef, backColour: Colour = Colour.White) =
      Rectangle.curved(100, 25, 5).subjAll(cmd, backColour, 3, backColour.contrastBW, 20, str)   
   def textBox(str: String, cmd: AnyRef) = Rectangle(10, 25).fillTextSubj(cmd, Colour.Gray, str, 15, Colour.White, TextLeft)
   def reTop(commands: List[CanvSubj[_]]): Unit = topPan.repaint(DisplayRow(10, commands).fromLeft(topPan.cenLeft))
   //   var status: DTextLine = mainBar.addTextLine("Nothing Selected")
   var statusText = "Use middle and right mouse buttons for greater deltas"
   def status = textBox(statusText, Unit)
   val mapPanel: Panel = addPanel(Rectangle.bL(canv.bottomLeft, canv.width, canv.height - barWidth))
   def mapPanelDiameter = mapPanel.width.min(mapPanel.height).max(10)   
   def mapObjs: CanvElems
   //def updateView(): Unit = {repaintMap; setStatus(viewStr) }
   def eTop(): Unit = {}
   def setStatus(str: String): Unit = 
   {
      println(str)
      statusText = str
      eTop()
   }
}

