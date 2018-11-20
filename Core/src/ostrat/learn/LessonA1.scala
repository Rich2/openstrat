/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

/** LessonA1 where A is for active. Your canvas will actually respond to user input. */
case class LessonA1(canv: CanvasPlatform) extends CanvasSimple("Lesson A1")
{  
  repaints(TextGraphic(0 vv 200, "Please click on the screen a few times.", 28, Green))
  var counter = 0
  def newText = TextGraphic(Vec2Z, "You have clicked the screen " + counter.toString + " times.", 28)
  setMouseSimplest{
    counter += 1
    repaints(newText)
  }
}