/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest.*

object ParseTest extends TestSuite
{
  val tests = Tests {
    val settingStr = "x = -5; y = 7; true"

    test("Test1")
    { "4".findType[Int] ==> Right(4)
      "-4".findType[Int] ==> Right(-4)
      settingStr.findSetting[Int]("x") ==> Right(-5)
      settingStr.findSetting[Int]("y") ==> Right(7)
      settingStr.findIntSetting("y") ==> Right(7)
      settingStr.findType[Boolean] ==> Right(true)
    }

    val l1 = List(4, 5, 6)
    val l1s = "Seq(4; 5; 6)"

    test("List")
    { l1.str ==> l1s
      l1s.findType[List[Int]] ==> Right(l1)
    }

    val oi: Option[Int] = Some(50)
    test("Option")
    { oi.str ==> "50"
    }
  }
}