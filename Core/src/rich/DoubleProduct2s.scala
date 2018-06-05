/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

trait DoubleProduct2s[A <: ProdD2] extends Any with ValueProducts[A]
{
   def productSize: Int = 2
   def arr: Array[Double]
   def arrLen = arr.length   
   def newElem(d1: Double, d2: Double): A
   def apply(index: Int): A = newElem(arr(2 * index), arr(2 * index + 1))
   def setElem(index: Int, elem: A): Unit =
   { arr(2 * index) = elem._1
     arr(2 * index + 1) = elem._2
   }
   def head1: Double = arr(0)
   def head2: Double = arr(1)
   
   def foreachPairTail[U](f: (Double, Double) => U): Unit =
   {
      var count = 1      
      while(count < length) { f(arr(count * 2), arr(count * 2 + 1)); count += 1 }
   }
   
   def elem1sArray: Array[Double] =
   {
      val res = new Array[Double](length)
      var count = 0
      while(count < length){ res(count) = arr(count * 2); count += 1 }
      res
   }
   def elem2sArray: Array[Double] =
   {
      val res = new Array[Double](length)
      var count = 0
      while(count < length){ res(count) = arr(count * 2 + 1); count += 1 }
      res
   }   
}

abstract class Double2sMaker[T <: ProdD2, ST <: DoubleProduct2s[T]]
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
         res.arr(count * 2) = elems(count)._1         
         res.arr(count * 2 + 1) = elems(count)._2
         count += 1
      }
      res
   }
   
   def doubles(elems: Double*): ST =
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
