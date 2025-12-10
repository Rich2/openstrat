/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._

object LsTimer extends LessonGraphics
{
  override def title: String = "Timer Lesson"

  override def bodyStr: String = """The B series lessons are dynamic. The display changes with time. You don't need to do all the a lessons before starting on
  the Bs. Alternatively if you merely want to create printable materials then you don't need to do the B series."""

  override def canv: CanvasPlatform => Any = TimerCanv(_)
  
  case class TimerCanv(canv: CanvasPlatform) extends CanvasNoPanels("Timer Lesson")
  {
    /** This is the simplest time helper method. You merely need to provide a single object that changes over time. Note each time we create a new different
     * object. rather than modifying the original. */
    timedRepaint1(e => TextFixed((e / 1000).toString + " Seconds have elapsed", 45, Origin2))
  }
}