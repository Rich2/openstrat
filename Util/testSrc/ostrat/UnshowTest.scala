/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

case class ExUA(a: Int, b: String = "blah") extends Show2[Int, String]
{
  override def typeStr: String = "ExUA"
  override def show1: Int = a
  override def show2: String = b
  override def showT1: ShowT[Int] = ShowT.intPersistEv
  override def showT2: ShowT[String] = ShowT.stringPersistEv
  override def name1: String = "a"
  override def name2: String = "b"

  override def opt2: Option[String] = Some("blah")
}

object ExUA{
  implicit val unshowEv: Unshow2[Int, String, ExUA] = Unshow2("ExUA", "a", "b", apply, Some("blah"))
}

object UnshowTest extends TestSuite
{
  val tests = Tests {
    test("U2") {
      "ExUA(42)".asType[ExUA] ==> Good(ExUA(42))
    }
  }
}
