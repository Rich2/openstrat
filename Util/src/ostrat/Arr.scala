/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait Arr[+A] extends Any
{
  def length: Int
  def apply(index: Int): A
  def setUnsafe(index: Int, value: Any): Unit
  
  def foreach[U](f: (A) ⇒ U): Unit =
  { var count = 0
    while (count < length){f(apply(count)); count += 1 }
  }
  
  final def map[B](f: A => B)(implicit ev: Int => Arr[B]): Arr[B] =
  {
    val newArr = ev(length)
    var count = 0
    while (count < 0)
    {  newArr.setUnsafe(count, f(apply(count)))
       count += 1
    }
    newArr
  }
}

trait ArrBuilder[A]
{
  def apply(length: Int): Arr[A]
}

object Arr
{
  def apply[A <: AnyRef](arr: Array[A]): Arr[A] = new ArrRef(arr.asInstanceOf[Array[AnyRef]])
  implicit def refBuildImplicit[A <: AnyRef](length: Int) = new ArrRef(new Array[AnyRef](length))
  
  def apply(arr: Array[Double]): Arr[Double] = new ArrDouble(arr)
  implicit def DoubleBuildImplicit(length: Int) = new ArrDouble(new Array[Double](length))
}

class ArrRef[+A](val arr: Array[AnyRef]) extends AnyVal with Arr[A]
{
  override def length: Int = arr.length
  override def apply(index: Int): A = arr(index).asInstanceOf[A]
  override def setUnsafe(index: Int, value: Any): Unit = arr(index) = value.asInstanceOf[AnyRef]  
}

class ArrInt(val arr: Array[Int]) extends AnyVal with Arr[Int]
{
  override def length: Int = arr.length
  override def apply(index: Int): Int = arr(index)
  override def setUnsafe(index: Int, value: Any): Unit = arr(index) = value.asInstanceOf[Int]  
}

class ArrDouble(val arr: Array[Double]) extends AnyVal with Arr[Double]
{
  override def length: Int = arr.length
  override def apply(index: Int): Double = arr(index)
  override def setUnsafe(index: Int, value: Any): Unit = arr(index) = value.asInstanceOf[Double]  
}

