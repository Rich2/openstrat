/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Int]]s. These are used in [[Int4Arr]] Array[Int] based collections. */
trait Int4Elem extends Any with ValueNElem
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int4Elem]]s. */
trait Int4Arr[A <: Int4Elem] extends Any with IntNArr[A]
{
  override def productSize: Int = 4
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A
  def apply(index: Int): A = newElem(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))
  override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(4 * index) = elem.int1;
    arrayUnsafe(4 * index + 1) = elem.int2
    arrayUnsafe(4 * index + 2) = elem.int3
    arrayUnsafe(4 * index + 3) = elem.int4
  }

  def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  def head3: Int = arrayUnsafe(2)
  def head4: Int = arrayUnsafe(3)
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int4Elem]]s collections. */
trait Int4sBuff[A <: Int4Elem, M <: Int4Arr[A]] extends Any with BuffProdIntN[A]
{ override def elemSize: Int = 4
  override def grow(newElem: A): Unit = { buffer.append(newElem.int1).append(newElem.int2).append(newElem.int3).append(newElem.int4); ()}
  def intsToT(i1: Int, i2: Int, i3: Int, i4: Int): A
  def apply(index: Int): A = intsToT(buffer(index * 4), buffer(index * 4 + 1), buffer(index * 4 + 2), buffer(index * 4 + 3))
}

/** Class for the companion objects of [[Int4Arr]] final classes to extend. */
abstract class Int4sCompanion[A <: Int4Elem, M <: Int4Arr[A]]
{ val factory: Int => M
  def buff(initialSize: Int): Int4sBuff[A, M]

  def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = factory(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.arrayUnsafe(count) = elems(count / 2).int1
      count += 1
      res.arrayUnsafe(count) = elems(count / 2).int2
      count += 1
      res.arrayUnsafe(count) = elems(count / 2).int3
      count += 1
      res.arrayUnsafe(count) = elems(count / 2).int4
      count += 1
    }
    res
  }
}

/** A builder class for specialised collections of [[Int4Elem]]s. */
abstract class Int4sBuilder[A <: Int4Elem, M <: Int4Arr[A]](typeStr: String) extends IntNArrPersist[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value.int1
    buf += value.int2
    buf += value.int3
    buf += value.int4
  }

  override def syntaxDepthT(obj: M): Int = 3
}