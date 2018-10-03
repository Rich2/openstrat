/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

class TestClass(val sym: Symbol) extends StringerSingleton
{
  def typeSym = 'TestClass
}
object TestClass
{
  implicit object TestClassPersistImplicit extends PersistSingletons[TestClass]('TestClass)
  { override val singletonList = List(TestObjA)    
  }
}

object TestObjA extends TestClass('TestObjA)

case class MyClass(ints: Seq[Int], myStr: String)
object MyClass
{
  implicit object MyClassPersist extends Persist2[Seq[Int], String, MyClass](
      'MyClass, m => (m.ints, m.myStr), apply)
}

object PersistTest extends TestSuite
{ val aa: TestClass = TestObjA
  deb(aa.strTyped)
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
    }
    val c1 = Colour.Black
    val aa: TestClass = TestObjA
    val str1: String = "I am a String"
    val str1Std: String = "\"I am a String\""
    deb(str1.strTyped)
    
    'persistOther -
    { assert(aa.str == "TestObjA")
      assert(aa.strTyped == "TestClass(TestObjA)")
      assert(c1.toString == "Colour(000000FF)")
      assert(cm.toString == "Multiple(Colour(FF0000FF); 5)")
      assert(str1.str == str1Std)
      assert(str1.strSemi == str1Std)
      assert(str1.strComma == str1Std)
      assert(str1.strTyped == "Str(" + str1Std + ")")
    }
    
    val cm: Multiple[Colour] = (Colour.Red * 5)
    val l1 = Seq(-1, -2, -30)
    val l1Comma: String = "-1, -2, -30"
    val l2: List[Int] = List(4, 5, 6)
    val l2Comma: String = "4, 5, 6"
    val ss: Seq[Seq[Int]] = Seq(l1, l2)
    val mc = MyClass(List(7, 8, 9), "hi")
    
    'Seq -
    { assert(l1.str == "Seq[Int](-1; -2; -30)")
      assert(l1.strSemi == "-1; -2; -30")
      assert(l1.strComma == l1Comma)
      assert(l1.strTyped == "Seq[Int](-1; -2; -30)")
      assert(l2.strComma == l2Comma)
      assert(ss.str == "Seq[Seq[Int]](" + l1Comma + "; " + l2Comma + ")")
      assert(mc.str == "MyClass(7, 8, 9; \"hi\")")
    }
  }
}