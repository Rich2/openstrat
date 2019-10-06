package ostrat
import utest._

object ArrTest extends TestSuite
{
  trait MyT
  case class My1(i: Int) extends MyT
  case class My2(j: Int) extends MyT
  val tests = Tests
  {
    val ar1: ArrR[My1] = ArrR(My1(1), My1(2), My1(3))
    val ar2: ArrR[My2] = ArrR(My2(4), My2(5))
    val ar12: ArrR[MyT] = ar1 ++ ar2
    'test1 -
    {
      ar1.length ==> 3
      ar12.length ==> 5
      //assert(at2.array.isInstanceOf[Array[Int]])
     // assert(at3(1) == true)
      //assert(at3.array.isInstanceOf[Array[Boolean]])*/
    }
  }
}