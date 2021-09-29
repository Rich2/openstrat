/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** So there is a lack of clarity over whether the segs are relative to the cen, and if the cen is needed at all. */
case class PolyCurveCentred(cen: Pt2, segs: ShapeGenOld) extends AffinePreserve
{ override type ThisT = PolyCurveCentred
   /** This may need clarification */
   override def ptsTrans(f: Pt2 => Pt2): PolyCurveCentred = PolyCurveCentred(f(cen), segs)//.fTrans(f))
  
  def parentAll(evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour, textSize: Int, str: String,
                   textAlign: TextAlign = CenAlign): PolyCurveParentFull =
    PolyCurveParentFull(cen, segs, evObj, Arr(PolyCurveFillDraw(segs, fillColour, lineColour, lineWidth), TextGraphic(str, textSize, cen, lineColour, textAlign)))

  def allElems(evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour, fontSize: Int, str: String, textAlign: TextAlign = CenAlign):
    PolyCurveAllOld = PolyCurveAllOld(segs, evObj, str, fillColour, fontSize, lineColour, lineWidth)
}