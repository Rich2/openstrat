/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._

/** 3 dimensional point specified using metres as units rather than pure numbers. */
final class Pt3M(val xMetres: Double, val yMetres: Double, val zMetres: Double) extends Dbl3Elem
{ def typeStr: String = "Metres3"
  override def toString: String = typeStr.appendParenthSemis(xMetres.toString, yMetres.toString, zMetres.toString)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[Metres3]
  def dbl1 = xMetres
  def dbl2 = yMetres
  def dbl3 = zMetres
  def x: Metre = Metre(xMetres)
  def y: Metre = Metre(yMetres)
  def z: Metre = Metre(zMetres)

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: Pt3M): Area = x * operand.x + y * operand.y + z * operand.z
  def xy: Pt2M = new Pt2M(xMetres, yMetres)
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
  def zPos: Boolean = z.pos
  def zNeg: Boolean = z.neg
  def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg)
  def / (operator: Metre): Pt3 = Pt3(x / operator, y / operator, z / operator)

  /** Converts this Metres3 point to a Some[Metres2] point of the X and Y values, returns None if the Z value is negative. */
  def toXYIfZPositive: Option[Pt2M] = ifZPos(Some(Pt2M(x, y)), None)

  /** Rotate this 3D point defined in metres around the X Axis by the given parameter given in radians. Returns a new [[Pt3M]] point. */
  def xRotateRadians(rotationRadians: Double): Pt3M =
  { val scalar: Metre = Metre(sqrt(y.metres * y.metres + z.metres * z.metres))
    if(scalar > EarthEquatorialRadius * 1.05) throw excep("scalar: " + scalar.toString)

    val ang0 = ife2(//As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      z.neg && y.neg, atan(y / z) - Pi,
      z.neg,          Pi + atan(y / z), //The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
                      atan(y / z))//This operates on the standard atan range -Pi/2 to pi/2

    val ang1 = ang0 + rotationRadians
    Pt3M(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }
}

/** Companion object for the Metres3 class. */
object Pt3M
{
  def metres(xMetres: Double, yMetres: Double, zMetres: Double): Pt3M = new Pt3M(xMetres, yMetres, zMetres)
  def apply(x: Metre, y: Metre, z: Metre): Pt3M = new Pt3M(x.metres, y.metres, z.metres)
  //implicit object Metres3Persist extends Persist3[Metres, Metres, Metres, Metres3]("Metres3", "x", _.x, "y", _.y, "z", _.z, apply)
  var counter = 0
}

/** Collection class for [[Pt3]]s. Only use this if the more specific [[PolygonMs]] and[[LinePathMs]] classes are not appropriate. */
class Pt3MArr(val arrayUnsafe: Array[Double]) extends AnyVal with Dbl3sArr[Pt3M]
{ type ThisT = Pt3MArr
  def unsafeFromArray(array: Array[Double]): ThisT = new Pt3MArr(array)
  override def typeStr: String = "Metres3s"
  override def fElemStr: Pt3M => String = _ => "Undefined" //_.str
  override def newElem(d1: Double, d2: Double, d3: Double): Pt3M = new Pt3M(d1, d2, d3)

  /** This methods function is to work on a sequence of 3d points representing a polygon on the surface a globe (eg the Earth). If Z is positive its
   *  on the side of the Earth that the viewer is looking at. Returns z positive dist2 points if 1 or more of the points are z positive. Z negative
   *  points are moved to the horizon. */
  def earthZPositive: OptEither[Pt2MArr, CurveSegDists] =
  {
    existsCount(_.z.pos) match
    { case 0 => NoOptEither
    case n if n == elemsLen => SomeA(pMap(_.xy))
    case n => NoOptEither
      //      {
      //        var els: List[Either[Dist2, Dist2]] = lMap {
      //          case el if el.z.pos => Right(el.xy)
      //          case el =>
      //          { val xy = el.xy
      //            val fac = xy.magnitude / EarthAvRadius
      //            Left(xy / fac)
      //          }
      //        }
      //        while (els.head.isLeft && els.last.isLeft && els.init.last.isLeft) els = els.init
      //
      //        val els2: List[Either[Dist2, Dist2]] = els.drop(2).foldLeft(els.take(2))((acc, el) => el match
      //          {
      //            case Left(v) if acc.last.isLeft && acc.init.last.isLeft => acc.init :+ el
      //            case el => acc :+ el
      //          })
      //
      //        val acc: CurveSegDists = CurveSegDists.factory(els2.length)// List[CurveSegDist] = Nil
      //        var last: Either[Dist2, Dist2] = els2.last
      //        els2.iForeach {(e, i) =>
      //          e match
      //          { case Right(d2) => acc.setElem(i, LineSegDist(d2))
      //            case Left(d2) if last.isLeft => acc.setElem(i, ArcSegDist(Dist2Z, d2))
      //            case Left(d2) => acc.setElem(i, LineSegDist(d2))
      //          }
      //          last = e
      //        }
      //        GlobedSome(acc)
      //      }
    }
  }
}

object Pt3MArr extends Dbl3sArrCompanion[Pt3M, Pt3MArr]
{ override def fromArrayDbl(array: Array[Double]): Pt3MArr = new Pt3MArr(array)
}