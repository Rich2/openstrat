/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

class TestClass(val objSym: Symbol) extends SingletonLeaf
{
  def typeSym = 'TestClass
}
object TestClass
{
  implicit object TestClassPersistImplicit extends PersistSingletons[TestClass]("TestClass")
  { override val singletonList = List(TestObjA, TestObjB)    
  }
}

object TestObjA extends TestClass('TestObjA)
object TestObjB extends TestClass('TestObjB)


case class MyClass(ints: Seq[Int], myStr: String)
object MyClass
{
  implicit object MyClassPersist extends Persist2[Seq[Int], String, MyClass]("MyClass", m => (m.ints, m.myStr), apply)
}

object PersistTest extends TestSuite
{ 
  val tests = Tests
  { 
    'persistNums -
    { assert(5.str == "5") 
      assert((-86).str == "-86")
      assert((-86).strComma == "-86")
      assert((-86).strTyped == "Int(-86)")
      assert(23.4.str == "23.4")
      assert((-6.00).str == "-6.0")
      val d: Double = 8
      assert(d.strTyped == "DFloat(8.0)")
      assert("7".findType[Int] == Good(7))
      assert("7".findType[Double] == Good(7))
    }    

    val aa: TestClass = TestObjA
    val aaStr: String = "TestObjA"
    val str1: String = "I am a String"
    val str1Std: String = "\"I am a String\""
    val abSeq = Seq(TestObjA, TestObjB)    
    
    'persistOther -
    { assert(aa.str == aaStr)
      assert(aaStr.findType[TestClass] == Good(TestObjA))
      assert(aa.strTyped == "TestClass(TestObjA)")
      assert(abSeq.str.findType[Seq[TestClass]] == Good(Seq(TestObjA, TestObjB)))      
      assert(str1.str == str1Std)
      assert(str1.strSemi == str1Std)
      assert(str1.strComma == str1Std)
      assert(str1.strTyped == "Str(" + str1Std + ")")
    }    
    
    val mc = MyClass(List(7, 8, 9), "hi")
    
    'More -
    {      
      assert(mc.str == "MyClass(7, 8, 9; \"hi\")")
    }
  }
}