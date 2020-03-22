package ostrat
import scala.collection.mutable.ArrayBuffer

class Dbls(val array: Array[Double]) extends AnyVal with ArrImut[Double]
{ type ThisT = Dbls
  override def unsafeNew(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def length: Int = array.length
  override def apply(index: Int): Double = array(index)
  override def unsafeSetElem(i: Int, value: Double): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }

  def ++ (op: Dbls): Dbls =
  { val newArray = new Array[Double](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Dbls(newArray)
  }
}

object Dbls
{ def apply(input: Double*): Dbls = new Dbls(input.toArray)
}

object DblsBuild extends ArrBuild[Double, Dbls] with ArrFlatBuild[Dbls]
{ type BuffT = DblsBuff
  override def imutNew(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def imutSet(arr: Dbls, index: Int, value: Double): Unit = arr.array(index) = value
  override def buffNew(length: Int = 4): DblsBuff = new DblsBuff(ArrayBuffer[Double](length))
  override def buffGrow(buff: DblsBuff, value: Double): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: DblsBuff, arr: Dbls): Unit = buff.unsafeBuff.addAll(arr.array)
  override def buffToArr(buff: DblsBuff): Dbls = new Dbls(buff.unsafeBuff.toArray)
}

class DblsBuff(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with ArrayLike[Double]
{ override def apply(index: Int): Double = unsafeBuff(index)
  override def length: Int = unsafeBuff.length
}