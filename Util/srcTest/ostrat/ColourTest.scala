/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object ColourTest extends TestSuite
{ 
  val tests = Tests
  {
    val bl = Colour.Black
    val bStr = "Colour(0x000000FF)"
    
    'test1 - 
    {
      assert(bl.toString == "Colour(0x000000FF)")
      assert(bl.str == "Colour(0x000000FF)")
      assert(bStr.findTokens match 
        {
        case Good(_) => true
        case _ => false
        })
    }
  }
}