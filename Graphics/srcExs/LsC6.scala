/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LsC6(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C6")
{
  deb("Lesson C6")
  val startText = TextGraphic("Press a key.", 0 vv 300, 28)
  repaints(startText)  
  
  canv.keyDown = (key: String) => { repaints(TextGraphic("key down '"+key+"'", 0 vv 0, 28, Blue)) }
  canv.keyUp = (key: String) => { repaints(TextGraphic("key up '"+key+"'", 0 vv 100, 28, Green)) }
}
