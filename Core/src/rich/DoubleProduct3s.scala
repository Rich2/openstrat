/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

trait DoubleProduct3s[A <: ProdD3] extends Any with ValueProducts[A]
{
   def productSize = 3
   def arr: Array[Double]
   def arrLen = arr.length   
   def newElem(d1: Double, d2: Double, d3: Double): A
   def apply(index: Int): A = newElem(arr(3 * index), arr(3 * index + 1), arr(3 * index + 2))
   def setElem(index: Int, elem: A): Unit =
   { arr(3 * index) = elem._1
     arr(3 * index + 1) = elem._2
     arr(3 * index + 2) = elem._3
   }
   def head1: Double = arr(0)
   def head2: Double = arr(1)
   def head3: Double = arr(2)   
}