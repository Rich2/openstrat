/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pgui._, Colour._

case class LsC6(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C6")
{
  deb("Lesson C6")
  val startText = TextFixed("Press a key.", 28, 0 pp 300)
  repaints(startText)  
  
  canv.keyDown = (key: String) => { repaints(TextFixed("key down '"+key+"'", 28, 0 pp 0, Blue)) }
  canv.keyUp = (key: String) => { repaints(TextFixed("key up '"+key+"'", 28, 0 pp 100, Green)) }
}