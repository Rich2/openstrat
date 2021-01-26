/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 2 [[Int]]s. These are used in [[Int2Arr]] Array[Int] based collections. */
trait Int2Elem extends Any with ProdHomo
{ def int1: Int
  def int2: Int
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int4Elem]]s. */
trait Int2Arr[A <: Int2Elem] extends Any with ArrProdIntN[A]
{
  override def productSize: Int = 2
  def newElem(i1: Int, i2: Int): A
  final override def apply(index: Int): A = newElem(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))

  final override def unsafeSetElem(index: Int, elem: A): Unit = { arrayUnsafe(2 * index) = elem.int1; arrayUnsafe(2 * index + 1) = elem.int2 }

  def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
}

trait ArrProdInt2Build[A <: Int2Elem, ArrT <: Int2Arr[A]] extends ArrProdIntNBuild[A, ArrT]
{ type BuffT <: Int2sBuff[A, ArrT]

  final override def elemSize: Int = 2
  def newArray(length: Int): Array[Int] = new Array[Int](length * 2)

  final override def arrSet(arr: ArrT, index: Int, value: A): Unit =
  { arr.arrayUnsafe(index * 2) = value.int1; arr.arrayUnsafe(index * 2 + 1) = value.int2
  }
  override def buffGrow(buff: BuffT, value: A): Unit = { buff.buffer.append(value.int1); buff.buffer.append(value.int2); () }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int2Elem]]s collections. */
trait Int2sBuff[A <: Int2Elem, M <: Int2Arr[A]] extends Any with BuffProdIntN[A]
{ type ArrT <: Int2Arr[A]
  override def elemSize: Int = 2
  override def grow(newElem: A): Unit = { buffer.append(newElem.int1).append(newElem.int2); () }
  def intsToT(i1: Int, i2: Int): A
  def apply(index: Int): A = intsToT(buffer(index * 2), buffer(index * 2 + 1))
}

abstract class ProductI2sCompanion[A <: Int2Elem, M <: Int2Arr[A]] extends ProductIntsCompanion[M]
{
  implicit val factory: Int => M = i => fromArray(new Array[Int](i * 2))
  def buff(initialSize: Int): Int2sBuff[A, M]

  def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 2
    val res = factory(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.arrayUnsafe(count) = elems(count / 2).int1
      count += 1
      res.arrayUnsafe(count) = elems(count / 2).int2
      count += 1
    }
    res
  }
}

/** A builder class for specialised collections of [[Int2Elem]]s. */
abstract class Int2sBuilder[A <: Int2Elem, M <: Int2Arr[A]](typeStr: String) extends ProductIntsBuilder[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value.int1
    buf += value.int2
  }

  override def syntaxDepthT(obj: M): Int = 3
}