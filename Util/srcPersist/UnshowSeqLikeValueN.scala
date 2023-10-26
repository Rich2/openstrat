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
trait UnshowSeqLikeIntN[A <: IntNElem, M <: SeqLikeIntN[A]] extends UnshowSeqLikeValueN[A, M]
{ type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = BuffInt()
  final override def appendtoBuffer(buffer: ArrayBuffer[Int], value: A): Unit = value.intBufferAppend(buffer)
}

class UnshowArrIntN[A <: IntNElem, M <: ArrIntN[A]](f: Array[Int] => M) extends UnshowSeqLikeIntN[A, M] with PersistBaseSeq[A, M]
{ override def fromArray(array: Array[Int]): M = f(array)
}

object UnshowArrIntN
{ def apply[A <: IntNElem, M <: ArrIntN[A]](f: Array[Int] => M): UnshowArrIntN[A, M] = new UnshowArrIntN[A, M](f)
}

class UnshowSeqSpecIntN[A <: IntNElem, M <: SeqSpecIntN[A]](val typeStr: String, f: Array[Int] => M) extends UnshowSeqLikeIntN[A, M]
{ override def fromArray(array: Array[Int]): M = f(array)
}

object UnshowSeqSpecIntN
{ def apply[A <: IntNElem, M <: SeqSpecIntN[A]](typeStr: String, f: Array[Int] => M): UnshowSeqSpecIntN[A, M] = new UnshowSeqSpecIntN[A, M](typeStr, f)
}



/**  Class to [[Unshow]] specialised flat Array[Double] based collections. */
trait UnshowSeqLikeDblN[A <: DblNElem, M <: SeqLikeDblN[A]] extends UnshowSeqLikeValueN[A, M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = BuffDbl()
  final override def appendtoBuffer(buffer: ArrayBuffer[Double], value: A): Unit = value.dblBufferAppend(buffer)
}

class UnshowArrDblN[A <: DblNElem, M <: ArrDblN[A]](f: Array[Double] => M) extends UnshowSeqLikeDblN[A, M] with PersistBaseSeq[A, M]
{ override def fromArray(array: Array[Double]): M = f(array)
}

object UnshowArrDblN
{ def apply[A <: DblNElem, M <: ArrDblN[A]](f: Array[Double] => M): UnshowArrDblN[A, M] = new UnshowArrDblN[A, M](f)
}

class UnshowSeqSpecDblN[A <: DblNElem, M <: SeqSpecDblN[A]](val typeStr: String, f: Array[Double] => M) extends UnshowSeqLikeDblN[A, M]
{ override def fromArray(array: Array[Double]): M = f(array)
}

object UnshowSeqSpecDblN
{ def apply[A <: DblNElem, M <: SeqSpecDblN[A]](typeStr: String, f: Array[Double] => M): UnshowSeqSpecDblN[A, M] = new UnshowSeqSpecDblN[A, M](typeStr, f)
}