/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Int]]s. These are used in [[ArrInt4s]] Array[Int] based collections. */
trait ElemInt4 extends Any with ElemIntN
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[ElemInt4]]s. */
trait ArrInt4s[A <: ElemInt4] extends Any with ArrIntNs[A]
{ override def elemProdSize: Int = 4
  final override def length: Int = unsafeArray.length / 4
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A
  override def indexData(index: Int): A = newElem(unsafeArray(4 * index), unsafeArray(4 * index + 1), unsafeArray(4 * index + 2), unsafeArray(4 * index + 3))
  override def unsafeSetElem(index: Int, elem: A): Unit =
  { unsafeArray(4 * index) = elem.int1;
    unsafeArray(4 * index + 1) = elem.int2
    unsafeArray(4 * index + 2) = elem.int3
    unsafeArray(4 * index + 3) = elem.int4
  }

  def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  def head3: Int = unsafeArray(2)
  def head4: Int = unsafeArray(3)
}

/** A specialised flat ArrayBuffer[Int] based trait for [[ElemInt4]]s collections. */
trait BuffInt4s[A <: ElemInt4, M <: ArrInt4s[A]] extends Any with IntNBuff[A]
{ override def elemProdSize: Int = 4
  final override def length: Int = unsafeBuffer.length / 4
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1).append(newElem.int2).append(newElem.int3).append(newElem.int4); ()}
  def intsToT(i1: Int, i2: Int, i3: Int, i4: Int): A
  override def indexData(index: Int): A = intsToT(unsafeBuffer(index * 4), unsafeBuffer(index * 4 + 1), unsafeBuffer(index * 4 + 2), unsafeBuffer(index * 4 + 3))

  override def unsafeSetElem(i: Int, value: A): Unit =
  { unsafeBuffer(i * 4) = value.int1; unsafeBuffer(i * 4 + 1) = value.int2; unsafeBuffer(i * 4 + 2) = value.int3; unsafeBuffer(i * 4 + 3) = value.int4
  }
}

/** Class for the singleton companion objects of [[ArrInt4s]] final classes to extend. */
abstract class ArrInt4sCompanion[A <: ElemInt4, M <: ArrInt4s[A]]
{ val factory: Int => M
  def buff(initialSize: Int): BuffInt4s[A, M]

  def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = factory(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.unsafeArray(count) = elems(count / 2).int1
      count += 1
      res.unsafeArray(count) = elems(count / 2).int2
      count += 1
      res.unsafeArray(count) = elems(count / 2).int3
      count += 1
      res.unsafeArray(count) = elems(count / 2).int4
      count += 1
    }
    res
  }
}

/**  Class to persist specialised flat Array[Int] based [[ArrInt4s]] collection classes. */
abstract class ArrInt4sPersist[B <: ElemInt4, ArrB <: ArrInt4s[B]](val typeStr: String) extends DataIntNsPersist[B, ArrB]
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: B): Unit =
  { buf += value.int1
    buf += value.int2
    buf += value.int3
    buf += value.int4
  }

  override def syntaxDepthT(obj: ArrB): Int = 3
}