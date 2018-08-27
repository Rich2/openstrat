/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** So there is a lack of clarity over whether the segs are relative to the cen, and if the cen is needed at all. */
case class Shape(cen: Vec2, segs: List[CurveSeg]) extends Transable[Shape]
{
   /** This may need clarification */
   override def fTrans(f: Vec2 => Vec2): Shape = Shape(f(cen), segs)//.fTrans(f))
   def subjAll(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = TextCen): ShapeSubj =
         ShapeSubj(cen, segs, evObj, List(ShapeFillDraw(segs, fillColour, lineWidth, lineColour),
               TextGraphic(cen, str, textSize, lineColour, textAlign)))
            
   def fixed(evObj: AnyRef, elems: List[PaintElem[_]]): NoScaleShape = NoScaleShape(cen, segs, evObj, elems)
   def fillDrawFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Colour.Black): NoScaleShape =
      NoScaleShape(cen, segs, evObj, List(ShapeFillDraw(segs, fillColour, lineWidth, lineColour)))
   def allFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = TextCen): NoScaleShape =
      NoScaleShape(cen, segs, evObj, List(
            ShapeFillDraw(segs, fillColour, lineWidth, lineColour),
            TextGraphic(Vec2Z, str, textSize, lineColour, textAlign)))   
   def fillFixed(evObj: AnyRef, fillColour: Colour): NoScaleShape = NoScaleShape(cen, segs, evObj, List(ShapeFill(segs, fillColour)))   
}
