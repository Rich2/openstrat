/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer, reflect.ClassTag

/** Trait for Array[Int] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Int]s. */
trait ArrayIntBacked extends Any
{ /** The backing Array[Int] of this collection class. End users should not normally need to interact with this directly. */
  def arrayUnsafe: Array[Int]

  /** The length of the backing Array. */
  final def arrayLen: Int = arrayUnsafe.length
}

/** Base trait for collections of elements that are based on [[array]][Int]s, backed by an underlying Array[Array[Int]]. */
trait ArrArrayInt[A <: ArrayIntBacked] extends Any, Arr[A]
{ type ThisT <: ArrArrayInt[A]
  def arrayOfArraysUnsafe: Array[Array[Int]]
  def elemFromArray(array: Array[Int]): A
  def fromArrayArray(array: Array[Array[Int]]): ThisT

  final override def apply(index: Int): A = elemFromArray(arrayOfArraysUnsafe(index))
  final override def elem(index: Int): A = elemFromArray(arrayOfArraysUnsafe(index))
  final override def length: Int = arrayOfArraysUnsafe.length
  final override def numElems: Int = arrayOfArraysUnsafe.length
  final def unsafeSameSize(length: Int): ThisT = fromArrayArray(new Array[Array[Int]](length))
  final def setElemUnsafe(index: Int, newElem: A): Unit = arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
}

/** This is the builder for Arrays Arrays of Int. It is not the builder for Arrays of Int.  */
trait ArrayIntArrBuilder[A <: ArrayIntBacked, ArrT <: ArrArrayInt[A]] extends BuilderArrMap[A, ArrT]
{ @inline def fromArray(array: Array[Array[Int]]): ArrT
  type BuffT <: ArrayIntBuff[A]
  @inline override def uninitialised(length: Int): ArrT = fromArray(new Array[Array[Int]](length))
  override def indexSet(seqLike: ArrT, index: Int, newElem: A): Unit = seqLike.arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
  override def buffToSeqLike(buff: BuffT): ArrT = fromArray(buff.unsafeBuffer.toArray)
  override def buffGrow(buff: BuffT, newElem: A): Unit = { buff.unsafeBuffer.append(newElem.arrayUnsafe); () }
}

/** Eqt instances for objects that are defined by [[Array]][Int]s. */
class EqArrayIntBacked[ArrT <: ArrayIntBacked] extends EqT[ArrT]
{ override def eqT(a1: ArrT, a2: ArrT): Boolean = a1.arrayUnsafe.sameElements(a2.arrayUnsafe)
}

object EqArrayIntBacked
{ def apply[ArrT <: ArrayIntBacked](): EqArrayIntBacked[ArrT] = new EqArrayIntBacked[ArrT]
}

/** This is a buffer class for Arrays of Int. It is not a Buffer class for Arrays. */
trait ArrayIntBuff[A <: ArrayIntBacked] extends Any, BuffSequ[A]
{ /** Constructs an element of this [[BuffSequ]] from an [[Array]][Int]. */
  def fromArrayInt(array: Array[Int]): A

  def unsafeBuffer: ArrayBuffer[Array[Int]]
  final override def length: Int = unsafeBuffer.length
  final override def numElems: Int = unsafeBuffer.length
  final override def grow(newElem: A): Unit = unsafeBuffer.append(newElem.arrayUnsafe)
  final override def apply(index: Int): A = fromArrayInt(unsafeBuffer(index))
  final override def elem(index: Int): A = fromArrayInt(unsafeBuffer(index))
  def arrayArrayInt: Array[Array[Int]] = unsafeBuffer.toArray
}


trait ArrayIntBackedPair[A1 <: ArrayIntBacked, A2] extends PairFinalA1Elem[A1, A2]
{ /** The backing Array of Ints for A1 component. */
  def a1ArrayInt: Array[Int]
}

trait ArrayIntBackedPairArr[A1 <: ArrayIntBacked, ArrA1 <: Arr[A1], A2, A <: ArrayIntBackedPair[A1, A2]] extends ArrPairFinalA1[A1, ArrA1, A2, A]
{ /** The backing Array for the A1 components. */
  def a1ArrayArrayInts: Array[Array[Int]]

  /** Constructs an A1 form an Array[Int]. */
  def a1FromArrayArrayInt(array: Array[Int]): A1

  /** Constructs the final class from an Array of Arrays of Ints and an Array[A2]. */
  def newFromArrays(array1: Array[Array[Int]], array2: Array[A2]): ThisT

  /** Constructs a [[PairFinalA1Elem]] from an Array[Int and an A2 value. */
  def elemFromComponents(a1: Array[Int], a2: A2): A

  final override def a1Index(index: Int): A1 = a1FromArrayArrayInt(a1ArrayArrayInts(index))
  final override def setElemUnsafe(index: Int, newElem: A): Unit = { a1ArrayArrayInts(index) = newElem.a1ArrayInt; a2Array(index) = newElem.a2 }
  final override def apply(index: Int): A = elemFromComponents(a1ArrayArrayInts(index), a2Array(index))
  final override def elem(index: Int): A = elemFromComponents(a1ArrayArrayInts(index), a2Array(index))
  final override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ThisT = newFromArrays(new Array[Array[Int]](length), new Array[A2](length))

  final override def setA1Unsafe(index: Int, value: A1): Unit = { a1ArrayArrayInts(index) = value.arrayUnsafe }

  override def replaceA1byA2(key: A2, newValue: A1): ThisT =
  { val newA1s = new Array[Array[Int]](length)
    a1ArrayArrayInts.copyToArray(newA1s)
    val res = newFromArrays(newA1s, a2Array)
    var i = 0
    while (i < length) {
      if (key == a2Index(i)) res.setA1Unsafe(i, newValue);
      i += 1
    }
    res
  }

  @targetName("append") final override def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final override def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Array[Int]](length + 1)
    a1ArrayArrayInts.copyToArray(newA1Array)
    newA1Array(length) = a1.arrayUnsafe
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

trait ArrayIntBackedPairArrCompanion[A1 <: ArrayIntBacked]
{
  def elemsToArrays[A2, ArrA](elems: Seq[ArrayIntBackedPair[A1, A2]], f: (Array[Array[Int]], Array[A2]) => ArrA)(implicit ct: ClassTag[A2]):  ArrA =
  { val len = elems.length
    val a1Arrays: Array[Array[Int]] = new Array[Array[Int]](len)
    val a2Array: Array[A2] = new Array[A2](len)
    var i = 0
    while (i < len) { a1Arrays(i) = elems(i).a1ArrayInt; a2Array(i) = elems(i).a2; i += 1 }
    f(a1Arrays, a2Array)
  }
}