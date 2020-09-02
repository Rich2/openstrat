/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA2(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A2")
{
  // everything on a line onwards from // is ignored by the compiler (which turns code into something a machine can run)
  // they are useful amongst other things for humans to remind themselves what is intended in their coding and give hints to others trying to understand
  // ??comment for repaints?
  //val sq0 = Square(100, Vec2Z, 0.degs).fill(Violet)
 // val sq1 = Square(100, -100, 200, 0.degs).fill(Red)
 // debvar(sq.shape.cen)
  //debvar(sq.shape.v2)
  //debvar(sq.v3)
  val sq2 = SquareGen(100, -100, 100, 0.degs)
  val sq3 = SquareGen(100, -200, 100, 20.degs)
 // debvar(sq0)

  repaints(
    //Below we create a Text Graphic Object and we customise it by using arguments (parameters that are separated with commas)
    // the first argument is the text to be displayed, the second is an integer number describing the font size to use
    TextGraphic("0, 0", 18),

    //Here we create another Text Graphic Object as before with a third argument which specifies where to place the
    // Text Graphic on the screen, using a point (Vec2 = 2 dimensional vector) from the centre of the screen
    // the point we use here: 0 vv 300 means 0 pixels to the right and 300 pixels up from the center of the screen
    // the vv separates the values for vectors in the same way commas separates arguments
    TextGraphic("This text is 300 pixels up.", 18, 0 vv 300, Red, CenAlign),

    //In this one the the - (minus) signifies the reverse direction, so here the Text Graphic Object is placed 200 pixels down from
    // the center of the screen
    TextGraphic("This text is 400 pixels right, down 200", 25, 400 vv -200),
    //TextGraphic("This text can't be seen till you uncomment it and rebuild the programme.", 18, 100 vv -125, Violet)

    //Here we try different baseline (vertical alignment)

    TextGraphic("-300, 0", 14, -300 vv 0, Orange),
    TextGraphic("-300, 100", 14, -300 vv 100, Violet),
    TextGraphic("x=0, y=100", 14, 0 vv 100, Green),
    TextGraphic("222, 100", 14, 222 vv 100, SlateBlue),
    Triangle.fill(-100 vv 0, 0 vv -200, -300 vv -400, Violet),
    Rectangle(200, 100, 100 vv 50).fillOld(Green),
   // sq0,
   // sq1,
   // sq2.fill(Pink),
   // sq3.fill(Colour.DarkMagenta),
    TextGraphic("s1", 24, -100 vv 200)
  )
}

//the signature of *** (names of the arguments separated with commas) for TextGraphic(str, fontsize, posn, colour, align, zOrder)
// the type of the argument follows..   name: type
// arguments can have defaults for when they are omitted from the code name: type = default
//NB some of these comments will be moved to later lessons
//i am exploring the pitch/level and terminology...

