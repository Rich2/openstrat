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
   
}

object CurveSeg
{
   
   
//   implicit class CurveSegSeqImplicit(thisSeq: Seq[CurveSeg])
//   {
//      def curveSegs: CurveSegs = CurveSegs.make(thisSeq)
//   }
   
   
      
}

//case class BezierSeg(xC1: Double, val yC1: Double, val xC2: Double, val yC2: Double, val xEnd: Double, val yEnd: Double) extends CurveSeg with
//   BezierSegLike
//{
//   override def fTrans(f: Vec2 => Vec2) = BezierSeg(f(pC1), f(pC2), f(pEnd))
//}
//object BezierSeg
//{
//   def apply(pC1: Vec2, pC2: Vec2, pEnd: Vec2): BezierSeg = new BezierSeg(pC1.x, pC1.y, pC2.x, pC2.y, pEnd.x, pEnd.y)
//}
//
///** Takes its start point from the previous segment */
//case class ArcSeg(xCen: Double, yCen: Double, xEnd: Double, yEnd: Double) extends CurveSeg
//{
//   def pCen: Vec2 = Vec2(xCen, yCen)
//   def fTrans(f: Vec2 => Vec2): CurveSeg = ArcSeg(f(pCen),f(pEnd))
//   def startAngle(pStart: Vec2): Angle = (pStart - pCen).angle
//   def endAngle: Angle = (pEnd - pCen).angle
//   def radius: Double = (pEnd - pCen).magnitude
//   def controlPt(startPt: Vec2): Vec2 = 
//   {
//      val sAng: Angle = startAngle(startPt)      
//      val resultAngle = sAng.bisect(endAngle)
//      val alphaAngle =  sAng.angleTo(endAngle) / 2      
//      pCen + resultAngle.toVec2 * radius / alphaAngle.cos
//   }
//   
//   /** Calculates ControlPt and then passes controlPt.x, controlPt.y, XENd, yEnd, radius to f */
//   def fControlEndRadius(startPt: Vec2, f: (Double, Double, Double, Double, Double) => Unit): Unit =
//   {
//      val cp = controlPt(startPt)
//      f(cp.x, cp.y, pEnd.x, pEnd.y, radius)
//   }
//}

//case class LineSeg(xEnd: Double, yEnd: Double) extends CurveSeg
//{
//   override def fTrans(f: Vec2 => Vec2): CurveSeg = LineSeg(f(pEnd))
//}

