/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest.{Show => _, _}, gOne.h1p._, gPlay._

object G1Scen1Test extends TestSuite
{ val os1: G1HScen1.type = G1HScen1
  val gs = os1.gridSys
  val g1: HGridReg = os1.gridSys
  val g1Str = "HGridReg(2; 6; 2; 10)"
  val cs1: LayerHcOptSys[Counter] = os1.counters
  val hr1 = LayerHcOptRow[Counter](4, CounterA, None * 2, CounterB)
//  val rs1 = hr1.str

  val tests = Tests {
    test("os1") {
      g1.str ==> g1Str
      g1Str.asType[HGridReg] ==> Good(g1)
    }
  }
}