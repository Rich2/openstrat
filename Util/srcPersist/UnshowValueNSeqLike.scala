/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

trait UnshowValueNSeqLike[A <: ValueNElem, M <: ValueNSeqLike[A]] extends UnshowCompound[M]
{ /** Atomic Value type normally [[Double]] or [[Int]]. */
type VT
  def appendtoBuffer(buffer: ArrayBuffer[VT], value: A): Unit
  def fromArray(array: Array[VT]): M
  def fromBuffer(buf: ArrayBuffer[VT]): M
  def newBuffer: ArrayBuffer[VT]
}

/**  Class to [[Unshow]] specialised flat Array[Double] based collections. */
trait UnshowDblNSeqLike[A <: DblNElem, M <: DblNSeqLike[A]] extends UnshowValueNSeqLike[A, M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = BuffDbl()
  final override def appendtoBuffer(buffer: ArrayBuffer[Double], value: A): Unit = value.dblBufferAppend(buffer)
}

class UnshowDblNArr[A <: DblNElem, M <: DblNArr[A]](f: Array[Double] => M) extends UnshowDblNSeqLike[A, M] with PersistBaseSeq[A, M]
{ override def fromArray(array: Array[Double]): M = f(array)
}

object UnshowDblNArr
{
  def apply[A <: DblNElem, M <: DblNArr[A]](f: Array[Double] => M): UnshowDblNArr[A, M] = new UnshowDblNArr[A, M](f)
}

/** Class to Persist specialised for [[ValueNElem]]s cLasses. */
trait PersistValueNSeqLike[A <: ValueNElem, M <: ValueNSeqLike[A]] extends PersistCompound[M] with UnshowValueNSeqLike[A, M]

/** Persists [[DblNArr]]s. */
trait DataDblNsPersist[A <: DblNElem, M <: DblNSeqLike[A]] extends PersistValueNSeqLike[A, M] with EqT[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  override def eqT(m1: M, m2: M): Boolean = m1.unsafeArray === m2.unsafeArray
}