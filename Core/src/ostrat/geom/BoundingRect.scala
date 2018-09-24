/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** An intermediate class */
case class BoundingRect(minX: Double, maxX: Double, minY: Double, maxY: Double)
{
   def tL = Vec2(minX, maxY)
   def tR = Vec2(maxX, maxY)
   def bL = Vec2(minX, minY)
   def bR = Vec2(maxX, minY)
   def width = maxX - minX
   def height = maxY - minY
   def cen = Vec2((minX + maxX) / 2, (minY + maxY) /2)
   def toPolygon: Polygon = Polygon(minX vv maxY, maxX vv maxY, maxX vv minY, minX vv minY)
   def ptInside(pt: Vec2): Boolean = maxX > pt.x & pt.x > minX & maxY > pt.y & pt.y > minY
   //def toRectangle: Polygon = Polygon(minX vv maxY, maxX vv maxY, maxX vv minY, minX vv minY)
}
