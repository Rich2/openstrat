/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package h1p
import prid.phex._

/** An object for defining [[HGridSys]]s which can be used in multiple example games. */
object GSys {
  val g1: HGridReg = HGridReg(3, 30)
  val g2: HGridReg = HGridReg.minMax(2, 12, 2, 60)
  val g3: HGridIrr = HGridIrr.fromTop(10, (6, 14), (8, 8), (2, 14), (4, 8), (6, 6))
}