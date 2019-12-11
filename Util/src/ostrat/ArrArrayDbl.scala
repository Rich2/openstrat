package ostrat

import scala.collection.mutable.ArrayBuffer
//import annotation.unchecked.uncheckedVariance

trait ArrArrayDbl[A] extends Any with ArrImut[A]
{ def array: Array[Array[Double]]
  def length: Int = array.length
}

trait ArrArrayDblBuild[A, AA <: ArrImut[A]] extends ArrBuild[A, AA]
{ @inline def fromArray(array: Array[Array[Double]]): AA
  type BuffT = ArrayBuffer[Array[Double]]
  @inline override def imutNew(length: Int): AA = fromArray(new Array[Array[Double]](length))
  override def buffNew(length: Int = 4): ArrayBuffer[Array[Double]] = new ArrayBuffer[Array[Double]]((length))
  override def buffToArr(buff: ArrayBuffer[Array[Double]]): AA = fromArray(buff.toArray)
}
