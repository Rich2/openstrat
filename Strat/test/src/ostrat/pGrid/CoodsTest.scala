/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import utest._

object CoodsTest  extends TestSuite
{
  val c1 = 5 cc -96
  val cs = Coods.ints(5,4, 3,2, 7,8, -5,-42)
  val tests = Tests
  {
    'test1 -
    {
      c1 ==> Cood(5, -96)
      assert(!cs.contains(Cood(4, 5))) 
      assert(cs.contains(Cood(7, 8))) 
      assert(cs.contains(Cood(-5, -42)))
    }
   }
}