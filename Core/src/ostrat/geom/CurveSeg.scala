/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** A CurveSeg can  be a line segment or an arc segment or a bezier segment. It takes its start point from the pEnd of the
 *   previous segment. */
trait CurveSeg extends Transable[CurveSeg] with CurveSegLike
{
   /** Sometimes traits without methods cause problems */
   def silly: String = "Silly!"     
}

object CurveSeg
{
   implicit class ImplicitCurveSegList(thisList: List[CurveSeg])// extends Transable[List[CurveSeg]]
   {
      def fill(colour: Colour): ShapeFill = ShapeFill(thisList, colour)
      def draw(lineWidth: Double, lineColour: Colour = Black) = ShapeDraw(thisList,lineWidth, lineColour)
      def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) = ShapeFillDraw(thisList, fillColour, lineWidth, lineColour)
      def fillDrawClick(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): List[GraphicElem[_]] = List(
          ShapeFillDraw(thisList, fillColour, lineWidth, lineColour),
          ClickShape(thisList, evObj))
      def fillSlateable(colour: Colour, evObj: AnyRef, posn: Vec2 = Vec2Z): NoScaleShape =
         NoScaleShape(posn, thisList.toList, evObj, List(ShapeFill(thisList.toList, colour)))      
      def fillScale(colour: Colour, factor: Double): ShapeFill = ShapeFill(thisList.scale(factor), colour)
      def fillScaleSlate(colour: Colour, factor: Double, offset: Vec2): ShapeFill = ShapeFill(thisList.scale(factor).slate(offset), colour)
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

case class LineSeg(xEnd: Double, yEnd: Double) extends CurveSeg
{
   override def fTrans(f: Vec2 => Vec2): CurveSeg = LineSeg(f(pEnd))
}
object LineSeg
{
   def apply(pEnd: Vec2): LineSeg = new LineSeg(pEnd.x, pEnd.y)
}
