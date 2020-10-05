/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** Reflect Axis type class. It has two methods to reflect across the X and the Y axes. This has been created as a separate typeclass to
 * [[ReflectAxesOffset]], as these transformations may preserve types that ReflectAxisOffset's transformations can not. */
trait TransAxes[T]
{ /** Reflect, mirror an object of type T across the X axis, by negating Y. */
  def negYT(obj: T): T

  /** Reflect, mirror an object of type T across the Y axis by negating X. */
  def negXT(obj: T): T

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees an object of type T clockwise 2D geometric transformation. */
  def rotate90T(obj: T): T

  /** Rotate 180 degrees 2D geometric an object of type T transformation. */
  def rotate180T(obj: T): T

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise an object of type T 2D geometric transformation. */
  def rotate270T(obj: T): T
}

/** Companion object for the [[TransAxes]] typeclass trait, contains instances for common container objects including Functor instances. */
object TransAxes
{
  implicit def transAlignerImplicit[T <: SimilarPreserve]: TransAxes[T] = new TransAxes[T]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: T): T = obj.negY.asInstanceOf[T]

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: T): T = obj.negX.asInstanceOf[T]

    /** Rotate 90 degrees anti clockwise or rotate 270 degrees an object of type T clockwise 2D geometric transformation. */
    def rotate90T(obj: T): T = obj.rotate90.asInstanceOf[T]

    /** Rotate 180 degrees 2D geometric an object of type T transformation. */
    def rotate180T(obj: T): T = obj.rotate180.asInstanceOf[T]

    /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise an object of type T 2D geometric transformation. */
    def rotate270T(obj: T): T = obj.rotate270.asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], evA: TransAxes[A]): TransAxes[AA] = new TransAxes[AA]
  { /** Reflect, mirror across the X axis. */
    override def negYT(obj: AA): AA = obj.map(evA.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: AA): AA = obj.map(evA.negXT(_))

    /** Rotate 90 degrees anti clockwise or rotate 270 degrees an object of type T clockwise 2D geometric transformation. */
    override def rotate90T(obj: AA): AA = ???

    /** Rotate 180 degrees 2D geometric an object of type T transformation. */
    override def rotate180T(obj: AA): AA = ???

    /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise an object of type T 2D geometric transformation. */
    override def rotate270T(obj: AA): AA = ???
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAxes[A]): TransAxes[F[A]] = new TransAxes[F[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: F[A]): F[A] = evF.mapT(obj, evA.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: F[A]): F[A] = evF.mapT(obj, evA.negXT(_))

    /** Rotate 90 degrees anti clockwise or rotate 270 degrees an object of type T clockwise 2D geometric transformation. */
    override def rotate90T(obj: F[A]): F[A] = ???

    /** Rotate 180 degrees 2D geometric an object of type T transformation. */
    override def rotate180T(obj: F[A]): F[A] = ???

    /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise an object of type T 2D geometric transformation. */
    override def rotate270T(obj: F[A]): F[A] = ???
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransAxes[A]): TransAxes[Array[A]] = new TransAxes[Array[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: Array[A]): Array[A] = obj.map(ev.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: Array[A]): Array[A] = obj.map(ev.negXT(_))

    /** Rotate 90 degrees anti clockwise or rotate 270 degrees an object of type T clockwise 2D geometric transformation. */
    override def rotate90T(obj: Array[A]): Array[A] = ???

    /** Rotate 180 degrees 2D geometric an object of type T transformation. */
    override def rotate180T(obj: Array[A]): Array[A] = ???

    /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise an object of type T 2D geometric transformation. */
    override def rotate270T(obj: Array[A]): Array[A] = ???
  }
}

/** Class to provide extension methods for TransAxes typeclass. */
class TransAxesExtension[A](thisReflector: A)(implicit ev: TransAxes[A])
{
  /** Reflect, mirror across the X axis by negating Y. */
  @inline def negY: A = ev.negYT(thisReflector)

  /** Reflect, mirror across the Y axis by negating X. */
  @inline def negX: A = ev.negXT(thisReflector)
  
  @inline def negXY: A = ev.negXT(ev.negYT(thisReflector))
}