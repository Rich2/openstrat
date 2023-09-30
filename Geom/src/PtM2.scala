/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** A 2 dimensional point specified in [[Length]] as units rather than pure scalar numbers. */
final class PtM2(val xMetresNum: Double, val yMetresNum: Double) extends PointDbl2 with ShowElemDbl2
{ override type ThisT = PtM2
  override type LineSegT = LineSegM2
  override def typeStr: String = "Pt2M"
  override def name1: String = "x"
  override def name2: String = "y"
  def x: Length = Length(xMetresNum)
  def y: Length = Length(yMetresNum)
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

  /** Rotates the point 180 degrees around the origin by negating the X and Y components. */
  def rotate180: PtM2 = new PtM2(-xMetresNum, -yMetresNum)

  /** Rotates th point 180 degrees around the origin if the condition is true. */
  def rotate180If(cond: Boolean): PtM2 = ife(cond, rotate180, this)

  /** Rotates the point 180 degrees around the origin  if the condition is not true. */
  def rotate180IfNot(cond: Boolean): PtM2 = ife(cond, this, rotate180)

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
  //def toLatLong: LatLong = LatLong.radians(math.asin(y / EarthPolarRadius), math.asin(x / EarthEquatorialRadius))

  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg

  /** [[LineSegM2]] from this point to the parameter point. */
  override def lineSegTo(endPt: PtM2): LineSegM2 = LineSegM2(this, endPt)

  /** [[LinSegM]] from the parameter point to this point. */
  override def lineSegFrom(startPt: PtM2): LineSegM2 = LineSegM2(startPt, this)

  def revY: PtM2 = new PtM2(xMetresNum, -yMetresNum)
  def revYIf(cond: Boolean): PtM2 = ife(cond, new PtM2(xMetresNum, -yMetresNum), this)
}

/** Companion object for [[PtM2]] class contains factory methods. */
object PtM2
{ /** Factory method for creating a 2 dimensional point measured in metres from the scalar [[Double]] values. */
  def metresNum(xMetres: Double, yMetres: Double): PtM2 = new PtM2(xMetres, yMetres)

  def apply(x: Length, y: Length): PtM2 = new PtM2(x.metresNum, y.metresNum)

  def origin: PtM2 = new PtM2(0, 0)

  implicit class Metres2Implicit(thisMetres2: PtM2)
  { def / (operator: Length): Pt2 = Pt2(thisMetres2.x.metresNum/ operator.metresNum, thisMetres2.y.metresNum / operator.metresNum)
  }

  implicit val PersistImplicit: Persist[PtM2] = new PersistDbl2Ed[PtM2]("Metres2", "x", "y", new PtM2(_, _))

  implicit val builderImplicit: Dbl2ArrMapBuilder[PtM2, PtMetre2Arr] = new Dbl2ArrMapBuilder[PtM2, PtMetre2Arr]
  { type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(buffer)
  }

  implicit val linePathBuildImplicit: LinePathDbl2Builder[PtM2, LinePathM2] = new LinePathDbl2Builder[PtM2, LinePathM2]
  { override type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): LinePathM2 = new LinePathM2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl2MapBuilder[PtM2, PolygonM2] = new PolygonDbl2MapBuilder[PtM2, PolygonM2]
  { override type BuffT = BuffPtMetre2
    override def fromDblArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtMetre2 = new BuffPtMetre2(inp)
  }

  /** Implicit instance for the [[PolygonM2Pair]] builder. This has to go in the [[PtM2]] companion object so it can be found by an A => B function
   * where PtM2 is the type B parameter. */
  implicit def polygonPairBuildImplicit[A2](implicit ct: ClassTag[A2]): PolygonM2PairBuilder[A2] = new PolygonM2PairBuilder[A2]
}

/** Specialised immutable Array based collection class for [[PtM2]]s. */
class PtMetre2Arr(val unsafeArray: Array[Double]) extends AnyVal with Dbl2Arr[PtM2]
{ type ThisT = PtMetre2Arr
  override def fromArray(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)
  override def typeStr: String = "Metres2s"
  override def seqDefElem(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
  override def fElemStr: PtM2 => String = _.str
}

/** Companion object for the [[PtMetre2Arr]] class. Contains implicit Instance for Persist type class. */
object PtMetre2Arr extends Dbl2SeqLikeCompanion[PtM2, PtMetre2Arr]
{
  override def fromArray(array: Array[Double]): PtMetre2Arr = new PtMetre2Arr(array)

  implicit val persistImplicit: Dbl2SeqDefPersist[PtM2, PtMetre2Arr] = new Dbl2SeqDefPersist[PtM2, PtMetre2Arr]("Metres2s")
  { override def fromArray(value: Array[Double]): PtMetre2Arr = new PtMetre2Arr(value)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtM2]]s collections. */
final class BuffPtMetre2(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl2Buff[PtM2]
{ override def typeStr: String = "BuffPtMetre2"
  def newElem(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
}

object BuffPtMetre2{
  def apply(initSize: Int = 4): BuffPtMetre2 = new BuffPtMetre2(new ArrayBuffer[Double](initSize * 2))
}