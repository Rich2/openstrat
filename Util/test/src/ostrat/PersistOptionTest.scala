/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object PersistOptionTest extends TestSuite
{    
  val tests = Tests
  {
    'None
    {
      None.str ==> ""
      "None".findType[None.type] ==> Good(None)
      "".asType[None.type] ==> Good(None)
    }    
    
    'Some
    {
      Some(-5).str ==> "-5"
    }
  
  case class Test1(a: Option[Int], b: Int, c: Option[Double])
  object Test1
  {
    implicit object Test1Persist extends Persist3[Option[Int], Int, Option[Double], Test1]("Test1", r => (r.a, r.b, r.c), apply)
  }
  val t1 = Test1(Some(5), 4, Some(2.0))
  val t1Str = "Test1(5; 4; 2.0)"
  val t2 = Test1(None, 7, None) 
  
  case class Test2(t1: Test1, t2: Test1)
  
 // object Test2
 // {
    //implicit object Test2Persist extends Persist2[Test1, Test1, Test2]
 // }
    
    'Option -
    {
      val oa: Option[Int] = Some(5)
      oa.str ==> "5"        
      t1.str ==> t1Str
      "27".findType[Some[Int]] ==> Good(Some(27))
      assert(t1Str.findType[Test1].isGood)
      t2.str ==> "Test1(; 7; ;)"
    }
  }
}