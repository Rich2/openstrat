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
    val am1 = ar1.map(_.i * 10)
    val am2 = am1.map(i => My2(i + 5))
  //  val am3 = am2.map(_.i * 0.5)
    //val am4 = am3.map(_.toLong)
    'test1 -
    {
      ar1.length ==> 3
      ar12.length ==> 5
      ar12(4).i ==> 5
      ai12.length ==> 6
      ai12(0) ==> 1
      ai12(5) ==> 6
      am1(1) ==> 20
     // assert(am1.array.isInstanceOf[Array[Int]])
      am2(2).i ==> 35
     // assert(am3.array.isInstanceOf[Array[Double]])
    //  am3(0) ==> 7.5
     // assert(am4.array.isInstanceOf[Array[Long]])
    }
  }
}