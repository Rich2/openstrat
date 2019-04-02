/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Base trait for Array[Double] based collections of Products of Doubles. */
trait ProductDs[A] extends Any with ProductVals[A]
{
  def arr: Array[Double]
  def arrLen = arr.length 
}

trait ProductInts[A] extends Any with ProductVals[A]
{
  def arr: Array[Int]
  def arrLen = arr.length 
}

abstract class ProductValsBuilder[A, M](typeSym: Symbol) extends PersistCompound[M](typeSym)
{
  /** Atomic Value type normally Double or Int. */
  type VT
  override def typeStr = typeSym.name
  def appendtoBuffer(buf: ArrayBuffer[VT], value: A): Unit
  def fromArray(value: Array[VT]): M 
  def fromBuffer(buf: ArrayBuffer[VT]): M
  def newBuffer: ArrayBuffer[VT]
}

abstract class ProductDsBuilder[A, M <: ProductDs[A]](typeSym: Symbol) extends ProductValsBuilder[A, M](typeSym)
{
  type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
}

abstract class ProductIntsBuilder[A, M <: ProductInts[A]](typeSym: Symbol) extends ProductValsBuilder[A, M](typeSym)
{
  type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = new ArrayBuffer[Int](0)
}