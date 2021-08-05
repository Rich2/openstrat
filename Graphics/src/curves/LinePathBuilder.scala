/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag, annotation.unused

/** A common trait inherited by [[SeqBuilder]] and [[ArrTFlatBuider]]. */
trait LinePathBuilderCommon[ArrB <: DataImut[_]]
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
trait LinePathBuilder[B, ArrB <: DataImut[B]] extends LinePathBuilderCommon[ArrB]
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