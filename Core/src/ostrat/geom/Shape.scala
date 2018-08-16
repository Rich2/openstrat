/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pDisp._

/** Not sure entirely what's going on with this class */
case class Shape(cen: Vec2, segs: List[ShapeSeg]) extends Transable[Shape]
{
   /** This may need clarification */
   override def fTrans(f: Vec2 => Vec2): Shape = Shape(f(cen), segs)//.fTrans(f))
   def subjAll(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = TextCen): ShapeSubj =
         ShapeSubj(cen, segs, evObj, List(FillDrawShape(segs, fillColour, lineWidth, lineColour),
               FillText(Vec2Z, str, textSize, lineColour, textAlign)))
            
   def fixed(evObj: AnyRef, elems: List[CanvEl[_]]): NoScaleShape = NoScaleShape(cen, segs, evObj, elems)
   def fillDrawFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Colour.Black): NoScaleShape =
      NoScaleShape(cen, segs, evObj, List(FillDrawShape(segs, fillColour, lineWidth, lineColour)))
   def allFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = TextCen): NoScaleShape =
      NoScaleShape(cen, segs, evObj, List(
            FillDrawShape(segs, fillColour, lineWidth, lineColour),
            FillText(Vec2Z, str, textSize, lineColour, textAlign)))   
   def fillFixed(evObj: AnyRef, fillColour: Colour): NoScaleShape = NoScaleShape(cen, segs, evObj, List(FillShape(segs, fillColour)))   
}

object Circle// extends Shape
{
   
   def apply(scale: Double, cen: Vec2 = Vec2Z): Shape = Shape(cen, segs(scale).slate(cen))
   def apply(scale: Double, xCen: Double, yCen: Double): Shape = apply(scale, Vec2(xCen, yCen)) 
   def segs(scale: Double = 1.0): List[ShapeSeg] = 
   {
      val a = ArcSeg(Vec2(0.5 * scale, 0), Vec2Z)
      (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2 * i)))).toList      
   }

   def fill(radius: Double, colour: Colour, posn: Vec2 = Vec2Z): FillShape =
   {
      val fSegs = segs(radius).slate(posn)            
      FillShape(fSegs, colour)
   }
   
   def fillSubj(radius: Double, evObj: AnyRef, colour: Colour, xCen: Double, yCen: Double): ShapeSubj = fillSubj(radius, evObj, colour, Vec2(xCen, yCen)) 
   def fillSubj(radius: Double, evObj: AnyRef, colour: Colour, cen: Vec2 = Vec2Z): ShapeSubj = ShapeSubj.fill(cen, segs(radius).slate(cen), evObj, colour)
   
}

object Hexagon
{
   //def apply
}