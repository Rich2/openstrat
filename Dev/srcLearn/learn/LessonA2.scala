package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA2(canv: CanvasPlatform) extends CanvasSimple("Lesson A2")
{
  repaints(
    //Below we create a Text Graphic Object, the first number is number of pixels left or right of the screen centre
    TextGraphic("This text is in the centre of the frame.", 18),

    //Here we create another the second number is the number of pixels up or down
    TextGraphic("This text is 200 pixels up.", 18, 0 vv 200, Blue),

    TextGraphic("This text is 400 pixels right, down 200", 25, 400 vv -200),
    //TextGraphic("This text can't be seen till you uncomment it and rebuild the programme.", 18, 100 vv -125, Violet)
  )
}


