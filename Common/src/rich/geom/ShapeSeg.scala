/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
import Colour.Black

trait ShapeSeg extends Transable[ShapeSeg]
{
   def endPt: Vec2   
}

object ShapeSeg
{
   implicit class ImplicitShapeSegSeq(thisSeq: Seq[ShapeSeg])// extends Transable[Seq[ShapeSeg]]
   {
      def fill(colour: Colour): FillShape = FillShape(thisSeq, colour)
      def draw(lineWidth: Double, lineColour: Colour = Black) = DrawShape(thisSeq,lineWidth, lineColour)
      def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) = FillDrawShape(thisSeq, fillColour, lineWidth, lineColour)
      def fillDrawClick(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): Seq[CanvObj[_]] = Seq(
          FillDrawShape(thisSeq, fillColour, lineWidth, lineColour),
          ClickShape(thisSeq, evObj))
      def fillSlateable(colour: Colour, evObj: AnyRef, posn: Vec2 = Vec2Z): FixedShape =
         FixedShape(posn, thisSeq, evObj, Seq(FillShape(thisSeq, colour)))      
      def fillScale(colour: Colour, factor: Double): FillShape = FillShape(thisSeq.scale(factor), colour)
      def fillScaleSlate(colour: Colour, factor: Double, offset: Vec2): FillShape = FillShape(thisSeq.scale(factor).slate(offset), colour)
   }
   
   implicit class ImpVec2Traversible[Repr](travLike: collection.TraversableLike[ShapeSeg, Repr])
   {
      /** Not sure if this method should be a member of Transable */
      def boundingRect =
      {
         //val t = Arc()
         var minX, maxX, minY, maxY = 0.0
         var i = 0
         for (ss <- travLike)
         {
            val v = ss.endPt
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
      def ptInShape: Vec2 => Boolean =
         {
            val vs: Seq[Vec2] = travLike.toSeq.map(_.endPt)
            vs.toProdD2[Vec2, Vec2s].ptInPolygon
         }
   }   
}

case class LineSeg(endPt: Vec2) extends ShapeSeg
{
   override def fTrans(f: Vec2 => Vec2): ShapeSeg = LineSeg(f(endPt))
}
object LineSeg
{
   def apply(toX: Double, toY: Double): LineSeg = LineSeg(Vec2(toX, toY))
}

case class ArcSeg(endPt: Vec2, cenPt: Vec2) extends ShapeSeg
{
   def fTrans(f: Vec2 => Vec2): ShapeSeg = ArcSeg(f(endPt), f(cenPt))
   def startAngle(startPt: Vec2): Angle = (startPt - cenPt).angle
   def endAngle: Angle = (endPt - cenPt).angle
   def radius: Double = (endPt - cenPt).magnitude
   def controlPt(startPt: Vec2): Vec2 = 
   {
      val sAng: Angle = startAngle(startPt)      
      val resultAngle = sAng.bisect(endAngle)
      val alphaAngle =  sAng.angleTo(endAngle) / 2      
      cenPt + resultAngle.toVec2 * radius / alphaAngle.cos
   }
   def fArcTo(startPt: Vec2, f: (Double, Double, Double, Double, Double) => Unit): Unit =
   {
      val cp = controlPt(startPt)
      f(cp.x, cp.y, endPt.x, endPt.y, radius)
   }
}

object ArcSeg
{
   def apply(endX: Double, endY: Double, cenX: Double, cenY: Double): ArcSeg =
      new ArcSeg(Vec2(endX, endY), Vec2(cenX, cenY))
}

//case class Arc(startPt: Vec2, endPt: Vec2, cenPt: Vec2) extends Transable[Arc]
//{
//   def fTrans(f: Vec2 => Vec2): Arc = Arc(f(startPt), f(endPt), f(cenPt))
//}


