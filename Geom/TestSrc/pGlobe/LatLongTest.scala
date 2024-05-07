/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import utest._

object LatLongTest extends TestSuite
{
  val tests = Tests {
    val ll1: LatLong = 44 ll 46
    val ll2 = 43 ll 45
    val ll3 = ll2.subLong(226.degsVec)
    
    test("AddLongitude")
    { assert(ll1.latDegs =~ 44)
      assert(ll1.longDegs =~ 46)
      assert(ll1.addLongVec(4.degsVec).longDegs =~ 50)
      assert(ll1.addLongVec(43.degsVec).longDegs =~ 89)
      assert(ll1.addLongVec(45.degsVec).longDegs =~ 91)
      assert(ll1.addLongVec(133.degsVec).longDegs =~ 179)
      assert(ll1.addLongVec(135.degsVec).longDegs =~ -179)
      assert(ll1.addLongVec(175.degsVec).longDegs =~ -139)

      assert(ll2.subLong(40.degsVec).longDegs =~ 5)
      assert(ll2.subLong(80.degsVec).longDegs =~ -35)
      assert(ll2.subLong(180.degsVec).longDegs =~ -135)
      assert(ll2.subLong(224.degsVec).longDegs =~ -179)
      assert(ll3.longDegs =~ 179)
    }

    test("Persist")
    { 44.north.str ==> "44N"
      77.52.south.str ==> "77.52S"
      18.0.north.str ==> "18N"
      22.2.north.tellDec(ShowStdNoSpace, -1, 3) ==> "22.200N"
      15.east.str ==> "15E"
      -22.12.west.str ==> "22.12E"
      22.12.west.str3 ==> "22.120W"
    }

    val o1 = Longitude.degs(20)
    test("Longitude"){
      o1.milliSecs ==> Longitude.milliSecs(o1.milliSecs).milliSecs
    }

    val ll4 = -5 ll -40
    test("Latlong Persist")
    {
      ll1.str ==> "44.00N, 46.00E"
      "LatLong(44; 46)".asType[LatLong] ==> Good(ll1)
      "44; 46".asType[LatLong] ==> Good(ll1)
      "-5, -40".asType[LatLong] ==> Good(ll4)
    }
  }
}