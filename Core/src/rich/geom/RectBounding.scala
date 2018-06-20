/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
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
   def toVec2s: Vec2s = Vec2s.xy(minX, minY, minX, maxY, maxX, maxY, maxX, maxY)
   def ptInside(pt: Vec2): Boolean = maxX > pt.x & pt.x > minX & maxY > pt.y & pt.y > minY
}
