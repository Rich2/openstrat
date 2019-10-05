package ostrat
import utest._

object ArrTest extends TestSuite
{
  val tests = Tests
  {
    val at1: Att[Int] = Att(1, 2, 3)
    val at2 = at1.map(_ * 2)
    val at3 = at2.map(_ > 3)
    'test1 -
    {
      assert(at1.length == 3)
      assert(at2(2) == 6)
      assert(at2.array.isInstanceOf[Array[Int]])
      assert(at3(1) == true)
      assert(at3.array.isInstanceOf[Array[Boolean]])
    }
  }
}