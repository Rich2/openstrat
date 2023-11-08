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
  override def newBuffer: ArrayBuffer[Int] = BufferInt()
  final override def appendtoBuffer(buffer: ArrayBuffer[Int], value: A): Unit = value.intBufferAppend(buffer)
}

class UnshowArrIntN[A <: IntNElem, M <: ArrIntN[A]](f: Array[Int] => M) extends UnshowSeqLikeIntN[A, M] with PersistBaseSeq[A, M]
{ override def fromArray(array: Array[Int]): M = f(array)
}

object UnshowArrIntN
{ def apply[A <: IntNElem, M <: ArrIntN[A]](f: Array[Int] => M): UnshowArrIntN[A, M] = new UnshowArrIntN[A, M](f)
}