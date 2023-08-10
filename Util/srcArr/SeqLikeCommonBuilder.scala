/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, annotation.unused

/** Base trait for all [[SeqLike]] builders, both map builders and flatMap builders. */
trait SeqLikeCommonBuilder[BB <: SeqLike[_]]
{
  /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compile time wrapped Arraybuffer inheriting from
   *  BuffProdHomo. */
  type BuffT <: Buff[_]

  /** Creates a new empty [[Buff]] with a default capacity of 4 elements. */
  def newBuff(length: Int = 4): BuffT

  /** converts a the buffer type to the target compound class. */
  def buffToSeqLike(buff: BuffT): BB
}

/** Builder trait for map operations. This has the additional method of buffGrow(buff: BuffT, value: B): Unit. This method is not required for flatMap
 * operations where the type of the element of the [[SeqLike]] that the builder is constructed may not be known at the point of dispatch. */
trait SeqLikeMapBuilder[B, BB <: SeqLike[B]] extends SeqLikeCommonBuilder[BB]
{ type BuffT <: Buff[B]

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, newElem: B): Unit

  /** Creates a new uninitialised [[Arr]] of type ArrB of the given length. */
  def uninitialised(length: Int): BB

  /** A mutable operation that extends the ArrayBuffer with the elements of the Iterable operand. */
  def buffGrowIter(buff: BuffT, newElems: Iterable[B]): Unit = newElems.foreach(buffGrow(buff, _))

  /** Sets the value in a [[SeqLike]] of type BB. This is usually used in conjunction with uninitialised method. */
  def indexSet(seqLike: BB, index: Int, elem: B): Unit
}

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait ArrMapBuilder[B, ArrB <: Arr[B]] extends SeqLikeMapBuilder[B, ArrB]
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

/** The companion object for ArrBuild contains implicit ArrBuild instances for common types. */
object ArrMapBuilder extends ArrBuilderPriority2
{ implicit val intsImplicit: ArrMapBuilder[Int, IntArr] = IntArrBuilder
  implicit val doublesImplicit: ArrMapBuilder[Double, DblArr] = DblArrBuilder
  implicit val longImplicit: ArrMapBuilder[Long, LongArr] = LongArrBuilder
  implicit val floatImplicit: ArrMapBuilder[Float, FloatArr] = FloatArrBuilder
  implicit val stringImplicit: ArrMapBuilder[String, StrArr] = StringArrBuilder
  implicit val booleansImplicit: ArrMapBuilder[Boolean, BoolArr] = BooleanArrBuilder
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. Traits that extend SpecialT are excluded from
 * the implicit instance for [[RArr]]. */
trait SpecialT extends Any

trait ArrBuilderPriority2
{
  /** This is the fall back builder implicit for Arrs that do not have their own specialist ArrBase classes. It is placed in this low priority trait
   * to gove those specialist Arr classes implicit priority. The notA implicit parameter is to exclude user defined types that have their own
   * specialist Arr classes. */
  implicit def rMapImplicit[B](implicit ct: ClassTag[B], @unused notA: Not[SpecialT]#L[B]): ArrMapBuilder[B, RArr[B]] = new RArrAllBuilder[B]
}

trait SeqLikeFlatBuilder[BB <: SeqLike[_]] extends SeqLikeCommonBuilder[BB]


/** A type class for the building of efficient compact Immutable Arrays through a flatMap method. Instances for this type class for classes / traits
 *  you control should go in the companion object of BB. This is different from the related [[ArrMapBuilder]][BB] type class where the instance
 *  should go into the B companion object. */
trait ArrFlatBuilder[ArrB <: Arr[_]] extends SeqLikeFlatBuilder[ArrB] with SeqLikeCommonBuilder[ArrB]
{ /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrB): Unit
}

/** Companion object for ArrTFlatBuilder, contains implicit instances for atomic value classes. */
object ArrFlatBuilder extends ArrFlatBuilderLowPriority
{ implicit val intsImplicit: ArrFlatBuilder[IntArr] = IntArrBuilder
  implicit val dblsImplicit: ArrFlatBuilder[DblArr] = DblArrBuilder
  implicit val longsImplicit: ArrFlatBuilder[LongArr] = LongArrBuilder
  implicit val floatImplicit: ArrFlatBuilder[FloatArr] = FloatArrBuilder
  implicit val booleansImplicit: ArrFlatBuilder[BoolArr] = BooleanArrBuilder
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. */
trait SpecialArrT extends Any

trait ArrFlatBuilderLowPriority
{ /** This is the fall back builder implicit for Arrs that do not have their own specialist ArrBuildBase classes. It is placed in this low priority trait
 * to gove those specialist Arr classes implicit priority. The notA implicit parameter is to exclude user defined types that have their own
 * specialist Arr classes. */
  implicit def anyImplicit[A](implicit ct: ClassTag[A], @unused notA: Not[ValueNElem]#L[A]): ArrFlatBuilder[RArr[A]] = new RArrAllBuilder[A]
}

trait SeqSpecFlatBuilder[ArrB <: Arr[_], BB <: SeqSpec[_]] extends SeqLikeCommonBuilder[BB]
{
  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: BB): Unit
}