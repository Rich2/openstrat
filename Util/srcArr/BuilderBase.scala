/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag

/** Common Builder trait for constructing [[SeqLikeImut]]s of type BB by both the map and flatMap and methods. The flatMap method f: A => BB doesn't indicate
 * the type of the elements of the [[SeqLikeImut]]. Hence, implicit instances for flatMap builders need to go in the companion object of the [[SeqLikeImut]]
 * class. However, the map metjhod, f: A => B does indicate the element type, hence the implicit type class instance for the map builders need to go in the
 * companion object of the type B class. So for example nn element usch as a Pt2, a 2-dimensional point will have its own specialist [[Arr]] class and will need
 * an implicit [[BuilderMap]] instance for the [[Arr]] in the Pt2's companion object. While an implicit [[BuilderFlat]] instance for the [[Arr]] will be
 * required in the companion object of Pt22's specialist [[Arr]] class. Further builder instances will be required to map and flatMpa to polygons and line
 * paths. */
trait BuilderBase[BB]
{ /** BuffT can be a specialist [[Buff]] class, or it can be an [[collection.mutable.ArrayBuffer]]. */
  type BuffT

  /** Creates a new empty [[Buff]] with a default capacity of 4 elements. */
  def newBuff(length: Int = 4): BuffT

  /** converts a buffer of the given type to the target compound class. */
  def buffToSeqLike(buff: BuffT): BB
}

/** Builder for collection via the map method. */
trait BuilderMap[B, BB] extends BuilderBase[BB]
{ /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, newElem: B): Unit

  /** Creates a new empty [[SeqLike]] of type BB. */
  def empty: BB
}

/** Companion object for [[BuilderMap]] type class, contains various type class instances for common standard library types. */
object BuilderMap
{ /** [[BuilderMap]] type class instance / evidence for the [[List]] class. */
  given listEv[A]: ListBuilder[A] = new ListBuilder[A]

  /** [[BuilderMap]] type class instance / evidence for the [[Vector]] class. */
  given vectorEv[A]: VectorBuilder[A] = new VectorBuilder[A]

  /** [[BuilderMap]] type class instance / evidence for the [[Array]][Int] class. */
  implicit val arrayIntEv: ArrayIntBuilder = new ArrayIntBuilder

  /** [[BuilderMap]] type class instance / evidence for the [[Array]][A] class. */
  given arrayEv[A <: AnyRef](using ctA: ClassTag[A]): BuilderMapStd[A, Array[A]] = new ArrayBuilder[A]
}

/** Builder for collection via the flatMap method. */
trait BuilderFlat[BB] extends BuilderBase[BB]