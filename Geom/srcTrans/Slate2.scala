/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait Slate2[A]
{ /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slate(obj: A, operand: VecPt2): A

  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters, on an object of type T, returning an object of type T. For many types
   * the implementation of this method will delegate to the object itself. */
  def slateXY(obj: A, xOperand: Double, yOperand: Double): A

  /** Translate 2D geometric transformation along the X axis, on an object of type T, returning an object of type T. */
  def slateX(obj: A, xOperand: Double): A

  /** Translate 2D geometric transformation along the Y axis, on an object of type T, returning an object of type T. */
  def slateY(obj: A, yOperand: Double): A
}

/** Companion object for the [[Slate2]] type class. Contains implicit instances for collections and other container classes. */
object Slate2
{
  given transSimerEv[T <: SimilarPreserve]: Slate2[T] = new Slate2[T]
  { override def slate(obj: T, operand: VecPt2): T = obj.slate(operand).asInstanceOf[T]
    override def slateXY(obj: T, xOperand: Double, yOperand: Double): T = obj.slate(xOperand, yOperand).asInstanceOf[T]
    override def slateX(obj: T, xOperand: Double): T = obj.slateX(xOperand).asInstanceOf[T]
    override def slateY(obj: T, yOperand: Double): T = obj.slateY(yOperand).asInstanceOf[T]
  }

  /** Implicit [[Slate]] instance / evidence for [[RArr]]. */
  given rArrEv[A](using ev: Slate2[A]): Slate2[RArr[A]] = new Slate2[RArr[A]]
  { override def slate(obj: RArr[A], operand: VecPt2): RArr[A] = obj.smap(ev.slate(_, operand))
    override def slateXY(obj: RArr[A], xOperand: Double, yOperand: Double): RArr[A] = obj.smap(ev.slateXY(_, xOperand, yOperand))
    override def slateX(obj: RArr[A], xOperand: Double): RArr[A] = obj.smap(ev.slateX(_, xOperand))
    override def slateY(obj: RArr[A], yOperand: Double): RArr[A] = obj.smap(ev.slateY(_, yOperand))
  }

  /** Implicit [[Slate]] instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  given functorEv[A, F[_]](using evF: Functor[F], evA: Slate2[A]): Slate2[F[A]] = new Slate2[F[A]]
  { override def slate(obj: F[A], operand: VecPt2): F[A] = evF.mapT(obj, evA.slate(_, operand))
    override def slateXY(obj: F[A], xOperand: Double, yOperand: Double): F[A] = evF.mapT(obj, evA.slateXY(_, xOperand, yOperand))
    override def slateX(obj: F[A], xOperand: Double): F[A] = evF.mapT(obj, evA.slateX(_, xOperand))
    override def slateY(obj: F[A], yOperand: Double): F[A] = evF.mapT(obj, evA.slateY(_, yOperand))
  }

  /** Implicit [[SlateXY]] instance / evidence for [[Array]]. */
  given arrayEv[A](using ct: ClassTag[A], ev: Slate2[A]): Slate2[Array[A]] = new Slate2[Array[A]]
  { override def slate(obj: Array[A], operand: VecPt2): Array[A] = obj.map(ev.slate(_, operand))
    override def slateXY(obj: Array[A], xOperand: Double, yOperand: Double): Array[A] = obj.map(ev.slateXY(_, xOperand, yOperand))
    override def slateX(obj: Array[A], xOperand: Double): Array[A] = obj.map(ev.slateX(_, xOperand))
    override def slateY(obj: Array[A], yOperand: Double): Array[A] = obj.map(ev.slateY(_, yOperand))
  }
}

/** Extension class for instances of the Slate type class. */
extension[A](value: A)(using ev: Slate2[A])
{ /** Translate 2D geometric transformation extension method, taking a [[Pt2]] or a [[Vec2]] as a parameter, on this object of type T, returning an object of
   * Type T. */
  def slate(operand: VecPt2): A = ev.slate(value, operand)

  /** Translate 2D geometric transformation extension method, taking the X offset and Y offset as parameters, on this object of type T, returning an object of
   * Type T. */
  def slate(xOperand: Double, yOperand: Double): A = ev.slateXY(value, xOperand, yOperand)

  /** Translate 2D geometric transformation extension method, along the X axis, on this object of type T, returning an object of Type T. */
  def slateX(xOperand: Double): A = ev.slateXY(value, xOperand, 0)

  /** Translate 2D geometric transformation extension method, along the Y axis, on this object of type T, returning an object of Type T. */
  def slateY(yOperand: Double): A = ev.slateXY(value, 0, yOperand)

  /** Translate 2D geometric transformation extension method, taking a 2-dimensional vector as its operand, specified in terms of its angle and magnitude
   * parameters, on this object of type T, returning an object of Type T. */
  def slateAngle(angle: Angle, magnitude: Double): A = ev.slate(value, angle.toVec2(magnitude))
}