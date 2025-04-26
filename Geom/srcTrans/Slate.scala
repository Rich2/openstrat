/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait Slate[A]
{ /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slate(obj: A, operand: VecPt2): A
}

/** Companion object for the [[Slate]] type class. Contains implicit instances for collections and other container classes. */
object Slate
{ given transSimerImplicit[T <: SimilarPreserve]: Slate[T] = (obj, op) => obj.slate(op).asInstanceOf[T]

  /** Implicit [[SlateXY]] instance / evidence for [[RArr]]. */
  given rArrImplicit[A](using ev: Slate[A]): Slate[RArr[A]] = (obj, op) => obj.smap(ev.slate(_, op))

  /** Implicit [[SlateXY]] instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  given functorImplicit[A, F[_]](using evF: Functor[F], evA: Slate[A]): Slate[F[A]] = (obj, op) => evF.mapT(obj, evA.slate(_, op))

  /** Implicit [[SlateXY]] instance / evidence for [[Array]]. */
  given arrayImplicit[A](using ct: ClassTag[A], ev: Slate[A]): Slate[Array[A]] = (obj, op) => obj.map(ev.slate(_, op))
}

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait SlateXY[A]
{ /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters, on an object of type T, returning an object of type T. For many types
   * the implementation of this method will delegate to the object itself. */
  def slateXY(obj: A, xOperand: Double, yOperand: Double): A

  /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slate(obj: A, operand: VecPt2): A = slateXY(obj, operand.x, operand.y)

  /** Translate 2D geometric transformation along the X axis, on an object of type T, returning an object of type T. */
  def SlateX(obj: A, xOperand: Double): A = slateXY(obj, xOperand, 0)

  /** Translate 2D geometric transformation along the Y axis, on an object of type T, returning an object of type T. */
  def SlateY(obj: A, yOperand: Double): A = slateXY(obj, 0, yOperand)
}

/** Companion object for the Slate type class. Contains implicit instances for collections and other container classes. */
object SlateXY
{ given transSimerImplicit[T <: SimilarPreserve]: SlateXY[T] = (obj: T, dx: Double, dy: Double) => obj.slate(dx, dy).asInstanceOf[T]

  /** Implicit [[SlateXY]] instance / evidence for [[RArr]]. */
  given rArrImplicit[A](using ev: SlateXY[A]): SlateXY[RArr[A]] = (obj, dx, dy) => obj.smap(ev.slateXY(_, dx, dy))

  /** Implicit [[SlateXY]] instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  given functorusing[A, F[_]](using evF: Functor[F], evA: SlateXY[A]): SlateXY[F[A]] = (obj, dx, dy) => evF.mapT(obj, evA.slateXY(_, dx, dy))

  /** Implicit [[SlateXY]] instance / evidence for [[Array]]. */
  given arrayImplicit[A](using ct: ClassTag[A], ev: SlateXY[A]): SlateXY[Array[A]] = (obj, dx, dy) => obj.map(ev.slateXY(_, dx, dy))
}

/** Extension class for instances of the Slate type class. */
implicit class SlateExtensions[A](value: A)(implicit ev: Slate[A])
{ /** Translate 2D geometric transformation extension method, taking a [[Pt2]] or a [[Vec2]] as a parameter, on this object of type T, returning an object of
   * Type T. */
  def slate(operand: VecPt2): A = ev.slate(value, operand)
}

/** Extension class for instances of the Slate type class. */
class SlateXYExtensions[A](value: A, ev: SlateXY[A])
{ /** Translate 2D geometric transformation extension method, taking the X offset and Y offset as parameters, on this object of type T, returning an object of
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

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait SlateLen2[A]
{ /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slateT(obj: A, delta: VecPtLen2): A

  /** Translate 2 [[Length]] dimension, geometric transformation, taking the xOffset and yOffset [[Length]]s as parameters, on an object of type T, returning an
   * object of type T. For many types * the implementation of this method will delegate to the object itself. */
  def slateXYT(obj: A, xDelta: Length, yDelta: Length): A
}

/** Companion object for the [[SlateLen2]] type class. Contains implicit instances for collections and other container classes. */
object SlateLen2
{ /** Implicit [[SlateLen2]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  given functorEv[A, F[_]](using evF: Functor[F], evA: SlateLen2[A]): SlateLen2[F[A]] = new SlateLen2[F[A]]
  { override def slateT(obj: F[A], delta: VecPtLen2): F[A] = evF.mapT(obj, evA.slateT(_, delta))
    override def slateXYT(obj: F[A], xDelta: Length, yDelta: Length): F[A] = evF.mapT(obj, evA.slateXYT(_, xDelta, yDelta))
  }

  /** Implicit [[SlateLen2]] type class instances / evidence for [[Array]]. */
  given arrayEv[A](using ct: ClassTag[A], ev: SlateLen2[A]): SlateLen2[Array[A]] = new SlateLen2[Array[A]]
  { override def slateT(obj: Array[A], delta: VecPtLen2): Array[A] = obj.map(ev.slateT(_, delta))
    override def slateXYT(obj: Array[A], xDelta: Length, yDelta: Length): Array[A] = obj.map(ev.slateXYT(_, xDelta, yDelta))
  }

  extension[A](thisArr: RArr[A])(using evA: SlateLen2[A])
  { /** Extension method to translate the elements of this [[RArr]]. */
    def slate(delta: VecPtLen2)(using ClassTag[A]): RArr[A] = thisArr.map(evA.slateT(_, delta))
  }
}