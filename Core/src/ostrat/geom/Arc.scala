/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

case class Arc(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, xCen: Double, yCen: Double) extends Curve// with DoublesTrCompound[Vec2]
{
   def pCen: Vec2 = Vec2(xCen, yCen)
   //def doublesTrSeq = Seq(startPt, endPt, cenPt)
   def persistName = "Arc"
   override def toString = ???// namedStr2
   def fTrans(f: Vec2 => Vec2): Arc = Arc(f(pStart), f(pEnd), f(pCen))
   def radius: Double = (pEnd - pCen).magnitude
   def startAngle: Angle = (pStart - pCen).angle
   def endAngle: Angle = (pEnd - pCen).angle
   def deltaAngle: Angle = startAngle.angleTo(endAngle)
   def controlPt: Vec2 = 
   {
      val sAng: Angle = startAngle     
      val resultAngle = sAng.bisect(endAngle)
      val alphaAngle =  sAng.angleTo(endAngle) / 2      
      pCen + resultAngle.toVec2 * radius / alphaAngle.cos
   }
   
   def fArcTo(f: (Double, Double, Double, Double, Double) => Unit): Unit =
   {
      val cp = controlPt
      f(cp.x, cp.y, xEnd, yEnd, radius)
   }
}

object Arc
{
   def apply(pStart: Vec2, pEnd: Vec2, pCen: Vec2): Arc =  new Arc(pStart.x, pStart.y, pEnd.x, pEnd.y, pCen.x, pCen.y)
}

/** Takes its start point from the previous segment */
case class ArcSeg(xEnd: Double, yEnd: Double, xCen: Double, yCen: Double) extends CurveSeg
{
   def pCen: Vec2 = Vec2(xCen, yCen)
   def fTrans(f: Vec2 => Vec2): CurveSeg = ArcSeg(f(pEnd), f(pCen))
   def startAngle(pStart: Vec2): Angle = (pStart - pCen).angle
   def endAngle: Angle = (pEnd - pCen).angle
   def radius: Double = (pEnd - pCen).magnitude
   def controlPt(startPt: Vec2): Vec2 = 
   {
      val sAng: Angle = startAngle(startPt)      
      val resultAngle = sAng.bisect(endAngle)
      val alphaAngle =  sAng.angleTo(endAngle) / 2      
      pCen + resultAngle.toVec2 * radius / alphaAngle.cos
   }
   def fArcTo(startPt: Vec2, f: (Double, Double, Double, Double, Double) => Unit): Unit =
   {
      val cp = controlPt(startPt)
      f(cp.x, cp.y, pEnd.x, pEnd.y, radius)
   }
}

object ArcSeg
{
   def apply(pEnd: Vec2, pCen: Vec2): ArcSeg = new ArcSeg(pEnd.x, pEnd.y, pCen.x, pCen.y)
}