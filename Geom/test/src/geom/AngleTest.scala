/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._

object PolygonTest extends TestSuite
{
  val tests = Tests{
    val rect = Rect(200, 100)
    val rect2 = rect.vertsMultiply(3)
    val sides = rect.sides
    val sides2 = rect2.sides
    test("Test1")
    {
      sides.length ==> 4
      sides2.length ==> 12
    }
  }
}