/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest.{Show => _, _}

/** Example of a [[Tell2]] class for testing purposes. */
case class ExUA(a: Int = 0, b: String = "blah") extends Tell2[Int, String]
{ override def typeStr: String = "ExUA"
  override def tell1: Int = a
  override def tell2: String = b
  override def show1: Show[Int] = Show.intEv
  override def show2: Show[String] = Show.stringEv
  override def name1: String = "a"
  override def name2: String = "b"

  override def opt2: Option[String] = Some("blah")
}

object ExUA
{ implicit val showEv: ShowTell2[Int, String, ExUA] = ShowTell2[Int, String, ExUA]("ExUA")
  implicit val unshowEv: Unshow2[Int, String, ExUA] = Unshow2[Int, String, ExUA]("ExUA", "a", "b", apply, Some("blah"), Some(0))
}

/** Example of a [[Tell3]] class on of whose parameters is also a [[TellN]] class. */
case class ExUB(a: ExUA = ExUA(), b: String = "BBB", c: Int = 777) extends Tell3[ExUA, String, Int]
{ override def typeStr: String = "ExUA"
  override def tell1: ExUA = a
  override def tell2: String = b
  override def tell3: Int = c
  override def show1: Show[ExUA] = ExUA.showEv
  override def show2: Show[String] = Show.stringEv
  override def show3: Show[Int] = Show.intEv
  override def name1: String = "a"
  override def name2: String = "b"
  override def name3: String = "c"

  override def opt2: Option[String] = Some("blah")

  override def tellDepth: Int = 3
}

object ExUB
{ implicit val showEv: ShowTell[ExUB] = ShowTell[ExUB ]("ExUB")
  implicit val unshowEv: Unshow3[ExUA, String, Int, ExUB] = Unshow3[ExUA, String, Int, ExUB ]("ExUB", "a", "b", "c", apply, Some(777), Some("BBB"), Some(ExUA()))
}

object UnshowTest extends TestSuite
{ val tests = Tests {
    test("Named 2 Parameters test")
    { """ExUA(42; "Hello")""".asType[ExUA] ==> Succ(ExUA(42, "Hello"))
      "ExUA(42)".asType[ExUA] ==> Succ(ExUA(42, "blah"))
      "ExUA()".asType[ExUA] ==> Succ(ExUA(0, "blah"))
      """ExUA(a = 42; "Hello")""".asType[ExUA] ==> Succ(ExUA(42, "Hello"))
      """ExUA(a = 42; b = "Hello")""".asType[ExUA] ==> Succ(ExUA(42, "Hello"))
      """ExUA(a = 42; c = "Hello")""".asType[ExUA].isFail ==> true
      """ExUA(b = "Hello"; a = 42)""".asType[ExUA] ==> Succ(ExUA(42, "Hello"))
      """ExUA(b = "Hello"; 42)""".asType[ExUA] ==> Succ(ExUA(42, "Hello"))
      """ExUA(b = "Hello")""".asType[ExUA] ==> Succ(ExUA(0, "Hello"))
    }

    test("Named 3 parameters test")
    { ExUB() ==> ExUB()
      """ExUB()""".asType[ExUB] ==> Succ(ExUB())
      """ExUB(ExUA(); "999"; -100)""".asType[ExUB] ==> Succ(ExUB(ExUA(), "999", -100))
      """ExUB(a = ExUA(); b = "999"; c = -100)""".asType[ExUB] ==> Succ(ExUB(ExUA(), "999", -100))
      """ExUB(a: ExUA = ExUA(); b: String = "999"; c: Int = -100)""".asType[ExUB] ==> Succ(ExUB(ExUA(), "999", -100))
      """ExUB(b: String = "AAA")""".asType[ExUB] ==> Succ(ExUB(b = "AAA"))
      """ExUB(c = 5;)""".asType[ExUB] ==> Succ(ExUB(c = 5))
      """ExUB(c = 5; a = ExUA(b = "Hi"))""".asType[ExUB] ==> Succ(ExUB(ExUA(b = "Hi"), c = 5))
      """ExUB(c = 5; a = 7, "Hi")""".asType[ExUB] ==> Succ(ExUB(ExUA(7, "Hi"), c = 5))
      """ExUB(c = 5; a = 7, "Hi",)""".asType[ExUB] ==> Succ(ExUB(ExUA(7, "Hi"), c = 5))
      """ExUB(c = 5; a = 7, "Hi";)""".asType[ExUB] ==> Succ(ExUB(ExUA(7, "Hi"), c = 5))
      """ExUB(c = 5; a = 7, "Hi", ;)""".asType[ExUB] ==> Succ(ExUB(ExUA(7, "Hi"), c = 5))
    }
  }
}