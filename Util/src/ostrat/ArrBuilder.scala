package ostrat
import collection.mutable.ArrayBuffer

trait BaseArr[A] extends Any
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

  def map[B](f: A => B)(implicit ev: ArrBuilder[B]): ev.G =
  { val res = ev.imutNew(length)
    iForeach((a, i) => ev.imutSet(res, i, f(a)))
    res
  }

  def flatMap[B](f: A => ImutArr[B])(implicit ev: ArrBuilder[B]): ev.G =
  { val buff = ev.buffNew(length)
    foreach(a => ev.buffAppends(buff, f(a).asInstanceOf[ev.G]))
    ev.buffImut(buff)
  }
}

trait ImutArr[A] extends Any with BaseArr[A]
trait BuffArr[A] extends Any with BaseArr[A]
trait MutArr[A] extends Any with BaseArr[A]

trait ArrBuilder[B]
{
  type G <: ImutArr[B]
  type H <: BuffArr[B]
  type J <: MutArr[B]
  def imutNew(length: Int): G
  def imutSet(arr: G, index: Int, value: B): Unit
  def buffNew(length: Int = 4): H
  def buffAppend(buff: H, value: B): Unit
  def buffAppends(buff: H, values: ImutArr[B]): Unit = values.foreach(buffAppend(buff, _))
  def buffImut(buff: H): G
  def mutNew(length: Int): J
}

object ArrBuilder
{
  implicit val intsImplicit: ArrBuilder[Int] = new ArrBuilder[Int]
  {
    type G = ArrInt
    type H = BuffInt
    type J = MutInt
    override def imutNew(length: Int): ArrInt = new ArrInt(new Array[Int](length))
    override def imutSet(arr: ArrInt, index: Int, value: Int): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): BuffInt = new BuffInt(new ArrayBuffer[Int](length))
    override def buffAppend(buff: BuffInt, value: Int): Unit = buff.buffer.append(value)
    //override def buffAppends(buff: BuffInt, values: ImutArr[Int]): Unit = values.buff.buffer.addAll(values.array)
    override def buffImut(buff: BuffInt): ArrInt = new ArrInt(buff.buffer.toArray)
    override def mutNew(length: Int): MutInt = new MutInt(new Array[Int](length))
  }

  implicit val doublesImplicit: ArrBuilder[Double] = new ArrBuilder[Double]
  { type G = ArrDou
    type H = BuffDou
    type J = MutDou
    override def imutNew(length: Int): ArrDou = new ArrDou(new Array[Double](length))
    override def imutSet(arr: ArrDou, index: Int, value: Double): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): BuffDou = new BuffDou(new ArrayBuffer[Double](length))
    override def buffAppend(buff: BuffDou, value: Double): Unit = buff.buffer.append(value)
    //override def buffAppends(buff: BuffDou, values: ArrDou): Unit = buff.buffer.addAll(values.array)
    override def buffImut(buff: BuffDou): ArrDou = new ArrDou(buff.buffer.toArray)
    override def mutNew(length: Int): MutDou = new MutDou(new Array[Double](length))
  }
}
