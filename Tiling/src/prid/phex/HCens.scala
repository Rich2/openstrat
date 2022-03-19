/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** An efficient array[Int] based collection for [[HCen]]s hex grid centre coordinates. */
class HCens(val unsafeArray: Array[Int]) extends AnyVal with ArrInt2s[HCen]
{ type ThisT = HCens

  override def dataElem(i1: Int, i2: Int): HCen = HCen(i1, i2)

  override def unsafeFromArray(array: Array[Int]): HCens = new HCens(array)

  override def typeStr: String = "HCens"

  override def fElemStr: HCen => String = _.toString
}

/** Companion object for [[HCens]] trait efficient array[Int] based collection for [[HCen]]s hex grid centre coordinates, contains factory apply and uninitialised methods.. */
object HCens extends DataInt2sCompanion[HCen, HCens]
{
  //override def buff(initialSize: Int): HCenBuff = new HCenBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): HCens = new HCens(array)

  implicit object PersistImplicit extends PersistArrInt2s[HCen, HCens]("HCens")
  { override def fromArray(value: Array[Int]): HCens = new HCens(value)

    override def showDecT(obj: HCens, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit flatMap builder instance / evidence for [[HCens]]. */
  implicit val flatBuilderEv: ArrFlatBuilder[HCens] = new ArrInt2sFlatBuilder[HCen, HCens]
  { type BuffT = HCenBuff
    override def fromIntArray(array: Array[Int]): HCens = new HCens(array)
    override def fromIntBuffer(inp: Buff[Int]): HCenBuff = new HCenBuff(inp)
  }
}

class HCenBuff(val unsafeBuffer: Buff[Int] = buffInt()) extends AnyVal with BuffInt2s[HCen]
{ type ArrT = HCens
  override def typeStr: String = "HCenBuff"
  override def intsToT(i1: Int, i2: Int): HCen = HCen(i1, i2)
}