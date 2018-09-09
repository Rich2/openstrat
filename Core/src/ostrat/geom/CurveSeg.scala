/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** The base trait for CurveSeg and Curve and their associated GraphicElems */
trait CurveEnding
{
   /** the x component of the end point */
   def xEnd: Double
   /** the y component of the end point */
   def yEnd: Double
   /** The end point. Often called p2 on a line or p4 on a cubic bezier. */
   final def pEnd: Vec2 = Vec2(xEnd, yEnd)  
}

/** A CurveSeg can  be a line segment or an arc segment or a bezier segment. It takes its start point from the pEnd of the
 *   previous segment. */
class CurveSeg(val xC1: Double, val yC1: Double, val xUses: Double, val yUses: Double, val xEnd: Double, val yEnd: Double) extends ProdD6 with
Transable[CurveSeg] with CurveEnding 
{
   override def canEqual(other: Any): Boolean = other.isInstanceOf[CurveSeg]
   @inline override def _1 = xC1
   @inline override def _2 = yC1
   @inline override def _3 = xUses
   @inline override def _4 = yUses
   @inline override def _5 = xEnd
   @inline override def _6 = yEnd
   
   /** This is control point 2 in a Bezier segment, the centre point in an arc segment and unused in a straight Line Segment */
   def pUses: Vec2 = Vec2(xUses, yUses)
   /** This is control point 1 in a Bezier segment, it not used an arc segment, but first Double set to NaN, it is not nused in a straight Line Segment
    *  but the first Double is set to Infinity */
   def pC1: Vec2 = Vec2(xC1, yC1)
      
   def fSeg[A](fLineSeg: Vec2 => A, fArcSeg: (Vec2, Vec2) => A, fBezierSeg: (Vec2, Vec2, Vec2) => A): A = xC1 match
   {
      case d if d.isNaN => fLineSeg(pEnd)
      case d if d.isInfinity => fArcSeg(pUses, pEnd)
      case d => fBezierSeg(pC1, pUses, pEnd)
   }
   
   def fSeg[A](fLineSeg: (Double, Double) => A,
         fArcSeg: (Double, Double, Double, Double) => A,
         fBezierSeg: (Double, Double, Double, Double, Double, Double) => A): A = xC1 match
   {
      case d if d.isNaN => fLineSeg(xEnd, yEnd)
      case d if d.isInfinity => fArcSeg(xUses, yUses, xEnd, yEnd)
      case d => fBezierSeg(xC1, yC1, xUses, yUses, xEnd, yEnd)
   }
   
   def segDo(fLineSeg: CurveSeg => Unit, fArcSeg: CurveSeg => Unit, fBezierSeg: CurveSeg => Unit): Unit =
   
   
   override def fTrans(f: Vec2 => Vec2): CurveSeg = xC1 match
   {
      case d if d.isNaN => { val newPEnd: Vec2 = f(pEnd); new CurveSeg(d, 0, 0, 0, newPEnd.x, newPEnd.y) }
      case d if d.isInfinity =>
         {
            val newPCen: Vec2 = f(pUses)
            val newPEnd: Vec2 = f(pEnd)
            new CurveSeg(d, 0, newPCen.x, newPCen.y, newPEnd.x, newPEnd.y)
         }
      case _ =>
         {
            val newPC1: Vec2 = f(pC1)
            val newPCen: Vec2 = f(pUses)
            val newPEnd: Vec2 = f(pEnd)
            new CurveSeg(newPC1.x, newPC1.y, newPCen.x, newPCen.y, newPEnd.x, newPEnd.y)
         }
   }
   /** Assuming this is Arc Segment */
   def arcCen: Vec2 = Vec2(xUses, yUses)
   /** Assuming this is Arc Segment */
   def arcStartAngle(pStart: Vec2): Angle = (pStart - arcCen).angle
   /** Assuming this is Arc Segment */
   def arcEndAngle: Angle = (pEnd - arcCen).angle   
   /** Assuming this is Arc Segment */
   def arcRadius: Double = (pEnd - arcCen).magnitude
   /** Assuming this is Arc Segment */
   def arcControlPt(startPt: Vec2): Vec2 = 
   {
      val sAng: Angle = arcStartAngle(startPt)      
      val resultAngle = sAng.bisect(arcEndAngle)
      val alphaAngle =  sAng.angleTo(arcEndAngle) / 2      
      arcCen + resultAngle.toVec2 * arcRadius / alphaAngle.cos
   }   
   /** Assuming this is ArcSeg, calculates ControlPt and then passes controlPt.x, controlPt.y, XENd, yEnd, radius to f */
   def fControlEndRadius(startPt: Vec2, f: (Double, Double, Double, Double, Double) => Unit): Unit =
   {
      val cp = arcControlPt(startPt)
      f(cp.x, cp.y, pEnd.x, pEnd.y, arcRadius)
   }   
}

object CurveSeg
{
        
}

//object BezierSeg
//{
//   def apply(pC1: Vec2, pC2: Vec2, pEnd: Vec2): BezierSeg = new BezierSeg(pC1.x, pC1.y, pC2.x, pC2.y, pEnd.x, pEnd.y)
//}
//




