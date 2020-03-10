package ostrat
import collection.mutable.ArrayBuffer

class Longs(val array: Array[Long]) extends AnyVal with ArrImut[Long]
{ type ThisT = Longs
  override def unsafeNew(length: Int): Longs = new Longs(new Array[Long](length))
  override def length: Int = array.length
  override def apply(index: Int): Long = array(index)
  override def unsafeSetElem(i: Int, value: Long): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Long], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }

  def ++ (op: Longs): Longs =
  { val newArray = new Array[Long](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Longs(newArray)
  }
}

object Longs
{ def apply(input: Long*): Longs = new Longs(input.toArray)
  implicit val bindImplicit: ArrFlatBuild[Longs] = new ArrFlatBuild[Longs]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Longs): Longs =
    { val buff = new ArrayBuffer[Long]
      orig.foreach(a => buff.addAll(f(a).array))
      new Longs(buff.toArray)
    }
  }
}

object LongsBuild extends ArrBuild[Long, Longs] with ArrArrBuild[Longs]
{ type BuffT = ArrayBuffer[Long]
  override def imutNew(length: Int): Longs = new Longs(new Array[Long](length))
  override def imutSet(arr: Longs, index: Int, value: Long): Unit = arr.array(index) = value
  override def buffNew(length: Int = 4): ArrayBuffer[Long] = new ArrayBuffer[Long](length)
  override def buffGrow(buff: ArrayBuffer[Long], value: Long): Unit = buff.append(value)
  override def buffGrowArr(buff: Buff[Long], arr: Longs): Unit = buff.addAll(arr.array)
  override def buffToArr(buff: ArrayBuffer[Long]): Longs = new Longs(buff.toArray)

}