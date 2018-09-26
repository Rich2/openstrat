/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

case class MyD2(a: Double, b: Double) extends Stringer
{ override def typeSym = 'MyD2
  override def str: String = persistD2(a, b)
}

object PersistTest  extends TestSuite
{ val m1 = MyD2(56.1, -45.2)
  val tests = Tests{
    println(m1.str)
    'test1 { assert(m1.str == "MyD2(56.1, -45.2)") }
    'test2 { assert("hi" == "hi") }
  }
}