/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for 2D geometric rotation transformations of objects of type T. */
trait Rotate[T]
{ def rotateT(obj: T, angle: AngleVec): T
}

/** Companion object for the Rotate[T] type class, contains implicit instances for collections and other container classes. */
object Rotate
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: Rotate[T] = (obj, angle) => obj.rotate(angle).asInstanceOf[T]

  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: ArrBuilder[A, AA], ev: Rotate[A]): Rotate[AA] =
    (obj, angle) => obj.map(ev.rotateT(_, angle))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Rotate[A]): Rotate[F[A]] =
    (obj, radians) => evF.mapT(obj, evA.rotateT(_, radians))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Rotate[A]): Rotate[Array[A]] = (obj, radians) => obj.map(ev.rotateT(_, radians))
}

/** Extension class for instances of the Rotate type class. */
class RotateExtensions[T](value: T, ev: Rotate[T]) extends RotateGenExtensions [T]
{ override def rotateRadians(radians: Double): T = ev.rotateT(value, AngleVec.radians(radians))

  /** Rotate (2D geometric transformation) the object by the [[AngleVec]] parameter. */
  def rotate(angle: AngleVec): T = ev.rotateT(value, angle)

  /** Rotate (2D geometric transformation) the object by the value of the parameter in degrees. */
  def rotateDegs(degrees: Double): T = ev.rotateT(value, AngleVec(degrees))

  /** Rotate (2D geometric transformation) the object in a clockwise direction by the value of the parameter in degrees. */
  def rotateClkDegs(degrees: Double): T = ev.rotateT(value, AngleVec(-degrees))
}

trait RotateGenExtensions[T]
{
  def rotateRadians(radians: Double): T
  def rotate(angle: AngleVec): T

  /** Rotates 15 degrees anti-clockwise or + Pi/12 */
  def rotate15: T = rotate(DegVec15)
  
  /** Rotates 30 degrees anti-clockwise or + Pi/6 */
  def rotate30: T = rotate(DegVec30)
  
  /** Rotates 45 degrees anti-clockwise or + Pi/4 */
  def rotate45: T = rotate(DegVec45)
  
  /** Rotates 60 degrees anti-clockwise or + Pi/3 */
  def rotate60: T  = rotate(DegVec60)
  
  /** Rotates 120 degrees anti-clockwise or + 2 * Pi/3 */
  def rotate120: T = rotate(DegVec120)
  
  /** Rotates 135 degrees anti-clockwise or + 3 * Pi/4 */
  def rotate135: T = rotate(DegVec135)
  
  /** Rotates 150 degrees anti-clockwise or + 5 * Pi/6 */
  def rotate150: T = rotate(DegVec150)
  
  /** Rotates 30 degrees clockwise or - Pi/3 */
  def clk30: T = rotate(-DegVec30)
  
  /** Rotates 45 degrees clockwise or - Pi/4 */
  def clk45: T = rotate(-DegVec45)
  
  /** Rotates 60 degrees clockwise or - Pi/3 */
  def clk60: T  = rotate(-DegVec60)

  /** Rotates 120 degrees clockwise or - 2 * Pi/3 */
  def clk120: T = rotate(-DegVec120)
  
  /** Rotates 135 degrees clockwise or - 3 * Pi/ 4 */
  def clk135: T = rotate(-DegVec135)
  
  /** Rotates 150 degrees clockwise or - 5 * Pi/ 6 */
  def clk150: T = rotate(-DegVec150)

  def clk90: T  = rotate(DegVec270)

  def clk270: T  = rotate(DegVec90)
}