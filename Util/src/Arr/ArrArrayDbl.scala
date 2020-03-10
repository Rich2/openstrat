package ostrat
import collection.mutable.ArrayBuffer

trait ArrArrayDbl[A <: ArrayDblBased] extends Any with ArrImut[A]
{ def array: Array[Array[Double]]
  def length: Int = array.length
  def unsafeFromArrayArray(array: Array[Array[Double]]): ThisT
  final def unsafeNew(length: Int): ThisT = unsafeFromArrayArray(new Array[Array[Double]](length))
  def unsafeSetElem(i: Int, value: A): Unit = array(i) = value.array
}

trait ArrArrayDblBuild[A <: ArrayDblBased, ArrT <: ArrArrayDbl[A]] extends ArrBuild[A, ArrT]
{ @inline def fromArray(array: Array[Array[Double]]): ArrT
  type BuffT = ArrayBuffer[Array[Double]]
  @inline override def imutNew(length: Int): ArrT = fromArray(new Array[Array[Double]](length))
  override def imutSet(arr: ArrT, index: Int, value: A): Unit = arr.array(index) = value.array
  override def buffNew(length: Int = 4): ArrayBuffer[Array[Double]] = new ArrayBuffer[Array[Double]]((length))
  override def buffToArr(buff: ArrayBuffer[Array[Double]]): ArrT = fromArray(buff.toArray)
  override def buffGrow(buff: ArrayBuffer[Array[Double]], value: A): Unit = buff.append(value.array)
  override def buffGrowArr(buff: BuffT, arr: ArrT): Unit = buff.addAll(arr.array)
}
