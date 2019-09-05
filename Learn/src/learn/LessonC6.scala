/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn

import ostrat._, geom._, pCanv._, Colour._

case class LessonC6(canv: CanvasPlatform) extends CanvasSimple("Lesson C6")
{
  deb("L6")
  val startText = TextGraphic("Press a key.", 28, 0 vv 400)
  repaints(startText)  
  
  canv.keyReleased = () => {deb("key"); repaints(TextGraphic("You pressed a key.", 28, 0 vv 0, Green)) }          
//  }
}