/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object ParseTest  extends TestSuite
{ 
  val tests = Tests
  {
    val settingStr = "x = -5; y = 7; true"
    
    'Test1
    {
      assert("4".findType[Int] == Good(4))
      assert("-4".findType[Int] == Good(-4))
      assert(settingStr.findSetting[Int]('x) == Good(-5))
      assert(settingStr.findSetting[Int]('y) == Good(7))
      assert(settingStr.findType[Boolean] == Good(true))
    }
  }
}