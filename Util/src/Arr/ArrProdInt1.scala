/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait ProdInt1 extends Any
{ def intValue: Int
  @inline def _1 : Int = intValue
}

trait ArrProdInt1[A <: ProdInt1] extends Any with ArrProdIntN[A]
{
  final override def productSize: Int = 1
  def newElem(intValue: Int): A
  final override def apply(index: Int): A = newElem(arrayUnsafe(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = arrayUnsafe(index) = elem.intValue

  /** This method could be made more general. */
  def findIndex(value: A): OptInt =
  {
    var count = 0
    var acc: OptInt = NoInt
    var continue = true
    while (continue == true & count < length)
    {
      if (value.intValue == arrayUnsafe(count))
      { acc = SomeInt(count)
        continue = false
      }
      count += 1
    }
    acc
  }

  /** Functionally appends the operand of type A. This alphanumeric method is not aliased by the ++ operator, to avoid confusion with numeric operators. */
  def append(op: A): ThisT =
  { val newArray = new Array[Int](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = op._1
    unsafeFromArray(newArray)
  }
}

trait ArrProdInt1sBuild[A <: ProdInt1, ArrT <: ArrProdInt1[A]] extends ArrProdIntNBuild[A, ArrT]
{ type BuffT <: BuffProdInt1[A, ArrT]

  final override def elemSize: Int = 1
  def newArray(length: Int): Array[Int] = new Array[Int](length)
  final override def arrSet(arr: ArrT, index: Int, value: A): Unit =  arr.arrayUnsafe(index) = value._1
  override def buffGrow(buff: BuffT, value: A): Unit = { buff.buffer.append(value._1); () }
}

trait BuffProdInt1[A <: ProdInt1, M <: ArrProdInt1[A]] extends Any with BuffProdIntN[A]
{ type ArrT <: ArrProdInt1[A]
  def intToT(value: Int): A
  def apply(i1: Int): A = intToT(buffer(i1))
  override def elemSize: Int = 1
  override def grow(newElem: A): Unit = { buffer.append(newElem._1); () }
}