/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, scala.annotation.unused

trait ArrBuildBase[ArrT <: ArrBase[_]]
{
  /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compilte time wrapped Arraybuffer inheriting from
      BuffProdHomo. */
  type BuffT <: ArrayLike[_]
  def newBuff(length: Int = 4): BuffT
  def buffToArr(buff: BuffT): ArrT
  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrT): Unit
}

/** ArrBuilder[B, BB] is a type class for the building of efficient compact Immutable Arrays. Instances for this typeclass for classes / traits you
 *  control should go in the companion object of B not the companion object of not BB. This is different from the related ArrBinder[BB] typeclass
 *  where instance should go into the BB companion object. The type parameter is named B rather than A, because normally this will be found by an
 *  implicit in the context of a function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever
 *  possible they should not be used directly by end users. */
trait ArrBuild[B, ArrT <: ArrBase[B]] extends ArrBuildBase[ArrT]
{ type BuffT <: ArrayLike[B]
  def newArr(length: Int): ArrT
  def arrSet(arr: ArrT, index: Int, value: B): Unit

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit

  def buffContains(buff: BuffT, newElem: B): Boolean =
  { var res = false
    var count = 0
    while (!res & count < buff.elemsLen) if (buff(count) == newElem) res = true else count += 1
    res
  }

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrT): Unit// = arr.foreach(buffGrow(buff, _))

  /** A mutable operation that extends the ArrayBuffer with the elements of the Iterable operand. */
  def buffGrowIter(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffGrow(buff, _))

  def iterMap[A](inp: Iterable[A], f: A => B): ArrT =
  { val buff = newBuff()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToArr(buff)
  }
}

/** The companion object for ArrBuild contains implicit ArrBuild instances for common types. */
object ArrBuild extends ArrBuildLowPriority
{ implicit val intsImplicit: ArrBuild[Int, Ints] = IntsBuild
  implicit val doublesImplicit: ArrBuild[Double, Dbls] = DblsBuild
  implicit val longImplicit: ArrBuild[Long, Longs] = LongsBuild
  implicit val floatImplicit: ArrBuild[Float, Floats] = FloatsBuild
  implicit val stringImplicit: ArrBuild[String, Strings] = StringsBuild
  implicit val booleansImplicit: ArrBuild[Boolean, Booleans] = BooleansBuild
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. */
trait SpecialT extends Any

trait ArrBuildLowPriority
{
  /** This is the fall back builder implicit for Arrs that do not have their own specialist ArrBase classes. It is placed in this low priority trait
   * to gove those specialist Arr classes implicit priority. The notA implicit parameter is to exclude user defined types that have their own
   * specialist Arr classes. */
  implicit def anyImplicit[A](implicit ct: ClassTag[A], @unused notA: Not[SpecialT]#L[A]): ArrBuild[A, Arr[A]] = new AnyBuild[A]
}
