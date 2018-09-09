/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A CurveSeg can  be a line segment or an arc segment or a bezier segment. It takes its start point from the pEnd of the
 *   previous segment. */
class CurveSeg(xC1: Double, xC2: Double, xUses: Double, yUses: Double, xEnd: Double, yEnd: Double) extends ProdD6 with Transable[CurveSeg]// with CurveSegLike
{
   /** Sometimes traits without methods cause problems */
   def silly: String = "Silly!"     
}

object CurveSeg
{
   
   
   implicit class CurveSegSeqImplicit(thisSeq: Seq[CurveSeg])
   {
      def curveSegs: CurveSegs = CurveSegs.make(thisSeq)
   }
   
   implicit class ImpVec2Traversible[Repr](travLike: collection.TraversableLike[CurveSeg, Repr])
   {
      /** Not sure if this method should be a member of Transable */
      def boundingRect =
      {
         //val t = Arc()
         var minX, maxX, minY, maxY = 0.0
         var i = 0
         for (ss <- travLike)
         {
            val v = ss.pEnd
            if (i == 0)
            {
               minX = v.x
               maxX = v.x
               minY = v.y
               maxY = v.y
            }
            else
            {
               minX = minX.min(v.x)
               maxX = maxX.max(v.x)
               minY = minY.min(v.y)
               maxY = maxY.max(v.y)
            }
            i += 1
         }
         if (i == 0) throw new Exception("boundingRect method called on empty Vec2 collection") else {}
         BoundingRect(minX, maxX, minY, maxY)               
      }
      def ptInShape: Vec2 => Boolean = pt =>
         {
            val vs: List[Vec2] = travLike.toList.map(_.pEnd)
            vs.toProdD2[Vec2, Vec2s].ptInPolygon(pt)            
         }
   }   
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

