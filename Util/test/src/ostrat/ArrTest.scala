package ostrat
import utest._

object ArrTest extends TestSuite
{

  val tests = Tests
  {
    val is1 = Ints(1, 2, 3, 4)
    val is2 = Ints(5, 6, 7)
    val is3 = is1 ++ is2
    val ds1 = Dbls(1.5, 3, 4.5, 6)
    val ds2 = is1.map(_ * 1.5)
    val ds3 = is1.seqMap{a => List(a + 0.1, a + 0.2)}
    val ds4 = is1.bind(a => Ints(a + 10, a + 20, a + 30))
    val ds5 = is2.bind(i => Dbls(i, i * 0.5))
    'test1 -
    {
      is1(3) ==> 4
      is3.length ==> 7
      is3(6) ==> 7
      ds1(2) ==> 4.5
      ds2(0) ==> 1.5
      ds2.length ==> 4
      ds3(0) ==> 1.1
      ds3.length ==> 8
      ds4(1) ==> 21
      ds4(5) ==> 32
      ds4.length ==> 12
      ds5(1) ==> 2.5
    }

    trait MyT { def i: Int}
    case class My1(i: Int) extends MyT
    case class My2(i: Int) extends MyT
    val ar1: Refs[My1] = Refs(My1(1), My1(2), My1(3))
    val ar2: Refs[My2] = Refs(My2(4), My2(5))
    val ar12: Refs[MyT] = ar1 ++ ar2
    val am1 = ar12.map(_.i * 10)
    val am2 = Ints(5, 6, 7).bind(i => Refs(My1(i), My2(i)))
    'Test2
    {
      ar12.length ==> 5
      am1(4) ==> 50
      am2.length ==> 6
    }
  }
}