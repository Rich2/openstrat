/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

trait ProductDoubles[A] extends Any with ProductValues[A]
{
  def arr: Array[Double]
  def arrLen = arr.length 
}

trait ProductInts[A] extends Any with ProductValues[A]
{
  def arr: Array[Int]
  def arrLen = arr.length 
}

trait ValuesMaker[A, B]
{
  type VT
  def appendtoBuffer(buf: ArrayBuffer[VT], value: A): Unit
  def fromArray(value: Array[VT]): B 
  def fromBuffer(buf: ArrayBuffer[VT]): B
  def newBuffer: ArrayBuffer[VT]
}

trait DoublesMaker[A, B <: ProductDoubles[A]] extends ValuesMaker[A, B]
{
  type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): B = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
}

trait IntsMaker[A, B <: ProductInts[A]] extends ValuesMaker[A, B]
{
  type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): B = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = new ArrayBuffer[Int](0)
}