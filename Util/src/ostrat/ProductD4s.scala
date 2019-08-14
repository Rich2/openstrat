/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Base trait for Array[Double] base collections of Products of 4 Doubles. */
trait ProductD4s[A <: ProdD4] extends Any with ProductDoubles[A]
{
  def productSize: Int = 4
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double): A
  def apply(index: Int): A = newElem(arr(4 * index), arr(4 * index + 1), arr(4 * index + 2), arr(4 * index + 3))
   
  def setElem(index: Int, elem: A): Unit =
  { arr(4 * index) = elem._1
    arr(4 * index + 1) = elem._2
    arr(4 * index + 2) = elem._3
    arr(4 * index + 3) = elem._4
  }
   
  def head1: Double = arr(0)
  def head2: Double = arr(1)
  def head3: Double = arr(2)
  def head4: Double = arr(3)

  def toArrs: Arr[Arr[Double]] = map(el => Arr(el._1, el._2, el._3, el._4))
  def foreachArr(f: Arr[Double] => Unit): Unit = foreach(el => f(Arr(el._1, el._2, el._3, el._4)))
}

abstract class ProductD4sCompanion[A <: ProdD4, M <: ProductD4s[A]]
{
  val factory: Int => M
  def apply(length: Int): M = factory(length)

  def apply(elems: A*): M =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { res.arr(count * 4) = elems(count)._1
      res.arr(count * 4 + 1) = elems(count)._2
      res.arr(count * 4 + 2) = elems(count)._3
      res.arr(count * 4 + 3) = elems(count)._4
      count += 1
    }
     res
   }
   
  def doubles(elems: Double*): M =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 4)
    var count: Int = 0

    while (count < arrLen)
    { res.arr(count) = elems(count)
      count += 1
    }
    res
  }

  def fromList(list: List[A]): M =
  { val arrLen: Int = list.length * 4
    val res = factory(list.length)
    var count: Int = 0
    var rem = list

    while (count < arrLen)
    { res.arr(count) = rem.head._1
      count += 1
      res.arr(count) = rem.head._2
      count += 1
      res.arr(count) = rem.head._3
      count += 1
      res.arr(count) = rem.head._4
      count += 1
      rem = rem.tail
    }
    res
  }
}