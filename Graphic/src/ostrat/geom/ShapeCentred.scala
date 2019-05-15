/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** So there is a lack of clarity over whether the segs are relative to the cen, and if the cen is needed at all. */
case class ShapeCentred(cen: Vec2, segs: Shape) extends Transer
{
   /** This may need clarification */
   override def fTrans(f: Vec2 => Vec2): ShapeCentred = ShapeCentred(f(cen), segs)//.fTrans(f))
   def subjAll(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = TextCen): ShapeSubj =
         ShapeSubj(cen, segs, evObj, List(ShapeFillDraw(segs, fillColour, lineWidth, lineColour),
               TextGraphic(str, textSize, cen, lineColour, textAlign)))
            
   def fixed(evObj: AnyRef, elems: List[PaintElem]): NoScaleShape = NoScaleShape(cen, segs, evObj, elems)
   def fillDrawFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Colour.Black): NoScaleShape =
      NoScaleShape(cen, segs, evObj, List(ShapeFillDraw(segs, fillColour, lineWidth, lineColour)))
   def allFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = TextCen): NoScaleShape =
      NoScaleShape(cen, segs, evObj, List(
            ShapeFillDraw(segs, fillColour, lineWidth, lineColour),
            TextGraphic(str, textSize, Vec2Z, lineColour, textAlign)))   
   def fillFixed(evObj: AnyRef, fillColour: Colour): NoScaleShape = NoScaleShape(cen, segs, evObj, List(ShapeFill(segs, fillColour)))   
}
