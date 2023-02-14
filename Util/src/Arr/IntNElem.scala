/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** A class that can be construct from a fixed number of [[Int]]s. Because of the fixed length of these elements they can be be stored as and
 * reconstructed from a single Array[Int] of primitive values. */
trait IntNElem extends Any with ValueNElem
{ /** Performs the side effecting function on each [[Double]] in this Product element. */
  def intForeach(f: Int => Unit): Unit
}

trait IntNSeqLike[A <: IntNElem] extends Any with ValueNSeqLike[A] with ArrayIntBacked
{ type ThisT <: IntNSeqLike[A]

  /** Constructs the final type of these [[IntNSeqLike]] from an [[Array]][Int]. Mostly you will access this capability from the companion object or
   *  the appropriate builder, but it can be useful to access this from the class itself. */
  def fromArray(array: Array[Int]): ThisT

  /** Method for creating a new Array[Int] backed collection class of this collection class's final type. */
  final def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Int](length * elemProdSize))

  /** Utility method to append element on to an [[ArrayBuffer]][Int]. End users should rarely need to use this method. */
  def intBufferAppend(buffer: ArrayBuffer[Int], elem: A): Unit
}

trait IntNSeqSpec[A <: IntNElem] extends Any with IntNSeqLike[A] with ValueNSeqSpec[A] with ArrayIntBacked
{ type ThisT <: IntNSeqSpec[A]

  override def ssReverse: ThisT =
  { val res: ThisT = unsafeSameSize(ssLength)
    ssIForeach({ (i, el) => res.setElemUnsafe(ssLength - 1 - i, el)})
    res
  }
}

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait IntNArr[A <: IntNElem] extends Any with ValueNArr[A] with IntNSeqLike[A]
{ /** The final type of this Array[Int] backed collection class. */
  type ThisT <: IntNArr[A]

  final override def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(length)
    iForeach({ (i, el) => res.setElemUnsafe(length - 1 - i, el) })
    res
  }

  final override def drop(n: Int): ThisT =
  { val nn = n.max0
    val newArray = new Array[Int]((unsafeLength - elemProdSize * nn).max0)
    iUntilForeach(unsafeLength - elemProdSize * nn) { i => newArray(i) = unsafeArray(i + elemProdSize * nn) }
    fromArray(newArray)
  }

  final override def dropRight(n: Int): ThisT =
  { val nn = n.max0
    val newArray = new Array[Int]((unsafeLength - elemProdSize * nn).max0)
    iUntilForeach(unsafeLength - elemProdSize * nn) { i => newArray(i) = unsafeArray(i) }
    fromArray(newArray)
  }

  @targetName("appendArr") final override def ++(operand: ThisT): ThisT =
  { val newArray: Array[Int] = new Array(unsafeLength + operand.unsafeLength)
    unsafeArray.copyToArray(newArray)
    operand.unsafeArray.copyToArray(newArray, unsafeLength)
    fromArray(newArray)
  }

  final def filter(f: A => Boolean): ThisT =
  { val buff = new ArrayBuffer[Int](4 * elemProdSize)
    foreach { a => if (f(a)) intBufferAppend(buff, a) }
    fromArray(buff.toArray)
  }
}

trait IntNSeqLikeCommonBuilder[BB <: SeqLike[_]] extends ValueNSeqLikeCommonBuilder[BB]
{ type BuffT <:  IntNBuff[_]
  def fromIntBuffer(buffer: ArrayBuffer[Int]): BuffT
  def fromIntArray(array: Array[Int]): BB
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
}

trait IntNSeqLikeMapBuilder[B <: IntNElem, BB <: IntNSeqLike[B]] extends IntNSeqLikeCommonBuilder[BB] with ValueNSeqLikeMapBuilder[B, BB]
{ type BuffT <:  IntNBuff[B]
  final override def uninitialised(length: Int): BB = fromIntArray(new Array[Int](length * elemProdSize))
  final override def buffToSeqLike(buff: BuffT): BB = fromIntArray(buff.unsafeBuffer.toArray)
}

trait IntNSeqLikeFlatBuilder[BB <: IntNSeqLike[_]] extends IntNSeqLikeCommonBuilder[BB] with ValueNSeqLikeFlatBuilder[BB]

/** Trait for creating the ArrTBuilder type class instances for [[IntNArr]] final classes. Instances for the [[ArrMapBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait IntNArrMapBuilder[B <: IntNElem, ArrB <: IntNArr[B]] extends IntNSeqLikeMapBuilder[B, ArrB] with ValueNArrMapBuilder[B, ArrB]

/** Trait for creating the ArrTFlatBuilder type class instances for [[IntNArr]] final classes. Instances for [[ArrFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait IntNArrFlatBuilder[ArrB <: IntNArr[_]] extends IntNSeqLikeCommonBuilder[ArrB] with ValueNArrFlatBuilder[ArrB]
{  final override def buffToSeqLike(buff: BuffT): ArrB = fromIntArray(buff.unsafeBuffer.toArray)
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Specialised flat ArrayBuffer[Int] based collection class. */
trait IntNBuff[A <: IntNElem] extends Any with ValueNBuff[A]
{ type ArrT <: IntNArr[A]
  def unsafeBuffer: ArrayBuffer[Int]
  def toArray: Array[Int] = unsafeBuffer.toArray[Int]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuffer.addAll(newElems.unsafeArray); () }
  override def length = unsafeBuffer.length / elemProdSize
}

/**  Class to persist specialised flat Array[Int] based collections. */
trait IntNSeqLikePersist[A <: IntNElem, M <: IntNSeqLike[A]] extends ValueNSeqLikePersist[A, M]
{ type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = BuffInt(0)
}

/** Helper trait for Companion objects of [[IntNArr]] collection classes, where the type parameter ArrA is the [[IntNElem]] type of the of the
 *  collection class. */
trait IntNSeqLikeCompanion[A <: IntNElem, AA <: IntNSeqLike[A]]
{ /** The number of [[Int]]s that are needed to construct an element of the defining-sequence. */
  def elemNumInts: Int

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an ArrayBuffer[Int]. */
  def fromBuffer(buffer: ArrayBuffer[Int]): AA = fromArray(buffer.toArray[Int])

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  def fromArray(array: Array[Int]): AA

  def fromBuff(buff: IntNBuff[A]): AA = fromArray(buff.unsafeBuffer.toArray)

  /** returns a collection class of type ArrA, whose backing Array[Int] is uninitialised. */
  def uninitialised(length: Int): AA = fromArray(new Array[Int](length * elemNumInts))
}

/** Helper trait for [[IntNBuff]] companion objects. Facilitates factory apply methods. */
trait IntNBuffCompanion[A <: IntNElem, AA <: IntNBuff[A]]
{ /** apply factory method for [[IntNBuff]] final classes */
  def apply(elems: A*): AA

  /** Number of [[Int]]s required to construct an element */
  def elemNumInts: Int

  def fromBuffer(buffer: ArrayBuffer[Int]): AA
}