/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait ShapeSegOpen[T <: ShapeSegOpen[T]] extends Transable[T]

trait ShapeOpen {
  
}

case class Arc(startPt: Vec2, endPt: Vec2, cenPt: Vec2) extends ShapeSegOpen[Arc]// with DoublesTrCompound[Vec2]
{
   def doublesTrSeq = Seq(startPt, endPt, cenPt)
   def persistName = "Arc"
   override def toString = ???// namedStr2
   def fTrans(f: Vec2 => Vec2): Arc = Arc(f(startPt), f(endPt), f(cenPt))
   def radius: Double = (endPt - cenPt).magnitude
   def startAngle: Angle = (startPt - cenPt).angle
   def endAngle: Angle = (endPt - cenPt).angle
   def deltaAngle: Angle = startAngle.angleTo(endAngle)
   def controlPt: Vec2 = 
   {
      val sAng: Angle = startAngle     
      val resultAngle = sAng.bisect(endAngle)
      val alphaAngle =  sAng.angleTo(endAngle) / 2      
      cenPt + resultAngle.toVec2 * radius / alphaAngle.cos
   }
   
   def fArcTo(f: (Double, Double, Double, Double, Double) => Unit): Unit =
   {
      val cp = controlPt
      f(cp.x, cp.y, endPt.x, endPt.y, radius)
   }
}

object Arc
{
   def apply(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, xCen: Double, yCen: Double): Arc =
      Arc(Vec2(xStart, yStart), Vec2(xEnd, yEnd), Vec2(xCen, yCen))
}