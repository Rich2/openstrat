/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object PersistTest extends TestSuite
{
  class TestClass(val str: String) extends PersistSingleton

  object TestClass
  {
    implicit object TestClassPersistImplicit extends PersistSingletons[TestClass]("TestClass")
    { override val singletonList = List(TestObjA, TestObjB)    
    }
  }

  object TestObjA extends TestClass("TestObjA")
  object TestObjB extends TestClass("TestObjB")
  
  case class My2(ints: Seq[Int], myStr: String)
 
  object My2
  { implicit object My2Persist extends Persist2[Seq[Int], String, My2]("My2", m => (m.ints, m.myStr), apply)
  }
  
  
  
  val tests = Tests
  {    
    'persistNums -
    {
      5.str ==> "5" 
      (-86).str ==> "-86"
      (-86).strComma ==> "-86"
      (-86).strTyped ==> "Int(-86)"
      23.4.str ==> "23.4"
      (-6.00).str ==> "-6.0"
      val d: Double = 8
      d.strTyped ==> "DFloat(8.0)"
      "7".findType[Int] ==> Good(7)
      "7".findType[Double] ==> Good(7)
    }    

    val aa: TestClass = TestObjA
    val aaStr: String = "TestObjA"
    val str1: String = "I am a String"
    val str1Std: String = "\"I am a String\""
    val abSeq = Seq(TestObjA, TestObjB)    
    val mc = My2(List(7, 8, 9), "hi")
    
    'persistOther -
    {
      aa.str ==> aaStr
      aaStr.findType[TestClass] ==> Good(TestObjA)
      aa.strTyped ==> "TestClass(TestObjA)"
      abSeq.str.findType[Seq[TestClass]] ==> Good(Seq(TestObjA, TestObjB))      
      str1.str ==> str1Std
      str1.strSemi ==> str1Std
      str1.strComma ==> str1Std
      str1.strTyped ==> "Str(" + str1Std + ")"
      mc.str ==> "My2(7, 8, 9; \"hi\")"
    }    
  }
}