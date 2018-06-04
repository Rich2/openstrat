/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

abstract class DoubleProduct4s[A <: ProdD4](val length: Int) extends ValueProducts[A]
{
   def arrLen = length * 4
   val arr: Array[Double] = new Array[Double](arrLen)
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