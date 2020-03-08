package ostrat
import utest._
object OptTest extends TestSuite
{
  val tests = Tests
  {
    def f(ss: EMon[String]): Int = ss.foldErrs(_.length)(_ => -1)
    val n1: EMon[String] = NoGood
    val n2 = Good("Hello")
    val i1: OptInt = NoInt

//    val i2 = OptOld(5)
    //def fa(opt: OptInt): Opt[String] = opt.map(_.toString)

    "test1" -
    {
      f(n1) ==> -1
      f(n2) ==> 5
      i1.toString ==> "NoInt"
 //     i1.mMap(_ *  2) ==> NoIntOld
//      i2.map(_ * 2) ==> SomeInt(10)
//      n1.map(_.length  + 2) ==> NoIntOld
//      assertMatch(n1.map(_.length  + 2)){ case NoOptOld() => }
//      assertMatch(n2.map(_.length  + 2)){ case SomeInt(7) => }
//      assertMatch(i2.map(i => Colour.Red)){ case SomeColour(Colour.Red) => }
    }
  }
}
