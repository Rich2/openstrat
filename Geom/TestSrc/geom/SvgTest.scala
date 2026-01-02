/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest.*

object SvgTest extends TestSuite
{
  val tests = Tests {
    val cf: CircleFill = Circle(10).fill(Colour.Red)

    test("shapes")
    { Colour.Red.svgStr ==> "red"
      cf.svgElem.out ==> "<circle  cx='0.00' cy='0.00' r='10.0' fill='red'></circle>"
    }
  }
}