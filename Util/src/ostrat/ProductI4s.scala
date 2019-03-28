/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProductI4s[A <: ProdI4] extends Any with ProductInts[A]
{
  override def productSize: Int = 4
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A
  def apply(index: Int): A = newElem(arr(4 * index), arr(4 * index + 1), arr(4 * index + 2), arr(4 * index + 3))
  def setElem(index: Int, elem: A): Unit =
  { arr(2 * index) = elem._1;
    arr(2 * index + 1) = elem._2
    arr(2 * index + 2) = elem._3
    arr(2 * index + 3) = elem._4
  }
  
  def head1: Int = arr(0)
  def head2: Int = arr(1)
  def head3: Int = arr(2)
  def head4: Int = arr(3)
}

abstract class ProductI4sCompanion[A <: ProdI4, M <: ProductI4s[A]]
{ val factory: Int => M

  def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = factory(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.arr(count) = elems(count / 2)._1
      count += 1
      res.arr(count) = elems(count / 2)._2
      count += 1
      res.arr(count) = elems(count / 2)._3
      count += 1
      res.arr(count) = elems(count / 2)._4
      count += 1      
    }
    res
  }
}