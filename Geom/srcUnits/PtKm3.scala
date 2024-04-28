/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** 3 dimensional point specified usingMetre metres [[Kilometres]] as units rather than pure numbers. The Letter M was used rather L for Length to avoid
 *  confusion with the LL ending which is short for Latitude-longitude. */
final class PtKm3(val xKilometresNum: Double, val yKilometresNum: Double, val zKilometresNum: Double) extends PtLength3
{ override type ThisT = PtKm3
  override type LineSegT = LineSegKm3
  def typeStr: String = "Kilometres3"
  override def toString: String = typeStr.appendParenthSemis(xKilometresNum.str2, yKilometresNum.str2, zKilometresNum.str2)
  def kmStr: String = typeStr.appendParenthSemis((xKilometresNum / 1000).str2, (yKilometresNum / 1000).str2, (zKilometresNum / 1000).str2)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[Kilometres3]
  def dbl1: Double = xKilometresNum
  def dbl2: Double = yKilometresNum
  def dbl3: Double = zKilometresNum
  override def xMetresNum: Double = xKilometresNum * 1000
  override def yMetresNum: Double = yKilometresNum * 1000
  override def zMetresNum: Double = zKilometresNum * 1000
  def x: Kilometres = Kilometres(xKilometresNum)
  def y: Kilometres = Kilometres(yKilometresNum)
  def z: Kilometres = Kilometres(zKilometresNum)

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: PtKm3): KilometresSq = x * operand.x + y * operand.y + z * operand.z
  def xy: PtM2 = PtM2.metresNum(xKilometresNum, yKilometresNum)
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
  def zPos: Boolean = z.pos
  def zNeg: Boolean = z.neg
  def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg)
  def / (operator: Length): Pt3 = Pt3(x / operator, y / operator, z / operator)

  /** Converts this Kilometres3 point to a Some[Kilometres2] point of the X and Y values, returns None if the Z value is negative. */
  def toXYIfZPositive: Option[PtKm2] = ifZPos(Some(PtKm2(x, y)), None)

  /** Rotate this 3D point defined in metres around the X Axis by the given parameter given in radians. Returns a new [[PtKm3]] point. */
  def xRotateRadians(rotationRadians: Double): PtKm3 =
  { val scalar: Kilometres = Kilometres(sqrt(y.metresNum * y.metresNum + z.metresNum * z.metresNum))
    if(scalar > EarthEquatorialRadius * 1.05) throw excep("scalar: " + scalar.toString)

    val ang0 = None match {//As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      case _ if z.neg && y.neg => atan(y / z) + Pi
      case _ if z.neg => Pi + atan(y / z)//The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
      case _ => atan(y / z)//This operates on the standard atan range -Pi/2 to pi/2
    }
    val ang1 = ang0 + rotationRadians
    ???//    PtKm3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }

  /** Rotate around the X axis, viewed from positive X. A positive angle is anti clockwise. */
  def rotateX(a: AngleVec): PtKm3 = PtKm3(x, z * a.sin + y * a.cos, z * a.cos - y * a.sin)

  /** rotates the vector around the Y axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateY90: PtKm3 = PtKm3(z, y, -x)

  /** Rotates the vector around the Y axis 180 degrees or Pi radians. */
  @inline def rotateY180: PtKm3 = PtKm3(-x, y, -z)

  /** rotates the vector around the Y axis 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateY270: PtKm3 = PtKm3(-z, y, x)

  /** Rotates this vector around the Y axis, viewed form positive Y through the given angle around the origin. */
  def rotateY(a: AngleVec): PtKm3 = a match
  { case a if a == Deg0 => this
    case a if a == DegVec90 => rotateY90
    case a if a == DegVec180 => rotateY180
    case a if a == DegVec270 => rotateY270
    //Pt2(x1 * a.cos - y1 * a.sin, x1 * a.sin + y1 * a.cos)
    case a => PtKm3(z * a.sin + x * a.cos, y, z * a.cos - x * a.sin)
  }

  /** rotates the vector around the Z axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateZ90: PtKm3 = PtKm3(-y, x, z)

  /** Rotates the vector around the Z axis 180 degrees or Pi radians. */
  @inline def rotateZ180: PtKm3 = PtKm3(-x, -y, z)

  /** rotates the vector around the Z axis 270 degrees anti clockwise or, 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateZ270: PtKm3 = PtKm3(y, -x, z)

  /** Rotate around the Z axis, viewed from positive Z. A positive angle is anti clockwise. */
  def rotateZ(a: AngleVec): PtKm3 = a match
  { case a if a == Deg0 => this
    case a if a == DegVec90 => rotateZ90
    case a if a == DegVec180 => rotateZ180
    case a if a == DegVec270 => rotateZ270
    case a => PtKm3(x * a.cos - y * a.sin, x * a.sin + y * a.cos, z)
  }

  /** The distance in the XY plane from an operand [[PtM2]], the default being from the origin. */
  def xyLengthFrom(operand: PtM2 = PtM2.origin): Kilometres = {
    val sq = xKilometresNum.squared + yKilometresNum.squared
    Kilometres(sq.sqrt)
  }

  override def lineSegTo(endPt: PtLength3): LineSegKm3 =
    LineSegKm3.kilometresNum(xKilometresNum, yKilometresNum, zKilometresNum, endPt.xKilometresNum, endPt.yKilometresNum, endPt.zKilometresNum)

  override def lineSegFrom(startPt: PtLength3): LineSegKm3 =
    LineSegKm3.kilometresNum(startPt.xKilometresNum, startPt.yKilometresNum, startPt.zKilometresNum, xKilometresNum, yKilometresNum, zKilometresNum)
}

