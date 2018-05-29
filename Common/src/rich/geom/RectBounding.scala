/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom

case class BoundingRect(minX: Double, maxX: Double, minY: Double, maxY: Double)
{
   def tL = Vec2(minX, maxY)
   def tR = Vec2(maxX, maxY)
   def bL = Vec2(minX, minY)
   def bR = Vec2(maxX, minY)
   def width = maxX - minX
   def height = maxY - minY
   def cen = Vec2((minX + maxX) / 2, (minY + maxY) /2)
}
