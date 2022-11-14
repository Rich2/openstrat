/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/** Trait for Array[Int] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Int]s. */
trait ArrayIntBacked extends Any
{ /** The backing Array[Int] of this collection class. End users should not normally need to interact with this directly. */
  def unsafeArray: Array[Int]
}

/** Base trait for collections of elements that are based on [[array]][Int]s, backed by an underlying Array[Array[Int]]. */
trait ArrayIntBackedArr[A <: ArrayIntBacked] extends Any with Arr[A]
{ type ThisT <: ArrayIntBackedArr[A]
  def unsafeArrayOfArrays: Array[Array[Int]]
  override final def length: Int = unsafeArrayOfArrays.length
  def unsafeFromArrayArray(array: Array[Array[Int]]): ThisT
  final def unsafeSameSize(length: Int): ThisT = unsafeFromArrayArray(new Array[Array[Int]](length))
  final def unsafeSetElem(i: Int, value: A): Unit = unsafeArrayOfArrays(i) = value.unsafeArray
}

/** This is the builder for Arrays Arrays of Int. It is not the builder for Arrays of Int.  */
trait ArrayIntArrBuilder[A <: ArrayIntBacked, ArrT <: ArrayIntBackedArr[A]] extends ArrMapBuilder[A, ArrT]
{ @inline def fromArray(array: Array[Array[Int]]): ArrT
  type BuffT <: ArrayIntBuff[A]
  @inline override def uninitialised(length: Int): ArrT = fromArray(new Array[Array[Int]](length))
  override def indexSet(seqLike: ArrT, index: Int, value: A): Unit = seqLike.unsafeArrayOfArrays(index) = value.unsafeArray
  override def buffToSeqLike(buff: BuffT): ArrT = fromArray(buff.unsafeBuffer.toArray)
  override def buffGrow(buff: BuffT, value: A): Unit = { buff.unsafeBuffer.append(value.unsafeArray); () }
}

class ArrArrayIntEq[A <: ArrayIntBacked, ArrT <: ArrayIntBackedArr[A]] extends EqT[ArrT]
{ override def eqT(a1: ArrT, a2: ArrT): Boolean = if (a1.length != a2.length) false
else a1.iForAll((i, el1) =>  el1.unsafeArray === a2(i).unsafeArray)
}

object ArrArrayIntEq
{ def apply[A <: ArrayIntBacked, ArrT <: ArrayIntBackedArr[A]]: ArrArrayIntEq[A, ArrT] = new ArrArrayIntEq[A, ArrT]
}

/** This is a buffer class for Arrays of Int. It is not a Buffer class for Arrays. */
trait ArrayIntBuff[A <: ArrayIntBacked] extends Any with Buff[A]
{ /** Constructs an element of this [[Buff]] from an [[Array]][Int]. */
  def fromArrayInt(array: Array[Int]): A

  def unsafeBuffer: ArrayBuffer[Array[Int]]
  override final def length: Int = unsafeBuffer.length
  final override def grow(newElem: A): Unit = unsafeBuffer.append(newElem.unsafeArray)
  inline final override def apply(index: Int): A = fromArrayInt(unsafeBuffer(index))
  def arrayArrayInt: Array[Array[Int]] = unsafeBuffer.toArray
}


trait ArrayIntBackedPair[A1 <: ArrayIntBacked, A2] extends ElemPair[A1, A2]
{ /** The backing Array of Ints for A1 component. */
  def a1ArrayInt: Array[Int]
}

trait ArrayIntBackedPairArr[A1 <: ArrayIntBacked, ArrA1 <: Arr[A1], A2, A <: ArrayIntBackedPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]
{ /** The backing Array for the A1 components. */
  def a1Arrays: Array[Array[Int]]

  /** Constructs an A1 form an Array[Int]. */
  def a1FromArrayInt(array: Array[Int]): A1

  /** Constructs the final class from an Array of Arrays of Ints and an Array[A2]. */
  def fromArrays(array1: Array[Array[Int]], array2: Array[A2]): ThisT

  /** Constructs a [[ElemPair]] from an Array[Int and an A2 value. */
  def elemFromComponents(a1: Array[Int], a2: A2): A

  final override def a1Index(index: Int): A1 = a1FromArrayInt(a1Arrays(index))
  final override def unsafeSetElem(i: Int, value: A): Unit = { a1Arrays(i) = value.a1ArrayInt; a2Array(i) = value.a2 }
  final override def apply(index: Int): A = elemFromComponents(a1Arrays(index), a2Array(index))

  /** Returns a new uninitialised [[PairArr]] of the same final type. */
  final override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ThisT = fromArrays(new Array[Array[Int]](length), new Array[A2](length))

  final override def unsafeSetA1(index: Int, value: A1): Unit = { a1Arrays(index) = value.unsafeArray }
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
