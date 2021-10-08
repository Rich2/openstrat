/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** The B series lessons are dynamic. The display changes with time. You don't need to do all the a lessons before starting on the Bs.
 *  Alternatively if you merely want to create printable materials then you don't need to do the B series. */
case class LsB1(canv: CanvasPlatform) extends CanvasNoPanels("Lesson B1")
{
  /*This is the simplest time helper method. You merly need to provide a single object that changes over time. Note each time we create a new
  differenet object. rather than modifying the original. */
  timedRepaint1(e => TextGraphic((e /1000).toString + " Seconds have elapsed", 45, Pt2Z))
}