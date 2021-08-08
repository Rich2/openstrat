/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, annotation.unused

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait ArrBuilder[B, ArrB <: ArrBase[B]] extends DataBuilder[B, ArrB]
{ def newArr(length: Int): ArrB
  def arrSet(arr: ArrB, index: Int, value: B): Unit

  def buffContains(buff: BuffT, newElem: B): Boolean =
  { var res = false
    var count = 0
    while (!res & count < buff.elemsNum) if (buff(count) == newElem) res = true else count += 1
    res
  }

  /** A mutable operation that extends the ArrayBuffer with the elements of the Iterable operand. */
  def buffGrowIter(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffGrow(buff, _))

  def iterMap[A](inp: Iterable[A], f: A => B): ArrB =
  { val buff = newBuff()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToBB(buff)
  }
}

/** The companion object for ArrBuild contains implicit ArrBuild instances for common types. */
object ArrBuilder extends ArrBuilderLowPriority
{ implicit val intsImplicit: ArrBuilder[Int, Ints] = IntsBuild
  implicit val doublesImplicit: ArrBuilder[Double, Dbls] = DblsBuild
  implicit val longImplicit: ArrBuilder[Long, Longs] = LongsBuild
  implicit val floatImplicit: ArrBuilder[Float, Floats] = FloatsBuild
  implicit val stringImplicit: ArrBuilder[String, Strings] = StringsBuild
  implicit val booleansImplicit: ArrBuilder[Boolean, Booleans] = BooleansBuild
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. Traits that extend SpecialT are excluded from
 * the implicit instance for [[Arr]]. */
trait SpecialT extends Any

trait ArrBuilderLowPriority
{
  /** This is the fall back builder implicit for Arrs that do not have their own specialist ArrBase classes. It is placed in this low priority trait
   * to gove those specialist Arr classes implicit priority. The notA implicit parameter is to exclude user defined types that have their own
   * specialist Arr classes. */
  implicit def anyImplicit[B](implicit ct: ClassTag[B], @unused notA: Not[SpecialT]#L[B]): ArrBuilder[B, Arr[B]] = new AnyBuild[B]
}
