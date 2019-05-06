/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse
import utest._ 

object TokenTest extends TestSuite
{ 
  val tests = Tests
  {
    val r1 = "+".findTokens
    deb(r1.toString)
    'Test1
    {
//      assert(r1 match 
//        {
//          case Good(List(PlusInToken(_, "+"))) => true
//          case _ => false
//        })
      assert("#".findTokens.isBad)
      
    }
  }
}