/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._

/** 2 dimensional vector using metres as units rather than pure scalar numbers. */
final class Metres2(val xMetres: Double, val yMetres: Double) extends ProdDbl2
{ override def toString: String = Metres2.PersistImplicit.strT(this)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Metres2]
  def x: Metres = Metres(xMetres)
  def y: Metres = Metres(yMetres)
  override def _1: Double = xMetres
  override def _2: Double = yMetres
  def + (op: Metres2): Metres2 = Metres2(x + op.x, y + op.y)
  def - (op: Metres2): Metres2 = Metres2(x - op.x, y - op.y)
  def addXY (otherX: Metres, otherY: Metres): Metres2 = Metres2(x + otherX, y + otherY)
  def subXY (otherX: Metres, otherY: Metres): Metres2 = Metres2(x - otherX, y - otherY)
  def addX(adj: Metres): Metres2 = Metres2(x + adj, y)
  def addY(adj: Metres): Metres2 = Metres2(x, y + adj)
  def subX(adj: Metres): Metres2 = Metres2(x - adj, y)
  def subY(adj: Metres): Metres2 = Metres2(x, y - adj)
  def * (operator: Double): Metres2 = Metres2(x * operator, y * operator)
  def / (operator: Double): Metres2 = Metres2(x / operator, y / operator)
  def magnitude: Metres = Metres(math.sqrt(xMetres.squared + yMetres.squared))

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: Metres2): Area = x * operand.x + y * operand.y

  def rotate(a: AngleVec): Metres2 =  Metres2.metres(x.metres * a.cos - y.metres * a.sin, x.metres * a.sin + y.metres * a.cos)

  def rotateRadians(r: Double): Metres2 =
  { val newX = xMetres * cos(r) - yMetres * sin(r)
    val newY =
    { val ya = xMetres * sin(r)
      val yb = yMetres * cos(r)
      ya + yb
    }
    Metres2.metres(newX, newY)
  }

  /** Currently not working for angles greater than Pi / 2 */
  def toLatLong: LatLong = LatLong.radians(math.asin(y / EarthPolarRadius), math.asin(x / EarthEquatorialRadius))

  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
}

object Metres2
{ def metres(xMetres: Double, yMetres: Double): Metres2 = new Metres2(xMetres, yMetres)
  def apply(x: Metres, y: Metres): Metres2 = new Metres2(x.metres, y.metres)

  implicit class Metres2Implicit(thisMetres2: Metres2)
  { def / (operator: Metres): Pt2 = Pt2(thisMetres2.x/ operator, thisMetres2.y / operator)
  }

  implicit val PersistImplicit: Persist[Metres2] = new Persist2Dbls[Metres2]("Metres2", "x", _.xMetres, "y", _.yMetres, new Metres2(_, _))
}

/** Specialised immutable Array based collection class for Metres2. */
class Metres2s(val arrayUnsafe: Array[Double]) extends AnyVal with ArrProdDbl2[Metres2]
{ type ThisT = Metres2s
  override def unsafeFromArray(array: Array[Double]): Metres2s = new Metres2s(array)
  override def typeStr: String = "Metres2s"
  override def elemBuilder(d1: Double, d2: Double): Metres2 = new Metres2(d1, d2)
  override def fElemStr: Metres2 => String = _.str
}

object Metres2s extends ProdDbl2sCompanion[Metres2, Metres2s]
{
  implicit val persistImplicit: ArrProdDbl2Persist[Metres2, Metres2s] = new ArrProdDbl2Persist[Metres2, Metres2s]("Metres2s")
  { override def fromArray(value: Array[Double]): Metres2s = new Metres2s(value)
  }
}