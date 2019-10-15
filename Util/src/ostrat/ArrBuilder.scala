package ostrat
import collection.mutable.ArrayBuffer

trait Bind[BB <: ArrImut[_]]
{ def bind[A](orig: ArrayBased[A], f: A => BB): BB
}

trait ArrImut[+A] extends Any with ArrayBased[A]
{
  /*def removeFirst(f: A => Boolean)(implicit ct: ClassTag[A]): Arr[A] = thisArr.indexWhere(f) match
  { case -1 => thisArr
    case i =>
    {
      val mutArr = new Array[A](thisArr.length - 1)
      (0 until i).foreach(i => mutArr(i) = thisArr(i))
      (i + 1).until(thisArr.length).foreach(i => mutArr(i - 1) = thisArr(i))
      mutArr.toArr
    }
  }*/

}
trait BuffArr[A] extends Any with ArrayBased[A]
trait MutArr[A] extends Any with ArrayBased[A]

trait ArrBuilder[B]
{ type ImutT <: ArrImut[B]
  type BuffT <: BuffArr[B]
  def imutNew(length: Int): ImutT
  def imutSet(arr: ImutT, index: Int, value: B): Unit
  def buffNew(length: Int = 4): BuffT
  def buffAppend(buff: BuffT, value: B): Unit
  def buffAppendSeq(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffAppend(buff, _))
  def buffImut(buff: BuffT): ImutT
  def fBind[A](as: ArrayBased[A], f: A => ImutT): ImutT = imutNew(0)
}

object ArrBuilder
{
  implicit val intsImplicit: ArrBuilder[Int] = new ArrBuilder[Int]
  { type ImutT = Ints
    type BuffT = BuffInts
    override def imutNew(length: Int): Ints = new Ints(new Array[Int](length))
    override def imutSet(arr: Ints, index: Int, value: Int): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): BuffInts = new BuffInts(new ArrayBuffer[Int](length))
    override def buffAppend(buff: BuffInts, value: Int): Unit = buff.buffer.append(value)
    //override def buffAppends(buff: BuffInts, values: ArrImut[Int]): Unit = values.buff.buffer.addAll(values.array)
    override def buffImut(buff: BuffInts): Ints = new Ints(buff.buffer.toArray)
  }

  implicit val doublesImplicit: ArrBuilder[Double] = new ArrBuilder[Double]
  { type ImutT = Dbls
    type BuffT = BuffDou
    override def imutNew(length: Int): Dbls = new Dbls(new Array[Double](length))
    override def imutSet(arr: Dbls, index: Int, value: Double): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): BuffDou = new BuffDou(new ArrayBuffer[Double](length))
    override def buffAppend(buff: BuffDou, value: Double): Unit = buff.buffer.append(value)
    //override def buffAppends(buff: BuffDou, values: Dbls): Unit = buff.buffer.addAll(values.array)
    override def buffImut(buff: BuffDou): Dbls = new Dbls(buff.buffer.toArray)
  }
}