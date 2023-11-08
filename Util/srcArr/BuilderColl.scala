/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Builder for a collection of type BB. */
trait BuilderColl[BB]
{ /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compile time wrapped Arraybuffer inheriting from
   * BuffProdHomo. */
  type BuffT

  /** Creates a new empty [[BuffSequ]] with a default capacity of 4 elements. */
  def newBuff(length: Int = 4): BuffT

  /** converts a the buffer type to the target compound class. */
  def buffToSeqLike(buff: BuffT): BB

}

trait BuilderCollMap[B, BB] extends BuilderColl[BB]
{ /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, newElem: B): Unit

  /** Creates a new empty [[SeqLike]] of type BB. */
  def empty: BB
}

object BuilderCollMap
{ implicit def listEv[A]: ListBuilder[A] = new ListBuilder[A]
  implicit def vectorEv[A]: VectorBuilder[A] = new VectorBuilder[A]
}

trait BuilderStdCollMap[A, R] extends BuilderCollMap[A, R]
{ override type BuffT = ArrayBuffer[A]
  override def buffGrow(buff: ArrayBuffer[A], newElem: A): Unit = buff.append(newElem)
  override def newBuff(length: Int = 4): ArrayBuffer[A] = new ArrayBuffer[A](length)
}

class ListBuilder[A] extends BuilderStdCollMap[A, List[A]]{
  override def empty: List[A] = Nil

  override def buffToSeqLike(buff: ArrayBuffer[A]): List[A] = buff.toList
}

class VectorBuilder[A] extends BuilderStdCollMap[A, Vector[A]]
{ override def empty: Vector[A] = Vector[A]()
  override def buffToSeqLike(buff: ArrayBuffer[A]): Vector[A] = buff.toVector
}