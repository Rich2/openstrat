/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A Common base trait for [[Arrbuilder]], [[ArrFlatBuilder]] and other builders like Polygon and  LinePath builders. */
trait DataBuilderCommon[BB]
{
  /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compile time wrapped Arraybuffer inheriting from
      BuffProdHomo. */
  type BuffT <: SeqGen[_]

  def newBuff(length: Int = 4): BuffT

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: BB): Unit

  /** converts a the buffer type to the target compound class. */
  def buffToBB(buff: BuffT): BB
}

trait DataImutBuilder[B, BB <: DataImut[B]] extends DataBuilderCommon[BB]
{ /** The type of the buffer used for building the target compound class BB. */
  type BuffT <: SeqGen[B]

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: BB): Unit
}