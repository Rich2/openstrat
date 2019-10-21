/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse
import utest._ 

object TokenTest extends TestSuite
{ 
  val tests = Tests
  { 
    'Test1
    {
      assertMatch("45".findTokens){case Good(List(IntToken(_, _, 45))) =>}
      assertMatch("0x11".findTokens){case Good(List(HexIntToken(_, _, 17))) =>}
      "#".findTokens.isBad ==> true
    }
  }
}