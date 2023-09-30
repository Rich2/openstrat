/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._, gOne.h1p._, gPlay._

object G1Scen1Test extends TestSuite {
  val os1: G1HScen1.type = G1HScen1
  val gs = os1.gridSys
  val g1: HGridReg = os1.gridSys
  val cs1: HCenOptLayer[Counter] = os1.counters

  val tests = Tests {
    test("os1") {
      cs1.out(gs) ==> ""
    }
  }
}