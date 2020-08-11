/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import collection.mutable.ArrayBuffer

/** Compact immutable Array[Double] based collection class for [[Sline]]s. Sline is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class Slines(val arrayUnsafe: Array[Double]) extends ArrProdDbl4[Sline] with AffinePreserve
{ type ThisT = Slines
  def unsafeFromArray(array: Array[Double]): Slines = new Slines(array)
  override def typeStr: String = "Line2s"
  override def fElemStr: Sline => String = _.str
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): Sline = new Sline(d1, d2, d3, d4)
  override def fTrans(f: Vec2 => Vec2): Slines = pMap(orig => Sline(f(orig.pStart), f(orig.pEnd)))

  override def canEqual(that: Any): Boolean = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  def ptInPolygon(pt: Vec2): Boolean =
  { val num = foldLeft(0)((acc, line) => acc + ife(line.rayIntersection(pt), 1, 0))
    num.isOdd
  }

  def draw(lineWidth: Double, colour: Colour = Colour.Black): LinesDraw = LinesDraw(this, lineWidth, colour)
}

/** Companion object for the Line2s class. */
object Slines extends ProdDbl4sCompanion[Sline, Slines]
{
  implicit val factory: Int => Slines = i => new Slines(new Array[Double](i * 4))

  implicit val persistImplicit: ArrProdDbl4Persist[Sline, Slines] = new ArrProdDbl4Persist[Sline, Slines]("Line2s")
  { override def fromArray(value: Array[Double]): Slines = new Slines(value)
  }

  implicit val arrArrBuildImplicit: ArrFlatBuild[Slines] = Sline.line2sBuildImplicit

  implicit val transImplicit: AffineTrans[Slines] = (obj, f) => obj.map(_.fTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class Line2sBuff(val buffer: ArrayBuffer[Double]) extends AnyVal with BuffProdDbl4[Sline]
{ override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): Sline = new Sline(d1, d2, d3, d4)
}