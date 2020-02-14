/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math._

/** 3 dimensional vector using metres as units rather than pure numbers. */
final class Dist3(val xMetres: Double, val yMetres: Double, val zMetres: Double) extends ProdDbl3 //with Stringer
{ def typeStr: String = "Dist3"
  //def str = persistD3(xMetres, yMetres, zMetres)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Dist3]
  def _1 = xMetres
  def _2 = yMetres
  def _3 = zMetres
  def x: Dist = Dist(xMetres)
  def y: Dist = Dist(yMetres)
  def z: Dist = Dist(zMetres)

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: Dist3): Area = x * operand.x + y * operand.y + z * operand.z
  def xy: Dist2 = new Dist2(xMetres, yMetres)
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
  def zPos: Boolean = z.pos
  def zNeg: Boolean = z.neg
  def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg)
  def / (operator: Dist): Vec3 = Vec3(x / operator, y / operator, z / operator)
  def toXYIfZPositive: Option[Dist2] = ifZPos(Some(Dist2(x, y)), None)
  def xRotation(rotation: Double): Dist3 =
  { val scalar: Dist = Dist(sqrt(y.metres * y.metres + z.metres * z.metres))
    if(scalar > EarthEquatorialRadius * 1.05) throw excep("scalar: " + scalar.toString)

    val ang0 = ife2(//As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      z.neg && y.neg, atan(y / z) - Pi,
      z.neg,          Pi + atan(y / z), //The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
                      atan(y / z))//This operates on the standard atan range -Pi/2 to pi/2

    val ang1 = ang0 + rotation
    Dist3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }
}

/** Companion object for the Dist3 class. */
object Dist3
{ 
  def metres(xMetres: Double, yMetres: Double, zMetres: Double): Dist3 = new Dist3(xMetres, yMetres, zMetres)
  def apply(x: Dist, y: Dist, z: Dist): Dist3 = new Dist3(x.metres, y.metres, z.metres)
  implicit object Dist3Persist extends Persist3[Dist, Dist, Dist, Dist3]("Dist3", "x", _.x, "y", _.y, "z", _.z, apply)
  var counter = 0
}
/** Collection class for Dist3s. Not clear if this a Polygon equivalent or a Vec3s equivalent */
class Dist3s(val array: Array[Double]) extends AnyVal with ArrProdDbl3[Dist3]
{ type ThisT = Dist3s
  def unsafeFromArray(array: Array[Double]): ThisT = new Dist3s(array)
  override def typeStr: String = "Dist3s"
  override def newElem(d1: Double, d2: Double, d3: Double): Dist3 = new Dist3(d1, d2, d3)
  /** This methods function is to work on a sequence of 3d points representing a polygon on the surface a globe (eg the Earth). If Z is positive its
   *  on the side of the Earth that the viewer is looking at. Returns z positive dist2 points if 1 or more of the points are z positive. Z negative
   *  points are moved to the horizon. */
  def earthZPositive: GlobedArea =
  {
    existsCount(_.z.pos) match
    { case 0 => GlobedNone
    case n if n == length => GlobedAll(pMap(_.xy))
    case n => GlobedNone
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

object Dist3s
{ implicit val factory: Int => Dist3s = i => new Dist3s(new Array[Double](i * 3))
}