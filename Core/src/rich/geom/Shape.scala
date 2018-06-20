/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
import pDisp._

/** Not sure entirely what's going on with this class */
case class Shape(cen: Vec2, segs: Seq[ShapeSeg]) extends Transable[Shape]
{
   /** This may need clarification */
   override def fTrans(f: Vec2 => Vec2): Shape = Shape(f(cen), segs)//.fTrans(f))
   def subjAll(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = TextCen): ShapeSubj =
         ShapeSubj(cen, segs, evObj, Seq(FillDrawShape(segs, fillColour, lineWidth, lineColour),
               FillText(Vec2Z, str, textSize, lineColour, textAlign)))
            
   def fixed(evObj: AnyRef, elems: Seq[CanvEl[_]]): FixedShape = FixedShape(cen, segs, evObj, elems)
   def fillDrawFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Colour.Black): FixedShape =
      FixedShape(cen, segs, evObj, Seq(FillDrawShape(segs, fillColour, lineWidth, lineColour)))
   def allFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = TextCen): FixedShape =
      FixedShape(cen, segs, evObj, Seq(
            FillDrawShape(segs, fillColour, lineWidth, lineColour),
            FillText(Vec2Z, str, textSize, lineColour, textAlign)))   
   def fillFixed(evObj: AnyRef, fillColour: Colour): FixedShape = FixedShape(cen, segs, evObj, Seq(FillShape(segs, fillColour)))   
}

object Circle// extends Shape
{
   
   def apply(scale: Double = 1.0): Shape = Shape(Vec2Z, segs(scale)) 
   def segs(scale: Double = 1.0): Seq[ShapeSeg] = 
   {
      val a = ArcSeg(Vec2(0.5 * scale, 0), Vec2Z)
      (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2 * i)))).toSeq      
   }
//   def apply(r: Double): Seq[ShapeSeg] = 
//   {
//      val a = ArcSeg(Vec2(r, r), Vec2(r, 0), Vec2.zero)
//      (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2) * i))).toSeq      
//   }
   def fill(posn: Vec2, r: Double, colour: Colour): FillShape =
   {
      val fSegs = segs(r).slate(posn)
            //val arcs: Seq[ShapeSeg] = (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2) * i).slate(posn))).toSeq
      FillShape(fSegs, colour)
   }  
}

object Hexagon
{
   //def apply
}