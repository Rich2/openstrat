/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import scala.reflect.ClassTag
/*trait Arr[+A] extends Any
{
  def length: Int
  def apply(index: Int): A
  
  
  def foreach[U](f: (A) => U): Unit =
  { var count = 0
    while (count < length){f(apply(count)); count += 1 }
  }
  
  final def map[B](f: A => B)(implicit ct: ClassTag[B], ev: Array[B] => Arr[B]): Arr[B] =
  {
    val newArr = new Array[B](length)
    var count = 0
    while (count < length)
    {  newArr(count) = f(apply(count))
       count += 1
    }
    ev(newArr)
  }
  
  final def flatMap[B](f: A => Array[B])(implicit ct: ClassTag[B], ev: Array[B] => Arr[B]): Arr[B] =
  {
    val acc = new collection.mutable.ArrayBuffer[B]
    var longCount: Int = 0
    var count: Int = 0
    while (count < length)
    {
      f(apply(count)).foreach{b => acc(longCount) = b; longCount += 1 }
      count += 1
    }
    ev(acc.toArray)
  }  
}

object Arr
{
  implicit def apply[A <: AnyRef](arr: Array[A]): Arr[A] = new ArrRef(arr.asInstanceOf[Array[AnyRef]])  
  implicit def apply(arr: Array[Int]): Arr[Int] = new ArrInt(arr)
  implicit def apply(arr: Array[Double]): Arr[Double] = new ArrDouble(arr)
}

class ArrRef[+A](val arr: Array[AnyRef]) extends AnyVal with Arr[A]
{ override def length: Int = arr.length
  override def apply(index: Int): A = arr(index).asInstanceOf[A]  
}

class ArrInt(val arr: Array[Int]) extends AnyVal with Arr[Int]
{ override def length: Int = arr.length
  override def apply(index: Int): Int = arr(index)  
}

class ArrDouble(val arr: Array[Double]) extends AnyVal with Arr[Double]
{ override def length: Int = arr.length
  override def apply(index: Int): Double = arr(index)  
}*/

