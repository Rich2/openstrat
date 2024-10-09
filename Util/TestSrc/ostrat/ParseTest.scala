/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object ParseTest extends TestSuite
{
  val tests = Tests {
    val settingStr = "x = -5; y = 7; true"

    "Test1" -
    {
      "4".findTypeOld[Int] ==> Good(4)
      "-4".findTypeOld[Int] ==> Good(-4)
      settingStr.findSettingOld[Int]("x") ==> Good(-5)
      settingStr.findSettingOld[Int]("y") ==> Good(7)
      settingStr.findIntSettingOld("y") ==> Good(7)
      settingStr.findTypeOld[Boolean] ==> Good(true)
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