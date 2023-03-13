/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest._

/** Also tests the srcToETokens function object. */
object XmlTest extends TestSuite
{
  case class City(str: String) extends XmlStr
  { override def tag: String = "City"
  }

  case class Cities(contents: RArr[XCon]) extends XmlMultiNoAtts
  { override def tag: String = "Cities"
  }

  val wash = City("Washington")
  val bost = City("Boston")

  val cities2 = Cities(RArr(wash, bost))

  val cities0 = Cities(RArr())
  val cities0Out =
    """<Cities/>""".stripMargin

  val cities1 = Cities(RArr(wash))
  val cities1Out =
    """<Cities>
      |  <City>Washington</City>
      |</Cities>""".stripMargin

  val cities2Out =
    """<Cities>
      |  <City>Washington</City>
      |  <City>Boston</City>
      |</Cities>""".stripMargin

  val tests = Tests {
    "Test1" - {
      wash.out() ==> "<City>Washington</City>"
      cities0.out() ==> cities0Out
      cities1.out() ==> cities1Out
      cities2.out() ==> cities2Out
    }
  }

}
