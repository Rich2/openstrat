/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** Builder for a collection of type BB. */
trait BuilderCollection[BB]
{ /** BuffT can be a specialist [[Buff]] class, or it can be an [[ArrayBuffer]]. */
  type BuffT

  /** Creates a new empty [[Buff]] with a default capacity of 4 elements. */
  def newBuff(length: Int = 4): BuffT

  /** converts a buffer of the given type to the target compound class. */
  def buffToSeqLike(buff: BuffT): BB
}

/** Builder for collection via the map method. */
trait BuilderMap[B, BB] extends BuilderCollection[BB]
{ /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, newElem: B): Unit

  /** Creates a new empty [[SeqLike]] of type BB. */
  def empty: BB
}

/** Companion object for [[BuilderMap]] type class, contains various type class instances for common standard library types. */
object BuilderMap
{ /** [[BuilderMap]] type class instance / evidence for the [[List]] class. */
  implicit def listEv[A]: ListBuilder[A] = new ListBuilder[A]

  /** [[BuilderMap]] type class instance / evidence for the [[Vector]] class. */
  implicit def vectorEv[A]: VectorBuilder[A] = new VectorBuilder[A]

  /** [[BuilderMap]] type class instance / evidence for the [[Array]][Int] class. */
  implicit val arrayIntEv: ArrayIntBuilder = new ArrayIntBuilder

  /** [[BuilderMap]] type class instance / evidence for the [[Array]][A] class. */
  implicit def arrayEv[A <: AnyRef](implicit ct: ClassTag[A]): BuilderMapStd[A, Array[A]] = new ArrayBuilder[A]
}

/** [[BuilderMap]] that uses the standard library [[ArrayBuffer]] directly as its Buff type. */
trait BuilderMapStd[A, R] extends BuilderMap[A, R]
{ override type BuffT = ArrayBuffer[A]
  override def buffGrow(buff: ArrayBuffer[A], newElem: A): Unit = buff.append(newElem)
  override def newBuff(length: Int = 4): ArrayBuffer[A] = new ArrayBuffer[A](length)
}

class ListBuilder[A] extends BuilderMapStd[A, List[A]]
{ override def empty: List[A] = Nil
  override def buffToSeqLike(buff: ArrayBuffer[A]): List[A] = buff.toList
}

class VectorBuilder[A] extends BuilderMapStd[A, Vector[A]]
{ override def empty: Vector[A] = Vector[A]()
  override def buffToSeqLike(buff: ArrayBuffer[A]): Vector[A] = buff.toVector
}

class ArrayIntBuilder extends BuilderMapStd[Int, Array[Int]]
{ override def empty: Array[Int] = Array[Int]()
  override def buffToSeqLike(buff: ArrayBuffer[Int]): Array[Int] = buff.toArray
}

class ArrayBuilder[A](implicit val ct: ClassTag[A]) extends BuilderMapStd[A, Array[A]]
{ override def empty: Array[A] = Array[A]()
  override def buffToSeqLike(buff: ArrayBuffer[A]): Array[A] = buff.toArray
}