/** Companion object for the [[PtKm3] class. the 3D point measure in metres length. */
object PtKm3
{
  def kilometres(xKilometres: Double, yKilometres: Double, zKilometres: Double): PtKm3 = new PtKm3(xKilometres, yKilometres, zKilometres)
  def apply(x: Kilometres, y: Kilometres, z: Kilometres): PtKm3 = new PtKm3(x.metresNum, y.metresNum, z.metresNum)

  implicit val arrBuilderImplicit: BuilderArrDbl3Map[PtKm3, PtKm3Arr] = new BuilderArrDbl3Map[PtKm3, PtKm3Arr]
  { type BuffT = PtKm3Buff
    override def fromDblArray(array: Array[Double]): PtKm3Arr = new PtKm3Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): PtKm3Buff = new PtKm3Buff(buffer)
  }

  /** [[Show]] type class instance / evidence for [[PTKm3]]. */
  implicit lazy val showEv: ShowDbl3[PtKm3] = ShowDbl3[PtKm3]("PtKm3", "x", _.xKilometresNum, "y", _.yKilometresNum, "z", _.zKilometresNum)

  /** [[Unshow]] type class instance / evidence for [[PTKm3]]. */
  implicit lazy val unshowEv: UnshowDbl3[PtKm3] = UnshowDbl3[PtKm3]("PtKm3", "x", "y", "z", kilometres)

  implicit def pairArrBuiderImplicit[B2](implicit ct: ClassTag[B2]): PtKm3PairArrMapBuilder[B2] = new PtKm3PairArrMapBuilder[B2]

  /** Implicit instance for the [[PolygonKm3Pair]] builder. This has to go in the [[PtKm3]] companion object so it can be found by an A => B function
   * where PtKm3 is the type B parameter. */
  implicit def polygonPairBuilderImplicit[A2](implicit ct: ClassTag[A2]): PolygonKm3PairBuilder[A2] = new PolygonKm3PairBuilder[A2]

  implicit val linePathBuildImplicit: LinePathDbl3MapBuilder[PtKm3, LinePathKm3] = new LinePathDbl3MapBuilder[PtKm3, LinePathKm3]
  { override type BuffT = PtKm3Buff
    override def fromDblArray(array: Array[Double]): LinePathKm3 = new LinePathKm3(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtKm3Buff = new PtKm3Buff(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl3MapBuilder[PtKm3, PolygonKm3] = new PolygonDbl3MapBuilder[PtKm3, PolygonKm3]
  { override type BuffT = PtKm3Buff
    override def fromDblArray(array: Array[Double]): PolygonKm3 = new PolygonKm3(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtKm3Buff = new PtKm3Buff(inp)
  }

  implicit val lineSegBuildEv: LineSegLikeMapBuilder[PtKm3, LineSegKm3] = LineSegKm3(_, _)
}