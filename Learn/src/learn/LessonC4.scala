/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** We will use this helper class for Lesson C4 and C5 */
case class ARect(val cen: Vec2, val width: Double = 200, val height: Double = 100, colour: Colour = Red) extends WithColour
{
  def mutateColour(newColour: Colour): ARect = copy(colour = newColour) 
  def graphic = Rectangle(width, height, cen).fillSubj(this, colour)
  def sGraphic = Rectangle(width, height, cen).fillDrawSubj(this, colour, 2, contrast)
}

/** This lesson is working, but has what looks like a very dubious implementation. */
case class LessonC4(canv: CanvasPlatform) extends CanvasSimple("Lesson C4")
{
  val r1 = ARect(-300 vv 300)
  val r2 = ARect(300 vv 300)
  val r3 = ARect(300 vv -300)
  val r4 = ARect(-300 vv -300)
  var rArr = Arr(r1, r2, r3, r4)
  def gArr = rArr.map(_.graphic)
  val textPosn = 0 vv 0
  val startText = TextGraphic("Click on the rectangles to cycle the colour.", 28, textPosn)
  repaint(gArr :+ startText)
  
  /** Note you can use what names you like. Here I put the types explicitly for clarity. When you are familiar with an anonymous function, you will
   *  probably want to use a short parameter list like (v, b, s).  */
  mouseUp = (posn: Vec2, button: MouseButton, selected: Arr[AnyRef]) => selected match
  {
    case Arr1(r: ARect, tail) =>
      { rArr = rArr.replace(r, r.mutateColour(r.colour.nextFromSeq(Colours(Red, Orange, Green))))
        repaint(rArr.map(_.graphic) :+ startText)
      }
      case _ =>       
  }
}