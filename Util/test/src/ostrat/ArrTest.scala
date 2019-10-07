package ostrat
import utest._

object ArrTest extends TestSuite
{
  trait MyT{ def i: Int}
  case class My1(i: Int) extends MyT
  case class My2(i: Int) extends MyT
  val tests = Tests
  {
    val ar1: ArrR[My1] = ArrR(My1(1), My1(2), My1(3))
    val ar2: ArrR[My2] = ArrR(My2(4), My2(5))
    val ar12: ArrR[MyT] = ar1 ++ ar2
    val ai1 = ArrI(1, 2, 3, 4)
    val ai2 = ArrI(5, 6)
    val ai12 = ai1 ++ ai2
    'test1 -
    {
      ar1.length ==> 3
      ar12.length ==> 5
      ar12(4).i ==> 5
      ai12.length ==> 6
      ai12(0) ==> 1
      ai12(5) ==> 6
      //assert(at2.array.isInstanceOf[Array[Int]])
     // assert(at3(1) == true)
      //assert(at3.array.isInstanceOf[Array[Boolean]])*/
    }
  }
}