/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from 2 [[Int]]s. These are used in [[ArrInt2s]] Array[Int] based collections. */
trait ElemInt2 extends Any with ElemIntN
{ def int1: Int
  def int2: Int
}

/** A specialised immutable, flat Array[Double] based trait defined by a data sequence of a type of [[ElemInt2]]s. */
trait DataInt2s[A <: ElemInt2] extends Any with DataIntNs[A]
{
  override def elemProdSize: Int = 2
  final override def indexData(index: Int): A = dataElem(unsafeArray(2 * index), unsafeArray(2 * index + 1))
  def dataElem(i1: Int, i2: Int): A
  final override def unsafeSetElem(index: Int, elem: A): Unit = { unsafeArray(2 * index) = elem.int1; unsafeArray(2 * index + 1) = elem.int2 }
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[ElemInt2]]s. */
trait ArrInt2s[A <: ElemInt2] extends Any with ArrIntNs[A] with DataInt2s[A]
{ def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  final override def length: Int = unsafeArray.length / 2
}

/** Trait for creating the ArrTBuilder type class instances for [[Int2Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int2Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait ArrInt2sBuilder[B <: ElemInt2, ArrB <: ArrInt2s[B]] extends ArrIntNsBuilder[B, ArrB]
{ type BuffT <: BuffInt2s[B]

  final override def elemProdSize: Int = 2
  def newArray(length: Int): Array[Int] = new Array[Int](length * 2)

  final override def arrSet(arr: ArrB, index: Int, value: B): Unit =
  { arr.unsafeArray(index * 2) = value.int1; arr.unsafeArray(index * 2 + 1) = value.int2
  }
  override def buffGrow(buff: BuffT, value: B): Unit = { buff.unsafeBuffer.append(value.int1); buff.unsafeBuffer.append(value.int2); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Int2Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B a sub class of Int2Elem, because to corresponds to the B in the
 *  ```map(f: A => B): ArrB``` function. */
trait ArrInt2sFlatBuilder[B <: ElemInt2, ArrB <: ArrInt2s[B]] extends ArrIntNsFlatBuilder[B, ArrB]
{ type BuffT <: BuffInt2s[B]
  final override def elemProdSize: Int = 2
  def newArray(length: Int): Array[Int] = new Array[Int](length * 2)
}

/** A specialised flat ArrayBuffer[Int] based trait for [[ElemInt2]]s collections. */
trait BuffInt2s[A <: ElemInt2] extends Any with BuffIntNs[A]
{ type ArrT <: ArrInt2s[A]
  override def elemProdSize: Int = 2
  final override def length: Int = unsafeBuffer.length / 2
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1).append(newElem.int2); () }
  def intsToT(i1: Int, i2: Int): A
  override def indexData(index: Int): A = intsToT(unsafeBuffer(index * 2), unsafeBuffer(index * 2 + 1))
  override def unsafeSetElem(i: Int, value: A): Unit = { unsafeBuffer(i * 4) = value.int1; unsafeBuffer(i * 4 + 1) = value.int2 }
}

/** Helper class for companion objects of final Int2sArr classes. */
abstract class DataInt2sCompanion[A <: ElemInt2, ArrA <: DataInt2s[A]] extends DataIntNsCompanion[A, ArrA]
{
  override def elemProdSize: Int = 2

  /** Apply factory method */
  def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length * 2
    val res = uninitialised(elems.length)
    var count: Int = 0

    while (count < arrLen)
    { res.unsafeArray(count) = elems(count / 2).int1
      count += 1
      res.unsafeArray(count) = elems(count / 2).int2
      count += 1
    }
    res
  }
}