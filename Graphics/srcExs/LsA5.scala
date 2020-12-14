package learn
import ostrat._, geom._, pCanv._, Colour._

/* These lessons are intended to be accessible to people who haven't programmed before and have limited geometry knowledge. This is a comment. It
 *  doesn't do anything. Everything between the forward-slash star at the beginning of the comment and the star forward-slash at the end is a comment.
 *  Hopefully in your
 *   editor or IDE (integrated Developer Environment) the comments will appear in a different colour. */

// This is also a comment. Everything after two forward-slashes to the end of line. You can add and remove //s from the beginning of the commands,
// Assuming you are running the "mill -w name.runBackground" when you do a save mill will automatically rebuild and you can see the result of your changes.
// The associated commands will appear / disappear from the screen.
 
case class LsA5(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A5")
{
//  val c1 = CArc(0, 100, 0, 0, 3)
//  val d1 = c1.draw(2, Red)
//  val c2 = c1.slate(200, 50)
//  val d2 = c2.draw(2, Orange)
//  val c3 = CArc(150, 0, 50, 0, math.Pi / 2)
//  val d3 = c3.draw(2, Blue)
//  val c4 = CArc(170, 0, 0, 0, math.Pi)
//  val d4 = c3.draw(2, Violet)
//  val c5 = CArc(190, 0, -10, 10, - math.Pi)
//  val d5 = c5.draw(2, Brown)
//  val c6 = CArc(-100, 200, -200, 100,  math.Pi / 5)
//  val d6 = c6.draw(2, Green)
//  val a1 = Arr(d1, d2, d3, d4, d5, d6)
//  val a2 = a1 ++ a1.flatMap(_.startCenEndTexts)

  val cen6 = TextGraphic("c6 cen", 14, -100 pp 200)
  val ps = Pt2(100, 100)
  val cc = Pt2(150, 150)
  val cd = Circle(100 * 2.sqrt, cc).draw()
  val p90 = ps.rotateAbout(cc, 90.degs)
  val p135 = ps.rotateAbout(cc, 135.degs)
  val p180 = ps.rotateAbout(cc, 180.degs)
  val p225 = ps.rotateAbout(cc, 225.degs)
  val p270 = ps.rotateAbout(cc, 270.degs)

  repaints(cd, ps.toText(10, Pink), p90.toText(10, Red), p135.toText(10, Brown), p180.toText(10, Green),
    p225.toText(10, Orange), p270.toText(10, Blue))
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