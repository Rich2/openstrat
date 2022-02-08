/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import utest._

object LatLongTest extends TestSuite
{
  val tests = Tests {
    val ll1: LatLong = 44 ll 46
    val ll2 = 43 ll 45
    val ll3 = ll2.subLong(226.degs)
    
    test("AddLongitude")
    { assert(ll1.latDegs =~ 44)
      assert(ll1.longDegs =~ 46)
      assert(ll1.addLong(4.degs).longDegs =~ 50)
      assert(ll1.addLong(43.degs).longDegs =~ 89)
      assert(ll1.addLong(45.degs).longDegs =~ 91)
      assert(ll1.addLong(133.degs).longDegs =~ 179)
      assert(ll1.addLong(135.degs).longDegs =~ -179)
      assert(ll1.addLong(175.degs).longDegs =~ -139)

      assert(ll2.subLong(40.degs).longDegs =~ 5)
      assert(ll2.subLong(80.degs).longDegs =~ -35)
      assert(ll2.subLong(180.degs).longDegs =~ -135)
      assert(ll2.subLong(224.degs).longDegs =~ -179)
      assert(ll3.longDegs =~ 179)
    }

    test("Persist")
    { 44.north.str ==> "44N"
      77.52.south.str ==> "77.52S"
      18.0.north.str ==> "18N"
      22.2.north.show(ShowStandard, -1, 3) ==> "22.200N"
      15.east.str ==> "15E"
      -22.12.west.str ==> "22.12E"
      22.12.west.str3 ==> "22.120W"
    }

    val o1 = Longitude.degs(20)
    test("Longitude"){
      o1.milliSecs ==> Longitude.milliSecs(o1.milliSecs).milliSecs
    }

    test
  }
}