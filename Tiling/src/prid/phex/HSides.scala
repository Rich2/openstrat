/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** An efficient array[Int] based collection for [[HSide]]s hex grid centre coordinates. */
class HSides(val unsafeArray: Array[Int]) extends AnyVal with ArrInt2s[HSide]
{ type ThisT = HSides
  override def dataElem(i1: Int, i2: Int): HSide = HSide(i1, i2)

  override def unsafeFromArray(array: Array[Int]): HSides = new HSides(array)

  override def typeStr: String = "HSides"

  override def fElemStr: HSide => String = _ => "Not implemented"
}

/** Companion object for [[HSides]] trait efficient array[Int] based collection for [[HSide]]s hex grid centre coordinates, contains factory apply and
 *  uninitialised methods. */
object HSides extends DataInt2sCompanion[HSide, HSides]
{
  //override def buff(initialSize: Int): RoordBuff = new RoordBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): HSides = new HSides(array)

  implicit object PersistImplicit extends PersistArrInt2s[HSide, HSides]("HSides")
  { override def fromArray(value: Array[Int]): HSides = new HSides(value)

    override def showDecT(obj: HSides, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit flatMap builder instance / evidence for [[HSides]]. */
  implicit val flatBuilderEv: ArrFlatBuilder[HSides] = new ArrInt2sFlatBuilder[HSide, HSides]
  { type BuffT = HSideBuff
    override def fromIntArray(array: Array[Int]): HSides = new HSides(array)
    override def fromIntBuffer(buffer: Buff[Int]): HSideBuff = new HSideBuff(buffer)
  }
}

class HSideBuff(val unsafeBuffer: Buff[Int] = buffInt()) extends AnyVal with Int2Buff[HSide]
{ type ArrT = HSides
  override def typeStr: String = "HSideBuff"
  override def intsToT(i1: Int, i2: Int): HSide = HSide(i1, i2)
}