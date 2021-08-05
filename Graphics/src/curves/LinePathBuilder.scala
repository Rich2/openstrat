/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A common trait inherited by [[SeqBuilder]] and [[ArrTFlatBuider]]. */
trait LinePathBuilderCommon[ArrB <: LinePathLike[_]]
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
trait LinePathBuilder[B, ArrB <: LinePathLike[B]] extends LinePathBuilderCommon[ArrB]
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

/** Trait for creating the line path builder instances for the [[LinePathBuilder]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait ValueNsLinePathBuilder[B <: ValueNElem, ArrB <: LinePathLike[B]] extends LinePathBuilder[B, ArrB]
{ def elemProdSize: Int
}

/** Trait for creating the ArrTBuilder type class instances for [[DblNsSeq]] final classes. Instances for the [[SeqBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait DblNsLinePathBuilder[B <: DblNElem, ArrB <: DblNsLinePath[B] ] extends ValueNsLinePathBuilder[B, ArrB]
{ type BuffT <: DblNsBuffer[B]
  def fromDblArray(array: Array[Double]): ArrB
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemProdSize))
  final override def newArr(length: Int): ArrB = fromDblArray(new Array[Double](length * elemProdSize))
  final override def buffToArr(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuff.toArray)
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuff.addAll(arr.arrayUnsafe); () }
  final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
}

/** Trait for creating the line path type class instances for [[Dbl2Arr]] final classes. Instances for the [[DataBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called B, because it
 *  corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2sLinePathBuilder[B <: Dbl2Elem, ArrB <: Dbl2sLinePath[B]] extends DblNsLinePathBuilder[B, ArrB]
{ type BuffT <: Dbl2sBuffer[B]
  final override def elemProdSize = 2
  override def arrSet(arr: ArrB, index: Int, value: B): Unit = { arr.arrayUnsafe(index * 2) = value.dbl1; arr.arrayUnsafe(index * 2 + 1) = value.dbl2}
}

/** Trait for creating the line path type class instances for [[Dbl3Arr]] final classes. Instances for the [[DataBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called B, because it
 *  corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl3sLinePathBuilder[B <: Dbl3Elem, ArrB <: Dbl3sLinePath[B]] extends DblNsLinePathBuilder[B, ArrB]
{ type BuffT <: Dbl3sBuffer[B]
  final override def elemProdSize = 3
  override def arrSet(arr: ArrB, index: Int, value: B): Unit = { arr.arrayUnsafe(index * 3) = value.dbl1; arr.arrayUnsafe(index * 3 + 1) = value.dbl3
    arr.arrayUnsafe(index * 2 + 2) = value.dbl3 }
}