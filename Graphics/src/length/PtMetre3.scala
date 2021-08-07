/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer

/** 3 dimensional point specified using metres as units rather than pure numbers. */
final class PtMetre3(val xMetres: Double, val yMetres: Double, val zMetres: Double) extends ElemDbl3
{ def typeStr: String = "Metres3"
  override def toString: String = typeStr.appendParenthSemis(xMetres.toString, yMetres.toString, zMetres.toString)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[Metres3]
  def dbl1 = xMetres
  def dbl2 = yMetres
  def dbl3 = zMetres
  def x: Metres = Metres(xMetres)
  def y: Metres = Metres(yMetres)
  def z: Metres = Metres(zMetres)

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: PtMetre3): Area = x * operand.x + y * operand.y + z * operand.z
  def xy: PtMetre2 = new PtMetre2(xMetres, yMetres)
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
  def zPos: Boolean = z.pos
  def zNeg: Boolean = z.neg
  def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg)
  def / (operator: Metres): Pt3 = Pt3(x / operator, y / operator, z / operator)

  /** Converts this Metres3 point to a Some[Metres2] point of the X and Y values, returns None if the Z value is negative. */
  def toXYIfZPositive: Option[PtMetre2] = ifZPos(Some(PtMetre2(x, y)), None)

  /** Rotate this 3D point defined in metres around the X Axis by the given parameter given in radians. Returns a new [[PtMetre3]] point. */
  def xRotateRadians(rotationRadians: Double): PtMetre3 =
  { val scalar: Metres = Metres(sqrt(y.metresNum * y.metresNum + z.metresNum * z.metresNum))
    if(scalar > EarthEquatorialRadius * 1.05) throw excep("scalar: " + scalar.toString)

    val ang0 = ife2(//As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      z.neg && y.neg, atan(y / z) - Pi,
      z.neg,          Pi + atan(y / z), //The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
                      atan(y / z))//This operates on the standard atan range -Pi/2 to pi/2

    val ang1 = ang0 + rotationRadians
    PtMetre3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }
}

/** Companion object for the Metres3 class. */
object PtMetre3
{
  def metres(xMetres: Double, yMetres: Double, zMetres: Double): PtMetre3 = new PtMetre3(xMetres, yMetres, zMetres)
  def apply(x: Metres, y: Metres, z: Metres): PtMetre3 = new PtMetre3(x.metresNum, y.metresNum, z.metresNum)
  //implicit object Metres3Persist extends Persist3[Metres, Metres, Metres, Metres3]("Metres3", "x", _.x, "y", _.y, "z", _.z, apply)
  var counter = 0

  implicit val builderImplicit: ArrDbl3sBuilder[PtMetre3, PtMetre3Arr] = new ArrDbl3sBuilder[PtMetre3, PtMetre3Arr]
  { type BuffT = Pt3MBuff
    override def fromDblArray(array: Array[Double]): PtMetre3Arr = new PtMetre3Arr(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): Pt3MBuff = new Pt3MBuff(inp)
  }

  implicit val linePathBuildImplicit: LinePathDbl3sBuilder[PtMetre3, LinePathMetre3] = new LinePathDbl3sBuilder[PtMetre3, LinePathMetre3]
  { override type BuffT = Pt3MBuff
    override def fromDblArray(array: Array[Double]): LinePathMetre3 = new LinePathMetre3(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): Pt3MBuff = new Pt3MBuff(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl3sBuilder[PtMetre3, PolygonMetre3] = new PolygonDbl3sBuilder[PtMetre3, PolygonMetre3]
  { override type BuffT = Pt3MBuff
    override def fromDblArray(array: Array[Double]): PolygonMetre3 = new PolygonMetre3(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): Pt3MBuff = new Pt3MBuff(inp)
  }
}
