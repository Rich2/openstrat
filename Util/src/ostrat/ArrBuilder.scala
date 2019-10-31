package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

trait Bind[BB <: ArrImut[_]]
{ def bind[A](orig: ArrayLike[A], f: A => BB): BB
}

/*trait BBuild[B, BB <: ArrImut[B]]
{
  //def map[A, B <: BB](fa: ArrayLike[A], f: A => B): F[B]
  def imutNew(length: Int): BB
  def imutSet(arr: BB, index: Int, value: B): Unit
}

object BBuild
{
  implicit def refsImplicit[B <: AnyRef](implicit ct: ClassTag[B]): BBuild[B, Refs[B]] = new BBuild[B, Refs[B]]
  {
    override def imutNew(length: Int): Refs[B] =  new Refs(new Array[B](length))
    override def imutSet(arr: Refs[B], index: Int, value: B): Unit = arr.array(index) = value
  }
  implicit val intsImplicit: BBuild[Int, Ints] = new BBuild[Int, Ints]
  {
    override def imutNew(length: Int): Ints =  new Ints(new Array[Int](length))
    override def imutSet(arr: Ints, index: Int, value: Int): Unit = arr.array(index) = value
  }

}*/

trait ArrBuilder[B, BB <: ArrImut[B]]
{ type BuffT <: ArrBuff[B]
  def imutNew(length: Int): BB
  def imutSet(arr: BB, index: Int, value: B): Unit
  def buffNew(length: Int = 4): BuffT
  def buffAppend(buff: BuffT, value: B): Unit
  def buffAppendSeq(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffAppend(buff, _))
  def buffImut(buff: BuffT): BB
  def fBind[A](as: ArrayLike[A], f: A => BB): BB = imutNew(0)
}

/*trait ArrBuilderLowPriority
{

}*/

object ArrBuilder// extends ArrBuilderLowPriority
{
  implicit val intsImplicit: ArrBuilder[Int, Ints] = new ArrBuilder[Int, Ints]
  { type BuffT = IntsBuff
    override def imutNew(length: Int): Ints = new Ints(new Array[Int](length))
    override def imutSet(arr: Ints, index: Int, value: Int): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): IntsBuff = new IntsBuff(new ArrayBuffer[Int](length))
    override def buffAppend(buff: IntsBuff, value: Int): Unit = buff.buffer.append(value)
    override def buffImut(buff: IntsBuff): Ints = new Ints(buff.buffer.toArray)
  }

  implicit val doublesImplicit: ArrBuilder[Double, Dbls] = new ArrBuilder[Double, Dbls]
  {// final type ImutT = Dbls
    type BuffT = DblBuff
    override def imutNew(length: Int): Dbls = new Dbls(new Array[Double](length))
    override def imutSet(arr: Dbls, index: Int, value: Double): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): DblBuff = new DblBuff(new ArrayBuffer[Double](length))
    override def buffAppend(buff: DblBuff, value: Double): Unit = buff.buffer.append(value)
    override def buffImut(buff: DblBuff): Dbls = new Dbls(buff.buffer.toArray)
  }

  implicit def refsImplicit[A <: AnyRef](implicit ct: ClassTag[A], notA: Not[ProdDbl2]#L[A]): ArrBuilder[A, Refs[A]] =
    new ArrBuilder[A, Refs[A]]
  { type BuffT = RefsBuff[A]
    override def imutNew(length: Int): Refs[A] = new Refs(new Array[A](length))
    override def imutSet(arr: Refs[A], index: Int, value: A): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): RefsBuff[A] = new RefsBuff(new ArrayBuffer[A](length))
    override def buffAppend(buff: RefsBuff[A], value: A): Unit = buff.buffer.append(value)
    override def buffImut(buff: RefsBuff[A]): Refs[A] = new Refs(buff.buffer.toArray)
  }
}