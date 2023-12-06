/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object PersistOptionTest extends TestSuite
{
  case class Test1(a: Option[Int], b: Int, c: Option[Double])
  object Test1
  { implicit val showEv: Show3[Option[Int], Int, Option[Double], Test1] = Show3[Option[Int], Int, Option[Double], Test1]("Test1", "a", _.a, "b", _.b, "c", _.c)
    implicit val unShowEv: Unshow3[Option[Int], Int, Option[Double], Test1] = Unshow3[Option[Int], Int, Option[Double], Test1]("Test1", "a", "b", "c", apply)
  }
  val t1 = Test1(Some(5), 4, Some(2.0))
  val t1Str = "Test1(5; 4; 2.0)"  
  val t2 = Test1(None, 7, None) 
  
  case class Test2(t1: Test1, t2: Test1)
  
  val tests = Tests {
    test("None")
    {
      None.str ==> "None"
      "None".findType[None.type] ==> Good(None)
     // "".asType[None.type] ==> Good(None)
    }    

    val sm5: Option[Int] = Some(-5)

    test("Some")
    {
      sm5.str ==> "-5"
      //"-78.2".findType[Some[Double]] ==> Good(Some(-78.2))
      //"-78.2".findType[Option[Double]] ==> Good(Some(-78.2))
     // "Some(-78.2)".findType[Some[Double]] ==> Good(Some(-78.2))
     // "Some(-78.2)".findType[Option[Double]] ==> Good(Some(-78.2))
    }
    
    test("Option")
    {
      /*val oa: Option[Int] = Some(5)
      oa.str ==> "5"
      t1.str ==> t1Str
      //"27".findType[Some[Int]] ==> Good(Some(27))
      t1Str.findType[Test1] ==> Good(Test1(Some(5), 4, Some(2.0)))
      t2.str ==> "Test1(; 7; ;)"
      "Test1(; 7; ;)".findType[Test1] ==> Good(Test1(None, 7, None))  */
    }
  }
}