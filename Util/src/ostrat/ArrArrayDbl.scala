package ostrat
import collection.mutable.ArrayBuffer

trait ArrArrayDbl[A <: ArrayDblBased] extends Any with ArrImut[A]
{ def array: Array[Array[Double]]
  def length: Int = array.length
  def unsafeSetElem(i: Int, value: A): Unit = array(i) = value.array
}

trait ArrArrayDblBuild[A <: ArrayDblBased, AA <: ArrArrayDbl[A]] extends ArrBuild[A, AA]
{ @inline def fromArray(array: Array[Array[Double]]): AA
  type BuffT = ArrayBuffer[Array[Double]]
  @inline override def imutNew(length: Int): AA = fromArray(new Array[Array[Double]](length))
  override def imutSet(arr: AA, index: Int, value: A): Unit = arr.array(index) = value.array
  override def buffNew(length: Int = 4): ArrayBuffer[Array[Double]] = new ArrayBuffer[Array[Double]]((length))
  override def buffToArr(buff: ArrayBuffer[Array[Double]]): AA = fromArray(buff.toArray)
  override def buffAppend(buff: ArrayBuffer[Array[Double]], value: A): Unit = buff.append(value.array)
}
