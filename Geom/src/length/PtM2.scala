/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pglobe._, collection.mutable.ArrayBuffer, math._

/** A 2 dimensional point specified in [[Length]] as units rather than pure scalar numbers. */
final class PtM2(val xMetresNum: Double, val yMetresNum: Double) extends Show2Dbls
{ override def typeStr: String = "Pt2M"
  override def name1: String = "x"
  override def name2: String = "y"
  def x: Length = Length(xMetresNum)
  def y: Length = Length(yMetresNum)
  override def dbl1: Double = xMetresNum
  override def dbl2: Double = yMetresNum
  override def show1: Double = xMetresNum
  override def show2: Double = yMetresNum
  def + (op: Vec2M): PtM2 = new PtM2(xMetresNum + op.xMetresNum, yMetresNum + op.yMetresNum)
  def - (op: PtM2): PtM2 = PtM2(x - op.x, y - op.y)
  def addXY (otherX: Length, otherY: Length): PtM2 = PtM2(x + otherX, y + otherY)
  def subXY (otherX: Length, otherY: Length): PtM2 = PtM2(x - otherX, y - otherY)
  def addX(adj: Length): PtM2 = PtM2(x + adj, y)
  def addY(adj: Length): PtM2 = PtM2(x, y + adj)
  def subX(adj: Length): PtM2 = PtM2(x - adj, y)
  def subY(adj: Length): PtM2 = PtM2(x, y - adj)
  def * (operator: Double): PtM2 = PtM2(x * operator, y * operator)
  def / (operator: Double): PtM2 = PtM2(x / operator, y / operator)
  //def magnitude: Metres = Metres(math.sqrt(xMetresNum.squared + yMetresNum.squared))

  def rotate(a: AngleVec): PtM2 =  PtM2.metresNum(x.metresNum * a.cos - y.metresNum * a.sin, x.metresNum * a.sin + y.metresNum * a.cos)

  def rotateRadians(r: Double): PtM2 =
  { val newX = xMetresNum * cos(r) - yMetresNum * sin(r)
    val newY =
    { val ya = xMetresNum * sin(r)
      val yb = yMetresNum * cos(r)
      ya + yb
    }
    PtM2.metresNum(newX, newY)
  }

  /** Currently not working for angles greater than Pi / 2 */
  def toLatLong: LatLong = LatLong.radians(math.asin(y / EarthPolarRadius), math.asin(x / EarthEquatorialRadius))

  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
}

/** Companion object for [[PtM2]] class contains factory methods. */
object PtM2
{ /** Factory method for creating a 2 dimensional point measured in metres from the scalar [[Double]] values. */
  def metresNum(xMetres: Double, yMetres: Double): PtM2 = new PtM2(xMetres, yMetres)

  def apply(x: Length, y: Length): PtM2 = new PtM2(x.metresNum, y.metresNum)

  implicit class Metres2Implicit(thisMetres2: PtM2)
  { def / (operator: Length): Pt2 = Pt2(thisMetres2.x.metresNum/ operator.metresNum, thisMetres2.y.metresNum / operator.metresNum)
  }

  implicit val PersistImplicit: Persist[PtM2] = new Persist2Dbls[PtM2]("Metres2", "x", "y", new PtM2(_, _))

  implicit val builderImplicit: ArrDbl2sBuilder[PtM2, PtMetre2Arr] = new ArrDbl2sBuilder[PtM2, PtMetre2Arr]
  { type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(inp)
  }

  implicit val linePathBuildImplicit: LinePathDbl2sBuilder[PtM2, LinePathMetre] = new LinePathDbl2sBuilder[PtM2, LinePathMetre]
  { override type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): LinePathMetre = new LinePathMetre(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl2sBuilder[PtM2, PolygonM] = new PolygonDbl2sBuilder[PtM2, PolygonM]
  { override type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): PolygonM = new PolygonM(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(inp)
  }
}

/** Specialised immutable Array based collection class for [[PtM2]]s. */
class PtMetre2Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2s[PtM2]
{ type ThisT = PtMetre2Arr
  override def unsafeFromArray(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)
  override def typeStr: String = "Metres2s"
  override def dataElem(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
  override def fElemStr: PtM2 => String = _.str
}

/** Companion object for the [[PtMetre2Arr]] class. Contains implicit Instance for Persist type class. */
object PtMetre2Arr extends DataDbl2sCompanion[PtM2, PtMetre2Arr]
{
  override def fromArrayDbl(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)

  implicit val persistImplicit: DataDbl2sPersist[PtM2, PtMetre2Arr] = new DataDbl2sPersist[PtM2, PtMetre2Arr]("Metres2s")
  { override def fromArray(value: Array[Double]): PtMetre2Arr = new PtMetre2Arr(value)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtM2]]s collections. */
final class BuffPtMetre2(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with BuffDbl2s[PtM2]
{ override def typeStr: String = "BuffPtMetre2"
  def dblsToT(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
}

object BuffPtMetre2{
  def apply(initSize: Int = 4): BuffPtMetre2 = new BuffPtMetre2(new ArrayBuffer[Double](initSize * 2))
}