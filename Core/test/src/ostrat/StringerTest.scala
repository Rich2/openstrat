/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object StringerTest extends TestSuite
{ val tests = Tests
  { val c1 = Colour.Black    
    'test1 - { assert(c1.toString == "Colour(000000FF)") }
    val cm: Multiple[Colour] = (Colour.Red * 5)
    deb(cm.toString)
    'test2 - { assert(cm.toString == "Multiple(Colour(FF0000FF); 5)") }
  }
}