/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This class needs replacing. */
class CurveSegDists(val unsafeArray: Array[Double]) extends AnyVal with Dbl7Arr[DistCurveTail]
{ type ThisT = CurveSegDists
  override def unsafeFromArray(array: Array[Double]): CurveSegDists = new CurveSegDists(array)
  override def typeStr: String = "CurvedSegDists"
  //override def sdElem(iMatch: Double, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): DistCurveTail =
  //  new DistCurveTail(iMatch, d1, d2, d3, d4, d5, d6)
  override def fElemStr: DistCurveTail => String = _.toString

  override def ssElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): DistCurveTail = ???
}

object CurveSegDists extends Dbl7SeqDefCompanion[DistCurveTail, CurveSegDists]
{ /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  override def fromArray(array: Array[Double]): CurveSegDists = new CurveSegDists(array)
}