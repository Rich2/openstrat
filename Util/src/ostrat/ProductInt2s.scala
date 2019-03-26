/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProductInt2s[A <: ProdI2] extends Any with ProductValues[A]
{ def productSize: Int = 2
  def arr: Array[Int]
  def arrLen = arr.length
  def newElem(i1: Int, i2: Int): A
  def apply(index: Int): A = newElem(arr(2 * index), arr(2 * index + 1))

  def setElem(index: Int, elem: A): Unit = { arr(2 * index) = elem._1; arr(2 * index + 1) = elem._2 }

  def head1: Int = arr(0)
  def head2: Int = arr(1)
}

abstract class Int2Maker[T <: ProdI2, ST <: ProductInt2s[T]]
{ val factory: Int => ST

  def apply(elems: T*): ST =
  { val arrLen: Int = elems.length * 2
    val res = factory(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.arr(count) = elems(count / 2)._1
      count += 1
      res.arr(count) = elems(count / 2)._2
      count += 1
    }
    res
  }
   
  def ints(elems: Int*): ST =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 2)
    var count: Int = 0
    while (count < arrLen) { res.arr(count) = elems(count); count += 1 }
    res
  }
  
  def fromList(list: List[T]): ST = 
   {
      val arrLen: Int = list.length * 2
      val res = factory(list.length)
      var count: Int = 0
      var rem = list
      while (count < arrLen)
      {
         res.arr(count) = rem.head._1
         count += 1
         res.arr(count) = rem.head._2
         count += 1
         rem = rem.tail
      }
      res
   }
  
}
