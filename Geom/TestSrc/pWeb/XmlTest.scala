/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest.*, utiljvm.*

/** Also tests the srcToETokens function object. */
object XmlTest extends TestSuite
{
  class City(name: String) extends XmlElemSimple("City", name)

  class Country(val nameStr: String, val otherElems: RArr[XmlElem]) extends XmlMulti
  { override def tag: String = "Country"
    override def attribs: RArr[XHAtt] = RArr()
    def nameEl = XmlElemSimple("name", nameStr)

    override def contents: RArr[XConElem] = nameEl %: otherElems
  }

  object Country
  { def apply(nameStr: String, otherElems: XmlElem*): Country = new Country(nameStr, otherElems.toRArr)
  }

  case class Cities(contents: RArr[XConElem]) extends XmlMultiNoAtts
  { override def tag: String = "Cities"
  }

  val wash = City("Washington")
  val bost = City("Boston")
  val cities0 = Cities(RArr())
  val cities0Out = """<Cities/>"""
  val cities1 = Cities(RArr(wash))
  val cities2 = Cities(RArr(wash, bost))
  val usa1 = Country("USA", cities2)
  val country1Out: String = usa1.out()

  val tests = Tests {
    test("Test1")
    { wash.out() ==> "<City>Washington</City>"
      cities0.out() ==> cities0Out
      Succ(cities1.out()) ==>  strFromResource("cities1.xml")
      Succ(cities2.out()) ==> strFromResource("cities2.xml")
      Succ(usa1.out()) ==>  strFromResource("usa1.xml")
    }
    val paris = City("Paris")
    val tours = City("Tours")
    val lyon = City("Lyon")
    val frCities = Cities(RArr(paris, tours, lyon))
    val france1 = new Country("France", RArr(frCities))
    {
      override val attribs: RArr[XHAtt] = RArr(VersionAtt("1.0.0"), NameAtt("France"), XHAtt("VeryLongAttribute", "Very long value!"),
        XHAtt("Colour", "violet"))
    }

    val france1Out = france1.out()
    projPathDo{path => fileWrite(path /% "target/france1.xml", france1Out) }
  }
}