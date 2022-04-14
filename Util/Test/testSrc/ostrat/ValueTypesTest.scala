/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

/** Not quite sure the logic of this test code. */
object ValueTypesTest extends TestSuite
{
  val tests = Tests {
    def tt(pIn: (Int, Int)): (Int, Int) = twoIntsToDouble(pIn._1, pIn._2).to2Ints     
    val dd1 = (-5, 4)
    val dd2 = (23457, -2147483647)
    val dd3 = (-4875, 2147483646)
    val dd4 = (-40875, 21474)
    val dd5 = (-40875, -21474)
    val dd6 = (410875, -21001474) 

    "TwoIntsToDouble" -
    { 
      tt(dd1) ==> dd1
      tt(dd2) ==> dd2
      tt(dd3) ==> dd3
      tt(dd4) ==> dd4
      tt(dd5) ==> dd5
      tt(dd6) ==> dd6       
     }
  }
}
