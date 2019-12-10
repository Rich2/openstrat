/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** LessonI1 where I is for interactive. Your canvas will actually respond to user input. */
case class LessonC1(canv: CanvasPlatform) extends CanvasSimple("Lesson C1")
{  
  repaints(TextGraphic("Please click on the screen a few times.", 28, 0 vv 200, Green))
  var counter = 0
  def newText = TextGraphic("You have clicked the screen " + counter.toString + " times.", 28)
  setMouseSimplest{
    counter += 1
    repaints(newText)
  }
}