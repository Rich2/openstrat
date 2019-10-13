package ostrat
import utest._

object ArrTest extends TestSuite
{
  trait MyT { def i: Int}
  case class My1(i: Int) extends MyT
  case class My2(i: Int) extends MyT
  val tests = Tests
  {
    val is1 = Ints(1, 2, 3, 4)
    val is2 = Ints(5, 6, 7)
    val is3 = is1 ++ is2
    val ds1 = ArrDou(1.5, 3, 4.5, 6)
    val ds2 = is1.map(_ * 1.5)
    val ds3 = is1.seqMap{a => List(a + 0.1, a + 0.2)}
   // val ds4 s= is1.bind[Double, ArrDou](a => ArrDou(a + 0.1, a + 0.2))
    val ar1: ArrRefs[My1] = ArrRefs(My1(1), My1(2), My1(3))
    /*val ar2: ArrR[My2] = ArrR(My2(4), My2(5))
    val ar12: ArrR[MyT] = ar1 ++ ar2
    val ai1 = ArrI(1, 2, 3, 4)
    val ai2 = ArrI(5, 6)
    val ai12 = ai1 ++ ai2
    val am1 = ar1.map(_.i * 10)
    val am2 = am1.map(i => My2(i + 5))*/
  //  val am3 = am2.map(_.i * 0.5)
    //val am4 = am3.map(_.toLong)
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
      ar1.length ==> 3
     /* ar12.length ==> 5
      ar12(4).i ==> 5
      ai12.length ==> 6
      ai12(0) ==> 1
      ai12(5) ==> 6
      am1(1) ==> 20*/
     // assert(am1.array.isInstanceOf[Array[Int]])
      //am2(2).i ==> 35
     // assert(am3.array.isInstanceOf[Array[Double]])
    //  am3(0) ==> 7.5
     // assert(am4.array.isInstanceOf[Array[Long]])
    }
  }
}