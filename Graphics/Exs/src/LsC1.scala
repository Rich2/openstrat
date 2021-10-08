/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** LessonC1 is for interactive lessons. Your canvas will actually respond to user input. */
case class LsC1(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C1")
{  
  repaints(TextGraphic("Please click on the screen a few times.", 28, 0 pp 200, Green))
  var counter = 0
  def newText = TextGraphic("You have clicked the screen " + counter.toString + " times.", 28, Pt2Z)
  setMouseSimplest{
    counter += 1
    repaints(newText)
  }
}