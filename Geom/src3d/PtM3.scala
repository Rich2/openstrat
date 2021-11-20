/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer

/** 3 dimensional point specified using metres as units rather than pure numbers. The Letter M was used rather L for Length to avoid confusion with
 * the LL ending which is short for Latitude-longitude. */
final class PtM3(val xMetres: Double, val yMetres: Double, val zMetres: Double) extends ElemDbl3
{ def typeStr: String = "Metres3"
  override def toString: String = typeStr.appendParenthSemis(xMetres.str2, yMetres.str2, zMetres.str2)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[Metres3]
  def dbl1 = xMetres
  def dbl2 = yMetres
  def dbl3 = zMetres
  def x: Length = Length(xMetres)
  def y: Length = Length(yMetres)
  def z: Length = Length(zMetres)

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: PtM3): Area = x * operand.x + y * operand.y + z * operand.z
  def xy: PtMetre2 = new PtMetre2(xMetres, yMetres)
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
  def zPos: Boolean = z.pos
  def zNeg: Boolean = z.neg
  def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg)
  def / (operator: Length): Pt3 = Pt3(x / operator, y / operator, z / operator)

  /** Converts this Metres3 point to a Some[Metres2] point of the X and Y values, returns None if the Z value is negative. */
  def toXYIfZPositive: Option[PtMetre2] = ifZPos(Some(PtMetre2(x, y)), None)

  /** Rotate this 3D point defined in metres around the X Axis by the given parameter given in radians. Returns a new [[PtM3]] point. */
  def xRotateRadians(rotationRadians: Double): PtM3 =
  { val scalar: Length = Length(sqrt(y.metresNum * y.metresNum + z.metresNum * z.metresNum))
    if(scalar > EarthEquatorialRadius * 1.05) throw excep("scalar: " + scalar.toString)

    val ang0 = ife2(//As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      z.neg && y.neg, atan(y / z) - Pi,
      z.neg,          Pi + atan(y / z), //The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
                      atan(y / z))//This operates on the standard atan range -Pi/2 to pi/2

    val ang1 = ang0 + rotationRadians
    PtM3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }

  /** rotates the vector around the Y axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateY90: PtM3 = PtM3(-z, y, x)

  /** Rotates the vector around the Y axis 180 degrees or Pi radians. */
  @inline def rotateY180: PtM3 = PtM3(-x, y, -z)

  /** rotates the vector around the Y axis 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateY270: PtM3 = PtM3(z, y, -x)

  /** Rotates this vector around the Y axis through the given angle around the origin. */
  def rotateY(a: AngleVec): PtM3 = a match
  { case Deg0 => this
    case Deg90 => rotateY90
    case Deg180 => rotateY180
    case Deg270 => rotateY270
    case a => PtM3(x * a.cos - z * a.sin, y, x * a.sin + z * a.cos)
  }
}

/** Companion object for the Metres3 class. */
object PtM3
{
  def metres(xMetres: Double, yMetres: Double, zMetres: Double): PtM3 = new PtM3(xMetres, yMetres, zMetres)
  def apply(x: Length, y: Length, z: Length): PtM3 = new PtM3(x.metresNum, y.metresNum, z.metresNum)
  //implicit object Metres3Persist extends Persist3[Metres, Metres, Metres, Metres3]("Metres3", "x", _.x, "y", _.y, "z", _.z, apply)
  var counter = 0

  implicit val builderImplicit: ArrDbl3sBuilder[PtM3, PtMetre3Arr] = new ArrDbl3sBuilder[PtM3, PtMetre3Arr]
  { type BuffT = BuffPtMetre3
    override def fromDblArray(array: Array[Double]): PtMetre3Arr = new PtMetre3Arr(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre3 = new BuffPtMetre3(inp)
  }

  implicit val linePathBuildImplicit: LinePathDbl3sBuilder[PtM3, LinePathMetre3] = new LinePathDbl3sBuilder[PtM3, LinePathMetre3]
  { override type BuffT = BuffPtMetre3
    override def fromDblArray(array: Array[Double]): LinePathMetre3 = new LinePathMetre3(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre3 = new BuffPtMetre3(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl3sBuilder[PtM3, PolygonM3] = new PolygonDbl3sBuilder[PtM3, PolygonM3]
  { override type BuffT = BuffPtMetre3
    override def fromDblArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPtMetre3 = new BuffPtMetre3(inp)
  }
}
