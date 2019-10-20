/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Base trait for Array[Double] base collections of Products of 3 Doubles. */
trait ProductD3s[A <: ProdD3] extends Any with ProductDbls[A]
{ def productSize = 3
  def newElem(d1: Double, d2: Double, d3: Double): A  
  def apply(index: Int): A = newElem(array(3 * index), array(3 * index + 1), array(3 * index + 2))
  override def unsafeSetElem(index: Int, elem: A): Unit = { array(3 * index) = elem._1; array(3 * index + 1) = elem._2; array(3 * index + 2) = elem._3 }
  def head1: Double = array(0); def head2: Double = array(1); def head3: Double = array(2)

  def toArrs: Arr[Arr[Double]] = map(el => Arr(el._1, el._2, el._3))
  def foreachArr(f: Arr[Double] => Unit): Unit = foreach(el => f(Arr(el._1, el._2, el._3)))
}

abstract class ProductD3sCompanion[A <: ProdD3, M <: ProductD3s[A]]
{ val factory: Int => M
  def apply(length: Int): M = factory(length)

  def apply(elems: A*): M =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0
    while (count < length)
    {
      res.array(count * 3) = elems(count)._1;  res.array(count * 3 + 1) = elems(count)._2; res.array(count * 3 + 2) = elems(count)._3
      count += 1
    }
    res
  }
}
