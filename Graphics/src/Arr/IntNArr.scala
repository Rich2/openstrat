/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait IntNArr[A] extends Any with ValueNsArr[A]
{ type ThisT <: IntNArr[A]
  def arrayUnsafe: Array[Int]
  def unsafeFromArray(array: Array[Int]): ThisT
  final override def unsafeNew(length: Int): ThisT = unsafeFromArray(new Array[Int](length * elemvaluesNum))
  def arrLen = arrayUnsafe.length

  /** The number of Ints, that specify / construct an element of this immutable flat Array based collection class. */
  def elemvaluesNum: Int
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[IntNArr]] final classes. Instances for the [[ArrTBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrTFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait IntNsArrBuilders[B, ArrB <: IntNArr[B]] extends ValueNsArrBuilders[B, ArrB]
{ type BuffT <:  IntNsBuffer[B]
  def fromIntArray(inp: Array[Int]): ArrB

  /* Not sure about the return type of this method. */
  def fromIntBuffer(inp: ArrayBuffer[Int]): BuffT
  final override def newArr(length: Int): ArrB = fromIntArray(new Array[Int](length * elemSize))
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemSize))
  final override def buffToArr(buff: BuffT): ArrB = fromIntArray(buff.buffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.buffer.addAll(arr.arrayUnsafe); () }
}

/** Specialised flat ArrayBuffer[Int] based collection class. */
trait IntNsBuffer[A] extends Any with ValueNsBuffer[A]
{ type ArrT <: IntNArr[A]
  def buffer: ArrayBuffer[Int]
  def toArray: Array[Int] = buffer.toArray[Int]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { buffer.addAll(newElems.arrayUnsafe); () }
  override def elemsLen = buffer.length / elemSize

}

/**  Class to persist specialised flat Array[Int] based collections. */
abstract class IntNsArrPersist[A, M <: IntNArr[A]](typeStr: String) extends ValueNsArrPersist[A, M](typeStr)
{ type VT = Int
  override def fromBuffer(buf: Buff[Int]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Int] = Buff[Int](0)
}

/** Helper trait for Companion objects of IntNArr classes. */
trait IntNArrCompanion[M]
{ def fromBuffer(buff: Buff[Int]): M = fromArray(buff.toArray[Int])
  val factory: Int => M
  def fromArray(array: Array[Int]): M
}