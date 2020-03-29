/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import utest._

object CoodsTest extends TestSuite
{
  val c1 = 5 cc -96
  val cs = Coods(5 cc 4, 3 cc 2, 7 cc 8, -5 cc -42)
  val tests = Tests
  {
    "test1" -
    {
      c1 ==> Cood(5, -96)
      cs.contains(Cood(4, 5)) ==> false 
      assert(cs.contains(Cood(7, 8))) 
      assert(cs.contains(Cood(-5, -42)))
    }
   }
}