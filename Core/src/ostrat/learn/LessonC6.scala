/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn

import ostrat._, geom._, pCanv._, Colour._

case class LessonC6(canv: CanvasPlatform) extends CanvasSimple("Lesson C6")
{    
  val startText = TextGraphic(0 vv 400, "Press a key.", 28)
  repaints(startText)  
  
  canv.keyReleased = () => {deb("key"); repaints(TextGraphic(0 vv 0, "You pressed a key.", 28, Green)) }          
//  }
}