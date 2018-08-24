/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** In geometry this is a line segment. But in this library a seg refers to shape segemnt with out its start (pt1) point */
case class Line2(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double) extends ProdD4 with Transable[Line2] with Curve
{
   //override def canEqual(other: Any): Boolean = isInstanceOf[Line2]
   override def _1 = xStart
   override def _2 = yStart
   override def _3 = xEnd
   override def _4 = yEnd   
   def func4Dou[T](f: (Double, Double, Double, Double) => T): T = f(xStart, yStart, xEnd, yEnd) 
   def fTrans(f: Vec2 => Vec2): Line2 = Line2(f(pStart), f(pEnd))
   def shortArray: Array[Short] = Array(xStart.toShort, yStart.toShort,xEnd.toShort,yEnd.toShort)
   def toLatLongLine(f: Vec2 => LatLong): LatLongLine = LatLongLine(f(pStart), f(pEnd))
   
   def rayIntersection(pt: Vec2): Boolean = //Checks whether a forward horizontal ray crosses this polygon side, yes, yes I know this is horrible code.
      if ( 
          //Check if point is above or below the polygon side
         ((pt.y > yStart) && (pt.y > yEnd)) || //above beg pt and end pt
         ((pt.y < yStart) && (pt.y < yEnd)) //below beg pt and end pt
      ) false
      else 
      {
         val deltaY = yEnd - yStart
         if (0.000001 > deltaY.abs) false //if the polygon side is close to horizontal the
// point is close enough to the perimeter of the polygon that the point can measured as outside
         else
         {
            val ptDeltaY: Double = pt.y - yStart
            val deltaX: Double = xEnd - xStart //Not entirely sure what's going on here
            val lineX: Double = xStart + (deltaX * ptDeltaY / deltaY)
            pt.x > lineX
         }
      }
   def lineAngle: Angle = (pEnd - pStart).angle
}

object Line2
{
   def apply(pStart: Vec2, pEnd: Vec2): Line2 = new Line2(pStart.x, pStart.y, pEnd.x, pEnd.y)
}

class Line2s(val arr: Array[Double]) extends AnyVal with DoubleProduct4s[Line2] with Transable[Line2s]
{
   override def typeName: Symbol = 'Line2s
   override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): Line2 = new Line2(d1, d2, d3, d4)
   override def fTrans(f: Vec2 => Vec2): Line2s = pMap(orig => Line2(f(orig.pStart), f(orig.pEnd)))
   def ptInPolygon(pt: Vec2): Boolean =
   {
      val num = foldLeft(0)((acc, line) => acc + ife(line.rayIntersection(pt), 1, 0))
      num.isOdd      
   }
   def ++ (operand: Line2s): Line2s =
   {
      val res = Line2s(length + operand.length)
      this.iForeach((elem, i) => res.setElem(i, elem))
      operand.iForeach((elem, i) => res.setElem(i + length, elem))
      res
   }
}

object Line2s extends Double4sMaker[Line2, Line2s]
{
   implicit val factory: Int => Line2s = i => new Line2s(new Array[Double](i * 4))
}

