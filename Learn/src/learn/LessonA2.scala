package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA2(canv: CanvasPlatform) extends CanvasSimple("Lesson A2")
{
  // everything on a line onwards from // is ignored by the compiler (which turns code into something a machine can run)
  // they are useful amongst other things for humans to remind themselves what is intended in their coding and give hints to others trying to understand
  // ??comment for repaints?
  repaints(
    //Below we create a Text Graphic Object and we customise it by using arguments (parameters that are separated with commas)
    // the first argument is the text to be displayed, the second is an integer number describing the font size to use
    TextGraphic("This text is in the centre of the frame.", 18),

    //Here we create another Text Graphic Object as before with a third argument which specifies where to place the
    // Text Graphic on the screen, using a point (Vec2 = 2 dimensional vector) from the centre of the screen
    // the point we use here: 0 vv 300 means 0 pixels to the right and 300 pixels up from the center of the screen
    // the vv separates the values for vectors in the same way commas separates arguments
    TextGraphic("This text is 300 pixels up.", 18, 0 vv 300, Red),

    //In this one the the - (minus) signifies the reverse direction, so here the Text Graphic Object is placed 200 pixels down from
    // the center of the screen
    TextGraphic("This text is 400 pixels right, down 200", 25, 400 vv -200),
    //TextGraphic("This text can't be seen till you uncomment it and rebuild the programme.", 18, 100 vv -125, Violet)
  )
}

//the signature of *** (names of the arguments separated with commas) for TextGraphic(str, fontsize, posn, colour, align, zOrder)
// the type of the argument follows..   name: type
// arguments can have defaults for when they are omitted from the code name: type = default
//NB some of these comments will be moved to later lessons
//i am exploring the pitch/level and terminology...

