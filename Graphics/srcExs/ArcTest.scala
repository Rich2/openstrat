package learn
import ostrat._, geom._, pCanv._, Colour._
import scala.math.{Pi}
/* These lessons are intended to be accessible to people who haven't programmed before and have limited geometry knowledge. This is a comment. It
 *  doesn't do anything. Everything between the forward-slash star at the beginning of the comment and the star forward-slash at the end is a comment.
 *  Hopefully in your
 *   editor or IDE (integrated Developer Environment) the comments will appear in a different colour. */

// This is also a comment. Everything after two forward-slashes to the end of line. You can add and remove //s from the beginning of the commands,
// Assuming you are running the "mill -w name.runBackground" when you do a save mill will automatically rebuild and you can see the result of your changes.
// The associated commands will appear / disappear from the screen.
 
case class ArcTest(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A4")
{
  // val co = CArcOld(-300 pp 0, 0 pp 0, 0 pp 300)
  // val c3 = CArc3(-200 pp 0, -141.421356237 pp 141.421356237, 0 pp 200)
  // val r1 = 0.1
  // val r2 = 0.2
  // val rot = AngleVec(r1 + r2)
  // deb(rot.toString)
  // debvar(c3.radius)
  // val stuff = Arr(

   
  //   CArcDrawOld(-250 pp 0, 0 pp 0, 0 pp 250),
  //   CArcDrawOld(-300 pp 0, 0 pp 0, 0 pp 300, 4, Green),
  //   co.controlPt.toText(),
  //   c3.draw(Crimson),

  //   // Bezier(200 pp -350, -500 pp -300, -600 pp -300, -450 pp -200).draw(Green, 2),
  // )
  // repaint(stuff)

  var x = 0
  var y = 0
  val radius = 20
  val startAngle = 0

  var myStuff:GraphicElems = Arr()
  for (i <- 0 to 3) {
    for (j <- 0 to 3) {
      x = 25 + j * 50; // x coordinate
      y = 25 + i * 50; // y coordinate
      var endAngle = Pi + (Pi * j) / 2; // End point on circle
      //val anticlockwise = i % 2 !== 0; // clockwise or anticlockwise
      myStuff = myStuff ++ Arr(//CArcDraw(CArc(x pp y, x+radius pp y, endAngle), 2, Blue),
                               CArc3(x pp y, x+15 pp y-30, x+30 pp y).draw(Crimson), // apex
                               CArcDrawOld(x pp y, x+15 pp y, x+15 pp y-15, 2, Green)) // centre 
    }
  }
  repaint(myStuff)
}


// CArcDrawOld(x pp y, 0 pp 0, 0 pp 250)
// CArc3(x pp y, -141.421356237 pp 141.421356237, 0 pp 200).draw(Crimson)
// Arr(CArcDraw(CArc(x pp y, x+radius pp y, endAngle), 2, Blue))



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
