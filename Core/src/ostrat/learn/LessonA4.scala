/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

sealed class ARect(val cen: Vec2)
{
  var colour = Red
}
object R1 extends ARect(-300 vv 300)
object R2 extends ARect(300 vv 300)
object R3 extends ARect(300 vv -300)
object R4 extends ARect(-300 vv -300)

case class LessonA4(canv: CanvasPlatform) extends CanvasSimple("Lesson A4")
{  
  val rList = List(R1, R2, R3, R4)
  def gList = rList.map(r => Rectangle(200, 100, r.cen).fillSubj(r, r.colour))
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