package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

/* These lessons are intended to be accessible to people who haven't programmed before and have limited geometry knowledge. This is a comment. It
 *  doesn't do anything. Everything between the forward-slash star at the beginning of the comment and the star forward-slash at the end is a comment.
 *  Hopefully in your
 *   editor or IDE (integrated Developer Environment) the comments will appear in a different colour. */

// This is also a comment. Everything after two forward-slashes to the end of line. You can add and remove //s from the beginning of the commands,
// Assuming you are running the "mill -w name.runBackground" when you do a save mill will automatically rebuild and you can see the result of your changes.
// The associated commands will appear / disappear from the screen.
 
case class Lesson1(canv: CanvasPlatform) extends CanvasSimple("Lesson 1")
{
  canv.textGraphic(100 vv 200, "Hi, there!", 28, Red)//This test is centred 300 pixels up and 100 pixels to the right of centre  
  canv.textGraphic(0 vv 0, "This text is centred on the centre of the canvas. The point from which postions are measured.", 18, Blue)
  canv.textGraphic(100 vv -100, "Text centred at 100, -100", 25)
  //canv.textGraphic(100 vv -200, "This text can't be seen till you uncomment it and rebuild the programme.", 18, Violet)
}

/** There are three types of values above. Numbers, text and Colours. Try changing the numbers, save the file and you should things move around the
  * screen. Congratulations! you are now a programmer. But, but, you respond, there's loads of magic going on here that I don't understand. All
  * programmers rely on magic, and of course the first thing we should know about magic is it doesn't always work. I will try and explain how some of
  * the magic works, but in programming there's always more magic to decode.
  *
  * The second type is text. Programmers refer to text as Strings, for some reason. I shall use the term string from now on. Strings start with a "
  * quotation mark and end with a quotation mark. Change the text in on one of the strings, save the file and you should see the text change on the
  * screen. If you try replacing a string with a number or replacing a number with a String, the compiler will complain and you will get an error
  * message. The third type is Colour. Note Colours must have the correct capital letters. You can just try guessing the colours or you can google /
  * duckduck web colours to see what is available. Again put a Colour where a number or a string is expected or vice versa and the compiler will
  * complain. */


 