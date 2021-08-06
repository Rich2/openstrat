/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait PolygonLike[VertT] extends Any
{
  /** The number of vertices and also the number of sides in this Polygon. */
  def vertsNum: Int

  def vertsForeach[U](f: VertT => U): Unit

  /** This method should be overridden in final classes. */
  def vertsMap[B, ArrB <: ArrBase[B]](f: VertT => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
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

trait PolygonValueN[VT <: ElemValueN] extends Any with PolygonLike[VT] with DataValueNs[VT]
{
  override def vertsForeach[U](f: VT => U): Unit = dataForeach(f)

  override def vertsNum: Int = elemsNum
}

trait PolygonDblNs[VT <: ElemDblN] extends Any with PolygonValueN[VT] with DataDblNs[VT]
trait PolygonDbl2s[VT <: ElemDbl2] extends Any with PolygonDblNs[VT] with DataDbl2s[VT]
trait PolygonDbl3s[VT <: ElemDbl3] extends Any with PolygonDblNs[VT] with Dbl3sData[VT]

trait PolygonIntNs[VT <: ElemIntN] extends Any with PolygonValueN[VT] with DataIntNs[VT]

/** A common trait inherited by [[PolygonBuilder]] and [[PolygonFlatBuilder]]. */
trait PolygonBuilderCommon[BB <: PolygonLike[_]]
{
  /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compilte time wrapped Arraybuffer inheriting from
      BuffProdHomo. */
  type BuffT <: SeqGen[_]
  def newBuff(length: Int = 4): BuffT
  def buffToArr(buff: BuffT): BB

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: BB): Unit
}

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait PolygonBuilder[B <: ElemValueN, BB <: PolygonLike[B]] extends PolygonBuilderCommon[BB]
{ type BuffT <: SeqGen[B]
  def newArr(length: Int): BB
  def arrSet(arr: BB, index: Int, value: B): Unit

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit

  def buffContains(buff: BuffT, newElem: B): Boolean =
  { var res = false
    var count = 0
    while (!res & count < buff.elemsNum) if (buff(count) == newElem) res = true else count += 1
    res
  }

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: BB): Unit// = arr.foreach(buffGrow(buff, _))

  /** A mutable operation that extends the ArrayBuffer with the elements of the Iterable operand. */
  def buffGrowIter(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffGrow(buff, _))

  def iterMap[A](inp: Iterable[A], f: A => B): BB =
  { val buff = newBuff()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToArr(buff)
  }
}