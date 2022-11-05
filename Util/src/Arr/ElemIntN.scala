/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be construct from a fixed number of [[Int]]s can be stored as an Array[Int] of primitive values. */
trait ElemIntN extends Any with ElemValueN
{ /** Performs the side effecting function on each [[Double]] in this Product element. */
  def intForeach(f: Int => Unit): Unit
}

trait IntNSeqLike[A <: ElemIntN] extends Any with ValueNSeqLike[A] with ArrayIntBacked
{ type ThisT <: IntNSeqLike[A]

  /** Constructs the final type of these [[IntNSeqLike]] from an [[Array]][Int]. Mostly you will access this capability from the companion object or
   *  the appropriate builder, but it can be useful to access this from the class itself. */
  def fromArray(array: Array[Int]): ThisT

  /** The length of the Array[Int] backing array. */
  def unsafeLength: Int = unsafeArray.length

  /** Method for creating a new Array[Int] backed collection class of this collection class's final type. */
  final def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Int](length * elemProdSize))

  /** Utility method to append element on to an [[ArrayBuffer]][Int]. End users should rarely need to use this method. */
  def intBufferAppend(buffer: ArrayBuffer[Int], elem: A): Unit
}

trait IntNSeqSpec[A <: ElemIntN] extends Any with IntNSeqLike[A] with ValueNSeqSpec[A] with ArrayIntBacked
{ type ThisT <: IntNSeqSpec[A]

  override def ssReverse: ThisT =
  { val res: ThisT = unsafeSameSize(ssLength)
    ssIForeach({ (i, el) => res.unsafeSetElem(ssLength - 1 - i, el)})
    res
  }
}

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait IntNArr[A <: ElemIntN] extends Any with ValueNArr[A] with IntNSeqLike[A]
{ /** The final type of this Array[Int] backed collection class. */
  type ThisT <: IntNArr[A]

  override final def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(length)
    iForeach({ (i, el) => res.unsafeSetElem(length - 1 - i, el) })
    res
  }

  def tail: ThisT =
  { val newArray = new Array[Int](unsafeLength - elemProdSize)
    iUntilForeach(unsafeLength - elemProdSize) { i => newArray(i) = unsafeArray(i + elemProdSize) }
    fromArray(newArray)
  }

  /** Appends ProductValue collection with the same type of Elements to a new ValueProduct collection. Note the operand collection can have a different
   * type, although it shares the same element type. In such a case, the returned collection will have the type of the operand not this collection. */
  def ++(operand: ThisT)(implicit build: IntNArrMapBuilder[A, ThisT]): ThisT =
  { val newArray: Array[Int] = new Array(unsafeLength + operand.unsafeLength)
    unsafeArray.copyToArray(newArray)
    operand.unsafeArray.copyToArray(newArray, unsafeLength)
    build.fromIntArray(newArray)
  }

  final def filter(f: A => Boolean): ThisT =
  { val buff = new ArrayBuffer[Int](4 * elemProdSize)
    foreach { a => if (f(a)) intBufferAppend(buff, a) }
    fromArray(buff.toArray)
  }
}

trait IntNSeqLikeCommonBuilder[BB] extends ValueNSeqLikeCommonBuilder[BB]
{

}

trait IntNSeqLikeMapBuilder[B <: ElemIntN, BB <: IntNSeqLike[B]] extends IntNSeqLikeCommonBuilder[BB] with ValueNSeqLikeMapBuilder[B, BB]
{
  def fromIntArray(array: Array[Int]): BB
  final override def uninitialised(length: Int): BB = fromIntArray(new Array[Int](length * elemProdSize))
  type BuffT <:  IntNBuff[B]

  /*final override def indexSet(arr: BB, index: Int, value: B): Unit = {
    var ii = 0
    value.intForeach { d => arr.unsafeArray(index * elemProdSize + ii); ii += 1 }
  }*/
}

/** Trait for creating the ArrTBuilder type class instances for [[IntNArr]] final classes. Instances for the [[ArrMapBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait IntNArrMapBuilder[B <: ElemIntN, ArrB <: IntNArr[B]] extends IntNSeqLikeMapBuilder[B, ArrB] with ValueNArrMapBuilder[B, ArrB]
{

  /* Not sure about the return type of this method. */
  def fromIntBuffer(buffer: ArrayBuffer[Int]): BuffT

  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromIntArray(buff.unsafeBuffer.toArray)
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[IntNArr]] final classes. Instances for [[ArrFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait IntNArrFlatBuilder[ArrB <: IntNArr[_]] extends ValueNArrFlatBuilder[ArrB]
{ type BuffT <:  IntNBuff[_]

  /* Constructs an ArrB instance from an [[Array]][Int]. */
  def fromIntArray(array: Array[Int]): ArrB

  /* Constructs a BuffT instance from an [[ArrayBuffer]][Int]. */
  def fromIntBuffer(buffer: ArrayBuffer[Int]): BuffT

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
  override def length = unsafeBuffer.length / elemProdSize
}

/**  Class to persist specialised flat Array[Int] based collections. */
trait IntNSeqLikePersist[A <: ElemIntN, M <: IntNSeqLike[A]] extends ValueNSeqLikePersist[A, M]
{ type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = BuffInt(0)
}

/** Helper trait for Companion objects of [[IntNArr]] collection classes, where the type parameter ArrA is the [[ElemIntN]] type of the of the
 *  collection class. */
trait IntNSeqLikeCompanion[A <: ElemIntN, AA <: IntNSeqLike[A]]
{ /** The number of [[Int]]s that are needed to construct an element of the defining-sequence. */
  def elemNumInts: Int

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an ArrayBuffer[Int]. */
  def fromBuffer(buff: ArrayBuffer[Int]): AA = fromArray(buff.toArray[Int])

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  def fromArray(array: Array[Int]): AA

  /** returns a collection class of type ArrA, whose backing Array[Int] is uninitialised. */
  def uninitialised(length: Int): AA = fromArray(new Array[Int](length * elemNumInts))
}

/** Helper trait for [[IntNBuff]] companion objects. Facilitates factory apply methods. */
trait IntNBuffCompanion[A <: ElemIntN, AA <: IntNBuff[A]]
{ /** apply factory method for [[IntNBuff]] final classes */
  def apply(elems: A*): AA

  /** Number of [[Int]]s required to construct an element */
  def elemNumInts: Int

  def fromBuffer(buffer: ArrayBuffer[Int]): AA
}