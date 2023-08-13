/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** 3 dimensional point specified using metres [[Length]] as units rather than pure numbers. The Letter M was used rather L for Length to avoid
 *  confusion with the LL ending which is short for Latitude-longitude. */
final class PtM3(val xMetres: Double, val yMetres: Double, val zMetres: Double) extends PointDbl3
{ override type ThisT = PtM3
  override type LineSegT = LineSegM3
  def typeStr: String = "Metres3"
  override def toString: String = typeStr.appendParenthSemis(xMetres.str2, yMetres.str2, zMetres.str2)
  def kmStr: String = typeStr.appendParenthSemis((xMetres / 1000).str2, (yMetres / 1000).str2, (zMetres / 1000).str2)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[Metres3]
  def dbl1: Double = xMetres
  def dbl2: Double = yMetres
  def dbl3: Double = zMetres
  def x: Length = Length(xMetres)
  def y: Length = Length(yMetres)
  def z: Length = Length(zMetres)

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: PtM3): Area = x * operand.x + y * operand.y + z * operand.z
  def xy: PtM2 = new PtM2(xMetres, yMetres)
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
  def zPos: Boolean = z.pos
  def zNeg: Boolean = z.neg
  def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg)
  def / (operator: Length): Pt3 = Pt3(x / operator, y / operator, z / operator)

  /** Converts this Metres3 point to a Some[Metres2] point of the X and Y values, returns None if the Z value is negative. */
  def toXYIfZPositive: Option[PtM2] = ifZPos(Some(PtM2(x, y)), None)

  /** Rotate this 3D point defined in metres around the X Axis by the given parameter given in radians. Returns a new [[PtM3]] point. */
  def xRotateRadians(rotationRadians: Double): PtM3 =
  { val scalar: Length = Length(sqrt(y.metresNum * y.metresNum + z.metresNum * z.metresNum))
    if(scalar > EarthEquatorialRadius * 1.05) throw excep("scalar: " + scalar.toString)

    val ang0 = None match {//As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      case _ if z.neg && y.neg => atan(y / z) + Pi
      case _ if z.neg => Pi + atan(y / z)//The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
      case _ => atan(y / z)//This operates on the standard atan range -Pi/2 to pi/2
    }
    val ang1 = ang0 + rotationRadians
    PtM3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }

  /** Rotate around the X axis, viewed from positive X. A positive angle is anti clockwise. */
  def rotateX(a: AngleVec): PtM3 = PtM3(x, z * a.sin + y * a.cos, z * a.cos - y * a.sin)

  /** rotates the vector around the Y axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateY90: PtM3 = PtM3(z, y, -x)

  /** Rotates the vector around the Y axis 180 degrees or Pi radians. */
  @inline def rotateY180: PtM3 = PtM3(-x, y, -z)

  /** rotates the vector around the Y axis 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateY270: PtM3 = PtM3(-z, y, x)

  /** Rotates this vector around the Y axis, viewed form positive Y through the given angle around the origin. */
  def rotateY(a: AngleVec): PtM3 = a match
  { case a if a == Deg0 => this
    case a if a == DegVec90 => rotateY90
    case a if a == DegVec180 => rotateY180
    case a if a == DegVec270 => rotateY270
    //Pt2(x1 * a.cos - y1 * a.sin, x1 * a.sin + y1 * a.cos)
    case a => PtM3(z * a.sin + x * a.cos, y, z * a.cos - x * a.sin)
  }

  /** rotates the vector around the Z axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateZ90: PtM3 = PtM3(-y, x, z)

  /** Rotates the vector around the Z axis 180 degrees or Pi radians. */
  @inline def rotateZ180: PtM3 = PtM3(-x, -y, z)

  /** rotates the vector around the Z axis 270 degrees anti clockwise or, 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateZ270: PtM3 = PtM3(y, -x, z)

  /** Rotate around the Z axis, viewed from positive Z. A positive angle is anti clockwise. */
  def rotateZ(a: AngleVec): PtM3 = a match
  { case a if a == Deg0 => this
    case a if a == DegVec90 => rotateZ90
    case a if a == DegVec180 => rotateZ180
    case a if a == DegVec270 => rotateZ270
    case a => PtM3(x * a.cos - y * a.sin, x * a.sin + y * a.cos, z)
  }

  /** The distance in the XY plane from an operand [[PtM2]], the default being from the origin. */
  def xyLengthFrom(operand: PtM2 = PtM2.origin): Length = {
    val sq = xMetres.squared + yMetres.squared
    Length(sq.sqrt)
  }

  override def lineSegTo(endPt: PtM3): LineSegM3 = LineSegM3(this, endPt)
  override def lineSegFrom(startPt: PtM3): LineSegM3 = LineSegM3(startPt, this)
}

/** Companion object for the [[PtM3] class. the 3D point measure in metres length. */
object PtM3
{
  def metres(xMetres: Double, yMetres: Double, zMetres: Double): PtM3 = new PtM3(xMetres, yMetres, zMetres)
  def apply(x: Length, y: Length, z: Length): PtM3 = new PtM3(x.metresNum, y.metresNum, z.metresNum)
  //implicit object Metres3Persist extends Persist3[Metres, Metres, Metres, Metres3]("Metres3", "x", _.x, "y", _.y, "z", _.z, apply)
  var counter = 0

  implicit val arrBuilderImplicit: Dbl3ArrMapBuilder[PtM3, PtM3Arr] = new Dbl3ArrMapBuilder[PtM3, PtM3Arr]
  { type BuffT = PtM3Buff
    override def fromDblArray(array: Array[Double]): PtM3Arr = new PtM3Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): PtM3Buff = new PtM3Buff(buffer)
  }

  implicit def pairArrBuiderImplicit[B2](implicit ct: ClassTag[B2]): PtM3PairArrMapBuilder[B2] = new PtM3PairArrMapBuilder[B2]

  /** Implicit instance for the [[PolygonM3Pair]] builder. This has to go in the [[PtM3]] companion object so it can be found by an A => B function
   * where PtM3 is the type B parameter. */
  implicit def polygonPairBuilderImplicit[A2](implicit ct: ClassTag[A2]): PolygonM3PairBuilder[A2] = new PolygonM3PairBuilder[A2]

  implicit val linePathBuildImplicit: LinePathDbl3MapBuilder[PtM3, LinePathM3] = new LinePathDbl3MapBuilder[PtM3, LinePathM3]
  { override type BuffT = PtM3Buff
    override def fromDblArray(array: Array[Double]): LinePathM3 = new LinePathM3(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtM3Buff = new PtM3Buff(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl3MapBuilder[PtM3, PolygonM3] = new PolygonDbl3MapBuilder[PtM3, PolygonM3]
  { override type BuffT = PtM3Buff
    override def fromDblArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtM3Buff = new PtM3Buff(inp)
  }

  implicit val lineSegBuildEv: LineSegLikeMapBuilder[PtM3, LineSegM3] = LineSegM3(_, _)
}