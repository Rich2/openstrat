package ostrat
import utest._

object EMonBuildTest  extends TestSuite
{
  val tests = Tests
  {
    val a1: EMonInt = GoodInt(5)
    val a2 = a1.mMap(_ * 11)
    //val a3 = a2.mMap("Good " + _.toString)
    val b = NoneInt
    val s1: EMon[String] = Good("Hello")
    "Test1" -
    {
      a2 ==> GoodInt(55)
      b.mMap(_ * 2) ==> NoneInt
      s1.mMap(_.length) ==> GoodInt(5)
    }
  }

}
