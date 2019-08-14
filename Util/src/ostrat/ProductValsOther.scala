/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Base trait for Array[Double] based collections of Products of Doubles. */
trait ProductDoubles[A] extends Any with ProductVals[A]
{ def arr: Array[Double]
  def arrLen = arr.length
  def toArrs: Arr[Arr[Double]]
  def foreachArr(f: Arr[Double] => Unit): Unit

  override def toString: String =
  { var body = ""
    var start = true
    foreachArr { arr =>
      val el = arr.toStrsCommaNoSpaceFold(_.toString)
      if(start == true) {body = el; start = false}
      else  body = body + ";  " + el
    }
    typeStr + body.enParenth
  }
}

trait ProductInts[A] extends Any with ProductVals[A]
{ def arr: Array[Int]
  def arrLen = arr.length
  def toArrs: Arr[Arr[Int]]
  def foreachArr(f: Arr[Int] => Unit): Unit

  override def toString: String =
  { var body = ""
    var start = true
    foreachArr { arr =>
      val el = arr.toStrsCommaNoSpaceFold(_.toString)
      if(start == true) {body = el; start = false}
      else  body = body + ";  " + el
    }
    typeStr + body.enParenth
  }
}

abstract class ProductValsBuilder[A, M](val typeStr: String) extends ShowCompound[M] with PersistCompound[M]
{
  /** Atomic Value type normally Double or Int. */
  type VT
  def appendtoBuffer(buf: ArrayBuffer[VT], value: A): Unit
  def fromArray(value: Array[VT]): M
  def fromBuffer(buf: ArrayBuffer[VT]): M
  def newBuffer: ArrayBuffer[VT]
}

abstract class ProductDsBuilder[A, M <: ProductDoubles[A]](typeStr: String) extends ProductValsBuilder[A, M](typeStr)
{
  type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
}

abstract class ProductIntsBuilder[A, M <: ProductInts[A]](typeStr: String) extends ProductValsBuilder[A, M](typeStr)
{
  type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = new ArrayBuffer[Int](0)
}