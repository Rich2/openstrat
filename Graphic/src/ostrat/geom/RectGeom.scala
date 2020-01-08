/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** This trait may need new name and modification */
trait RectGeom
{   
   var backColour: Colour = Colour.White   
   def width: Double 
   def height: Double
   def left = - width / 2
   def right = width / 2
   def top = height / 2
   def bottom = - height / 2
   def panelCen: Vec2 = Vec2(0, 0)
   def topLeft: Vec2 = Vec2(left, top)
   def topRight: Vec2 = Vec2(right, top)
   def bottomRight: Vec2 = Vec2(right, bottom)
   def bottomLeft: Vec2 = Vec2(left, bottom)
   def cenLeft = Vec2(left, 0)
   def crossHairs(lineWidth: Double = 1, lineColour: Colour = Black): LinesDraw =
     Line2s.doubles(left, 0, right, 0,
        0, top, 0, bottom).draw(lineWidth, lineColour)

   /** Not sure why spacing has got a minus sign */
   def gridLines(spacing: Double = 100, colour: Colour = Black, lineWidth: Double = 1.0): LinesDraw =
   {
      val xl: List[Double] = doubleFromToOld(-spacing, left, - spacing) ::: 0.0.fromToOld(right, spacing)
      val xlc: Line2s = xl.pMap(x => new Line2(x, bottom, x, top))
      val yl: List[Double] = doubleFromToOld(-spacing, bottom, - spacing) ::: 0.0.fromToOld(top, spacing)
      val ylc: Line2s = yl.pMap(y => new Line2(left, y, right, y))
      LinesDraw(xlc ++ ylc, lineWidth, colour)
   }
   /** Badly named I think, not sure why spacing has got a minus sign */  
   def gridLines2Colours(spacing: Double = 100, cenColour: Colour = Colour.DarkRed, otherColour: Colour = Black, lineWidth: Double = 1.0):
      Seq[LinesDraw] =
   {
      val xl = doubleFromToOld(-spacing, left, - spacing) ::: spacing.fromToOld(right, spacing)
      val xlc: Line2s = xl.pMap(x => new  Line2(x, bottom, x, top))
      val yl = doubleFromToOld(-spacing, bottom, - spacing) ::: spacing.fromToOld(top, spacing)
      val ylc: Line2s = yl.pMap(y => new Line2(left, y, right, y))
      Seq(LinesDraw(xlc ++ ylc, lineWidth, otherColour), crossHairs(1, cenColour))
   }
}