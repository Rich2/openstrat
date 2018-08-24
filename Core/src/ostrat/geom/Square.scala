/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object Square
{
   def apply(width: Double, cen: Vec2 = Vec2Z): Vec2s = apply(width, cen.x, cen.y)
   def apply(width: Double, xCen: Double, yCen: Double): Vec2s = Vec2s(
         xCen - width / 2 vv yCen + width / 2,
         xCen + width / 2 vv yCen + width / 2,
         xCen + width / 2 vv yCen - width / 2,
         xCen - width/2   vv yCen - width / 2)
         
   def fill(width: Double, colour: Colour, cen: Vec2 = Vec2Z) = apply(width, cen.x, cen.y).fill(colour)
   def fill(width: Double, colour: Colour, xCen: Double, yCen: Double) = apply(width, xCen, yCen).fill(colour) 
   
   def curvedSegs(width: Double, radius: Double): List[CurveSeg] =
   {
      val w = width / 2
      (0 to 3).toList.flatMap(i => List( LineSeg(w - radius, w), ArcSeg(w, w - radius, w - radius, w -radius)).rotateRadians(-i * math.Pi / 2))
   }
}