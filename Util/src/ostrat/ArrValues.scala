package ostrat
import scala.collection.mutable.ArrayBuffer

trait ArrValues[A] extends Any with ArrImut[A]
{ type ThisT <: ArrValues[A]
  def :+ (op: A): ThisT =
  { val newArr = buildThis(length + 1)
    //array.copyToArray(newArr.arr)
    // newArray(length) = op
    ??? //new Refs(newArray)
  }
}
final class Ints(val array: Array[Int]) extends AnyVal with ArrValues[Int]
{ type ThisT = Ints
  override def buildThis(length: Int): Ints = new Ints(new Array[Int](length))
  override def length: Int = array.length
  override def apply(index: Int): Int = array(index)
  override def unsafeSetElem(i: Int, value: Int): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Int], offset: Int, copyLength: Int): Unit = array.copyToArray(array, offset, copyLength)

  def ++ (op: Ints): Ints =
  { val newArray = new Array[Int](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Ints(newArray)
  }
}

object Ints
{ def apply(input: Int*): Ints = new Ints(input.toArray)
  implicit val bindImplicit: ArrFlatBuild[Ints] = new ArrFlatBuild[Ints]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Ints): Ints =
    { val buff = new ArrayBuffer[Int]
      orig.foreach(a => buff.addAll(f(a).array))
      new Ints(buff.toArray)
    }
  }
}

class Longs(val array: Array[Long]) extends AnyVal with ArrImut[Long]
{ type ThisT = Longs
  override def buildThis(length: Int): Longs = new Longs(new Array[Long](length))
  override def length: Int = array.length
  override def apply(index: Int): Long = array(index)
  override def unsafeSetElem(i: Int, value: Long): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Long], offset: Int, copyLength: Int): Unit = array.copyToArray(array, offset, copyLength)

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

/*class LongsBuff(val buffer: ArrayBuffer[Long]) extends AnyVal with BufferLike[Long]
{ override def length: Int = buffer.length
  override def apply(index: Int): Long = buffer(index)
}*/

class Dbls(val array: Array[Double]) extends AnyVal with ArrImut[Double]
{ type ThisT = Dbls
  override def buildThis(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def length: Int = array.length
  override def apply(index: Int): Double = array(index)
  override def unsafeSetElem(i: Int, value: Double): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = array.copyToArray(array, offset, copyLength)

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

/*
class DblBuff(val buffer: ArrayBuffer[Double]) extends AnyVal with BufferLike[Double]
{ override def length: Int = buffer.length
  override def apply(index: Int): Double = buffer(index)
}*/
