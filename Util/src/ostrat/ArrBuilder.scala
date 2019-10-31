package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

trait Bind[BB <: ArrImut[_]]
{ def bind[A](orig: ArrayLike[A], f: A => BB): BB
}

trait ArrBuilder[B, BB <: ArrImut[B]]
{ type BuffT // <: BufferLike[B]
  def imutNew(length: Int): BB
  def imutSet(arr: BB, index: Int, value: B): Unit
  def buffNew(length: Int = 4): BuffT
  def buffAppend(buff: BuffT, value: B): Unit
  def buffAppendSeq(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffAppend(buff, _))
  def buffImut(buff: BuffT): BB
  def fBind[A](as: ArrayLike[A], f: A => BB): BB = imutNew(0)
}

object ArrBuilder
{
  implicit val intsImplicit: ArrBuilder[Int, Ints] = new ArrBuilder[Int, Ints]
  { type BuffT = ArrayBuffer[Int]
    override def imutNew(length: Int): Ints = new Ints(new Array[Int](length))
    override def imutSet(arr: Ints, index: Int, value: Int): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Int] = new ArrayBuffer[Int]((length))
    override def buffAppend(buff: ArrayBuffer[Int], value: Int): Unit = buff.append(value)
    override def buffImut(buff: ArrayBuffer[Int]): Ints = new Ints(buff.toArray)
  }

  implicit val doublesImplicit: ArrBuilder[Double, Dbls] = new ArrBuilder[Double, Dbls]
  { type BuffT = ArrayBuffer[Double]
    override def imutNew(length: Int): Dbls = new Dbls(new Array[Double](length))
    override def imutSet(arr: Dbls, index: Int, value: Double): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Double] = new ArrayBuffer[Double](length)
    override def buffAppend(buff: ArrayBuffer[Double], value: Double): Unit = buff.append(value)
    override def buffImut(buff: ArrayBuffer[Double]): Dbls = new Dbls(buff.toArray)
  }

  implicit def refsImplicit[A <: AnyRef](implicit ct: ClassTag[A], notA: Not[ProdHomo]#L[A]): ArrBuilder[A, Refs[A]] = new ArrBuilder[A, Refs[A]]
  { type BuffT = ArrayBuffer[A]
    override def imutNew(length: Int): Refs[A] = new Refs(new Array[A](length))
    override def imutSet(arr: Refs[A], index: Int, value: A): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[A] = new ArrayBuffer[A](length)
    override def buffAppend(buff: ArrayBuffer[A], value: A): Unit = buff.append(value)
    override def buffImut(buff: ArrayBuffer[A]): Refs[A] = new Refs(buff.toArray)
  }
}