/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

trait ProductDoubles[A] extends Any with ProductValues[A]
{
  def arr: Array[Double]
  def arrLen = arr.length 
}
trait DoublesMaker[A, B <: ProductDoubles[A]]
{
  def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit
  def fromArray(value: Array[Double]): B 
  def fromBuffer(buf: ArrayBuffer[Double]): B = fromArray(buf.toArray)
}