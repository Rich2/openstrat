package ostrat
package myPackage

import geom._
import pCanv._
import Colour._

case class Lesson1(canv: CanvasPlatform) extends pCanv.CanvasSimple
{   
   canv.lineDraw(Vec2Z, 100 vv 100)// 0 vv 0 is the same as Vec2Z
   canv.lineDraw(0 vv 50, 150 vv 200, 3)
   canv.lineDraw(50 vv -50, 200 vv -50, 2, Red)//Note if you don't include a Colour you get Black
   
   canv.arcDraw(-200 vv 0, Vec2Z, 0 vv 200)
   canv.arcDraw(-220 vv 0, Vec2Z, 0 vv 220, 4, Pink)
   canv.bezierDraw(200 vv -350, -500 vv -300, -600 vv -300, -450 vv -200, 2, Green)
   
   canv.polyFill(Orange, -300 vv 200, -300 vv 300, -250 vv 300)
   canv.polyDraw(2, Blue, -250 vv 300, -200 vv 325, -150 vv 300, -275 vv 200)
   
   canv.textGraphic(Vec2Z, "This text is centred on the centre of the canvas. The point from which postions are measured.", 18, Turquoise)
   canv.textGraphic(100 vv -100, "Text centred at 100, -100", 25)
   //canv.textGraphic(100 vv -200, "This text can't be seen till you uncomment it and rebuild the programme.", 18, Violet)
}

/* These lessons are intended to be accessible to people who haven't programmed before and have poor or no geometry knowledge.This is a comment. It
 *  doesn't do anything. Everything between the forward-slash star at the beginning of the comment and the star forward-slash at the end is a comment.
 *  Everything after two forward-slashes is also a comment. You can add and remove //s from the beginning of the commands, save-rebuild and the
 *  associated commands will appear / disappear from the screen.  */
 