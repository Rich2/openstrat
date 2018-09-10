/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** In geometry this is a line segment. But in this library a seg refers to shape segemnt with out its start (pt1) point */
case class Line2(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double) extends ProdD4 with Transable[Line2] with CurveLike
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
   def draw(lineWidth: Double, colour: Colour = Black): LineDraw = LineDraw(xStart, yStart, xEnd, yEnd, lineWidth, colour)
}

object Line2
{
   def apply(pStart: Vec2, pEnd: Vec2): Line2 = new Line2(pStart.x, pStart.y, pEnd.x, pEnd.y)
}

object LineSeg
{
   def apply(pEnd: Vec2): CurveSeg =  new CurveSeg(Double.NaN, 0, 0, 0, pEnd.x, pEnd.y)
   def apply(xEnd: Double, yEnd: Double): CurveSeg = new CurveSeg(Double.NaN, 0, 0, 0, xEnd, yEnd)
}

case class LineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, lineWidth: Double, colour: Colour) extends
PaintElem[LineDraw] with CurveLike
{
   override def fTrans(f: Vec2 => Vec2) = LineDraw(f(pStart), f(pEnd), lineWidth, colour)
   
}

object LineDraw
{
   def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double, colour: Colour = Black): LineDraw =
      new LineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour)
}

case class LinesDraw(lineSegs: Line2s, lineWidth: Double, linesColour: Colour = Black) extends PaintElem[LinesDraw]
{ override def fTrans(f: Vec2 => Vec2) = LinesDraw(lineSegs.fTrans(f), lineWidth, linesColour) }

object LinesDraw
{
   def apply(lineWidth: Double, linesColour: Colour, lineSegs: Line2 *): LinesDraw = LinesDraw(lineSegs.valueProducts[Line2s], lineWidth, linesColour)     
}

