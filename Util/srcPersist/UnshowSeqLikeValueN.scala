/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

trait UnshowSeqLikeValueN[A <: ValueNElem, M <: SeqLikeValueN[A]] extends UnshowCompound[M]
{ /** Atomic Value type normally [[Double]] or [[Int]]. */
type VT
  def appendtoBuffer(buffer: ArrayBuffer[VT], value: A): Unit
  def fromArray(array: Array[VT]): M
  def fromBuffer(buf: ArrayBuffer[VT]): M
  def newBuffer: ArrayBuffer[VT]
}

/**  Class to [[Unshow]] specialised flat Array[Int] based collections. */
trait UnshowSeqLikeIntN[A <: IntNElem, M <: IntNSeqLike[A]] extends UnshowSeqLikeValueN[A, M]
{ type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = BuffInt()
  final override def appendtoBuffer(buffer: ArrayBuffer[Int], value: A): Unit = value.intBufferAppend(buffer)
}

class UnshowArrIntN[A <: IntNElem, M <: IntNArr[A]](f: Array[Int] => M) extends UnshowSeqLikeIntN[A, M] with PersistBaseSeq[A, M]
{ override def fromArray(array: Array[Int]): M = f(array)
}

object UnshowArrIntN
{ def apply[A <: IntNElem, M <: IntNArr[A]](f: Array[Int] => M): UnshowArrIntN[A, M] = new UnshowArrIntN[A, M](f)
}

class UnshowSeqSpecIntN[A <: IntNElem, M <: IntNSeqSpec[A]](val typeStr: String, f: Array[Int] => M) extends UnshowSeqLikeIntN[A, M]
{ override def fromArray(array: Array[Int]): M = f(array)
}

object UnshowSeqSpecIntN
{ def apply[A <: IntNElem, M <: IntNSeqSpec[A]](typeStr: String, f: Array[Int] => M): UnshowSeqSpecIntN[A, M] = new UnshowSeqSpecIntN[A, M](typeStr, f)
}



/**  Class to [[Unshow]] specialised flat Array[Double] based collections. */
trait UnshowSeqLikeDblN[A <: DblNElem, M <: DblNSeqLike[A]] extends UnshowSeqLikeValueN[A, M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = BuffDbl()
  final override def appendtoBuffer(buffer: ArrayBuffer[Double], value: A): Unit = value.dblBufferAppend(buffer)
}

class UnshowArrDblN[A <: DblNElem, M <: DblNArr[A]](f: Array[Double] => M) extends UnshowSeqLikeDblN[A, M] with PersistBaseSeq[A, M]
{ override def fromArray(array: Array[Double]): M = f(array)
}

object UnshowArrDblN
{ def apply[A <: DblNElem, M <: DblNArr[A]](f: Array[Double] => M): UnshowArrDblN[A, M] = new UnshowArrDblN[A, M](f)
}

class UnshowSeqSpecDblN[A <: DblNElem, M <: DblNSeqSpec[A]](val typeStr: String, f: Array[Double] => M) extends UnshowSeqLikeDblN[A, M]
{ override def fromArray(array: Array[Double]): M = f(array)
}

object UnshowSeqSpecDblN
{ def apply[A <: DblNElem, M <: DblNSeqSpec[A]](typeStr: String, f: Array[Double] => M): UnshowSeqSpecDblN[A, M] = new UnshowSeqSpecDblN[A, M](typeStr, f)
}

/** Class to Persist specialised for [[ValueNElem]]s cLasses. */
trait PersistValueNSeqLike[A <: ValueNElem, M <: SeqLikeValueN[A]] extends PersistCompound[M] with UnshowSeqLikeValueN[A, M]

/** Persists [[DblNArr]]s. */
trait DataDblNsPersist[A <: DblNElem, M <: DblNSeqLike[A]] extends PersistValueNSeqLike[A, M] with EqT[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  override def eqT(m1: M, m2: M): Boolean = m1.unsafeArray === m2.unsafeArray
}