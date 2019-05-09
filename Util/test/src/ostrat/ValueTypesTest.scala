/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

/** Not quite sure the logic of this test code. */
object ValueTypesTest extends TestSuite
{
  val tests = Tests
  {
    def t(i1: Int, i2: Int): Unit =
    { val d: Double = twoIntsToDouble(i1, i2)
      val p:(Int, Int) = d.to2Ints         
      assert ((i1,i2) == p)
    }
    * - { t(-5, 4) }
    * - {t(23457, -2147483647) }
    * - {t( -4875, 2147483646) }
    * - {t( -40875, 21474) }
    * - {t( -40875, -21474) }
    * - {t( 410875, -21001474) }
  }
}
