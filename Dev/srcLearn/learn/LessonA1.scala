package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA1(canv: CanvasPlatform) extends CanvasSimple("Lesson A1")
{
  repaints(
    TextGraphic("This text is in the centre of the frame.", 18),
    TextGraphic("This text is 200 pixels up.", 18, 0 vv 200, Blue),
    TextGraphic("This text is 400 pixels right, down 200", 25, 400 vv -200),
  )
}

 