/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Array[Double based collection class for Line2s */
class Line2s(val array: Array[Double]) extends AnyVal with ProductD4s[Line2] with Transer
{ type ThisT = Line2s
  def unsafeFromArray(array: Array[Double]): Line2s = new Line2s(array)
  override def typeStr: String = "Line2s"
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): Line2 = new Line2(d1, d2, d3, d4)
  override def fTrans(f: Vec2 => Vec2): Line2s = pMap(orig => Line2(f(orig.pStart), f(orig.pEnd)))

  def ptInPolygon(pt: Vec2): Boolean =
  { val num = foldLeft(0)((acc, line) => acc + ife(line.rayIntersection(pt), 1, 0))
    num.isOdd
  }

  def draw(lineWidth: Double, colour: Colour): LinesDraw = LinesDraw(this, lineWidth, colour)
}

/** Companion object for the Lines class */
object Line2s extends ProductD4sCompanion[Line2, Line2s]
{
  implicit val factory: Int => Line2s = i => new Line2s(new Array[Double](i * 4))
   
  implicit val persistImplicit: ProductD4sBuilder[Line2, Line2s] = new ProductD4sBuilder[Line2, Line2s]("Line2s")
  { override def fromArray(value: Array[Double]): Line2s = new Line2s(value)
  }
}
