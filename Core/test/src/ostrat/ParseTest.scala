/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object ParseTest  extends TestSuite
{ 
  val tests = Tests
  {
    deb("4 + 4".parseTree.toString)    
    'Test1
    {
      assert("4".findType[Int] == Good(4))
      assert("-4".findType[Int] == Good(-4))
    }
  }
}