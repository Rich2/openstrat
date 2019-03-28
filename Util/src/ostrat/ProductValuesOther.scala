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

trait ProductValuesBuilder[A, M]
{
  /** Atomic Value type normally Double or Int. */
  type VT
  def appendtoBuffer(buf: ArrayBuffer[VT], value: A): Unit
  def fromArray(value: Array[VT]): M 
  def fromBuffer(buf: ArrayBuffer[VT]): M
  def newBuffer: ArrayBuffer[VT]
}

trait ProductDoublesBuilder[A, M <: ProductDoubles[A]] extends ProductValuesBuilder[A, M]
{
  type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
}

trait ProductIntsBuilder[A, M <: ProductInts[A]] extends ProductValuesBuilder[A, M]
{
  type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = new ArrayBuffer[Int](0)
}