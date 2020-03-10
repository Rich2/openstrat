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
  implicit val bindImplicit: ArrFlatBuild[Dbls] = new ArrFlatBuild[Dbls]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Dbls): Dbls =
    { val buff = new ArrayBuffer[Double]
      orig.foreach(a => buff.addAll(f(a).array))
      new Dbls(buff.toArray)
    }
  }
}

object DblsBuild extends ArrBuild[Double, Dbls] with ArrArrBuild[Dbls]
{ type BuffT = ArrayBuffer[Double]
  override def imutNew(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def imutSet(arr: Dbls, index: Int, value: Double): Unit = arr.array(index) = value
  override def buffNew(length: Int = 4): ArrayBuffer[Double] = new ArrayBuffer[Double](length)
  override def buffGrow(buff: ArrayBuffer[Double], value: Double): Unit = buff.append(value)
  override def buffGrowArr(buff: Buff[Double], arr: Dbls): Unit = buff.addAll(arr.array)
  override def buffToArr(buff: ArrayBuffer[Double]): Dbls = new Dbls(buff.toArray)
}