package ostrat
import utest._

object EMonBuildTest  extends TestSuite
{
  val tests = Tests
  {
    val a1: EMonInt = GoodInt(5)
    val a2 = a1.baseMap(_ * 11)
    val a3 = a2.baseMap("Good " + _.toString)
    val b = BadIntNo
    val s1: EMon[String] = Good("Hello")

    "Test1" -
    { a2 ==> GoodInt(55)
      a3 ==> Good("Good 55")
      b.baseMap(_ * 2) ==> BadIntNo
      s1.baseMap(_.length) ==> GoodInt(5)
    }
  }

}
