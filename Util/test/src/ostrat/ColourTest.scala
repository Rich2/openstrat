/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._, Colour._

object ColourTest extends TestSuite
{ 
  val tests = Tests
  {
    val c1 = Black
    val s1 = c1.str
    val e1 = s1.findType[Colour]
    val c2 = Colour(0xFF000000)
    val s3 = "Colour(0xFF000000)"
    val e3 = s3.findType[Colour]

    
    'test1 - 
    {
      s1 ==> "Black"
      e1 ==> Good(Black)
      c2 ==> Black
      e3 ==> Good(Black)
     // assertMatch(bStr.findTokens){case Good(_) =>}
    }
  }
}