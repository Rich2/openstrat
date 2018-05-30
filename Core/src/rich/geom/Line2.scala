/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom

/** In geometry this is a line segment */
case class Line2(x1: Double, y1: Double, val x2: Double, y2: Double) extends ProdD4 with Transable[Line2]// with LineOrPt
{
   //override def canEqual(other: Any): Boolean = isInstanceOf[Line2]
   override def _1 = x1
   override def _2 = y1
   override def _3 = x2
   override def _4 = y2
   def pt1: Vec2 = Vec2(x1, y1)
   def pt2: Vec2 = Vec2(x2, y2)
   def func4Dou[T](f: (Double, Double, Double, Double) => T): T = f(x1, y1, x2, y2) 
   def fTrans(f: Vec2 => Vec2): Line2 = Line2(f(pt1), f(pt2))
   def shortArray: Array[Short] = Array(x1.toShort, y1.toShort,x2.toShort,y2.toShort)
   def toLatLongLine(f: Vec2 => LatLong): LatLongLine = LatLongLine(f(pt1), f(pt2))
   
   def rayIntersection(pt: Vec2): Boolean = //Checks whether a forward horizontal ray crosses this polygon side, yes, yes I know this is horrible code.
      if ( 
          //Check if point is above or below the polygon side
         ((pt.y > y1) && (pt.y > y2)) || //above pnt1 and pnt2
         ((pt.y < y1) && (pt.y < y2)) //below pnt1 and pnt 2
      ) false
      else 
      {
         val deltaY = y2 - y1
         if (0.000001 > deltaY.abs) false //if the polygon side is close to horizontal the
// point is close enough to the perimeter of the polygon that the point can measured as outside
         else
         {
            val ptDeltaY = pt.y - y1
            val deltaX = x2 - x1
            val lineX = x1 + (deltaX * ptDeltaY / deltaY)
            pt.x > lineX
         }
      }      
}

object Line2
{
   def apply(c1: Vec2, c2: Vec2): Line2 = new Line2(c1.x, c1.y, c2.x, c2.y)
 
//   implicit class Line2SeqImplicit(thisSeq: Seq[Line2])
//   {
//      def polyLineDraw(lineWidth: Double, linesColour: Colour = Colour.Black) = PolyLineDraw(thisSeq, lineWidth, linesColour)
//   }
}

class Line2s(length: Int) extends DoubleProduct4s[Line2](length) with Transable[Line2s]
{
   override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): Line2 = new Line2(d1, d2, d3, d4)
   override def fTrans(f: Vec2 => Vec2): Line2s = pMap(orig => Line2(f(orig.pt1), f(orig.pt2)))
   def ptInPolygon(pt: Vec2): Boolean =
    {
      val num = foldLeft(0)((acc, line) => acc + ife(line.rayIntersection(pt), 1, 0))
      num.isOdd      
    }
}

object Line2s
{
   implicit val factory: Int => Line2s = i => new Line2s(i)
}

