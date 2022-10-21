/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A Common base trait for [[Arrbuilder]], [[ArrFlatBuilder]] and other builders like Polygon and  LinePath builders. */
trait SeqLikeBuilderCommon[BB]
{
  /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compile time wrapped Arraybuffer inheriting from
      BuffProdHomo. */
  type BuffT <: Sequ[_]

  def newBuff(length: Int = 4): BuffT

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: BB): Unit

  /** converts a the buffer type to the target compound class. */
  def buffToBB(buff: BuffT): BB
}

/** Builder trait for map operations. This has the additional method of buffGrow(buff: BuffT, value: B): Unit. This method is not required for flatMap
 * operations where the type of the element of the [[SeqLike]] that the builder is constructed may not be known at the point of dispatch. */
trait SeqLikeMapBuilder[B, BB <: SeqLike[B]] extends SeqLikeBuilderCommon[BB]
{ type BuffT <: Buff[B]

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit
}