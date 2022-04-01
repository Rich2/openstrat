/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be construct from a fixed number of [[Int]]s can be stored as an Array[Int] of primitive values. */
trait ElemIntN extends Any with ElemValueN

trait IntNSeqDef[A <: ElemIntN] extends Any with ValueNSeqDef[A]
{ type ThisT <: IntNSeqDef[A]

  /** The backing Array[Int] of this collection class. End users should not normally need to interact with this directly. */
  def unsafeArray: Array[Int]

  def fromArray(array: Array[Int]): ThisT

  /** The length of the Array[Int] backing array. */
  def arrLen = unsafeArray.length

  override def reverseData: ThisT =
  { val res: ThisT = unsafeSameSize(sdLength)
    dataIForeach({ (i, el) => res.unsafeSetElem(sdLength - 1 - i, el)})
    res
  }
  /** Method for creating a new Array[Int] backed collection class of this collection class's final type. */
  final override def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Int](length * elemProdSize))

  def tail: ThisT = {
    val newArray = new Array[Int](arrLen - elemProdSize)
    iUntilForeach(0, arrLen - elemProdSize){i => newArray(i) = unsafeArray(i + elemProdSize) }
    fromArray(newArray)
  }
}

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait IntNArr[A <: ElemIntN] extends Any with ValueNArr[A] with IntNSeqDef[A]
{ /** The final type of this Array[Int] backed collection class. */
  type ThisT <: IntNArr[A]

  /** Appends ProductValue collection with the same type of Elements to a new ValueProduct collection. Note the operand collection can have a different
   * type, although it shares the same element type. In such a case, the returned collection will have the type of the operand not this collection. */
  def ++(operand: ThisT)(implicit build: IntNArrBuilder[A, ThisT]): ThisT = {
    val newArray: Array[Int] = new Array(arrLen + operand.arrLen)
    unsafeArray.copyToArray(newArray)
    operand.unsafeArray.copyToArray(newArray, arrLen)
    build.fromIntArray(newArray)
  }

  /** Appends an element to a new ProductValue collection of type N with the same type of Elements. */
  /*def :+[N <: ArrValueNs[A]](operand: A)(implicit factory: Int => N): N =
  { val res = factory(dataLength + 1)
    iForeach((i, elem) => res.unsafeSetElem(i, elem))
    res.unsafeSetElem(dataLength, operand)
    res
  }*/
}

/** Trait for creating the ArrTBuilder type class instances for [[IntNArr]] final classes. Instances for the [[ArrBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait IntNArrBuilder[B <: ElemIntN, ArrB <: IntNArr[B]] extends ValueNArrBuilder[B, ArrB]
{ type BuffT <:  IntNBuff[B]
  def fromIntArray(array: Array[Int]): ArrB

  /* Not sure about the return type of this method. */
  def fromIntBuffer(buffer: Buff[Int]): BuffT
  final override def newArr(length: Int): ArrB = fromIntArray(new Array[Int](length * elemProdSize))
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromIntArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[IntNArr]] final classes. Instances for [[ArrFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait IntNArrFlatBuilder[B <: ElemIntN, ArrB <: IntNArr[B]] extends ValueNArrFlatBuilder[B, ArrB]
{ type BuffT <:  IntNBuff[B]

  /* Constructs an ArrB instance from an [[Array]][Int]. */
  def fromIntArray(array: Array[Int]): ArrB

  /* Constructs a BuffT instance from an [[ArrayBuffer]][Int]. */
  def fromIntBuffer(buffer: Buff[Int]): BuffT

  //final override def newArr(length: Int): ArrB = fromIntArray(new Array[Int](length * elemSize))
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromIntArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Specialised flat ArrayBuffer[Int] based collection class. */
trait IntNBuff[A <: ElemIntN] extends Any with ValueNBuff[A]
{ type ArrT <: IntNArr[A]
  def unsafeBuffer: ArrayBuffer[Int]
  def toArray: Array[Int] = unsafeBuffer.toArray[Int]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuffer.addAll(newElems.unsafeArray); () }
  override def sdLength = unsafeBuffer.length / elemProdSize
}

/**  Class to persist specialised flat Array[Int] based collections. */
trait DataIntNsPersist[A <: ElemIntN, M <: IntNSeqDef[A]] extends ValueNSeqDefPersist[A, M]
{ type VT = Int
  override def fromBuffer(buf: Buff[Int]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Int] = Buff[Int](0)
}

/** Helper trait for Companion objects of [[IntNArr]] collection classes, where the type parameter ArrA is the [[ElemIntN]] type of the of the
 *  collection class. */
trait IntNSeqDefCompanion[A <: ElemIntN, ArrA <: IntNSeqDef[A]] extends ValueNSeqDefCompanion[A, ArrA]
{ /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an ArrayBuffer[Int]. */
  def fromBuffer(buff: Buff[Int]): ArrA = fromArray(buff.toArray[Int])

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  def fromArray(array: Array[Int]): ArrA

  /** returns a collection class of type ArrA, whose backing Array[Int] is uninitialised. */
  override implicit def uninitialised(length: Int): ArrA = fromArray(new Array[Int](length * elemProdSize))
}