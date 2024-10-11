/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object ParseTest extends TestSuite
{
  val tests = Tests {
    val settingStr = "x = -5; y = 7; true"

    test("Test1")
    { "4".findType[Int] ==> Succ(4)
      "-4".findType[Int] ==> Succ(-4)
      settingStr.findSetting[Int]("x") ==> Succ(-5)
      settingStr.findSetting[Int]("y") ==> Succ(7)
      settingStr.findIntSetting("y") ==> Succ(7)
      settingStr.findType[Boolean] ==> Succ(true)
    }
    val oi: Option[Int] = Some(50)

    val l1 = List(4, 5, 6)
    val l1s = "Seq(4; 5; 6)"

    "List Test" -
    {
      l1.str ==> l1s
      //l1s.findType[List[Int]] ==> Good(l1)
    }

    "Option Test" -
    {
      oi.str ==> "50"
    }
  }
}