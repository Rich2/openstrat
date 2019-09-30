/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Base trait for Array[Double] based collections of Products of Doubles. */
trait ProductDoubles[A] extends Any with ProductVals[A]
{ def array: Array[Double]
  def arrLen = array.length
  //def toArrs: Arr[Arr[Double]]
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

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  {
    val acc = new Array[Double](arrLen + appendProductsLength * productSize)
    array.copyToArray(acc)
    acc
  }
}

abstract class ProductDsBuilder[A, M <: ProductDoubles[A]](typeStr: String) extends ProductValsBuilder[A, M](typeStr) with Eq[M]
{
  type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  //implicit val eqImplicit: Eq[M] = (m1, m2) => m1.array.eq(m2.array)
  override def eqv(m1: M, m2: M): Boolean = ???
}
