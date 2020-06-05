/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Homogeneous Product3[Double, Double, Double]. These are used in ArrHomoDbl3 Array[Double] based collections. */
trait ProdDbl3 extends Any with Product3[Double, Double, Double] with ProdHomo

/** Base trait for Array[Double] base collections of Products of 3 Doubles. */
trait ArrProdDbl3[A <: ProdDbl3] extends Any with ArrProdDblN[A]
{ def productSize = 3
  def newElem(d1: Double, d2: Double, d3: Double): A
  def apply(index: Int): A = newElem(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))
  override def unsafeSetElem(index: Int, elem: A): Unit = { arrayUnsafe(3 * index) = elem._1; arrayUnsafe(3 * index + 1) = elem._2; arrayUnsafe(3 * index + 2) = elem._3 }
  def head1: Double = arrayUnsafe(0); def head2: Double = arrayUnsafe(1); def head3: Double = arrayUnsafe(2)

  //def toArrs: ArrOld[ArrOld[Double]] = mapArrSeq(el => ArrOld(el._1, el._2, el._3))
  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el._1, el._2, el._3)))
}

trait ArrProdDbl3Build[A <: ProdDbl3, ArrT <: ArrProdDbl3[A]] extends ArrProdDblNBuild[A, ArrT]
{ type BuffT <: BuffProdDbl3[A]

  final override def elemSize = 3
  //def newArray(length: Int): Array[Double] = new Array[Double](length * 2)

  override def arrSet(arr: ArrT, index: Int, value: A): Unit =
  { arr.arrayUnsafe(index * 3) = value._1; arr.arrayUnsafe(index * 3 + 1) = value._2; arr.arrayUnsafe(index * 3 + 2) = value._3
  }

  override def buffGrow(buff: BuffT, value: A): Unit = ??? //{ buff.append(value._1,) ??? //buff.buffer.append(value)
}

abstract class ProdDbl3sCompanion[A <: ProdDbl3, M <: ArrProdDbl3[A]]
{ val factory: Int => M
  def apply(length: Int): M = factory(length)

  def apply(elems: A*): M =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0
    while (count < length)
    {
      res.arrayUnsafe(count * 3) = elems(count)._1;  res.arrayUnsafe(count * 3 + 1) = elems(count)._2; res.arrayUnsafe(count * 3 + 2) = elems(count)._3
      count += 1
    }
    res
  }
}

trait BuffProdDbl3[A <: ProdDbl3] extends Any with BuffProdDblN[A]
{ type ArrT <: ArrProdDbl3[A]
  override def elemSize: Int = 3

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit = { buffer.append(newElem._1).append(newElem._2).append(newElem._3); () }

  def dblsToT(d1: Double, d2: Double, d3: Double): A
  def apply(index: Int): A = dblsToT(buffer(index * 3), buffer(index * 3 + 1), buffer(index * 3 + 2))
}
