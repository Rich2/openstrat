/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A first level area of the Earth, a large area such as North West Europe. */
abstract class EArea1(val name: String, val cen: LatLong) extends GeographicSymbolKey
{ def neighbs: RArr[EArea1] = RArr()
  def a2Arr: RArr[EArea2]
  def disp2(eg: EarthGuiOld): GraphicElems = a2Arr.flatMap(_.display(eg))
  def places: LocationLLArr = a2Arr.flatMap(_.places)(LocationLLArr.flatArrBuilderImplicit)
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

  implicit def flatArrBuilderImplicit(implicit ct: ClassTag[Place]): DblNPairArrFlatBuilder[LatLong, LatLongArr, Place, LocationLLArr] =
  new DblNPairArrFlatBuilder[LatLong, LatLongArr, Place, LocationLLArr]
  { override type BuffT = LatLongPairBuff[Place]
    override type B1BuffT = LatLongBuff
    override implicit def b2ClassTag: ClassTag[Place] = ct
    override def newB1Buff(): LatLongBuff = LatLongBuff()

    override def buffFromBuffers(b1Buffer: ArrayBuffer[Double], b2Buffer: ArrayBuffer[Place]): LatLongPairBuff[Place] =
      new LatLongPairBuff[Place](b1Buffer, b2Buffer)

    override def arrFromArrays(b1ArrayDbl: Array[Double], b2Array: Array[Place]): LocationLLArr = new LocationLLArr(b1ArrayDbl, b2Array)
  }
}