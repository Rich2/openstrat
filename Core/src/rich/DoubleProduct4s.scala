/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

trait DoubleProduct4s[A <: ProdD4] extends Any with ValueProducts[A]
{
   def productSize: Int = 4
   def arr: Array[Double]
   def arrLen: Int = arr.length
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
}

abstract class Double4sMaker[T <: ProdD4, ST <: DoubleProduct4s[T]]
{
   val factory: Int => ST
   def apply(length: Int): ST = factory(length)
   def apply(elems: T*): ST =
   { 
      val length = elems.length
      val res = factory(length)
      var count: Int = 0
      while (count < length)
      {
         res.arr(count * 4) = elems(count)._1         
         res.arr(count * 4 + 1) = elems(count)._2
         res.arr(count * 4 + 2) = elems(count)._3
         res.arr(count * 4 + 3) = elems(count)._4
         count += 1
      }
      res
   }
   
   def doubles(elems: Double*): ST =
   {
      val arrLen: Int = elems.length
      val res = factory(elems.length / 4)
      var count: Int = 0
      while (count < arrLen)
      {
         res.arr(count) = elems(count)
         count += 1         
      }
      res
   }
   
   def fromList(list: List[T]): ST = 
   {
      val arrLen: Int = list.length * 4
      val res = factory(list.length)
      var count: Int = 0
      var rem = list
      while (count < arrLen)
      {
         res.arr(count) = rem.head._1
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