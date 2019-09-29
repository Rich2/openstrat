/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProductI4s[A <: ProdI4] extends Any with ProductInts[A]
{
  override def productSize: Int = 4
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A
  def apply(index: Int): A = newElem(array(4 * index), array(4 * index + 1), array(4 * index + 2), array(4 * index + 3))
  def setElem(index: Int, elem: A): Unit =
  { array(4 * index) = elem._1;
    array(4 * index + 1) = elem._2
    array(4 * index + 2) = elem._3
    array(4 * index + 3) = elem._4
  }
  
  def head1: Int = array(0)
  def head2: Int = array(1)
  def head3: Int = array(2)
  def head4: Int = array(3)
  def toArrs: Arr[Arr[Int]] = map(el => Arr(el._1, el._2, el._3, el._4))
  def foreachArr(f: Arr[Int] => Unit): Unit = foreach(el => f(Arr(el._1, el._2, el._3, el._4)))
}

abstract class ProductI4sCompanion[A <: ProdI4, M <: ProductI4s[A]]
{ val factory: Int => M

  def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = factory(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.array(count) = elems(count / 2)._1
      count += 1
      res.array(count) = elems(count / 2)._2
      count += 1
      res.array(count) = elems(count / 2)._3
      count += 1
      res.array(count) = elems(count / 2)._4
      count += 1      
    }
    res
  }
}