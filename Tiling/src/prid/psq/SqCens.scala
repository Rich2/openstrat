/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import collection.mutable.ArrayBuffer

/** An efficient array[Int] based collection for [[SqCen]]s hex grid centre coordinates. */
class SqCens(val unsafeArray: Array[Int]) extends AnyVal with Int2Arr[SqCen]
{ type ThisT = SqCens

  override def sdElem(int1: Int, int2: Int): SqCen = SqCen(int1, int2)

  override def fromArray(array: Array[Int]): SqCens = new SqCens(array)

  override def typeStr: String = "SqCens"

  override def fElemStr: SqCen => String = _ => "Not implemented"
}

/** Companion object for [[SqCens]] trait efficient array[Int] based collection for [[SqCen]]s hex grid centre coordinates, contains factory apply and uninitialised methods.. */
object SqCens extends Int2SeqDefCompanion[SqCen, SqCens]
{
  //override def buff(initialSize: Int): SqCenBuff = new SqCenBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): SqCens = new SqCens(array)

  implicit object PersistImplicit extends PersistArrInt2s[SqCen, SqCens]("SqCens")
  { override def fromArray(value: Array[Int]): SqCens = new SqCens(value)

    override def showDecT(obj: SqCens, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  //implicit val arrArrayImplicit: ArrTFlatBuilder[SqCens] = SqCen.hCensBuildImplicit
}

class SqCenBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int2Buff[SqCen]
{ type ArrT = SqCens
  override def typeStr: String = "SqCenBuff"
  override def intsToT(i1: Int, i2: Int): SqCen = SqCen(i1, i2)
}