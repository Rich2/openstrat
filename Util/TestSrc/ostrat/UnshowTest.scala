/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest.{Show => _, _}

/** Example of a [[Tell2]] class for testing purposes. */
case class ExUA(a: Int = 0, b: String = "blah") extends Tell2[Int, String]
{ override def typeStr: String = "ExUA"
  override def show1: Int = a
  override def show2: String = b
  override def persist1: Show[Int] = Show.intPersistEv
  override def persist2: Show[String] = Show.stringPersistEv
  override def name1: String = "a"
  override def name2: String = "b"

  override def opt2: Option[String] = Some("blah")
}

object ExUA{
  implicit val persistEv: Persist2[Int, String, ExUA] = PersistTell2[Int, String, ExUA]("ExUA", "a", "b", apply, Some("blah"), Some(0))
}

/** Example of a [[Tell3]] class on of whose parameters is also a [[TellN]] class. */
case class ExUB(a: ExUA = ExUA(), b: String = "BBB", c: Int = 777) extends Tell3[ExUA, String, Int]
{
  override def typeStr: String = "ExUA"
  override def show1: ExUA = a
  override def show2: String = b
  override def show3: Int = c
  override def persist1: Show[ExUA] = ExUA.persistEv
  override def persist2: Show[String] = Show.stringPersistEv
  override def persist3: Show[Int] = Show.intPersistEv
  override def name1: String = "a"
  override def name2: String = "b"
  override def name3: String = "c"

  override def opt2: Option[String] = Some("blah")

  override def syntaxDepth: Int = 3
}

object ExUB
{ implicit val persistEv: PersistTell3[ExUA, String, Int, ExUB] =
  PersistTell3[ExUA, String, Int, ExUB ]("ExUB", "a", "b", "c", apply, Some(777), Some("BBB"), Some(ExUA()))
}

object UnshowTest extends TestSuite
{ val tests = Tests {
    test("UA")
    {
     """ExUA(42; "Hello")""".asType[ExUA] ==> Good(ExUA(42, "Hello"))
      "ExUA(42)".asType[ExUA] ==> Good(ExUA(42, "blah"))
      "ExUA()".asType[ExUA] ==> Good(ExUA(0, "blah"))
      """ExUA(a = 42; "Hello")""".asType[ExUA] ==> Good(ExUA(42, "Hello"))
      """ExUA(a = 42; b = "Hello")""".asType[ExUA] ==> Good(ExUA(42, "Hello"))
      """ExUA(a = 42; c = "Hello")""".asType[ExUA].isBad ==> true
      """ExUA(b = "Hello"; a = 42)""".asType[ExUA] ==> Good(ExUA(42, "Hello"))
      """ExUA(b = "Hello"; 42)""".asType[ExUA] ==> Good(ExUA(42, "Hello"))
      """ExUA(b = "Hello")""".asType[ExUA] ==> Good(ExUA(0, "Hello"))
    }

    test("UB")
    {
      5 ==> 5
      ExUB() ==> ExUB()
    """ExUB()""".asType[ExUB] ==> Good(ExUB())
      """ExUB(ExUA(); "999"; -100)""".asType[ExUB] ==> Good(ExUB(ExUA(), "999", -100))
      """ExUB(a = ExUA(); b = "999"; c = -100)""".asType[ExUB] ==> Good(ExUB(ExUA(), "999", -100))
      """ExUB(a: ExUA = ExUA(); b: String = "999"; c: Int = -100)""".asType[ExUB] ==> Good(ExUB(ExUA(), "999", -100))
      """ExUB(b: String = "AAA")""".asType[ExUB] ==> Good(ExUB(b = "AAA"))
      """ExUB(c = 5;)""".asType[ExUB] ==> Good(ExUB(c = 5))
      """ExUB(c = 5; a = ExUA(b = "Hi"))""".asType[ExUB] ==> Good(ExUB(ExUA(b = "Hi"), c = 5))
      """ExUB(c = 5; a = 7, "Hi")""".asType[ExUB] ==> Good(ExUB(ExUA(7, "Hi"), c = 5))
      """ExUB(c = 5; a = 7, "Hi",)""".asType[ExUB] ==> Good(ExUB(ExUA(7, "Hi"), c = 5))
      """ExUB(c = 5; a = 7, "Hi";)""".asType[ExUB] ==> Good(ExUB(ExUA(7, "Hi"), c = 5))
      """ExUB(c = 5; a = 7, "Hi", ;)""".asType[ExUB] ==> Good(ExUB(ExUA(7, "Hi"), c = 5))
    }
  }
}