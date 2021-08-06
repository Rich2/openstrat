/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from 2 [[Int]]s. These are used in [[Int2sSeq]] Array[Int] based collections. */
trait Int2Elem extends Any with ElemIntN//ValueNElem
{ def int1: Int
  def int2: Int
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int2Elem]]s. */
trait Int2sData[A <: Int2Elem] extends Any with DataIntNs[A]

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int2Elem]]s. */
trait Int2sSeq[A <: Int2Elem] extends Any with ArrIntNs[A]
{ def newElem(i1: Int, i2: Int): A
  override def elemProdSize: Int = 2
  final override def indexData(index: Int): A = newElem(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))
  final override def unsafeSetElem(index: Int, elem: A): Unit = { arrayUnsafe(2 * index) = elem.int1; arrayUnsafe(2 * index + 1) = elem.int2 }
  def unsafeSetElemInts(index: Int, int1: Int, int2: Int): Unit = { arrayUnsafe(2 * index) = int1; arrayUnsafe(2 * index + 1) = int2 }

  def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
}

/** Trait for creating the ArrTBuilder type class instances for [[Int2Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int2Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int2sArrBuilder[B <: Int2Elem, ArrB <: Int2sSeq[B]] extends ArrIntNsBuilder[B, ArrB]
{ type BuffT <: Int2sBuffer[B, ArrB]

  final override def elemProdSize: Int = 2
  def newArray(length: Int): Array[Int] = new Array[Int](length * 2)

  final override def arrSet(arr: ArrB, index: Int, value: B): Unit =
  { arr.arrayUnsafe(index * 2) = value.int1; arr.arrayUnsafe(index * 2 + 1) = value.int2
  }
  override def buffGrow(buff: BuffT, value: B): Unit = { buff.unsafeBuff.append(value.int1); buff.unsafeBuff.append(value.int2); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Int2Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[SeqFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B a sub class of Int2Elem, because to corresponds to the B in the
 *  ```map(f: A => B): ArrB``` function. */
trait Int2sArrFlatBuilder[B <: Int2Elem, ArrB <: Int2sSeq[B]] extends IntNsArrFlatBuilder[B, ArrB]
{ type BuffT <: Int2sBuffer[B, ArrB]
  final override def elemProdSize: Int = 2
  def newArray(length: Int): Array[Int] = new Array[Int](length * 2)
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int2Elem]]s collections. */
trait Int2sBuffer[A <: Int2Elem, M <: Int2sSeq[A]] extends Any with BuffIntNs[A]
{ type ArrT <: Int2sSeq[A]
  override def elemProdSize: Int = 2
  override def grow(newElem: A): Unit = { unsafeBuff.append(newElem.int1).append(newElem.int2); () }
  def intsToT(i1: Int, i2: Int): A
  override def indexData(index: Int): A = intsToT(unsafeBuff(index * 2), unsafeBuff(index * 2 + 1))
  override def unsafeSetElem(i: Int, value: A): Unit = { unsafeBuff(i * 4) = value.int1; unsafeBuff(i * 4 + 1) = value.int2 }

}

/** Helper class for companion objects of final Int2sArr classes. */
abstract class Int2sArrCompanion[A <: Int2Elem, ArrA <: Int2sSeq[A]] extends DataIntNsCompanion[A, ArrA]
{
  override def elemProdSize: Int = 2

  /** Apply factory method */
  def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length * 2
    val res = uninitialised(elems.length)
    var count: Int = 0

    while (count < arrLen)
    { res.arrayUnsafe(count) = elems(count / 2).int1
      count += 1
      res.arrayUnsafe(count) = elems(count / 2).int2
      count += 1
    }
    res
  }
}