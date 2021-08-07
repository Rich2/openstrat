/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._

/** A 2 dimensional point specified in [[Metres]] as units rather than pure scalar numbers. */
final class PtMetre2(val xMetresNum: Double, val yMetresNum: Double) extends Show2Dbls
{ override def typeStr: String = "Pt2M"
  override def name1: String = "x"
  override def name2: String = "y"
  def x: Metres = Metres(xMetresNum)
  def y: Metres = Metres(yMetresNum)
  override def show1: Double = xMetresNum
  override def show2: Double = yMetresNum
  def + (op: Vec2M): PtMetre2 = new PtMetre2(xMetresNum + op.xMetresNum, yMetresNum + op.yMetresNum)
  def - (op: PtMetre2): PtMetre2 = PtMetre2(x - op.x, y - op.y)
  def addXY (otherX: Metres, otherY: Metres): PtMetre2 = PtMetre2(x + otherX, y + otherY)
  def subXY (otherX: Metres, otherY: Metres): PtMetre2 = PtMetre2(x - otherX, y - otherY)
  def addX(adj: Metres): PtMetre2 = PtMetre2(x + adj, y)
  def addY(adj: Metres): PtMetre2 = PtMetre2(x, y + adj)
  def subX(adj: Metres): PtMetre2 = PtMetre2(x - adj, y)
  def subY(adj: Metres): PtMetre2 = PtMetre2(x, y - adj)
  def * (operator: Double): PtMetre2 = PtMetre2(x * operator, y * operator)
  def / (operator: Double): PtMetre2 = PtMetre2(x / operator, y / operator)
  //def magnitude: Metres = Metres(math.sqrt(xMetresNum.squared + yMetresNum.squared))

  def rotate(a: AngleVec): PtMetre2 =  PtMetre2.metresNum(x.metresNum * a.cos - y.metresNum * a.sin, x.metresNum * a.sin + y.metresNum * a.cos)

  def rotateRadians(r: Double): PtMetre2 =
  { val newX = xMetresNum * cos(r) - yMetresNum * sin(r)
    val newY =
    { val ya = xMetresNum * sin(r)
      val yb = yMetresNum * cos(r)
      ya + yb
    }
    PtMetre2.metresNum(newX, newY)
  }

  /** Currently not working for angles greater than Pi / 2 */
  def toLatLong: LatLong = LatLong.radians(math.asin(y / EarthPolarRadius), math.asin(x / EarthEquatorialRadius))

  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
}

/** Companion object for [[PtMetre2]] class contains factory methods. */
object PtMetre2
{ /** Factory method for creating a 2 dimensional point measured in metres from the scalar [[Double]] values. */
  def metresNum(xMetres: Double, yMetres: Double): PtMetre2 = new PtMetre2(xMetres, yMetres)

  def apply(x: Metres, y: Metres): PtMetre2 = new PtMetre2(x.metresNum, y.metresNum)

  implicit class Metres2Implicit(thisMetres2: PtMetre2)
  { def / (operator: Length): Pt2 = Pt2(thisMetres2.x.metresNum/ operator.metresNum, thisMetres2.y.metresNum / operator.metresNum)
  }

  implicit val PersistImplicit: Persist[PtMetre2] = new Persist2Dbls[PtMetre2]("Metres2", "x", "y", new PtMetre2(_, _))

  implicit val linePathBuildImplicit: LinePathDbl2sBuilder[PtMetre2, LinePathMetre] = new LinePathDbl2sBuilder[PtMetre2, LinePathMetre]
  { override type BuffT = Pt2MBuff
    override def fromDblArray(array: Array[Double]): LinePathMetre = new LinePathMetre(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): Pt2MBuff = new Pt2MBuff(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl2sBuilder[PtMetre2, PolygonMetre] = new PolygonDbl2sBuilder[PtMetre2, PolygonMetre]
  { override type BuffT = Pt2MBuff
    override def fromDblArray(array: Array[Double]): PolygonMetre = new PolygonMetre(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): Pt2MBuff = new Pt2MBuff(inp)
  }
}

/** Specialised immutable Array based collection class for [[PtMetre2]]s. */
class Pt2MArr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2s[PtMetre2]
{ type ThisT = Pt2MArr
  override def unsafeFromArray(array: Array[Double]): Pt2MArr = new Pt2MArr(array)
  override def typeStr: String = "Metres2s"
  override def dataElem(d1: Double, d2: Double): PtMetre2 = new PtMetre2(d1, d2)
  override def fElemStr: PtMetre2 => String = _.str
}

/** Companion object for the [[Pt2MArr]] class. Contains implicit Instance for Persist type class. */
object Pt2MArr extends DataDbl2sCompanion[PtMetre2, Pt2MArr]
{
  override def fromArrayDbl(array: Array[Double]): Pt2MArr = new Pt2MArr(array)

  implicit val persistImplicit: DataDbl2sPersist[PtMetre2, Pt2MArr] = new DataDbl2sPersist[PtMetre2, Pt2MArr]("Metres2s")
  { override def fromArray(value: Array[Double]): Pt2MArr = new Pt2MArr(value)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtMetre2]]s collections. */
final class Pt2MBuff(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with BuffDbl2s[PtMetre2]
{ def dblsToT(d1: Double, d2: Double): PtMetre2 = new PtMetre2(d1, d2)
}