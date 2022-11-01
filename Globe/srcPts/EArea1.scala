/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._
import pglobe._

import scala.reflect.ClassTag

/** A first level area of the Earth, a large area such as North West Europe. */
abstract class EArea1(val name: String, val cen: LatLong) extends GeographicSymbolKey
{ def neighbs: RArr[EArea1] = RArr()
  def a2Arr: RArr[EArea2]
  def disp2(eg: EarthGuiOld): GraphicElems = a2Arr.flatMap(_.display(eg))
  val places: RArr[LocationLL] = RArr()
}

case class Place(name: String, level: Int)

class LocationLL(latMilliSecs: Double, longMilliSecs: Double, nameIn: String, level: Int) extends
  LatLongPair[Place](latMilliSecs, longMilliSecs, Place(nameIn, level))
{ def name: String = a2.name
}

object LocationLL
{ def apply(name: String, latDegs: Double, longDegs: Double, level: Int): LocationLL =
    new LocationLL(latDegs.degsToMilliSecs, longDegs.degsToMilliSecs, name, level)
}

class LocationLLArr(a1ArrayDbl: Array[Double], a2Array: Array[Place]) extends LatLongPairArr[Place](a1ArrayDbl, a2Array)
{ override def newFromArrays(a1Array: Array[Double], a2Array: Array[Place]): LatLongPairArr[Place] = new LocationLLArr(a1Array, a2Array)

  override def newA1(dbl1: Double, dbl2: Double): LatLong = LatLong.milliSecs(dbl1, dbl2)
}

object LocationLLArr extends Dbl2PairArrCompanion[Pt2, Pt2Arr]
{
  def apply(pairs: LocationLL*): LocationLLArr =
  { val arrays = seqToArrays(pairs)
    new LocationLLArr(arrays._1, arrays._2)
  }

  implicit val flatArrBuilderImplicit: DblNPairArrFlatBuilder[LatLong, LatLongArr, Place, LocationLLArr] =
    new DblNPairArrFlatBuilder[LatLong, LatLongArr, Place, LocationLLArr] {
      override type BuffT = LatLongPairBuff[Place]
      override type B1BuffT = LatLongBuff

      /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
      override def buffGrowArr(buff: LatLongPairBuff[Place], arr: LocationLLArr): Unit = ???

      /** ClassTag for building Arrays and ArrayBuffers of B2s. */
      override implicit def b2ClassTag: ClassTag[Place] = implicitly[ClassTag[Place]]

      override def newB1Buff(): LatLongBuff = ???

      /** Expands / appends the B1 [[Buff]] with a songle element of B1. */
      override def b1BuffGrow(buff: LatLongBuff, newElem: LatLong): Unit = ???

      override def newBuff(length: Int): LatLongPairBuff[Place] = ???

      /** converts a the buffer type to the target compound class. */
      override def buffToBB(buff: LatLongPairBuff[Place]): LocationLLArr = ???
    }
}