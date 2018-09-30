/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object PersistTest extends TestSuite
{ val tests = Tests
  {
    'test1 -
    { assert(5.persist == "5") 
      assert((-86).persist == "-86")
      assert((-86).persistComma == "-86")
    }
     val c1 = Colour.Black    
    'test11 - { assert(c1.toString == "Colour(000000FF)") }
    val cm: Multiple[Colour] = (Colour.Red * 5)
    
    'test12 - { assert(cm.toString == "Multiple(Colour(FF0000FF); 5)") }
  }
}