/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

class HCoords(val unsafeArray: Array[Int]) extends AnyVal with ArrInt2s[HCoord] {
  type ThisT = HCoords

  override def unsafeFromArray(array: Array[Int]): HCoords = new HCoords(array)

  override def typeStr: String = "HCoords" + foldLeft("")(_ + "; " + _.rcStr)

  override def dataElem(i1: Int, i2: Int): HCoord = HCoord(i1, i2)

  override def fElemStr: HCoord => String = _.toString
}

class HCoordBuff(val unsafeBuffer: Buff[Int] = buffInt()) extends AnyVal with BuffInt2s[HCoord]
{ type ArrT = HCoords
  override def typeStr: String = "HCoordBuff"
  override def intsToT(i1: Int, i2: Int): HCoord = HCoord(i1, i2)
}