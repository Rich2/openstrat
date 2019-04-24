/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object PersistOptionTest extends TestSuite
{ 
  val oa: Option[Int] = Some(5)
  val tests = Tests
  { 
    'persistNums -
    {
      assert(oa.str == "5")//compiles fine
     // assert(Some(5).str == "5")//value str is not a member of Some[Int]
    }
  }
}