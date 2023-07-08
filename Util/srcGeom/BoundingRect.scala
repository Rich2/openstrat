/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

sealed trait BoundingOpt
{ def || (operand: BoundingOpt): BoundingOpt
}

/** An intermediate class for describing the vertical / horrisontal bounding rectangle for a Polygon or Shape. Defined by 4 Double values. */
case class BoundingRect(minX: Double, maxX: Double, minY: Double, maxY: Double) extends BoundingOpt
{ def topLeft = Pt2(minX, maxY)
  def topRight = Pt2(maxX, maxY)
  def bottomLeft = Pt2(minX, minY)
  def bottomRight = Pt2(maxX, minY)
  def width: Double = maxX - minX
  def height: Double = maxY - minY
  def xCen: Double = (minX + maxX) / 2
  def yCen: Double = (minY + maxY) / 2
  def cen: Pt2 = Pt2((minX + maxX) / 2, (minY + maxY) /2)
  def cenVec: Vec2 = Vec2((minX + maxX) / 2, (minY + maxY) /2)
  def toPolygon: Polygon = PolygonGen(minX pp maxY, maxX pp maxY, maxX pp minY, minX pp minY)
  def ptInside(pt: Pt2): Boolean = maxX > pt.x & pt.x > minX & maxY > pt.y & pt.y > minY
  def toRectangle: Polygon = PolygonGen(minX pp maxY, maxX pp maxY, maxX pp minY, minX pp minY)
  
  override def || (operand: BoundingOpt): BoundingRect = operand match
  { case br2: BoundingRect => BoundingRect (minX.min (br2.minX), maxX.max (br2.maxX), minY.min (br2.minY), maxY.max (br2.maxY) )
    case _ => this
  }
}

object BoundingNone extends BoundingOpt
{
  override def || (operand: BoundingOpt): BoundingOpt = operand match
  { case br: BoundingRect => br
    case _ => BoundingNone
  }
}