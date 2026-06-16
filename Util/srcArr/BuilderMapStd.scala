/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** [[BuilderMap]] that uses the standard library [[collection.mutable.ArrayBuffer]] directly as its Buff type. */
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