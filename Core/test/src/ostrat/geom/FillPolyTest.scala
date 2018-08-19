/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._
import Colour._
/** I wrote this test to solve a couple a of bugs, but actually found them by the use of printlns */
object FillPolyTestAlt  extends TestSuite{
   val v = Rectangle(4, 2)  
   val fp = FillPolyAlt(Red, v)   
   val fp2 = fp.fTrans(_ * 2)   
   val testArr = Array(Red.argbValue, -4.0,2.0,4.0,2.0,4.0,-2.0,-4.0,-2.0)
   val tests = Tests{
     'test1 { assert(fp2.arr.sameElements(testArr))}
  }

}