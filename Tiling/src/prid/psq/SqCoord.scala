/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer

/** A square grid integer tile coordinate. */
trait SqCoord extends Any with TCoord
{ override type ThisT = SqCoord
  override type LineSegT = LineSegSC

  def view(pxScale: Double = 50): SqGridView = SqGridView(r, c, pxScale)
  override def toVecReg: Vec2 = Vec2(c, r)
  override def toPt2Reg: Pt2 = Pt2(c, r)

  /** [[LineSegSC]] from this point to the parameter point. */
  override def lineSegTo(endPt: SqCoord): LineSegSC = LineSegSC(this, endPt)

  /** [[LinSegLike]] from the parameter point to this point. */
  override def lineSegFrom(startPt: SqCoord): LineSegSC = LineSegSC(startPt, this)
}

object SqCoord
{ /** Apply factory method for [[SqCoord]] trait, returns a [[SqCen]], [[SqSide]] or [[SqVert]] depending on the coordinates. */
  def apply(r: Int, c: Int): SqCoord = None match {
    case _ if r.isEven & c.isEven => SqCen(r, c)
    case _ if r.isOdd & c.isOdd => SqVert(r, c)
    case _ => SqSide(r, c)
  }

  implicit val persistImplicit: Persist[SqCoord] = PersistShowInt2[SqCoord]("SqCoord", "r", "c", SqCoord(_, _))
}

trait SqCoordSeqLike extends Any with Int2SeqLike[SqCoord]
{ final override def newElem(int1: Int, int2: Int): SqCoord = SqCoord(int1, int2)
  final override def fElemStr: SqCoord => String = _.toString
}

trait SqCoordSeqSpec extends Any with SqCoordSeqLike with Int2SeqSpec[SqCoord]

/** Specialised sequence class for [[SqCoord]]. */
class SqCoordArr(val unsafeArray: Array[Int]) extends AnyVal with Int2Arr[SqCoord] with SqCoordSeqLike
{ type ThisT = SqCoordArr
  override def typeStr: String = "SqCoords"
  override def fromArray(array: Array[Int]): SqCoordArr = new SqCoordArr(array)
}

/** Specialised sequence buffer class for [[SqCoord]]. */
class SqCoordBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int2Buff[SqCoord]
{ type ArrT = SqCoordArr
  override def typeStr: String = "SqCoordBuff"
  override def newElem(i1: Int, i2: Int): SqCoord = SqCoord(i1, i2)
}

object SqCoordBuff extends Int2BuffCompanion[SqCoord, SqCoordBuff]
{ override def fromBuffer(buffer: ArrayBuffer[Int]): SqCoordBuff = new SqCoordBuff(buffer)
}