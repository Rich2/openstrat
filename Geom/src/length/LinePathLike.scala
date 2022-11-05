/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A generalisation of a line path where the type of the points is not resriscted to [[Pt2]]. */
trait LinePathLike[A] extends Any with SeqSpec[A]
{
  def map[B <: ElemValueN, BB <: LinePathLike[B]](f: A => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.newLinePath(ssLength)
    ssIForeach((i, p) => res.unsafeSetElem(i, f(p)))
    res
  }
}

//trait LinePathValueNsData[A <: ElemValueN] extends Any with LinePathLike[A] with ValueNSeqDef[A]
trait LinePathDblN[A <: ElemDblN] extends  Any with LinePathLike[A] with DblNSeqSpec[A]
trait LinePathDbl2[A <: ElemDbl2] extends Any with LinePathDblN[A] with Dbl2SeqSpec[A]
trait LinePathDbl3[A <: ElemDbl3] extends Any with LinePathDblN[A] with Dbl3SeqSpec[A]

trait LinePathIntN[A <: ElemIntN] extends  Any with LinePathLike[A] with IntNSeqSpec[A]
trait LinePathInt2[A <: ElemInt2] extends Any with LinePathIntN[A] with Int2SeqSpec[A]

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait LinePathBuilder[B, BB <: LinePathLike[B]] extends SeqLikeMapBuilder[B, BB]
{ def newLinePath(length: Int): BB
  def indexSet(arr: BB, index: Int, value: B): Unit

  def buffContains(buff: BuffT, newElem: B): Boolean =
  { var res = false
    var count = 0
    while (!res & count < buff.length) if (buff(count) == newElem) res = true else count += 1
    res
  }

  def iterMap[A](inp: Iterable[A], f: A => B): BB =
  { val buff = newBuff()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToBB(buff)
  }
}

/** Trait for creating the line path builder instances for the [[LinePathBuilder]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait LinePathValueNBuilder[B <: ElemValueN, BB <: LinePathLike[B]] extends LinePathBuilder[B, BB] with ValueNSeqLikeCommonBuilder[BB]

/** Trait for creating the builder type class instances for [[LinePathDblN]] final classes. Instances for the [[LinePathBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait LinePathDblNsBuilder[B <: ElemDblN, BB <: LinePathDblN[B] ] extends LinePathValueNBuilder[B, BB] with DblNSeqLikeCommonBuilder[BB]
{ type BuffT <: DblNBuff[B]
  def fromDblArray(array: Array[Double]): BB
  def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffT
  final override def newLinePath(length: Int): BB = fromDblArray(new Array[Double](length * elemProdSize))
}

/** Trait for creating the line path type class instances for [[LinePathDbl2]] final classes. Instances for the [[LinePathDbl2Builder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl2]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathDbl2Builder[B <: ElemDbl2, BB <: LinePathDbl2[B]] extends LinePathDblNsBuilder[B, BB] with Dbl2SeqLikeMapBuilder[B, BB]
{ type BuffT <: Dbl2Buff[B]
  override def indexSet(arr: BB, index: Int, value: B): Unit = { arr.unsafeArray(index * 2) = value.dbl1; arr.unsafeArray(index * 2 + 1) = value.dbl2}
}

/** Trait for creating the line path type class instances for [[LinePathDbl3]] final classes. Instances for the [[LinePathDbl3sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl3]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathDbl3sBuilder[B <: ElemDbl3, BB <: LinePathDbl3[B]] extends LinePathDblNsBuilder[B, BB] with Dbl3SeqLikeMapBuilder[B, BB]
{ type BuffT <: Dbl3Buff[B]
  override def indexSet(arr: BB, index: Int, value: B): Unit = { arr.unsafeArray(index * 3) = value.dbl1; arr.unsafeArray(index * 3 + 1) = value.dbl3
    arr.unsafeArray(index * 2 + 2) = value.dbl3 }
}

/** Trait for creating the builder type class instances for [[LinePathIntN]] final classes. Instances for the [[LinePathBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait LinePathIntNsBuilder[B <: ElemIntN, BB <: LinePathIntN[B] ] extends LinePathValueNBuilder[B, BB] with IntNSeqLikeMapBuilder[B, BB]
{ type BuffT <: IntNBuff[B]
  def fromIntBuffer(inp: ArrayBuffer[Int]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
  final override def newLinePath(length: Int): BB = fromIntArray(new Array[Int](length * elemProdSize))
  final override def buffToBB(buff: BuffT): BB = fromIntArray(buff.unsafeBuffer.toArray)
  //final override def buffGrowArr(buff: BuffT, arr: BB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
  final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
}

/** Trait for creating the line path type class instances for [[LinePathInt2]] final classes. Instances for the [[LinePathInt2sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemInt2]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathInt2sBuilder[B <: ElemInt2, BB <: LinePathInt2[B]] extends LinePathIntNsBuilder[B, BB] with Int2SeqLikeMapBuilder[B, BB]
{ type BuffT <: Int2Buff[B]
}