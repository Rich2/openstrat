/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** So there is a lack of clarity over whether the segs are relative to the cen, and if the cen is needed at all. */
case class PolyCurveCentred(cen: Vec2, segs: PolyCurve) extends TranserAll
{ override type ThisT = PolyCurveCentred
   /** This may need clarification */
   override def fTrans(f: Vec2 => Vec2): PolyCurveCentred = PolyCurveCentred(f(cen), segs)//.fTrans(f))
  
  def parentAll(evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour, textSize: Int, str: String,
                   textAlign: TextAlign = CenAlign): PolyCurveParentFull =
    PolyCurveParentFull(cen, segs, evObj, Arr(PolyCurveFillDraw(segs, fillColour, lineWidth, lineColour), TextGraphic(str, textSize, cen, lineColour, textAlign)))

  def allElems(evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour, fontSize: Int, str: String, textAlign: TextAlign = CenAlign):
    PolyCurveAll = PolyCurveAll(segs, evObj, str, fillColour, fontSize, lineWidth, lineColour)

  /* def fixed(evObj: Any, elems: Arr[PaintFullElem]): UnScaledShape = UnScaledShape(cen, segs, evObj, elems)
   def fillDrawFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Colour.Black): UnScaledShape =
      UnScaledShape(cen, segs, evObj, Arr(PolyCurveFillDraw(segs, fillColour, lineWidth, lineColour)))
   def allFixed(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour,
         textSize: Int, str: String, textAlign: TextAlign = CenAlign): UnScaledShape =
      UnScaledShape(cen, segs, evObj, Arr(
            PolyCurveFillDraw(segs, fillColour, lineWidth, lineColour),
            TextGraphic(str, textSize, Vec2Z, lineColour, textAlign)))   */
  // def fillFixed(evObj: AnyRef, fillColour: Colour): UnScaledShape = UnScaledShape(cen, segs, evObj, Arr(PolyCurveFill(segs, fillColour)))
}
