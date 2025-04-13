/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, annotation.unused

/** Base trait for all [[SeqLike]] builders, both map builders and flatMap builders. */
trait BuilderSeqLike[BB <: SeqLike[?]] extends BuilderBoth[BB]
{ /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = [[IntArr]], or it can be a compile time wrapped Arraybuffer inheriting from
   * [[Buff]]. */
  type BuffT <: Buff[?]
}

/** Builder trait for map operations. This has the additional method of buffGrow(buff: BuffT, value: B): Unit. This method is not required for flatMap
 * operations where the type of the element of the [[SeqLike]] that the builder is constructed may not be known at the point of dispatch. */
trait BuilderMapSeqLike[B, BB <: SeqLikeImut[B]] extends BuilderMap[B, BB], BuilderSeqLike[BB]
{ type BuffT <: Buff[B]

  /** Creates a new uninitialised [[SeqLike]] of type BB of the given length. */
  def uninitialised(length: Int): BB

  /** Creates a new empty [[SeqLike]] of type BB. */
  override def empty: BB = uninitialised(0)

  /** A mutable operation that extends the [[Buff]] with the elements of the Iterable operand. */
  def buffGrowIter(buff: BuffT, newElems: Iterable[B]): Unit = newElems.foreach(buffGrow(buff, _))

  /** Sets the value in a [[SeqLike]] of type BB. This is usually used in conjunction with uninitialised method. */
  def indexSet(seqLike: BB, index: Int, newElem: B): Unit
}

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in the
 * companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into the BB
 * companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a function from A => B or
 * A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be used directly by end users. */
trait BuilderMapArr[B, ArrB <: Arr[B]] extends BuilderMapSeqLike[B, ArrB]
{
  def buffContains(buff: BuffT, newElem: B): Boolean =
  { var res = false
    var count = 0
    while (!res & count < buff.length) if (buff(count) == newElem) res = true else count += 1
    res
  }

  def iterMap[A](inp: Iterable[A], f: A => B): ArrB =
  { val buff = newBuff()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToSeqLike(buff)
  }
}

/** The companion object for [[BuilderMapArr]] type class, contains implicit instances for common types. */
object BuilderMapArr extends BuilderMapArrPriority2
{ /** 1st priority implicit [[BuilderMapArr]] type class instance evidence for [[Int]] and [[IntArr]]. */
  implicit val intEv: BuilderMapArr[Int, IntArr] = IntArrBuilder

  /** 1st priority implicit [[BuilderMapArr]] type class instance evidence for [[Double]] and [[DblArr]]. */
  implicit val doubleEv: BuilderMapArr[Double, DblArr] = DblArrBuilder

  /** 1st priority implicit [[BuilderMapArr]] type class instance evidence for [[Long]] and [[LongArr]]. */
  implicit val longEv: BuilderMapArr[Long, LongArr] = LongArrBuilder

  /** 1st priority implicit [[BuilderMapArr]] type class instance evidence for [[Float]] and [[FloatArr]]. */
  implicit val floatEv: BuilderMapArr[Float, FloatArr] = FloatArrBuilder

  /** 1st priority implicit [[BuilderMapArr]] type class instance evidence for [[String]] and [[StringArr]]. */
  implicit val stringEv: BuilderMapArr[String, StrArr] = BuilderArrString

  /** 1st priority implicit [[BuilderMapArr]] type class instance evidence for [[Boolean]] and [[BooleanArr]]. */
  implicit val booleanEv: BuilderMapArr[Boolean, BoolArr] = BooleanArrBuilder
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. Traits that extend SpecialT are excluded from the implicit
 * instance for [[RArr]]. */
trait SpecialT extends Any

trait BuilderMapArrPriority2
{ /** 2nd priority implicit [[BuilderMapArr]] instances evidence for [[Any]] and [[RArr]]. This is the fallback builder implicit for [[Arr]]s that do not have
   * their own specialist [[Arr]] classes. It is placed in this low priority trait to give those specialist Arr classes implicit priority. The notA implicit
   * parameter is to exclude user defined types that have their own specialist Arr classes. */
  implicit def anyEv[B](implicit ct: ClassTag[B], @unused notA: Not[SpecialT]#L[B]): BuilderMapArr[B, RArr[B]] = new RArrAllBuilder[B]
}

/** Builds [[SeqLike]] objects via flatMap methods. Hence, the type of the element of the sequence or specifiying sequence is not known at the call site. */
trait BuilderFlatSeqLike[BB <: SeqLike[?]] extends BuilderSeqLike[BB]

/** A type class for the building of efficient compact Immutable Arrays through a flatMap method. Instances for this type class for classes / traits you control
 * should go in the companion object of BB. This is different from the related [[BuilderMapArr]][BB] type class where the instance should go into the B
 * companion object. */
trait BuilderFlatArr[ArrB <: Arr[?]] extends BuilderFlatSeqLike[ArrB]
{ /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrB): Unit
}

/** Companion object for [[BuilderFlatArr]], contains implicit instances for atomic value classes. */
object BuilderFlatArr extends BuilderArrFlatPriority2
{
  /** Implicit [[BuilderFlatArr]] type class instance / evidence for [[IntArr]]. */
  implicit val intArrEv: BuilderFlatArr[IntArr] = IntArrBuilder

  /** Implicit [[BuilderFlatArr]] type class instance / evidence for [[DblArr]]. */
  implicit val dblArrEv: BuilderFlatArr[DblArr] = DblArrBuilder

  /** Implicit [[BuilderFlatArr]] type class instance / evidence for [[LongArr]]. */
  implicit val longArrEv: BuilderFlatArr[LongArr] = LongArrBuilder

  /** Implicit [[BuilderFlatArr]] type class instance / evidence for [[FloatArr]]. */
  implicit val floatArrEv: BuilderFlatArr[FloatArr] = FloatArrBuilder

  /** Implicit [[BuilderFlatArr]] type class instance / evidence for [[BoolArr]]. */
  implicit val boolArrEv: BuilderFlatArr[BoolArr] = BooleanArrBuilder
}

/** if you create your own specialist [[Arr]] class for a type T, make sure that type T extends [[SpecialArrUser]]. This invalidates the implicit to build an
 * [[RArr]] */
trait SpecialArrUser extends Any

trait BuilderArrFlatPriority2
{ /** [[BuilderFlatArr]] type class instances / evidence for [[RArr]] This is the fallback builder implicit for types that do not have their own specialist
   * [[Arr]] and [[BuilderFlatArr]] classes. It is placed in this low priority trait to give those specialist Arr classes implicit priority. The notA implicit
   * parameter is to exclude user defined types that have their own specialist Arr classes. */
  implicit def anyRArrEv[A](implicit ct: ClassTag[A], @unused notA: Not[ValueNElem]#L[A]): BuilderFlatArr[RArr[A]] = new RArrAllBuilder[A]
}