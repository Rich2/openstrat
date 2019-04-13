/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import utest._

object CoodsTest  extends TestSuite
{
   val cs = Coods.ints(5,4, 3,2, 7,8, -5,-42)
   val tests = Tests
   {
      'test1 - {assert(!cs.contains(Cood(4, 5))) }
      'test2 - {assert(cs.contains(Cood(7, 8))) }
      'test3 - {assert(cs.contains(Cood(-5, -42))) }
   }
}