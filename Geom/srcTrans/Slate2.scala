/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait Slate2Like[A, B]
{
  /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slate(obj: A, operand: VecPt2): B

  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters, on an object of type T, returning an object of type T. For many types
   * the implementation of this method will delegate to the object itself. */
  def slateXY(obj: A, xOperand: Double, yOperand: Double): B

  /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slateFrom(obj: A, operand: VecPt2): B

  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters, on an object of type T, returning an object of type T. For many types
   * the implementation of this method will delegate to the object itself. */
  def slateFromXY(obj: A, xOperand: Double, yOperand: Double): B

  /** Translate 2D geometric transformation along the X axis, on an object of type T, returning an object of type T. */
  def slateX(obj: A, xOperand: Double): B

  /** Translate 2D geometric transformation along the Y axis, on an object of type T, returning an object of type T. */
  def slateY(obj: A, yOperand: Double): B
}

object Slate2Like
{ /** Subtype type class instances / evidence for [[Slate2Like]]. */
  given subTypesEv[A, B >: A](using ev: Slate2[B]): Slate2Like[A, B] = new Slate2Like[A, B]
  { override def slate(obj: A, operand: VecPt2): B = ev.slate(obj, operand)
    override def slateXY(obj: A, xOperand: Double, yOperand: Double): B = ev.slateXY(obj, xOperand, yOperand)
    override def slateFrom(obj: A, operand: VecPt2): B = ev.slateFrom(obj, operand)
    override def slateFromXY(obj: A, xOperand: Double, yOperand: Double): B = ev.slateFromXY(obj, xOperand, yOperand)
    override def slateX(obj: A, xOperand: Double): B = ev.slateX(obj, xOperand)
    override def slateY(obj: A, yOperand: Double): B = ev.slateY(obj, yOperand)
  }

  /** Implicit [[Slate]] instance / evidence for [[RArr]]. */
  given rArrEv[A, B](using evAB: Slate2Like[A, B], ctB: ClassTag[B]): Slate2Like[RArr[A], RArr[B]] = new Slate2Like[RArr[A], RArr[B]]
  { override def slate(obj: RArr[A], operand: VecPt2): RArr[B] = obj.map(evAB.slate(_, operand))
    override def slateXY(obj: RArr[A], xOperand: Double, yOperand: Double): RArr[B] = obj.map(evAB.slateXY(_, xOperand, yOperand))
    override def slateFrom(obj: RArr[A], operand: VecPt2): RArr[B] = obj.map(evAB.slateFrom(_, operand))
    override def slateFromXY(obj: RArr[A], xOperand: Double, yOperand: Double): RArr[B] = obj.map(evAB.slateFromXY(_, xOperand, yOperand))
    override def slateX(obj: RArr[A], xOperand: Double): RArr[B] = obj.map(evAB.slateX(_, xOperand))
    override def slateY(obj: RArr[A], yOperand: Double): RArr[B] = obj.map(evAB.slateY(_, yOperand))
  }

  given transSimerEv[T <: SimilarPreserve]: Slate2[T] = new Slate2[T] {
    override def slate(obj: T, operand: VecPt2): T = obj.slate(operand).asInstanceOf[T]

    override def slateXY(obj: T, xOperand: Double, yOperand: Double): T = obj.slate(xOperand, yOperand).asInstanceOf[T]

    override def slateFrom(obj: T, operand: VecPt2): T = obj.slateFrom(operand).asInstanceOf[T]

    override def slateFromXY(obj: T, xOperand: Double, yOperand: Double): T = obj.slateFrom(xOperand, yOperand).asInstanceOf[T]

    override def slateX(obj: T, xOperand: Double): T = obj.slateX(xOperand).asInstanceOf[T]

    override def slateY(obj: T, yOperand: Double): T = obj.slateY(yOperand).asInstanceOf[T]
  }

