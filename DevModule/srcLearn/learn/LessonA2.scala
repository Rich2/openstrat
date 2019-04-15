package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA2(canv: CanvasPlatform) extends CanvasSimple("Lesson A2")
{
  val x1 = 100
  val y1 = 200
  repaints(
      //Below we create a Text Graphic Object, the first number is number of pixels left or right of the screen centre
      TextGraphic("This text is at " + x1.toString + ", " + y1.toString, 18, x1 vv y1, Red),  
      
      //Here we create another the second number is the number of pixels up or down
      TextGraphic("This text is centred on the centre of the canvas. The point from which postions are measured.", 18, 0 vv 0, Blue),
      
      TextGraphic("Text centred at 100 pixels right of centre and 100 pixels below centre", 25, 100 vv -100),
      //TextGraphic(100 vv -200, "This text can't be seen till you uncomment it and rebuild the programme.", 18, Violet)
      )  
}


 