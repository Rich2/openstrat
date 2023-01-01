/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A polygon made up of 1 or more hex tiles, defined by the [[HCen]]s of its outer ring of hex tiles. */
class HTilePolygon(override val unsafeArray: Array[Int]) extends Int2SeqSpec[HCen]
{
  override type ThisT = HTilePolygon
  override def typeStr: String = "HTilePolygon"
  override def fromArray(array: Array[Int]): HTilePolygon = new HTilePolygon(array)
  override def fElemStr: HCen => String = _.toString
  override def elemsStr: String = typeStr
  override def newElem(i1: Int, i2: Int): HCen = new HCen(i1, i2)
}