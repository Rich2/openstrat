/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest.*, utiljvm.*

/** Also tests the srcToETokens function object. */
object XmlTest extends TestSuite
{
  class City(name: String) extends XmlElemSimple("City", name)

  class Country(val nameStr: String, val otherElems: RArr[XmlElem]) extends XmlMulti
  { override def tag: String = "Country"
    override def attribs: RArr[XmlAtt] = RArr()
    def nameEl = XmlElemSimple("name", nameStr)

    override def contents: RArr[XCon] = nameEl %: otherElems
  }

  object Country
  { def apply(nameStr: String, otherElems: XmlElem*): Country = new Country(nameStr, otherElems.toRArr)
  }

  case class Cities(contents: RArr[XCon]) extends XmlMultiNoAtts
  { override def tag: String = "Cities"
  }

  val wash = City("Washington")
  val bost = City("Boston")
  val cities0 = Cities(RArr())
  val cities0Out = """<Cities/>"""
  val cities1 = Cities(RArr(wash))
  val cities2 = Cities(RArr(wash, bost))
  val country1 = Country("USA", cities2)
  val country1Out: String = country1.out()
  projPathDo{path => fileWrite(path /% "target/usa1.xml", country1Out) }

  val tests = Tests {
    test("Test1")
    { wash.out() ==> "<City>Washington</City>"
      cities0.out() ==> cities0Out
      Succ(cities1.out()) ==>  strFromResource("cities1.xml")
      Succ(cities2.out()) ==> strFromResource("cities2.xml")
    }
  }
}