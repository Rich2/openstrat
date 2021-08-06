/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, annotation.unused

/** A common trait inherited by [[SeqBuilder]] and [[SeqFlatBuilder]]. */
trait SeqBuilderCommon[ArrB <: SeqImut[_]]
{
  /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compilte time wrapped Arraybuffer inheriting from
      BuffProdHomo. */
  type BuffT <: SeqGen[_]
  def newBuff(length: Int = 4): BuffT
  def buffToArr(buff: BuffT): ArrB

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrB): Unit
}

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait SeqBuilder[B, ArrB <: SeqImut[B]] extends SeqBuilderCommon[ArrB]
{ type BuffT <: SeqGen[B]
  def newArr(length: Int): ArrB
  def arrSet(arr: ArrB, index: Int, value: B): Unit

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit

  def buffContains(buff: BuffT, newElem: B): Boolean =
  { var res = false
    var count = 0
    while (!res & count < buff.elemsNum) if (buff(count) == newElem) res = true else count += 1
    res
  }

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrB): Unit// = arr.foreach(buffGrow(buff, _))

  /** A mutable operation that extends the ArrayBuffer with the elements of the Iterable operand. */
  def buffGrowIter(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffGrow(buff, _))

  def iterMap[A](inp: Iterable[A], f: A => B): ArrB =
  { val buff = newBuff()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToArr(buff)
  }
}

/** The companion object for ArrBuild contains implicit ArrBuild instances for common types. */
object SeqBuilder extends ArrBuildLowPriority
{ implicit val intsImplicit: SeqBuilder[Int, Ints] = IntsBuild
  implicit val doublesImplicit: SeqBuilder[Double, Dbls] = DblsBuild
  implicit val longImplicit: SeqBuilder[Long, Longs] = LongsBuild
  implicit val floatImplicit: SeqBuilder[Float, Floats] = FloatsBuild
  implicit val stringImplicit: SeqBuilder[String, Strings] = StringsBuild
  implicit val booleansImplicit: SeqBuilder[Boolean, Booleans] = BooleansBuild
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. Traits that extend SpecialT are excluded from
 * the implicit instance for [[Arr]]. */
trait SpecialT extends Any

trait ArrBuildLowPriority
{
  /** This is the fall back builder implicit for Arrs that do not have their own specialist ArrBase classes. It is placed in this low priority trait
   * to gove those specialist Arr classes implicit priority. The notA implicit parameter is to exclude user defined types that have their own
   * specialist Arr classes. */
  implicit def anyImplicit[B](implicit ct: ClassTag[B], @unused notA: Not[SpecialT]#L[B]): SeqBuilder[B, Arr[B]] = new AnyBuild[B]
}
