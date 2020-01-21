package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** ArrFlatBuild[BB] is a type class for the building of efficient compact Immutable Arrays through a bind method, which works similar to flatMap on
 * standard Library collections. It is called bind rather than flatMap partly to distinguish it and party so as it can be used as extension method on
 *  Standard Library collections. Instances for this typeclass for classes / traits you control should go in the companion object of BB. This is
 *  different from the related ArrBuild[BB] typeclass where the instance should go into the B companion object. */
trait ArrFlatBuild[ArrT <: ArrImut[_]]
{ def flatMap[A](orig: ArrayLike[A], f: A => ArrT): ArrT
}

/** ArrBuilder[B, BB] is a type class for the building of efficient compact Immutable Arrays. Instances for this typeclass for classes / traits you
 *  control should go in the companion object of B not the companion object of not BB. This is different from the related ArrBinder[BB] typeclass
 *  where instance should go into the BB companion object. The type parameter is named B rather than A, because normally this will be found by an
 *  implicit in the context of a function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever
 *  possible they should not be used directly by end users. */
trait ArrBuild[B, ArrT <: ArrImut[B]]
{ /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compilte time wrapped Arraybuffer inheriting from
      BuffProdHomo. */
  type BuffT
  def imutNew(length: Int): ArrT
  def imutSet(arr: ArrT, index: Int, value: B): Unit
  def buffNew(length: Int = 4): BuffT

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit

  /** Amutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrT): Unit = arr.foreach(buffGrow(buff, _))

  /** This method. */
  def buffAppendSeq(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffGrow(buff, _))
  
  def buffToArr(buff: BuffT): ArrT

  def iterMap[A](inp: Iterable[A], f: A => B): ArrT =
  { val buff = buffNew()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToArr(buff)
  }
}

object ArrBuild
{
  implicit val intsImplicit: ArrBuild[Int, Ints] = new ArrBuild[Int, Ints]
  { type BuffT = ArrayBuffer[Int]
    override def imutNew(length: Int): Ints = new Ints(new Array[Int](length))
    override def imutSet(arr: Ints, index: Int, value: Int): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Int] = new ArrayBuffer[Int]((length))
    override def buffGrow(buff: ArrayBuffer[Int], value: Int): Unit = buff.append(value)
    override def buffToArr(buff: ArrayBuffer[Int]): Ints = new Ints(buff.toArray)
  }

  implicit val doublesImplicit: ArrBuild[Double, Dbls] = new ArrBuild[Double, Dbls]
  { type BuffT = ArrayBuffer[Double]
    override def imutNew(length: Int): Dbls = new Dbls(new Array[Double](length))
    override def imutSet(arr: Dbls, index: Int, value: Double): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Double] = new ArrayBuffer[Double](length)
    override def buffGrow(buff: ArrayBuffer[Double], value: Double): Unit = buff.append(value)
    override def buffToArr(buff: ArrayBuffer[Double]): Dbls = new Dbls(buff.toArray)
  }

  implicit def refsImplicit[A <: AnyRef](implicit ct: ClassTag[A], notA: Not[ProdHomo]#L[A]): ArrBuild[A, Refs[A]] = new ArrBuild[A, Refs[A]]
  { type BuffT = ArrayBuffer[A]
    override def imutNew(length: Int): Refs[A] = new Refs(new Array[A](length))
    override def imutSet(arr: Refs[A], index: Int, value: A): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[A] = new ArrayBuffer[A](length)
    override def buffGrow(buff: ArrayBuffer[A], value: A): Unit = buff.append(value)
    override def buffToArr(buff: ArrayBuffer[A]): Refs[A] = new Refs(buff.toArray)
  }

  implicit val booleansImplicit: ArrBuild[Boolean, Booleans] = new ArrBuild[Boolean, Booleans]
  { type BuffT = ArrayBuffer[Boolean]
    override def imutNew(length: Int): Booleans = new Booleans(new Array[Boolean](length))
    override def imutSet(arr: Booleans, index: Int, value: Boolean): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Boolean] = new ArrayBuffer[Boolean](length)
    override def buffGrow(buff: ArrayBuffer[Boolean], value: Boolean): Unit = buff.append(value)
    override def buffToArr(buff: ArrayBuffer[Boolean]): Booleans = new Booleans(buff.toArray)
  }

  implicit val longImplicit: ArrBuild[Long, Longs] = new ArrBuild[Long, Longs]
  { type BuffT = ArrayBuffer[Long]
    override def imutNew(length: Int): Longs = new Longs(new Array[Long](length))
    override def imutSet(arr: Longs, index: Int, value: Long): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Long] = new ArrayBuffer[Long](length)
    override def buffGrow(buff: ArrayBuffer[Long], value: Long): Unit = buff.append(value)
    override def buffToArr(buff: ArrayBuffer[Long]): Longs = new Longs(buff.toArray)
  }

  implicit val floatImplicit: ArrBuild[Float, Floats] = new ArrBuild[Float, Floats]
  { type BuffT = ArrayBuffer[Float]
    override def imutNew(length: Int): Floats = new Floats(new Array[Float](length))
    override def imutSet(arr: Floats, index: Int, value: Float): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Float] = new ArrayBuffer[Float](length)
    override def buffGrow(buff: ArrayBuffer[Float], value: Float): Unit = buff.append(value)
    override def buffToArr(buff: ArrayBuffer[Float]): Floats = new Floats(buff.toArray)
  }
}