/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._

/** A 2 dimensional point specified in metres as units rather than pure scalar numbers. */
final class Pt2M(val xMetres: Double, val yMetres: Double) extends Show2Dbls
{ //override def toString: String = Metres2.PersistImplicit.strT(this)
  override def typeStr: String = "Pt2M"
 // override def approx(that: Any, delta: Double): Boolean = ???
  override def name1: String = "x"
  override def name2: String = "y"
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[Metres2]
  def x: Metres = Metres(xMetres)
  def y: Metres = Metres(yMetres)
  override def show1: Double = xMetres
  override def show2: Double = yMetres
  def + (op: Pt2M): Pt2M = Pt2M(x + op.x, y + op.y)
  def - (op: Pt2M): Pt2M = Pt2M(x - op.x, y - op.y)
  def addXY (otherX: Metres, otherY: Metres): Pt2M = Pt2M(x + otherX, y + otherY)
  def subXY (otherX: Metres, otherY: Metres): Pt2M = Pt2M(x - otherX, y - otherY)
  def addX(adj: Metres): Pt2M = Pt2M(x + adj, y)
  def addY(adj: Metres): Pt2M = Pt2M(x, y + adj)
  def subX(adj: Metres): Pt2M = Pt2M(x - adj, y)
  def subY(adj: Metres): Pt2M = Pt2M(x, y - adj)
  def * (operator: Double): Pt2M = Pt2M(x * operator, y * operator)
  def / (operator: Double): Pt2M = Pt2M(x / operator, y / operator)
  def magnitude: Metres = Metres(math.sqrt(xMetres.squared + yMetres.squared))

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: Pt2M): Area = x * operand.x + y * operand.y

  def rotate(a: AngleVec): Pt2M =  Pt2M.metres(x.metres * a.cos - y.metres * a.sin, x.metres * a.sin + y.metres * a.cos)

  def rotateRadians(r: Double): Pt2M =
  { val newX = xMetres * cos(r) - yMetres * sin(r)
    val newY =
    { val ya = xMetres * sin(r)
      val yb = yMetres * cos(r)
      ya + yb
    }
    Pt2M.metres(newX, newY)
  }

  /** Currently not working for angles greater than Pi / 2 */
  def toLatLong: LatLong = LatLong.radians(math.asin(y / EarthPolarRadius), math.asin(x / EarthEquatorialRadius))

  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
}

/** Companion object for [[Pt2M]] class contains factory methods. */
object Pt2M
{ def metres(xMetres: Double, yMetres: Double): Pt2M = new Pt2M(xMetres, yMetres)
  def apply(x: Metres, y: Metres): Pt2M = new Pt2M(x.metres, y.metres)

  implicit class Metres2Implicit(thisMetres2: Pt2M)
  { def / (operator: Metres): Pt2 = Pt2(thisMetres2.x/ operator, thisMetres2.y / operator)
  }

  implicit val PersistImplicit: Persist[Pt2M] = new Persist2Dbls[Pt2M]("Metres2", "x", "y", new Pt2M(_, _))
}

/** Specialised immutable Array based collection class for [[Pt2M]]s. */
class Pt2MArr(val arrayUnsafe: Array[Double]) extends AnyVal with Dbl2sArr[Pt2M]
{ type ThisT = Pt2MArr
  override def unsafeFromArray(array: Array[Double]): Pt2MArr = new Pt2MArr(array)
  override def typeStr: String = "Metres2s"
  override def elemBuilder(d1: Double, d2: Double): Pt2M = new Pt2M(d1, d2)
  override def fElemStr: Pt2M => String = _.str
}

/** Companion object for the [[Pt2MArr]] class. Contains implicit Instance for Persist type class. */
object Pt2MArr extends Dbl2sArrCompanion[Pt2M, Pt2MArr]
{
  override def fromArrayDbl(array: Array[Double]): Pt2MArr = new Pt2MArr(array)

  implicit val persistImplicit: Dbl2sArrPersist[Pt2M, Pt2MArr] = new Dbl2sArrPersist[Pt2M, Pt2MArr]("Metres2s")
  { override def fromArray(value: Array[Double]): Pt2MArr = new Pt2MArr(value)
  }
}