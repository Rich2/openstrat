/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import collection.mutable.ArrayBuffer

/** Common trait for [[Hverts]] and [[PolygonHC]] */
trait SqVertSeqLike extends Any with SlImutInt2[SqVert]
{ override def elemFromInts(int1: Int, int2: Int): SqVert = SqVert.apply(int1, int2)
  override def fElemStr: SqVert => String = _.str
  def vertNum: Int = arrayUnsafe.length / 2
}

/** An array[Int] based collection for SqVert. */
class SqVertArr(val arrayUnsafe: Array[Int]) extends AnyVal with SqVertSeqLike with ArrInt2[SqVert]
{ type ThisT = SqVertArr
  override def fromArray(array: Array[Int]): SqVertArr = new SqVertArr(array)
  override def typeStr: String = "SqVerts" + foldLeft("")(_ + "; " + _.rcStr)
  def toPolygon: PolygonSqC = new PolygonSqC(arrayUnsafe)
}

object SqVertArr extends CompanionSlInt2[SqVert, SqVertArr]
{ def fromArray(array: Array[Int]): SqVertArr = new SqVertArr(array)

  /** Implicit [[Show]] type class instance / evidence for [[SqVertArr]]. */
  implicit val showEv: ShowSequ[SqVert, SqVertArr] = ShowSequ[SqVert, SqVertArr]()

  /** Implicit [[Unshow]] type class instance / evidence for [[SqVertArr]]. */
  implicit val unshowEv: UnshowSeq[SqVert, SqVertArr] = UnshowSeq[SqVert, SqVertArr]()

  implicit val arrArrayImplicit: BuilderArrFlat[SqVertArr] = new BuilderFlatArrInt2[SqVertArr]
  { type BuffT = SqVertBuff
    override def fromIntArray(array: Array[Int]): SqVertArr = new SqVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqVertBuff = new SqVertBuff(buffer)
  }
}

/** [[Buff]] class for storing [[SqVert]]s in an [[ArrayBuffer]][Int]. */
class SqVertBuff(val bufferUnsafe: ArrayBuffer[Int] = BufferInt()) extends AnyVal with BuffInt2[SqVert]
{ type ArrT = SqVertArr
  override def typeStr: String = "SqVertBuff"
  override def newElem(i1: Int, i2: Int): SqVert = SqVert(i1, i2)
}