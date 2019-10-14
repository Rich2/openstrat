package ostrat
import collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance, reflect.ClassTag

class Refs[+A <: AnyRef](val array: Array[A] @uncheckedVariance) extends AnyVal with ArrImut[A]
{ override def length: Int = array.length
  override def apply(index: Int): A = array(index)

  def ++ [AA >: A <: AnyRef](op: Refs[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] =
  { val newArray = new Array[AA](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Refs(newArray)
  }
}
object Refs
{ def apply[A <: AnyRef](input: A*)(implicit ct: ClassTag[A]): Refs[A] = new Refs(input.toArray)

  implicit def bindImplicit[B <: AnyRef](implicit ct: ClassTag[B]): Bind[Refs[B]] = new Bind[Refs[B]]
  {
    override def bind[A](orig: ArrayBased[A], f: A => Refs[B]): Refs[B] =
    { val buff = new ArrayBuffer[B]
      orig.foreach(a => buff.addAll(f(a).array))
      new Refs[B](buff.toArray)
    }
  }
}

class BuffRefs(val buffer: ArrayBuffer[Int]) extends AnyVal with BuffArr[Int]
{ override def length: Int = buffer.length
  override def apply(index: Int): Int = buffer(index)
}

class MutRefs(val array: Array[Int]) extends AnyVal with MutArr[Int]
{ override def length: Int = array.length
  override def apply(index: Int): Int = array(index)
}

class Ints(val array: Array[Int]) extends AnyVal with ArrImut[Int]
{ override def length: Int = array.length
  override def apply(index: Int): Int = array(index)
  def ++ (op: Ints): Ints =
  { val newArray = new Array[Int](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Ints(newArray)
  }
}
object Ints
{ def apply(input: Int*): Ints = new Ints(input.toArray)
  implicit val bindImplicit: Bind[Ints] = new Bind[Ints]
  {
    override def bind[A](orig: ArrayBased[A], f: A => Ints): Ints =
    { val buff = new ArrayBuffer[Int]
      orig.foreach(a => buff.addAll(f(a).array))
      new Ints(buff.toArray)
    }
  }
}

class BuffInts(val buffer: ArrayBuffer[Int]) extends AnyVal with BuffArr[Int]
{ override def length: Int = buffer.length
  override def apply(index: Int): Int = buffer(index)
}

class MutInts(val array: Array[Int]) extends AnyVal with MutArr[Int]
{ override def length: Int = array.length
  override def apply(index: Int): Int = array(index)
}

class Longs(val array: Array[Long]) extends AnyVal with ArrImut[Long]
{ override def length: Int = array.length
  override def apply(index: Int): Long = array(index)
  def ++ (op: Longs): Longs =
  { val newArray = new Array[Long](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Longs(newArray)
  }
}
object Longs
{ def apply(input: Long*): Longs = new Longs(input.toArray)
  implicit val bindImplicit: Bind[Longs] = new Bind[Longs]
  {
    override def bind[A](orig: ArrayBased[A], f: A => Longs): Longs =
    { val buff = new ArrayBuffer[Long]
      orig.foreach(a => buff.addAll(f(a).array))
      new Longs(buff.toArray)
    }
  }
}

class BuffLongs(val buffer: ArrayBuffer[Long]) extends AnyVal with BuffArr[Long]
{ override def length: Int = buffer.length
  override def apply(index: Int): Long = buffer(index)
}

class MutLongs(val array: Array[Long]) extends AnyVal with MutArr[Long]
{ override def length: Int = array.length
  override def apply(index: Int): Long = array(index)
}


class Dbls(val array: Array[Double]) extends AnyVal with ArrImut[Double]
{ override def length: Int = array.length
  override def apply(index: Int): Double = array(index)
  def ++ (op: Dbls): Dbls =
  { val newArray = new Array[Double](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Dbls(newArray)
  }
}

object Dbls
{ def apply(input: Double*): Dbls = new Dbls(input.toArray)
  implicit val bindImplicit: Bind[Dbls] = new Bind[Dbls]
  {
    override def bind[A](orig: ArrayBased[A], f: A => Dbls): Dbls =
    { val buff = new ArrayBuffer[Double]
      orig.foreach(a => buff.addAll(f(a).array))
      new Dbls(buff.toArray)
    }
  }
}

class BuffDou(val buffer: ArrayBuffer[Double]) extends AnyVal with BuffArr[Double]
{ override def length: Int = buffer.length
  override def apply(index: Int): Double = buffer(index)
}

class MutDou(val array: Array[Double]) extends AnyVal with MutArr[Double]
{ override def length: Int = array.length
  override def apply(index: Int): Double = array(index)
}