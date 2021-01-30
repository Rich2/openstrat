/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._

/** 2 dimensional vector using metres as units rather than pure scalar numbers. */
final class Metres2(val xMetres: Double, val yMetres: Double) extends Show2Dbls
{ //override def toString: String = Metres2.PersistImplicit.strT(this)
  override def typeStr: String = "Metres2"
  override def approx(that: Any, delta: Double): Boolean = ???
  override def name1: String = "x"
  override def name2: String = "y"
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Metres2]
  def x: Metres = Metres(xMetres)
  def y: Metres = Metres(yMetres)
  override def el1: Double = xMetres
  override def el2: Double = yMetres
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

/** Companion object for Metres2 class contains factory methods. */
object Metres2
{ def metres(xMetres: Double, yMetres: Double): Metres2 = new Metres2(xMetres, yMetres)
  def apply(x: Metres, y: Metres): Metres2 = new Metres2(x.metres, y.metres)

  implicit class Metres2Implicit(thisMetres2: Metres2)
  { def / (operator: Metres): Pt2 = Pt2(thisMetres2.x/ operator, thisMetres2.y / operator)
  }

  implicit val PersistImplicit: Persist[Metres2] = new Persist2Dbls[Metres2]("Metres2", "x", "y", new Metres2(_, _))
}

/** Specialised immutable Array based collection class for Metres2. */
class Metres2s(val arrayUnsafe: Array[Double]) extends AnyVal with Dbl2sArr[Metres2]
{ type ThisT = Metres2s
  override def unsafeFromArray(array: Array[Double]): Metres2s = new Metres2s(array)
  override def typeStr: String = "Metres2s"
  override def elemBuilder(d1: Double, d2: Double): Metres2 = new Metres2(d1, d2)
  override def fElemStr: Metres2 => String = _.str
}

/** Companion object for the [[Metres2s]] class not to be confused with the [[Metres2]] class. Contains implicit nstance for Persist type class. */
object Metres2s extends Dbl2sArrCompanion[Metres2, Metres2s]
{
  implicit val persistImplicit: Dbl2sArrPersist[Metres2, Metres2s] = new Dbl2sArrPersist[Metres2, Metres2s]("Metres2s")
  { override def fromArray(value: Array[Double]): Metres2s = new Metres2s(value)
  }
}