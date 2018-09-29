/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object StringerTest extends TestSuite
{ val tests = Tests
  { val c1 = Colour.Black
    deb(c1.toString)
    'test1 - { assert(c1.toString == "Colour(000000FF)") }
  }
}