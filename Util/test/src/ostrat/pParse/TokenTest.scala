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
      assert("#".findTokens.isBad)      
    }
  }
}