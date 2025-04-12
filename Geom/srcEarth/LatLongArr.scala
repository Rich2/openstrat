/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import collection.mutable.ArrayBuffer

trait LatLongSeqLike extends Any with SlImutDbl2[LatLong]
{  final override def fElemStr: LatLong => String = _.toString
}

/** A base trait for a sequence of [[LatLong]]s. The final classes are more strongly typed as a [[LinePathLL], a [[PolygonLL]]and [[LatLongArr]], for a
 * a general collection of [[LatLong]] points. */
trait LatLongSeqSpec extends Any with LatLongSeqLike with SsDbl2[LatLong]
{ final override def elemFromDbls(d1: Double, d2: Double): LatLong = LatLong.milliSecs(d1, d2)
}

/** Immutable flat efficient Array[Double] based collection class for [[LatLong]]s. Prefer [[PolygonLL]] or [[LineSegLL]] where applicable. */
final class LatLongArr(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongSeqLike with ArrDbl2[LatLong]
{ override type ThisT = LatLongArr
  override def fromArray(array: Array[Double]): LatLongArr = new LatLongArr(array)
  override def typeStr: String = "LatLongs"

  /** Method for creating new data elements from 2 [[Double]]s In the case of [[ArrDbl2]] this will be thee type of the elements of the sequence. */
  override def elemFromDbls(d1: Double, d2: Double): LatLong = LatLong.milliSecs(d1, d2)
}

object LatLongArr{
  implicit val arrFlatBuilderImplicit: BuilderFlatArrDbl2[LatLongArr] = new BuilderFlatArrDbl2[LatLongArr]
  { type BuffT = LatLongBuff
    override def fromDblArray(array: Array[Double]): LatLongArr = new LatLongArr(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): LatLongBuff = new LatLongBuff(inp)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[LatLong]]s collections. */
final class LatLongBuff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[LatLong]
{ override def typeStr: String = "LatLongBuff"
  def elemFromDbls(d1: Double, d2: Double): LatLong = LatLong.milliSecs(d1, d2)
}

object LatLongBuff
{ def apply(initialLength: Int = 4): LatLongBuff = new LatLongBuff(new ArrayBuffer[Double](initialLength * 2))
}