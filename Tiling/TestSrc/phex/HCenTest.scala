/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._

object HCenTest extends TestSuite
{
  val hc1 = HCen(6, 2)
  val hcs1 = "HCen(6; 2)"
  val hc2 = HCen(6, 10)
  val hc3 = HCen(2, 14)
  val arr1 = HCenArr(hc1, hc2, hc3)
  val arrStr1 = "HCenArr(6, 2; 6. 10; 2, 14)"

  val tests = Tests {
    test("test1")
    { hc1.str ==> hcs1
      hcs1.asType[HCen] ==> Good(hc1)
      //arr1.str ==> arrStr1
      //arrStr1.asType[HCenArr] ==> Good(arr1)
    }
  }
}
