/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import utest._

object RoordTest extends TestSuite
{
  val r1 = 5 rr -96
  val r2 = r1 + Roord(-5, 96)

  val tests = Tests {
    test("test1")
    { r1 ==> Roord(5, -96)
      r2 ==> Roord(0, 0)
    }
  }
}