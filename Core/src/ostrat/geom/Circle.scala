/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object Circle
{   
   def apply(scale: Double, cen: Vec2 = Vec2Z): Shape = Shape(cen, segs(scale).slate(cen))
   def apply(scale: Double, xCen: Double, yCen: Double): Shape = apply(scale, Vec2(xCen, yCen)) 
   def segs(scale: Double = 1.0): List[CurveSeg] = 
   {
      val a = ArcSeg(Vec2Z, Vec2(0.5 * scale, 0))
      (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2 * i)))).toList      
   }

   def fill(radius: Double, colour: Colour, posn: Vec2 = Vec2Z): ShapeFill =
   {
      val fSegs = segs(radius).slate(posn)            
      ShapeFill(fSegs, colour)
   }
   
   def fillSubj(radius: Double, evObj: AnyRef, colour: Colour, xCen: Double, yCen: Double): ShapeSubj = fillSubj(radius, evObj, colour, Vec2(xCen, yCen)) 
   def fillSubj(radius: Double, evObj: AnyRef, colour: Colour, cen: Vec2 = Vec2Z): ShapeSubj = ShapeSubj.fill(cen, segs(radius).slate(cen), evObj, colour)
} 