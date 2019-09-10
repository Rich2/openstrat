/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Base trait for Array[Double] base collections of Products of 2 Doubles. */
trait ProductD2s[A <: ProdD2] extends Any with ProductDoubles[A]
{
  def productSize: Int = 2
  /** Method for creating new elements from 2 Doubles. */
  def elemBuilder(d1: Double, d2: Double): A
  def apply(index: Int): A = elemBuilder(arr(2 * index), arr(2 * index + 1))
  def getPair(index: Int): (Double, Double) = (arr(2 * index), arr(2 * index + 1))
  
  def setElem(index: Int, elem: A): Unit =
  { arr(2 * index) = elem._1
    arr(2 * index + 1) = elem._2
  }
  def head1: Double = arr(0)
  def head2: Double = arr(1)
   
  def foreachPairTail[U](f: (Double, Double) => U): Unit =
  { var count = 1      
    while(count < length) { f(arr(count * 2), arr(count * 2 + 1)); count += 1 }
  }
   
  def elem1sArray: Array[Double] =
  { val res = new Array[Double](length)
    var count = 0
    while(count < length){ res(count) = arr(count * 2); count += 1 }
    res
  }
  
  def elem2sArray: Array[Double] =
  { val res = new Array[Double](length)
    var count = 0
    while(count < length){ res(count) = arr(count * 2 + 1); count += 1 }
    res
  }
  
  def mapBy2[B](f: (Double, Double) => B)(implicit m: scala.reflect.ClassTag[B]): Array[B] =
  {
    val newArr = new Array[B](length)
    var count = 0
    while (count < length) 
    { newArr(count) = f(arr(count * 2), arr(count * 2 + 1))
      count += 1
    }
    newArr
   }

  def toArrs: Arr[Arr[Double]] = map(el => Arr(el._1, el._2))
  def foreachArr(f: Arr[Double] => Unit): Unit = foreach(el => f(Arr(el._1, el._2)))
}
