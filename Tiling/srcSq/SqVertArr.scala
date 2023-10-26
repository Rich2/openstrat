/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import collection.mutable.ArrayBuffer

/** Common trait for [[Hverts]] and [[PolygonHC]] */
trait SqVertSeqLike extends Any with SeqLikeInt2[SqVert]
{ override def newElem(int1: Int, int2: Int): SqVert = SqVert.apply(int1, int2)
  override def fElemStr: SqVert => String = _.str
  def vertNum: Int = unsafeArray.length / 2
}

/** An array[Int] based collection for SqVert. */
class SqVertArr(val unsafeArray: Array[Int]) extends AnyVal with SqVertSeqLike with Int2Arr[SqVert]
{ type ThisT = SqVertArr
  override def fromArray(array: Array[Int]): SqVertArr = new SqVertArr(array)
  override def typeStr: String = "SqVerts" + foldLeft("")(_ + "; " + _.rcStr)
  def toPolygon: PolygonSqC = new PolygonSqC(unsafeArray)
}

object SqVertArr extends CompanionSeqLikeInt2[SqVert, SqVertArr]
{ def fromArray(array: Array[Int]): SqVertArr = new SqVertArr(array)

  /** Implicit [[Show]] type class instance / evidence for [[SqVertArr]]. */
  implicit val showEv: ShowSequ[SqVert, SqVertArr] = ShowSequ[SqVert, SqVertArr]()

  /** Implicit [[Unshow]] type class instance / evidence for [[SqVertArr]]. */
  implicit val unshowEv: UnshowArrIntN[SqVert, SqVertArr] = UnshowArrIntN[SqVert, SqVertArr](fromArray)

  implicit val arrArrayImplicit: BuilderArrFlat[SqVertArr] = new Int2ArrFlatBuilder[SqVertArr]
  { type BuffT = SqVertBuff
    override def fromIntArray(array: Array[Int]): SqVertArr = new SqVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqVertBuff = new SqVertBuff(buffer)
  }
}

/** [[Buff]] class for storing [[SqVert]]s in an [[ArrayBuffer]][Int]. */
class SqVertBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with BuffInt2[SqVert]
{ type ArrT = SqVertArr
  override def typeStr: String = "SqVertBuff"
  override def newElem(i1: Int, i2: Int): SqVert = SqVert(i1, i2)
}