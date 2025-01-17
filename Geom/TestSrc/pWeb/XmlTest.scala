/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest.*, utiljvm.*

/** Also tests the srcToETokens function object. */
object XmlTest extends TestSuite
{
  class City(str: String) extends XmlElemSimple("City", str)

  case class Cities(contents: RArr[XCon]) extends XmlMultiNoAtts
  { override def tag: String = "Cities"
  }

  val wash = City("Washington")
  val bost = City("Boston")
  val cities0 = Cities(RArr())
  val cities0Out = """<Cities/>"""
  val cities1 = Cities(RArr(wash))
  val cities2 = Cities(RArr(wash, bost))

  val tests = Tests {
    test("Test1")
    { wash.out() ==> "<City>Washington</City>"
      cities0.out() ==> cities0Out
      Succ(cities1.out()) ==>  strFromResource("cities1.xml")
      Succ(cities2.out()) ==> strFromResource("cities2.xml")
    }
  }
}