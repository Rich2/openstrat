package ostrat
import utest._
object OptTest extends TestSuite
{
  val tests = Tests
  {
    def f(ss: OptOld[String]): Int = ss.fold(-1, _.length)
    val n1: OptOld[String] = NoOptOld()
    val n2 = OptOld("Hello")
    val i1 = NoIntOld
    val i2 = OptOld(5)
    //def fa(opt: OptInt): Opt[String] = opt.map(_.toString)

    "test1" -
    { f(n1) ==> -1
      f(n2) ==> 5
      i1.map(_ *  2) ==> NoIntOld
      i2.map(_ * 2) ==> SomeInt(10)
      n1.map(_.length  + 2) ==> NoIntOld
      assertMatch(n1.map(_.length  + 2)){ case NoOptOld() => }
      assertMatch(n2.map(_.length  + 2)){ case SomeInt(7) => }
      assertMatch(i2.map(i => Colour.Red)){ case SomeColour(Colour.Red) => }
    }
  }
}
