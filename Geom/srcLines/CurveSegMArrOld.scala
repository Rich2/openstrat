/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This class needs replacing. */
class CurveSegMArrOld(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl7[CurveTailMOld]
{ type ThisT = CurveSegMArrOld
  override def fromArray(array: Array[Double]): CurveSegMArrOld = new CurveSegMArrOld(array)
  override def typeStr: String = "CurvedSegDists"
  //override def sdElem(iMatch: Double, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): DistCurveTail =
  //  new DistCurveTail(iMatch, d1, d2, d3, d4, d5, d6)
  override def fElemStr: CurveTailMOld => String = _.toString

  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): CurveTailMOld = ???
}

object CurveSegMArrOld extends CompanionSeqLikeDbl7[CurveTailMOld, CurveSegMArrOld]
{ /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  override def fromArray(array: Array[Double]): CurveSegMArrOld = new CurveSegMArrOld(array)
}