  /** Implicit [[Slate]] instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  given functorEv[F[_], A, B](using evAB: Slate2Like[A, B], evF: Functor[F]): Slate2Like[F[A], F[B]] = new Slate2Like[F[A], F[B]]
  { override def slate(obj: F[A], operand: VecPt2): F[B] = evF.mapT(obj, evAB.slate(_, operand))
    override def slateXY(obj: F[A], xOperand: Double, yOperand: Double): F[B] = evF.mapT(obj, evAB.slateXY(_, xOperand, yOperand))
    override def slateFrom(obj: F[A], operand: VecPt2): F[B] = evF.mapT(obj, evAB.slateFrom(_, operand))
    override def slateFromXY(obj: F[A], xOperand: Double, yOperand: Double): F[B] = evF.mapT(obj, evAB.slateFromXY(_, xOperand, yOperand))
    override def slateX(obj: F[A], xOperand: Double): F[B] = evF.mapT(obj, evAB.slateX(_, xOperand))
    override def slateY(obj: F[A], yOperand: Double): F[B] = evF.mapT(obj, evAB.slateY(_, yOperand))
  }

  /** Implicit [[SlateXY]] instance / evidence for [[Array]]. */
  given arrayEv[A, B](using ev: Slate2Like[A, B], ct: ClassTag[B]): Slate2Like[Array[A], Array[B]] = new Slate2Like[Array[A], Array[B]]
  { override def slate(obj: Array[A], operand: VecPt2): Array[B] = obj.map(ev.slate(_, operand))
    override def slateXY(obj: Array[A], xOperand: Double, yOperand: Double): Array[B] = obj.map(ev.slateXY(_, xOperand, yOperand))
    override def slateFrom(obj: Array[A], operand: VecPt2): Array[B] = obj.map(ev.slateFrom(_, operand))
    override def slateFromXY(obj: Array[A], xOperand: Double, yOperand: Double): Array[B] = obj.map(ev.slateFromXY(_, xOperand, yOperand))
    override def slateX(obj: Array[A], xOperand: Double): Array[B] = obj.map(ev.slateX(_, xOperand))
    override def slateY(obj: Array[A], yOperand: Double): Array[B] = obj.map(ev.slateY(_, yOperand))
  }
}

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait Slate2[A] extends Slate2Like[A, A]

/** Companion object for the [[Slate2]] type class. Contains implicit instances for collections and other container classes. */
object Slate2
{


}

/** Extension class for instances of the Slate type class. */
extension[A, B](value: A)(using ev: Slate2Like[A, B])
{ /** Translate 2D geometric transformation extension method, taking a [[Pt2]] or a [[Vec2]] as a parameter, on this object of type T, returning an object of
   * Type T. */
  def slate(operand: VecPt2): B = ev.slate(value, operand)

  /** Translate 2D geometric transformation extension method, taking the X offset and Y offset as parameters, on this object of type T, returning an object of
   * Type T. */
  def slate(xOperand: Double, yOperand: Double): B = ev.slateXY(value, xOperand, yOperand)
  
  /** Translate from 2D geometric transformation extension method, taking a [[Pt2]] or a [[Vec2]] as a parameter, on this object of type T, returning an object of
   * Type T. */
  def slateFrom(operand: VecPt2): B = ev.slateFrom(value, operand)

  /** Translate from 2D geometric transformation extension method, taking the X offset and Y offset as parameters, on this object of type T, returning an object of
   * Type T. */
  def slateFrom(xOperand: Double, yOperand: Double): B = ev.slateFromXY(value, xOperand, yOperand)
  
  /** Translate 2D geometric transformation extension method, along the X axis, on this object of type T, returning an object of Type T. */
  def slateX(xOperand: Double): B = ev.slateXY(value, xOperand, 0)

  /** Translate 2D geometric transformation extension method, along the Y axis, on this object of type T, returning an object of Type T. */
  def slateY(yOperand: Double): B = ev.slateXY(value, 0, yOperand)

  /** Translate 2D geometric transformation extension method, taking a 2-dimensional vector as its operand, specified in terms of its angle and magnitude
   * parameters, on this object of type T, returning an object of Type T. */
  def slateAngle(angle: Angle, magnitude: Double): B = ev.slate(value, angle.toVec2(magnitude))
}