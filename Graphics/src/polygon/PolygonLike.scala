/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait PolygonLike[VertT] extends Any
{
  /** The number of vertices and also the number of sides in this Polygon. */
  def vertsNum: Int

  def vertsForeach[U](f: VertT => U): Unit

  /** This method should be overridden in final classes. */
  def vertsMap[B, ArrB <: SeqImut[B]](f: VertT => B)(implicit builder: SeqBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  //def mapq

  //def foreachLineVert[U](f: VertT => U): Unit
}

trait PolygonValueN[VT <: ValueNElem] extends Any with PolygonLike[VT] with ValueNsData[VT]
{
  override def vertsForeach[U](f: VT => U): Unit = dataForeach(f)

  override def vertsNum: Int = elemsNum
}
trait PolygonDblNs[VT <: DblNElem] extends Any with PolygonValueN[VT] with DblNsData[VT]
trait PolygonDbl2s[VT <: Dbl2Elem] extends Any with PolygonDblNs[VT] with Dbl2sData[VT]
trait PolygonDbl3s[VT <: Dbl3Elem] extends Any with PolygonDblNs[VT] with Dbl3sData[VT]

/** A common trait inherited by [[PolygonBuilder]] and [[PolygonFlatBuilder]]. */
trait PolygonBuilderCommon[ArrB <: PolygonLike[_]]
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
trait PolygonBuilder[B <: ValueNElem, ArrB <: PolygonLike[B]] extends PolygonBuilderCommon[ArrB]
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