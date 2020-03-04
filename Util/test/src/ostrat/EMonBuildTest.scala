package ostrat
import utest._

object EMonBuildTest  extends TestSuite
{
  val tests = Tests
  {
    val a1: EMonInt = GoodInt(5)
    val a2 = a1.tMap(_ * 11)
    val a3 = a2.tMap("Good " + _.toString)
    val b = NoInt
    val s1: EMon[String] = Good("Hello")

    "Test1" -
    { a2 ==> GoodInt(55)
      a3 ==> Good("Good 55")
      b.tMap(_ * 2) ==> NoInt
      s1.tMap(_.length) ==> GoodInt(5)
    }
  }

}
