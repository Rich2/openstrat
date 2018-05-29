/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

abstract class IntProduct2s[A <: ProdI2](val length: Int) extends ValueProducts[A]
{
   def arrLen = length * 2
   val arr: Array[Int] = new Array[Int](arrLen)
   def newElem(i1: Int, i2: Int): A
   def apply(index: Int): A = newElem(arr(2 * index), arr(2 * index + 1))
   def setElem(index: Int, elem: A): Unit =
   { arr(2 * index) = elem._1
     arr(2 * index + 1) = elem._2
   }
   def head1: Int = arr(0)
   def head2: Int = arr(1)
   }

abstract class Int2Maker[T <: ProdI2, ST <: IntProduct2s[T]]
{
   val factory: Int => ST
   def apply(elems: T*): ST =
   {
      val arrLen: Int = elems.length * 2
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
   {
      val arrLen: Int = elems.length
      val res = factory(elems.length / 2)
      var count: Int = 0
      while (count < arrLen)
      {
         res.arr(count) = elems(count)
         count += 1         
      }
      res
   }
   
}
