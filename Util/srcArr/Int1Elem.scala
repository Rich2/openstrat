/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from a single [[Int]]. These are used in [[Int1Arr]] Array[Int] based collections. */
trait Int1Elem extends Any with IntNElem
{ /* The single [[int]] value from which the final class can be constructed. */
  def int1: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]) : Unit = { buffer.append(int1) }
}

trait Int1SeqLike[A <: Int1Elem] extends Any with SeqLikeIntN[A]
{ final override def elemProdSize: Int = 1
  final override def setElemUnsafe(index: Int, newElem: A): Unit = { unsafeArray(index) = newElem.int1 }
}

/** A specialised immutable, flat Array[Int] based trait defined by a data sequence of a type of [[Int1Elem]]s. */
trait Int1SeqSpec[A <: Int1Elem] extends Any with Int1SeqLike[A] with SeqSpecIntN[A]
{ final override def ssIndex(index: Int): A = ssElem(unsafeArray(index))

  /** Constructs an element of the specifing sequence from an [[Int]] value. */
  def ssElem(intValue: Int): A
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int1Elem]]s. */
trait Int1Arr[A <: Int1Elem] extends Any with ArrIntN[A] with Int1SeqLike[A]
{ final override def length: Int = unsafeArray.length

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = operand.int1
    fromArray(newArray)
  }

  def newElem(intValue: Int): A
  final override def apply(index: Int): A = newElem(unsafeArray(index))
  final override def elemEq(a1: A, a2: A): Boolean = a1.int1 == a2.int1
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int1Elem]]s collections. */
trait Int1Buff[A <: Int1Elem] extends Any with IntNBuff[A]
{ type ThisT <: Int1Buff[A]

  /** Constructs a new element of this [[Buff]] from a single [[Int]]. */
  def newElem(value: Int): A

  final override def length: Int = unsafeBuffer.length
  def apply(i1: Int): A = newElem(unsafeBuffer(i1))
  override def elemProdSize: Int = 1
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1); () }
  override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer(i) = newElem.int1
}

trait Int1ArrCommonBuilder[ArrB <: Int1Arr[_]] extends IntNSeqLikeCommonBuilder[ArrB]
{ type BuffT <: Int1Buff[_]
  final override def elemProdSize: Int = 1
}

/** Trait for creating the ArrTBuilder type class instances for [[Int1Arr]] final classes. Instances for the [[MapBuilderArr]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to
 *  the B in ```map(f: A => B): ArrB``` function. */
trait Int1ArrMapBuilder[A <: Int1Elem, ArrT <: Int1Arr[A]] extends Int1ArrCommonBuilder[ArrT] with IntNArrMapBuilder[A, ArrT]
{ type BuffT <: Int1Buff[A]
  final override def indexSet(seqLike: ArrT, index: Int, elem: A): Unit =  seqLike.unsafeArray(index) = elem.int1
  final override def buffGrow(buff: BuffT, newElem: A): Unit = { buff.unsafeBuffer.append(newElem.int1); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Int1Arr]] final classes. Instances for the [[MapBuilderArr]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[FlatBuilderArr] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Int1ArrFlatBuilder[ArrT <: Int1Arr[_]] extends Int1ArrCommonBuilder[ArrT] with IntNArrFlatBuilder[ArrT]


/** Helper class for companion objects of final [[Int1SeqSpec]] classes. */
trait Int1SeqLikeCompanion[A <: Int1Elem, ArrA <: Int1SeqLike[A]] extends CompanionSeqLikeIntN[A, ArrA]
{
  final override def elemNumInts: Int = 1

  /** Apply factory method */
  final def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length
    val res = uninitialised(elems.length)
    var count: Int = 0

    while (count < arrLen)
    { res.unsafeArray(count) = elems(count).int1
      count += 1
    }
    res
  }
}