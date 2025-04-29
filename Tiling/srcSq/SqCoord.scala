/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer

/** A square grid integer tile coordinate. */
trait SqCoord extends Any with TCoord
{ override type ThisT = SqCoord
  override type LineSegT = LSegSC

  def view(pxScale: Double = 50): SGView = SGView(r, c, pxScale)
  override def toVecReg: Vec2 = Vec2(c, r)
  override def toPt2Reg: Pt2 = Pt2(c, r)

  /** [[LSegSC]] from this point to the parameter point. */
  def lineSegTo(endPt: SqCoord): LSegSC = LSegSC(this, endPt)

  /** [[LinSegLike]] from the parameter point to this point. */
  def lineSegFrom(startPt: SqCoord): LSegSC = LSegSC(startPt, this)
}

object SqCoord
{ /** Apply factory method for [[SqCoord]] trait, returns a [[SqCen]], [[SqSep]] or [[SqVert]] depending on the coordinates. */
  def apply(r: Int, c: Int): SqCoord = None match
  { case _ if r.isEven & c.isEven => SqCen(r, c)
    case _ if r.isOdd & c.isOdd => SqVert(r, c)
    case _ => SqSep(r, c)
  }

  /** implicit [[Show]] type class instance / evidence for [[SqCoor]]s. */
  implicit val showEv: ShowTellInt2[SqCoord] = ShowTellInt2[SqCoord]("SqCoord")

  /** implicit [[Show]] type class instance / evidence for [[SqCoord]]s. */
  implicit val unshowEv: UnshowInt2[SqCoord] = UnshowInt2[SqCoord]("SqCoord", "r", "c", apply)
}

trait SqCoordSeqLike extends Any with SlImutInt2[SqCoord]
{ final override def elemFromInts(int1: Int, int2: Int): SqCoord = SqCoord(int1, int2)
  final override def fElemStr: SqCoord => String = _.toString
}

trait SqCoordSeqSpec extends Any with SqCoordSeqLike with SsInt2[SqCoord]

/** Specialised sequence class for [[SqCoord]]. */
class SqCoordArr(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt2[SqCoord] with SqCoordSeqLike
{ type ThisT = SqCoordArr
  override def typeStr: String = "SqCoords"
  override def fromArray(array: Array[Int]): SqCoordArr = new SqCoordArr(array)
}

/** Specialised sequence buffer class for [[SqCoord]]. */
class SqCoordBuff(val bufferUnsafe: ArrayBuffer[Int] = BufferInt()) extends AnyVal with BuffInt2[SqCoord]
{ type ArrT = SqCoordArr
  override def typeStr: String = "SqCoordBuff"
  override def newElem(i1: Int, i2: Int): SqCoord = SqCoord(i1, i2)
}

object SqCoordBuff extends CompanionBuffInt2[SqCoord, SqCoordBuff]
{ override def fromBuffer(buffer: ArrayBuffer[Int]): SqCoordBuff = new SqCoordBuff(buffer)
}