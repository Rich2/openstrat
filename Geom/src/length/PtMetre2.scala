/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pglobe._, collection.mutable.ArrayBuffer, math._

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
  { def / (operator: Metres): Pt2 = Pt2(thisMetres2.x.metresNum/ operator.metresNum, thisMetres2.y.metresNum / operator.metresNum)
  }

  implicit val PersistImplicit: Persist[PtMetre2] = new Persist2Dbls[PtMetre2]("Metres2", "x", "y", new PtMetre2(_, _))

  implicit val builderImplicit: ArrDbl2sBuilder[PtMetre2, PtMetre2Arr] = new ArrDbl2sBuilder[PtMetre2, PtMetre2Arr]
  { type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(inp)
  }

  implicit val linePathBuildImplicit: LinePathDbl2sBuilder[PtMetre2, LinePathMetre] = new LinePathDbl2sBuilder[PtMetre2, LinePathMetre]
  { override type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): LinePathMetre = new LinePathMetre(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl2sBuilder[PtMetre2, PolygonMetre] = new PolygonDbl2sBuilder[PtMetre2, PolygonMetre]
  { override type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): PolygonMetre = new PolygonMetre(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(inp)
  }
}

/** Specialised immutable Array based collection class for [[PtMetre2]]s. */
class PtMetre2Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2s[PtMetre2]
{ type ThisT = PtMetre2Arr
  override def unsafeFromArray(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)
  override def typeStr: String = "Metres2s"
  override def dataElem(d1: Double, d2: Double): PtMetre2 = new PtMetre2(d1, d2)
  override def fElemStr: PtMetre2 => String = _.str
}

/** Companion object for the [[PtMetre2Arr]] class. Contains implicit Instance for Persist type class. */
object PtMetre2Arr extends DataDbl2sCompanion[PtMetre2, PtMetre2Arr]
{
  override def fromArrayDbl(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)

  implicit val persistImplicit: DataDbl2sPersist[PtMetre2, PtMetre2Arr] = new DataDbl2sPersist[PtMetre2, PtMetre2Arr]("Metres2s")
  { override def fromArray(value: Array[Double]): PtMetre2Arr = new PtMetre2Arr(value)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtMetre2]]s collections. */
final class BuffPtMetre2(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with BuffDbl2s[PtMetre2]
{ override def typeStr: String = "BuffPtMetre2"
  def dblsToT(d1: Double, d2: Double): PtMetre2 = new PtMetre2(d1, d2)
}

object BuffPtMetre2{
  def apply(initSize: Int = 4): BuffPtMetre2 = new BuffPtMetre2(new ArrayBuffer[Double](initSize * 2))
}