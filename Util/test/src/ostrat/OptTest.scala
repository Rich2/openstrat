package ostrat
import utest._
object OptTest extends TestSuite
{
  val tests = Tests
  {

    def f(ss: Opt[String]): Int = ss.fold(-1, _.length)
    val n1: Opt[String] = NoOpt()
    val n2 = Opt("Hello")

    'test1
    {
      f(n1) ==> -1
      f(n2) ==> 5
    }

  }

}
