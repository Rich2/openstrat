/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._

/** 2 dimensional vector using metres as units rather than pure scalar numbers. */
final class Dist2(val xMetres: Double, val yMetres: Double) extends ProdDbl2
{ override def toString: String = Dist2.PersistImplicit.show(this, 0)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Dist2]
  def x: Dist = Dist(xMetres)
  def y: Dist = Dist(yMetres)
  override def _1: Double = xMetres
  override def _2: Double = yMetres
  def + (op: Dist2): Dist2 = Dist2(x + op.x, y + op.y)
  def - (op: Dist2): Dist2 = Dist2(x - op.x, y - op.y)
  def addXY (otherX: Dist, otherY: Dist): Dist2 = Dist2(x + otherX, y + otherY)
  def subXY (otherX: Dist, otherY: Dist): Dist2 = Dist2(x - otherX, y - otherY)
  def addX(adj: Dist): Dist2 = Dist2(x + adj, y)
  def addY(adj: Dist): Dist2 = Dist2(x, y + adj)
  def subX(adj: Dist): Dist2 = Dist2(x - adj, y)
  def subY(adj: Dist): Dist2 = Dist2(x, y - adj)
  def * (operator: Double): Dist2 = Dist2(x * operator, y * operator)
  def / (operator: Double): Dist2 = Dist2(x / operator, y / operator)
  def magnitude: Dist = Dist(math.sqrt(xMetres.squared + yMetres.squared))

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: Dist2): Area = x * operand.x + y * operand.y

  def rotate(a: Angle): Dist2 =  Dist2.metres(x.metres * a.cos - y.metres * a.sin, x.metres * a.sin + y.metres * a.cos)

  def rotateRadians(r: Double): Dist2 =
  { val newX = xMetres * cos(r) - yMetres * sin(r)
    val newY =
    { val ya = xMetres * sin(r)
      val yb = yMetres * cos(r)
      ya + yb
    }
    Dist2.metres(newX, newY)
  }
   
  /** Currently not working for angles greater than Pi / 2 */
  def toLatLong: LatLong = LatLong.radians(math.asin(y / EarthPolarRadius), math.asin(x / EarthEquatorialRadius))
   
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
}

object Dist2
{ def metres(xMetres: Double, yMetres: Double): Dist2 = new Dist2(xMetres, yMetres)
  def apply(x: Dist, y: Dist): Dist2 = new Dist2(x.metres, y.metres)
  
  implicit class Dist2Implicit(thisDist2: Dist2)
  { def / (operator: Dist): Pt2 = Pt2(thisDist2.x/ operator, thisDist2.y / operator)
  }

  implicit val PersistImplicit: Persist[Dist2] = new PersistD2[Dist2]("Dist2", "x", _.xMetres, "y", _.yMetres, new Dist2(_, _))
}

/** Specialised immutable Array based collection class for Dist2. */
class Dist2s(val arrayUnsafe: Array[Double]) extends AnyVal with ArrProdDbl2[Dist2]
{ type ThisT = Dist2s
  override def unsafeFromArray(array: Array[Double]): Dist2s = new Dist2s(array)
  override def typeStr: String = "Dist2s"
  override def elemBuilder(d1: Double, d2: Double): Dist2 = new Dist2(d1, d2)
  override def fElemStr: Dist2 => String = _.str
}

object Dist2s extends ProdDbl2sCompanion[Dist2, Dist2s]
{
  implicit val persistImplicit: ArrProdDbl2Persist[Dist2, Dist2s] = new ArrProdDbl2Persist[Dist2, Dist2s]("Dist2s")
  { override def fromArray(value: Array[Double]): Dist2s = new Dist2s(value)
  }
}