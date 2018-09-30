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
    'persistOther -
    { assert(aa.persist == "TestObjA")
      assert(aa.persistTyped == "TestClass(TestObjA)")
      assert(c1.toString == "Colour(000000FF)")      
    }
    val cm: Multiple[Colour] = (Colour.Red * 5)
    
    'test12 - { assert(cm.toString == "Multiple(Colour(FF0000FF); 5)") }
  }
}