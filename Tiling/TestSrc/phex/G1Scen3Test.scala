/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._, gOne.hp1._

object G1Scen3Test extends TestSuite
{
  val os1: G1HScen3.type = G1HScen3
  val g1: HGridIrr = os1.gridSys
  val tests = Tests {

    test("os1") {
      g1.numTileRows ==> 5
      g1.bottomCenR ==> 2
      g1.topCenR ==> 10
      g1.leftCenC ==> 2
      g1.rightCenC ==> 14
      g1.numTiles ==> 11
    }
  }
}
