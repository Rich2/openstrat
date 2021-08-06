/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An efficient array[Int] based collection for [[HSide]]s hex grid centre coordinates. */
class HSides(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt2s[HSide]
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

  implicit object PersistImplicit extends Int2sArrPersist[HSide, HSides]("HSides")
  { override def fromArray(value: Array[Int]): HSides = new HSides(value)

    override def showT(obj: HSides, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ???
  }

  //implicit val arrArrayImplicit: ArrTFlatBuilder[HSides] = HSide.roordsBuildImplicit
}