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

object PersistTest extends TestSuite
{ val aa: TestClass = TestObjA
  deb(aa.persistTyped)
  val tests = Tests
  { 
    'persistNums -
    { assert(5.persist == "5") 
      assert((-86).persist == "-86")
      assert((-86).persistComma == "-86")
      assert((-86).persistTyped == "Int(-86)")
      assert(23.4.persist == "23.4")
      assert((-6.00).persist == "-6.0")
      val d: Double = 8
      assert(d.persistTyped == "DFloat(8.0)")
    }
    val c1 = Colour.Black
    val aa: TestClass = TestObjA
    val str1: String = "I am a String"
    val str1Std: String = "\"I am a String\""
    deb(str1.persistTyped)
    
    'persistOther -
    { assert(aa.persist == "TestObjA")
      assert(aa.persistTyped == "TestClass(TestObjA)")
      assert(c1.toString == "Colour(000000FF)")
      assert(cm.toString == "Multiple(Colour(FF0000FF); 5)")
      assert(str1.persist == str1Std)
      assert(str1.persistSemi == str1Std)
      assert(str1.persistComma == str1Std)
      assert(str1.persistTyped == "Str(" + str1Std + ")")
    }
    
    val cm: Multiple[Colour] = (Colour.Red * 5)
    val l1 = Seq(-1, -2, -30)
  //  val l2: List[Int] = List(4, 5, 6)
    
    'Seq -
    { assert(l1.persist == "Seq[Int](-1; -2; -30)") 
      
    }
  }
}