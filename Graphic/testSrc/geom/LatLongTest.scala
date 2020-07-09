/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

object LatLongTest   extends TestSuite
{
  val tests = Tests
  {
    val ll1 = 44 ll 46
    val ll2 = 43 ll 45
    val ll3 = ll2.subLong(226.degsToRadians)
    
    "AddLongitude" -
    { assert(ll1.latDegs =~ 44)
      assert(ll1.longDegs =~ 46)
      assert(ll1.addLong(4.degsToRadians).longDegs =~ 50)
      assert(ll1.addLong(43.degsToRadians).longDegs =~ 89)
      assert(ll1.addLong(45.degsToRadians).longDegs =~ 91)
      assert(ll1.addLong(133.degsToRadians).longDegs =~ 179)
      assert(ll1.addLong(135.degsToRadians).longDegs =~ -179)
      assert(ll1.addLong(175.degsToRadians).longDegs =~ -139)
      
      assert(ll2.subLong(40.degsToRadians).longDegs =~ 5)
      assert(ll2.subLong(80.degsToRadians).longDegs =~ -35)
      assert(ll2.subLong(180.degsToRadians).longDegs =~ -135)
      assert(ll2.subLong(224.degsToRadians).longDegs =~ -179)
      assert(ll3.longDegs =~ 179)
    }
  }
}