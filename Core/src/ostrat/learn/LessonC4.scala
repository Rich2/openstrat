/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

/** We will use this helper class for Lesson C4 and C5 */
case class ARect(val cen: Vec2, val width: Double = 200, val height: Double = 100) extends WithColour
{
  var colour = Red
  def graphic = Rectangle(width, height, cen).fillSubj(this, colour)
  def sGraphic = Rectangle(width, height, cen).fillDrawSubj(this, colour, 2, contrast)
}

case class LessonC4(canv: CanvasPlatform) extends CanvasSimple("Lesson C4")
{
  val r1 = ARect(-300 vv 300)
  val r2  = ARect(300 vv 300)
  val r3 = ARect(300 vv -300)
  val r4 = ARect(-300 vv -300)
  val rList = List(r1, r2, r3, r4)
  def gList = rList.map(_.graphic)
  val textPosn = 0 vv 0
  val startText = TextGraphic(textPosn, "Click on the rectangles to cycle the colour.", 28)
  repaint(gList :+ startText)
  
  mouseUp = (v, b, s) => s match
  {
    case (r: ARect) :: tail =>
      {
        val newColour =  r.colour match
        {
          case Red => Orange
          case Orange => Yellow
          case Yellow => Green
          case _ => Red
        }
        r.colour = newColour
        repaint(gList :+ startText)
      }
      case _ =>       
  }
}