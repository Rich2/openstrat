/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A trait for classes of line paths specified by [[[HCen]] hex grid tile centre coordinates. Can't remember why this is a trait. */
class LinePathHC(val unsafeArray: Array[Int]) extends AnyVal with HCoordSeqDef with LinePathInt2s[HCoord]
{ override type ThisT = LinePathHC
  override def typeStr: String = "LinePathHC"
  override def fromArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
}

object LinePathHC extends Int2SeqDefCompanion[HCoord, LinePathHC]
{ override def fromArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
}