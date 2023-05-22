/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package h1p
import prid.phex._

/** An object for defining [[HGridSys]]s which can be used in multiple example games. */
object GSys {
  val g1: HGridReg = HGridReg(2, 6, 2, 10)
  val g2: HGridReg = HGridReg(2, 12, 2, 60)
  val g3: HGridIrr = HGridIrr(10, (3, 6), (1, 8), (4, 2), (2, 4), (1, 6))
}