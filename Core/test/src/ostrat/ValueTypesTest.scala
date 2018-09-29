/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object ValueTypesTest extends TestSuite
{
  val tests = Tests
  {
    def t(i1: Int, i2: Int): Unit =
    { val d: Double = twoIntsToDouble(i1, i2)
      val p:(Int, Int) = d.to2Ints         
      assert ((i1,i2) == p)
    }
    'test1 - { t(-5, 4) }
    'test2 - {t(23457, -2147483647) }
    'test3 - {t( -4875, 2147483646) }
    'test4 - {t( -40875, 21474) }
    'test5 - {t( -40875, -21474) }
    'test6 - {t( 410875, -21001474) }
  }
}
