/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A polygon made up of 1 or more hex tiles, defined by the [[HCen]]s of its outer ring of hex tiles. */
class HTilePolygon(override val arrayUnsafe: Array[Int]) extends SeqSpecInt2[HCen]
{
  override type ThisT = HTilePolygon
  override def typeStr: String = "HTilePolygon"
  override def fromArray(array: Array[Int]): HTilePolygon = new HTilePolygon(array)
  override def fElemStr: HCen => String = _.toString
  override def elemsStr: String = typeStr
  override def elemFromInts(i1: Int, i2: Int): HCen = new HCen(i1, i2)

  def perimeter: PolygonHC = numElems match
  { case 1 => elem(0).hVertPolygon
    case 2 => ???
    case _ => ???
  }
}