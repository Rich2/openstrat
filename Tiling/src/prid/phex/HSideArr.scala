/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer

/** An efficient array[Int] based collection for [[HSide]]s hex grid centre coordinates. */
class HSideArr(val unsafeArray: Array[Int]) extends AnyVal with Int2Arr[HSide]
{ type ThisT = HSideArr
  override def sdElem(int1: Int, int2: Int): HSide = HSide(int1, int2)

  override def fromArray(array: Array[Int]): HSideArr = new HSideArr(array)

  override def typeStr: String = "HSides"

  override def fElemStr: HSide => String = _ => "Not implemented"
}

/** Companion object for [[HSideArr]] trait efficient array[Int] based collection for [[HSide]]s hex grid centre coordinates, contains factory apply and
 *  uninitialised methods. */
object HSideArr extends Int2SeqLikeCompanion[HSide, HSideArr]
{
  override def fromArray(array: Array[Int]): HSideArr = new HSideArr(array)

  implicit val persistImplicit: PersistArrInt2s[HSide, HSideArr] = new PersistArrInt2s[HSide, HSideArr]("HSides")
  { override def fromArray(value: Array[Int]): HSideArr = new HSideArr(value)
    override def showDecT(obj: HSideArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit flatMap builder instance / evidence for [[HSideArr]]. */
  implicit val flatBuilderEv: ArrFlatBuilder[HSideArr] = new Int2ArrFlatBuilder[HSide, HSideArr]
  { type BuffT = HSideBuff
    override def fromIntArray(array: Array[Int]): HSideArr = new HSideArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HSideBuff = new HSideBuff(buffer)
  }
}

class HSideBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int2Buff[HSide]
{ type ArrT = HSideArr
  override def typeStr: String = "HSideBuff"
  override def intsToT(i1: Int, i2: Int): HSide = HSide(i1, i2)
}