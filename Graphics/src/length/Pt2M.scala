/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._
import scala.collection.mutable.ArrayBuffer

/** A 2 dimensional point specified in [[Metres]] as units rather than pure scalar numbers. */
final class Pt2M (val xMetresNum: Double, val yMetresNum: Double) extends Show2Dbls
{ override def typeStr: String = "Pt2M"
  override def name1: String = "x"
  override def name2: String = "y"
  def x: Metres = Metres(xMetresNum)
  def y: Metres = Metres(yMetresNum)
  override def show1: Double = xMetresNum
  override def show2: Double = yMetresNum
  def + (op: Vec2M): Pt2M = new Pt2M(xMetresNum + op.xMetresNum, yMetresNum + op.yMetresNum)
  def - (op: Pt2M): Pt2M = Pt2M(x - op.x, y - op.y)
  def addXY (otherX: Metres, otherY: Metres): Pt2M = Pt2M(x + otherX, y + otherY)
  def subXY (otherX: Metres, otherY: Metres): Pt2M = Pt2M(x - otherX, y - otherY)
  def addX(adj: Metres): Pt2M = Pt2M(x + adj, y)
  def addY(adj: Metres): Pt2M = Pt2M(x, y + adj)
  def subX(adj: Metres): Pt2M = Pt2M(x - adj, y)
  def subY(adj: Metres): Pt2M = Pt2M(x, y - adj)
  def * (operator: Double): Pt2M = Pt2M(x * operator, y * operator)
  def / (operator: Double): Pt2M = Pt2M(x / operator, y / operator)
  //def magnitude: Metres = Metres(math.sqrt(xMetresNum.squared + yMetresNum.squared))

  def rotate(a: AngleVec): Pt2M =  Pt2M.metresNum(x.metresNum * a.cos - y.metresNum * a.sin, x.metresNum * a.sin + y.metresNum * a.cos)

  def rotateRadians(r: Double): Pt2M =
  { val newX = xMetresNum * cos(r) - yMetresNum * sin(r)
    val newY =
    { val ya = xMetresNum * sin(r)
      val yb = yMetresNum * cos(r)
      ya + yb
    }
    Pt2M.metresNum(newX, newY)
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
{ /** Factory method for creating a 2 dimensional point measured in metres from the scalar [[Double]] values. */
  def metresNum(xMetres: Double, yMetres: Double): Pt2M = new Pt2M(xMetres, yMetres)

  def apply(x: Metres, y: Metres): Pt2M = new Pt2M(x.metresNum, y.metresNum)

  implicit class Metres2Implicit(thisMetres2: Pt2M)
  { def / (operator: Length): Pt2 = Pt2(thisMetres2.x.metresNum/ operator.metresNum, thisMetres2.y.metresNum / operator.metresNum)
  }

  implicit val PersistImplicit: Persist[Pt2M] = new Persist2Dbls[Pt2M]("Metres2", "x", "y", new Pt2M(_, _))

  implicit val linePathBuildImplicit: Dbl2sLinePathBuilder[Pt2M, LinePathM] = new Dbl2sLinePathBuilder[Pt2M, LinePathM]
  { override type BuffT = Pt2MBuff
    override def fromDblArray(array: Array[Double]): LinePathM = new LinePathM(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): Pt2MBuff = new Pt2MBuff(inp)
  }
}

/** Specialised immutable Array based collection class for [[Pt2M]]s. */
class Pt2MArr(val arrayUnsafe: Array[Double]) extends AnyVal with Dbl2sSeq[Pt2M]
{ type ThisT = Pt2MArr
  override def unsafeFromArray(array: Array[Double]): Pt2MArr = new Pt2MArr(array)
  override def typeStr: String = "Metres2s"
  override def dataElem(d1: Double, d2: Double): Pt2M = new Pt2M(d1, d2)
  override def fElemStr: Pt2M => String = _.str
}

/** Companion object for the [[Pt2MArr]] class. Contains implicit Instance for Persist type class. */
object Pt2MArr extends Dbl2sDataCompanion[Pt2M, Pt2MArr]
{
  override def fromArrayDbl(array: Array[Double]): Pt2MArr = new Pt2MArr(array)

  implicit val persistImplicit: Dbl2sDataPersist[Pt2M, Pt2MArr] = new Dbl2sDataPersist[Pt2M, Pt2MArr]("Metres2s")
  { override def fromArray(value: Array[Double]): Pt2MArr = new Pt2MArr(value)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt2M]]s collections. */
final class Pt2MBuff(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with Dbl2sBuffer[Pt2M]
{ def dblsToT(d1: Double, d2: Double): Pt2M = new Pt2M(d1, d2)
}