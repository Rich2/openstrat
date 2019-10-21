package ostrat
import collection.mutable.ArrayBuffer

trait Bind[BB <: ArrImut[_]]
{ def bind[A](orig: ArrayLike[A], f: A => BB): BB
}


trait ArrBuilder[B]
{ type ImutT <: ArrImut[B]
  type BuffT <: ArrBuff[B]
  def imutNew(length: Int): ImutT
  def imutSet(arr: ImutT, index: Int, value: B): Unit
  def buffNew(length: Int = 4): BuffT
  def buffAppend(buff: BuffT, value: B): Unit
  def buffAppendSeq(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffAppend(buff, _))
  def buffImut(buff: BuffT): ImutT
  def fBind[A](as: ArrayLike[A], f: A => ImutT): ImutT = imutNew(0)
}

object ArrBuilder
{
  implicit val intsImplicit: ArrBuilder[Int] = new ArrBuilder[Int]
  { type ImutT = Ints
    type BuffT = IntsBuff
    override def imutNew(length: Int): Ints = new Ints(new Array[Int](length))
    override def imutSet(arr: Ints, index: Int, value: Int): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): IntsBuff = new IntsBuff(new ArrayBuffer[Int](length))
    override def buffAppend(buff: IntsBuff, value: Int): Unit = buff.buffer.append(value)
    //override def buffAppends(buff: BuffInts, values: ArrImut[Int]): Unit = values.buff.buffer.addAll(values.array)
    override def buffImut(buff: IntsBuff): Ints = new Ints(buff.buffer.toArray)
  }

  implicit val doublesImplicit: ArrBuilder[Double] = new ArrBuilder[Double]
  { type ImutT = Dbls
    type BuffT = DblBuff
    override def imutNew(length: Int): Dbls = new Dbls(new Array[Double](length))
    override def imutSet(arr: Dbls, index: Int, value: Double): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): DblBuff = new DblBuff(new ArrayBuffer[Double](length))
    override def buffAppend(buff: DblBuff, value: Double): Unit = buff.buffer.append(value)
    //override def buffAppends(buff: BuffDou, values: Dbls): Unit = buff.buffer.addAll(values.array)
    override def buffImut(buff: DblBuff): Dbls = new Dbls(buff.buffer.toArray)
  }
}