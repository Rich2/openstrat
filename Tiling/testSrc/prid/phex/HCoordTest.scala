/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._

object HCoordTest extends TestSuite
{
  val hc1 = HCen(6, 2)
  val hc2 = HCen(6, 10)

  val tests = Tests {
    test("test1")
      { 10.base32 ==> "A"
        hc1.strComma ==> "6, 2"
        hc2.strComma ==> "6, 10"
      }
  }
}