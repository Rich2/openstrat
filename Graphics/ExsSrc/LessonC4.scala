/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** We will use this helper class for Lesson C4 and C5 */
case class ARect(val cen: Vec2, val width: Double = 200, val height: Double = 100, colour: Colour = Red) extends WithColour
{ def mutateColour(newColour: Colour): ARect = copy(colour = newColour) 
  def graphic = Rectangle(width, height, cen).parentFill(this, colour)
  def sGraphic = Rectangle(width, height, cen).parentFillDraw(this, colour, 2, contrast)
}

/** This lesson is working, but has what looks like a very dubious implementation. */
case class LessonC4(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C4")
{ val r0 = Rectangle(200, 100)//.fill(Red)
  val r1 = r0.slate(-300 vv 300)
  val r2 = r0.slate(300 vv 300)
  val r3 = r0.slate(300 vv -300)
  val r4 = r0.slate(-300 vv -300)
  val rArr = Polygons(r1, r2, r3, r4)
  var gArr = rArr.map(r => r.parentFill(r.polyCentre, Red))
  val textPosn = 0 vv 0
  val startText = TextGraphic("Click on the rectangles to cycle the colour.", 28, textPosn)
  deb((gArr +- startText).length.toString)
  repaint(gArr +- startText)
  
  /** Note you can use what names you like. Here I put the types explicitly for clarity. When you are familiar with an anonymous function, you will
   *  probably want to use a short parameter list like (v, b, s).  */
  mouseUp = (button: MouseButton, selected: List[Any], posn: Vec2) => selected match
  {
    /*case Refs1Tail(cen: Vec2, tail) =>
      { gArr = gArr.modifyWhere(_.cen == cen, r =>  r.mutateColour(r.colour.nextFromSeq(Colours(Red, Orange, Green))))
        repaint(gArr :+ startText)
      }*/
      case _ =>  deb("Missed")
  }
}