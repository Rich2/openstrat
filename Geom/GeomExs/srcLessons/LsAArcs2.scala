/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

/* These lessons are intended to be accessible to people who haven't programmed before and have limited geometry knowledge. This is a comment. It  doesn't do
   anything. Everything between the forward-slash star at the beginning of the comment and the star forward-slash at the end is a comment. Hopefully in your
   editor or IDE (integrated Developer Environment) the comments will appear in a different colour. */

// This is also a comment. Everything after two forward-slashes to the end of line. You can add and remove //s from the beginning of the commands,
// Assuming you are running the "mill -w name.runBackground" when you do a save mill will automatically rebuild and you can see the result of your changes.
// The associated commands will appear / disappear from the screen.

/** Arcs Lesson. There are three types of values above. Numbers, text and Colours. Try changing the numbers, save the file and you should things move around the
 * screen. Congratulations! you are now a programmer. But, but, you respond, there's loads of magic going on here that I don't understand. All programmers rely
 * on magic, and of course the first thing we should know about magic is it doesn't always work. I will try and explain how some of the magic works, but in
 * programming there's always more magic to decode.
 *
 * The second type is text. Programmers refer to text as Strings, for some reason. I shall use the term string from now on. Strings start with a " quotation
 * mark and end with a quotation mark. Change the text in on one of the strings, save the file, and you should see the text change on the screen. If you try
 * replacing a string with a number or replacing a number with a String, the compiler will complain and you will get an error message. The third type is Colour.
 * Note Colours must have the correct capital letters. You can just try guessing the colours, or you can google / duckduck web colours to see what is available.
 * Again put a Colour where a number or a string is expected or vice versa and the compiler will complain. */
object LsAArcs2 extends LessonStatic
{
  override def title: String = "Arcs 2"

  override def bodyStr: String = "Arcs"

  val c1St = Pt2(100, 200)
  val c1Cen = Pt2(100, 100)
  val c1End = Pt2(200, 100)
  val c1StText = c1St.textArrow("C1 Start, negative or clockwise arc", Ang30, 20, Blue)
  val c1 = CArc.neg(c1St, c1Cen, c1End).draw(lineColour = Blue)
  val c2StText = c1St.textArrow("C2 Start, positive or anti clockwise arc", Ang150, 20, DarkRed)
  val c2 = CArc.pos(c1St, c1Cen, c1End).draw(lineColour = DarkRed)
  val c3: CArc = CArc.neg(-100, 0, 0, 0, 0, 100)
  val c3d = c3.draw(lineColour = Violet)
  val c4 = c3.slate(-25, 0).addRotations(-1)
  val c4d = c4.draw(lineColour = Turquoise)
  val c4Text = c4.cen.textArrow("C4 Centre", 135.degs, 150, Turquoise)
  val e1St = Pt2(400, 0)
  val e1StArrow = e1St.textArrow("E1 Start, positive or anti clockwise arc", Ang30, 25, Red)
  val e1Cen = Pt2(200, 0)
  val e1 = EArclign.pos(e1St, e1Cen, 200, 100, c1End).draw(lineColour = Red)
  val e2StArrow = e1St.textArrow("E2 Start, negative or clockwise arc", Ang330, 25, Green)
  val e2 = EArclign.neg(e1St, e1Cen, 200, 100, c1End).draw(lineColour = Green)

  val stuff = RArr(
    LSeg(0, 0, 160, 100).draw(),//This line starts at the centre of the screen and goes to point 160 right of centre and 100 up form centre.
    LSeg(0, 50, 150, 200).draw(lineWidth = 3),
    LSeg(50, -50, 200, -50).draw(2, Red),//Note if you don't include a Colour you get Black

    LinePath.dbls(0,-50, 50,-100, -25,-75, 200,-60).draw(2, Orange),

    Bezier(200,-350, -500,-300, -600,-300, -450,-200).draw(2, Green),
    c1, c2,
    c3d, c4d, e1, e2
  )

  override def output: GraphicElems = stuff ++ c1StText ++ c2StText ++ e1StArrow ++ e2StArrow ++ c4Text
}