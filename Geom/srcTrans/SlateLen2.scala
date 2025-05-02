/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait SlateLen2[A]
{ /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slateT(obj: A, delta: VecPtLen2): A

  /** Translate 2 [[Length]] dimension, geometric transformation, taking the xOffset and yOffset [[Length]]s as parameters, on an object of type T, returning an
   * object of type T. For many types the implementation of this method will delegate to the object itself. */
  def slateXY(obj: A, xDelta: Length, yDelta: Length): A

  /** Translate 2 [[Length]] dimension, geometric transformation in the X dimension. For many types the implementation of this method will delegate to the
   * object itself. */
  def slateX(obj: A, xDelta: Length): A

  /** Translate 2 [[Length]] dimension, geometric transformation in the Y dimesnion. For many types the implementation of this method will delegate to the
   * object itself. */
  def slateY(obj: A, yDelta: Length): A
}

/** Companion object for the [[SlateLen2]] type class. Contains implicit instances for collections and other container classes. */
object SlateLen2
{ /** Implicit [[SlateLen2]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  given rArrEv[A](using evA: SlateLen2[A], ct: ClassTag[A]): SlateLen2[RArr[A]] = new SlateLen2[RArr[A]]
  { override def slateT(obj: RArr[A], delta: VecPtLen2): RArr[A] = obj.map(evA.slateT(_, delta))
    override def slateXY(obj: RArr[A], xDelta: Length, yDelta: Length): RArr[A] = obj.map(evA.slateXY(_, xDelta, yDelta))
    override def slateX(obj: RArr[A], xDelta: Length): RArr[A] = obj.map(evA.slateX(_, xDelta))
    override def slateY(obj: RArr[A], yDelta: Length): RArr[A] = obj.map(evA.slateY(_, yDelta))
  }

  /** Implicit [[SlateLen2]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  given functorEv[A, F[_]](using evF: Functor[F], evA: SlateLen2[A]): SlateLen2[F[A]] = new SlateLen2[F[A]]
  { override def slateT(obj: F[A], delta: VecPtLen2): F[A] = evF.mapT(obj, evA.slateT(_, delta))
    override def slateXY(obj: F[A], xDelta: Length, yDelta: Length): F[A] = evF.mapT(obj, evA.slateXY(_, xDelta, yDelta))
    override def slateX(obj: F[A], xDelta: Length): F[A] = evF.mapT(obj, evA.slateX(_, xDelta))
    override def slateY(obj: F[A], yDelta: Length): F[A] = evF.mapT(obj, evA.slateY(_, yDelta))
  }

  /** Implicit [[SlateLen2]] type class instances / evidence for [[Array]]. */
  given arrayEv[A](using ct: ClassTag[A], ev: SlateLen2[A]): SlateLen2[Array[A]] = new SlateLen2[Array[A]]
  { override def slateT(obj: Array[A], delta: VecPtLen2): Array[A] = obj.map(ev.slateT(_, delta))
    override def slateXY(obj: Array[A], xDelta: Length, yDelta: Length): Array[A] = obj.map(ev.slateXY(_, xDelta, yDelta))
    override def slateX(obj: Array[A], xDelta: Length): Array[A] = obj.map(ev.slateX(_, xDelta))
    override def slateY(obj: Array[A], yDelta: Length): Array[A] = obj.map(ev.slateY(_, yDelta))
  }
}