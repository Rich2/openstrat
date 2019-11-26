/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn

import ostrat._, geom._, pCanv._, Colour._

case class LessonC6(canv: CanvasPlatform) extends CanvasSimple("Lesson C6")
{
  deb("Lesson C6")
  val startText = TextGraphic("Press a key.", 28, 0 vv 300)
  repaints(startText)  
  
  canv.keyPressed = (key: String, code: String) => { repaints(TextGraphic("key down '"+key+"' with code = "+code, 28, 0 vv 0, Blue)) }          
  canv.keyReleased = (key: String, code: String) => { repaints(TextGraphic("key up '"+key+"' with code = "+code, 28, 0 vv 100, Green)) }          
//  }
}
