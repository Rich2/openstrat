/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be construct from a fixed number of [[Int]]s can be stored as an Array[Int] of primitive values. */
trait IntNElem extends Any with ValueNElem

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait IntNsArr[A <: IntNElem] extends Any with ValueNsArr[A]
{ /** The final type of this Array[Int] backed collection class. */
  type ThisT <: IntNsArr[A]

  /** The backing Array[Int] of this collection class. End users should not normally need to interact with this directly. */
  def arrayUnsafe: Array[Int]

  def unsafeFromArray(array: Array[Int]): ThisT

  /** Method for creating a new Array[Int] backed collection class of this collection class's final type. */
  final override def unsafeNew(length: Int): ThisT = unsafeFromArray(new Array[Int](length * elemProductNum))

  /** The length of the Array[Int] backing array. */
  def arrLen = arrayUnsafe.length
}
/** Trait for creating the ArrTBuilder type class instances for [[IntNsArr]] final classes. Instances for the [[ArrTBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait IntNsArrBuilder[B <: IntNElem, ArrB <: IntNsArr[B]] extends ValueNsArrBuilder[B, ArrB]
{ type BuffT <:  IntNsBuffer[B]
  def fromIntArray(inp: Array[Int]): ArrB

  /* Not sure about the return type of this method. */
  def fromIntBuffer(inp: ArrayBuffer[Int]): BuffT
  final override def newArr(length: Int): ArrB = fromIntArray(new Array[Int](length * elemSize))
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemSize))
  final override def buffToArr(buff: BuffT): ArrB = fromIntArray(buff.buffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.buffer.addAll(arr.arrayUnsafe); () }
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[IntNsArr]] final classes. Instances for [[ArrTFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait IntNsArrFlatBuilder[B <: IntNElem, ArrB <: IntNsArr[B]] extends ValueNsArrFlatBuilder[B, ArrB]
{ type BuffT <:  IntNsBuffer[B]
  def fromIntArray(inp: Array[Int]): ArrB

  /* Not sure about the return type of this method. */
  def fromIntBuffer(inp: ArrayBuffer[Int]): BuffT
  //final override def newArr(length: Int): ArrB = fromIntArray(new Array[Int](length * elemSize))
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemSize))
  final override def buffToArr(buff: BuffT): ArrB = fromIntArray(buff.buffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.buffer.addAll(arr.arrayUnsafe); () }
}

/** Specialised flat ArrayBuffer[Int] based collection class. */
trait IntNsBuffer[A <: IntNElem] extends Any with ValueNsBuffer[A]
{ type ArrT <: IntNsArr[A]
  def buffer: ArrayBuffer[Int]
  def toArray: Array[Int] = buffer.toArray[Int]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { buffer.addAll(newElems.arrayUnsafe); () }
  override def elemsLen = buffer.length / elemSize

}

/**  Class to persist specialised flat Array[Int] based collections. */
abstract class IntNsArrPersist[A <: IntNElem, M <: IntNsArr[A]](typeStr: String) extends ValueNsArrPersist[A, M](typeStr)
{ type VT = Int
  override def fromBuffer(buf: Buff[Int]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Int] = Buff[Int](0)
}

/** Helper trait for Companion objects of [[IntNArr]] collection classes, where the type parameter ArrA is the [[IntNElem]] type of the of the
 *  collection class. */
trait IntNArrCompanion[A <: IntNElem, ArrA <: IntNsArr[A]] extends ValueNArrCompanion[A, ArrA]
{ /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an ArrayBuffer[Int]. */
  def fromBuffer(buff: Buff[Int]): ArrA = fromArray(buff.toArray[Int])

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  def fromArray(array: Array[Int]): ArrA

  /** returns a collection class of type ArrA, whose backing Array[Int] is uninitialised. */
  override implicit def uninitialised(length: Int): ArrA = fromArray(new Array[Int](length * elemSize))
}