package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

/* These lessons are intended to be accessible to people who haven't programmed before and have poor or no geometry knowledge. This is a comment. It
 *  doesn't do anything. Everything between the forward-slash star at the beginning of the comment and the star forward-slash at the end is a comment.
 *  Hopefully in you editor or IDE (integrated Developer Environment) the comments will appear in a different colour. */

// This is also a comment. Everything after two forward-slashes to the end of line. You can add and remove //s from the beginning of the commands,
// Assuming you are running the "mill -w name.runBackground" when you do a save mill will automatically rebuild and you can see the result of your changes.
// The associated commands will appear / disappear from the screen. */
 
case class Lesson1(canv: CanvasPlatform) extends Lesson
{ val title = "Lesson 1"
  canv.lineDraw(0 vv 0, 100 vv 100)
  canv.lineDraw(0 vv 50, 150 vv 200, 3)
  canv.lineDraw(50 vv -50, 200 vv -50, 2, Red)//Note if you don't include a Colour you get Black
   
  canv.arcDraw(-200 vv 0, 0 vv 0, 0 vv 200)
  canv.arcDraw(-220 vv 0, 0 vv 0, 0 vv 220, 4, Pink)
  canv.bezierDraw(200 vv -350, -500 vv -300, -600 vv -300, -450 vv -200, 2, Green)   
  
  canv.textGraphic(0 vv 0, "This text is centred on the centre of the canvas. The point from which postions are measured.", 18, Turquoise)
  canv.textGraphic(100 vv -100, "Text centred at 100, -100", 25)
   //canv.textGraphic(100 vv -200, "This text can't be seen till you uncomment it and rebuild the programme.", 18, Violet)
}


 