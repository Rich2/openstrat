/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._, Colour._

object ColourTest extends TestSuite
{ 
  val tests = Tests
  {
    val bl = Black
    //val bStr = "Colour(0x000000FF)"
    
    'test1 - 
    {
      bl.toString ==> "Black"
      bl.str ==> "Black"      
      //bStr.findType[Colour] ==> Good(Black)
    }
  }
}