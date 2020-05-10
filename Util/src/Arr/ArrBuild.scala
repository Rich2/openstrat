/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
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

/** ArrFlatBuild[BB] is a type class for the building of efficient compact Immutable Arrays through a bind method, which works similar to flatMap on
 * standard Library collections. It is called bind rather than flatMap partly to distinguish it and party so as it can be used as extension method on
 *  Standard Library collections. Instances for this typeclass for classes / traits you control should go in the companion object of BB. This is
 *  different from the related ArrBuild[BB] typeclass where the instance should go into the B companion object. */
trait ArrFlatBuild[ArrT <: ArrBase[_]] extends ArrBuildBase[ArrT]

object ArrFlatBuild
{
  implicit val intsImplicit = IntsBuild
  implicit val dblsImplicit = DblsBuild
  implicit val longsImplicit = LongsBuild
  implicit val floatImplicit = FloatsBuild
  implicit val booleansImplicit = BooleansBuild
  implicit def refsImplicit[A <: AnyRef](implicit ct: ClassTag[A]/*, @unused notA: Not[ProdHomo]#L[A]*/) = new RefsBuild[A]
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
  { val res = false
    var count = 0
    while (!res & count < buff.length) if (buff(count) == newElem) true else count += 1
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

object ArrBuild extends ArrBuildLowPriority
{ implicit val intsImplicit = IntsBuild
  implicit val doublesImplicit = DblsBuild
  implicit val longImplicit = LongsBuild
  implicit val floatImplicit = FloatsBuild
  implicit val booleansImplicit = BooleansBuild


}

trait SpecialT extends Any


trait ArrBuildLowPriority
{
  /** This is currently set up to exclude types not extending AnyRef. The notA implicit parameter is to exclude types that are Homogeneous value
   * types. */
  implicit def refsImplicit[A](implicit ct: ClassTag[A], @unused notA: Not[SpecialT]#L[A]) = new RefsBuild[A]
  //implicit def anyImplicit[A](implicit ct: ClassTag[A], @unused notA: Not[SpecialT]#L[A]) = new AnysBuild[A]
}
