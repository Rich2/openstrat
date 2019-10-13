package ostrat
import collection.mutable.ArrayBuffer

trait BaseArr[+A] extends Any
{ def length: Int
  def apply(index: Int): A

  def foreach[U](f: A => U): Unit =
  { var count = 0
    while(count < length)
    { f(apply(count))
      count = count + 1
    }
  }

  /** foreach with index. The startIndex parameter is placed 2nd to allow it to have a default value of zero. */
  def iForeach[U](f: (A, Int) => U, startIndex: Int = 0): Unit =
  { val endIndex = length + startIndex
    var i: Int = startIndex
    while(i < endIndex ) { f(apply(i), i); i = i + 1 }
  }

  def map[B](f: A => B)(implicit ev: ArrBuilder[B]): ev.ImutT =
  { val res = ev.imutNew(length)
    iForeach((a, i) => ev.imutSet(res, i, f(a)))
    res
  }

  def seqMap[B](f: A => Iterable[B])(implicit ev: ArrBuilder[B]): ev.ImutT =
  { val buff = ev.buffNew(length)
    foreach(a => ev.buffAppendSeq(buff, f(a)))
    ev.buffImut(buff)
  }
}

object BaseArr
{
  implicit class BaseArrImplicit[A](ba: BaseArr[A])
  {
   def bind[BB <: ImutArr[_]](f: A => BB)(implicit ev: Bind[BB]): BB = ev.bind[A](ba, f)
  }
}

trait Bind[BB <: ImutArr[_]]
{
  def bind[A](orig: BaseArr[A], f: A => BB): BB
}

trait ImutArr[+A] extends Any with BaseArr[A]
trait BuffArr[A] extends Any with BaseArr[A]
trait MutArr[A] extends Any with BaseArr[A]

trait ArrBuilder[B]
{ type ImutT <: ImutArr[B]
  type BuffT <: BuffArr[B]
  type MutT <: MutArr[B]
  def imutNew(length: Int): ImutT
  def imutSet(arr: ImutT, index: Int, value: B): Unit
  def buffNew(length: Int = 4): BuffT
  def buffAppend(buff: BuffT, value: B): Unit
  def buffAppendSeq(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffAppend(buff, _))
  def buffImut(buff: BuffT): ImutT
  def mutNew(length: Int): MutT
  def fBind[A](as: BaseArr[A], f: A => ImutT): ImutT = imutNew(0)
}

object ArrBuilder
{
  implicit val intsImplicit: ArrBuilder[Int] = new ArrBuilder[Int]
  { type ImutT = Ints
    type BuffT = BuffInts
    type MutT = MutInts
    override def imutNew(length: Int): Ints = new Ints(new Array[Int](length))
    override def imutSet(arr: Ints, index: Int, value: Int): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): BuffInts = new BuffInts(new ArrayBuffer[Int](length))
    override def buffAppend(buff: BuffInts, value: Int): Unit = buff.buffer.append(value)
    //override def buffAppends(buff: BuffInts, values: ImutArr[Int]): Unit = values.buff.buffer.addAll(values.array)
    override def buffImut(buff: BuffInts): Ints = new Ints(buff.buffer.toArray)
    override def mutNew(length: Int): MutInts = new MutInts(new Array[Int](length))
  }

  implicit val doublesImplicit: ArrBuilder[Double] = new ArrBuilder[Double]
  { type ImutT = DFloats
    type BuffT = BuffDou
    type MutT = MutDou
    override def imutNew(length: Int): DFloats = new DFloats(new Array[Double](length))
    override def imutSet(arr: DFloats, index: Int, value: Double): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): BuffDou = new BuffDou(new ArrayBuffer[Double](length))
    override def buffAppend(buff: BuffDou, value: Double): Unit = buff.buffer.append(value)
    //override def buffAppends(buff: BuffDou, values: DFloats): Unit = buff.buffer.addAll(values.array)
    override def buffImut(buff: BuffDou): DFloats = new DFloats(buff.buffer.toArray)
    override def mutNew(length: Int): MutDou = new MutDou(new Array[Double](length))
  }
}
