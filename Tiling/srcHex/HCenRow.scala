/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import scala.collection.mutable.ArrayBuffer

/** A hex tile row. Has a row number, a row starting coordinate number and the number of tiles. */
final class HCenRow(val r: Int, val cStart: Int, val cEnd: Int) extends TellInt3 with Int3Elem with SpecialT
{ override def typeStr: String = "HCenRow"
  inline override def int1: Int = r
  inline override def int2: Int = cStart
  inline override def int3: Int = cEnd

  override def name1: String = "r"
  override def tell1: Int = r
  override def name2: String = "cStart"
  override def tell2: Int = cStart
  override def name3: String = "cEnd"
  override def tell3: Int = cEnd
  def cLen: Int = (cEnd - cStart).max0
  def tNum: Int = ((cEnd - cStart + 1) / 4).max0

  def verts: HVertArr = new HVertArr(setHVertArray)

  /** The polygon of this tile, specified in [[HVert]] coordinates. */
  def hVertPolygon: PolygonHC = new PolygonHC(setHVertArray)

  /** Creates the backing Array[Int] of [[HVert]]s for this HCenRow. This same array can be used inside an [[HVertArr]] or a [[PolygonHC]] class. */
  def setHVertArray: Array[Int] =
  { val res = new Array[Int]((tNum * 4 + 2) * 2)
    res.setIndex2(0, r + 1, cStart + 2)
    iToForeach(2, tNum ){ i =>
      res.setIndex2(i * 2 - 3, r + 1, cStart + i * 4 - 4)
      res.setIndex2(i * 2 - 2, r + 1, cStart + i * 4 - 2)
    }
    iToForeach(tNum, 2, - 1) { i =>
      res.setIndex2(tNum * 4 - i * 2 - 1, r - 1, cStart + i * 4 - 2)
      res.setIndex2(tNum * 4 - i * 2,     r - 1, cStart + i * 4 - 4)
    }
    res.setIndex2(tNum * 4 - 3, r - 1, cStart + 2)
    res.setIndex2(tNum * 4 - 2, r - 1, cStart)
    res.setIndex2(tNum * 4 - 1, r - 1, cStart - 2)
    res.setIndex2(tNum * 4,     r + 1, cStart - 2)
    res.setIndex2(tNum * 4 + 1, r + 1, cStart)
    res
  }
}

object HCenRow
{
  def apply(r: Int, cStart: Int, cEnd: Int): HCenRow = new HCenRow(r, cStart, cEnd)

  /** [[Show]] type class instance / evidence for [[HCenRow]]. */
  implicit val showEv: ShowTellInt3[HCenRow] = ShowTellInt3[HCenRow]("HCenRow")

  /** [[Show]] type class instance / evidence for [[HCenRow]]. */
  implicit val unshowEv: UnshowInt3[HCenRow] = UnshowInt3[HCenRow]("HCenRow", "r", "cStart", "cEnd", apply)

  implicit val buildMapEv: BuilderArrInt3Map[HCenRow, HCenRowArr] {type BuffT = HCenRowBuff} = new BuilderArrInt3Map[HCenRow, HCenRowArr]
  { override type BuffT = HCenRowBuff
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HCenRowBuff = new HCenRowBuff(buffer)
    override def fromIntArray(array: Array[Int]): HCenRowArr = new HCenRowArr(array)
  }
}

class HCenRowArr(val unsafeArray: Array[Int]) extends AnyVal with ArrInt3[HCenRow]
{ override type ThisT = HCenRowArr
  override def typeStr: String = "HCenRowArr"
  override def newElem(i1: Int, i2: Int, i3: Int): HCenRow = new HCenRow(i1, i2, i3)
  override def fromArray(array: Array[Int]): HCenRowArr = new HCenRowArr(array)
  override def fElemStr: HCenRow => String = _.str
}

class HCenRowBuff(val unsafeBuffer: ArrayBuffer[Int]) extends BuffInt3[HCenRow]
{ override type ThisT = HCenRowBuff
  override type ArrT = HCenRowArr
  override def typeStr: String = "HCenRowBuff"
  override def newElem(i1: Int, i2: Int, i3: Int): HCenRow = new HCenRow(i1, i2, i3)
}

class HCenRowPair[A]private (val r: Int, val cStart: Int, val cEnd: Int, val a2: A) extends PairElem[HCenRow, A]
{
  override def a1: HCenRow = HCenRow(r, cStart, cEnd)

  def polygonHCTuple: PolygonHCPair[A] = new PolygonHCPair[A](a1.setHVertArray, a2)
}

object HCenRowPair
{
 def apply[A] (r: Int, cStart: Int, cEnd: Int, a2: A): HCenRowPair[A] = new HCenRowPair[A](r, cStart, cEnd, a2)
}