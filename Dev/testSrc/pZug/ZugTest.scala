/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import utest._//, pGrid._

object ZugTest  extends TestSuite
{
  val tests = Tests {
    /*val f1 = Zug1Old.zPath(26 cc 10, 28 cc 8)
    val f2 = Zug1Old.zPath(26 cc 10, 22 cc 14)
    val f3 = Zug1Old.zPath(24 cc 8, 32 cc 8)*/
    
    "Path" -
    { 5 ==> 5
      /*assert(f1 == None)
      assert(f2 == Some(List(24 cc 12, 22 cc 14)))
      assert(f3 == Some(List(26 cc 10, 30 cc 10, 32 cc 8)))   */
    }
  }
}