/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

sealed trait BoundingOpt
{
  def || (operand: BoundingOpt): BoundingOpt = (this, operand) match
  { case (br1: BoundingRect, br2: BoundingRect) => br1 || br2
    case (br1: BoundingRect, _) => br1
    case (_, br2: BoundingRect) => br2
    case _ => BoundingNone
  }
}

/** An intermediate class for describing the vertical / horrisontal bounding rectangle for a Polygon or Shape. Defined by 4 Double values. */
case class BoundingRect(minX: Double, maxX: Double, minY: Double, maxY: Double) extends BoundingOpt
{ def topLeft = Vec2(minX, maxY)
  def topRight = Vec2(maxX, maxY)
  def bottomLeft = Vec2(minX, minY)
  def bottomRight = Vec2(maxX, minY)
  def width = maxX - minX
  def height = maxY - minY
  def xCen: Double = (minX + maxX) / 2
  def yCen: Double = (minY + maxY) / 2
  def cen = Vec2((minX + maxX) / 2, (minY + maxY) /2)
  def toPolygon: Polygon = PolygonImp(minX vv maxY, maxX vv maxY, maxX vv minY, minX vv minY)
  def ptInside(pt: Vec2): Boolean = maxX > pt.x & pt.x > minX & maxY > pt.y & pt.y > minY
  def toRectangle: Polygon = PolygonImp(minX vv maxY, maxX vv maxY, maxX vv minY, minX vv minY)
  
  def || (operand: BoundingRect): BoundingRect =
    BoundingRect(minX.min(operand.minX), maxX.max(operand.maxX), minY.min(operand.minY), maxY.max(operand.maxY))
}

object BoundingNone extends BoundingOpt