/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for rotation. Normally you will only create instances of [[Rotate]] with its single type parameter. This 2 parameter trait allows for subtype
 * rotations instances to be inferred via the subTypes evidence in the companion object. So for example [[Rect]]s can be rotated, returning an ordinary
 * [[Rectangle]] and a [[Sqlign]] can be rotated, returning an ordinary [[Square]]. */
trait RotateLike[A, B]
{ /** Acts on the object to create the rotation. Normally you will only create instances of [[Rotate]], so the object's type and the return type will be the
   * same. */
  def rotateT(obj: A, angle: AngleVec): B
}

object RotateLike
{ /** [[Functor]] type class instances / evidence for [[RotateLike]]. */
  given functorEv[F[_], A, B](using evF: Functor[F], evA: RotateLike[A, B]): RotateLike[F[A], F[B]] = (obj, op) => evF.mapT(obj, evA.rotateT(_, op))

  /** [[Arr]] type class instances / evidence for [[RotateLike]]. */
  given arrEv[A, B, ArrA <: Arr[A], ArrB <: Arr[B]](using evA: RotateLike[A, B], build: BuilderArrMap[B, ArrB]): RotateLike[ArrA, ArrB] =
    (obj, op) => obj.map(evA.rotateT(_, op))

  /** [[Array]] type class instances / evidence for [[RotateLike]]. */
  given arrayEv[A, B](using ct: ClassTag[B], ev: RotateLike[A, B]): RotateLike[Array[A], Array[B]] = (obj, radians) => obj.map(ev.rotateT(_, radians))

  /** Subtype type class instances / evidence for [[RotateLike]]. */
  given subTypesEv[A, B >: A](using ev: Rotate[B]): RotateLike[A, B] = (obj, op) => ev.rotateT(obj, op)
}

/** Type class for 2D geometric rotation transformations of objects where the type of the object is maintained. Normally the [[RotateLike]] instances that you
 * create will all be [[Rotate]] instances. */
trait Rotate[T] extends RotateLike[T, T]
{ def rotateT(obj: T, angle: AngleVec): T
}

/** Extension class for instances of the Rotate type class. */
implicit class RotateExtensions[A, B](value: A)(using ev: RotateLike[A, B]) extends RotateGenExtensions[B]
{ override def rotateRadians(radians: Double): B = ev.rotateT(value, AngleVec.radians(radians))

  /** Rotate (2D geometric transformation) the object by the [[AngleVec]] parameter. */
  def rotate(angle: AngleVec): B = ev.rotateT(value, angle)

  /** Rotate (2D geometric transformation) the object by the value of the parameter in degrees. */
  def rotateDegs(degrees: Double): B = ev.rotateT(value, AngleVec(degrees))

  /** Rotate (2D geometric transformation) the object in a clockwise direction by the value of the parameter in degrees. */
  def rotateClkDegs(degrees: Double): B = ev.rotateT(value, AngleVec(-degrees))
}

trait RotateGenExtensions[B]
{
  def rotateRadians(radians: Double): B
  def rotate(angle: AngleVec): B

  /** Rotates 15 degrees anti-clockwise or + Pi/12 */
  def rotate15: B = rotate(DegVec15)
  
  /** Rotates 30 degrees anti-clockwise or + Pi/6 */
  def rotate30: B = rotate(DegVec30)
  
  /** Rotates 45 degrees anti-clockwise or + Pi/4 */
  def rotate45: B = rotate(DegVec45)
  
  /** Rotates 60 degrees anti-clockwise or + Pi/3 */
  def rotate60: B  = rotate(DegVec60)
  
  /** Rotates 120 degrees anti-clockwise or + 2 * Pi/3 */
  def rotate120: B = rotate(DegVec120)
  
  /** Rotates 135 degrees anti-clockwise or + 3 * Pi/4 */
  def rotate135: B = rotate(DegVec135)
  
  /** Rotates 150 degrees anti-clockwise or + 5 * Pi/6 */
  def rotate150: B = rotate(DegVec150)
  
  /** Rotates 30 degrees clockwise or - Pi/3 */
  def clk30: B = rotate(-DegVec30)
  
  /** Rotates 45 degrees clockwise or - Pi/4 */
  def clk45: B = rotate(-DegVec45)
  
  /** Rotates 60 degrees clockwise or - Pi/3 */
  def clk60: B  = rotate(-DegVec60)

  /** Rotates 120 degrees clockwise or - 2 * Pi/3 */
  def clk120: B = rotate(-DegVec120)
  
  /** Rotates 135 degrees clockwise or - 3 * Pi/ 4 */
  def clk135: B = rotate(-DegVec135)
  
  /** Rotates 150 degrees clockwise or - 5 * Pi/ 6 */
  def clk150: B = rotate(-DegVec150)

  def clk90: B  = rotate(DegVec270)

  def clk270: B  = rotate(DegVec90)
}