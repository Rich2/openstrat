package ostrat
import utest._

/** Test object for our own immutable wrapper  Types have been deliberatly left unannotated to test demonstrated type inference.*/
object ArrOtherTest extends TestSuite
{
  val tests = Tests
  {
    val intArr1 = Ints(1, 2, 3, 4)
    val intArr2 = Ints(5, 6, 7)
    val intArr3 = intArr1 ++ intArr2
    val dblArr1 = Dbls(1.5, 3, 4.5, 6)
    val dblArr2 = intArr1.map(_ * 1.5)
    val dblList1 = intArr1.seqMap{a => List(a + 0.1, a + 0.2)}
    val intArr4 = intArr1.bind(a => Ints(a + 10, a + 20, a + 30))
    val dblArr3 = intArr2.bind(i => Dbls(i, i * 0.5))

    'test1 -
    { intArr1(3) ==> 4
      intArr3.length ==> 7
      intArr3(6) ==> 7
      dblArr1(2) ==> 4.5
      dblArr2(0) ==> 1.5
      dblArr2.length ==> 4
      dblList1(0) ==> 1.1
      dblList1.length ==> 8
      intArr4(1) ==> 21
      intArr4(5) ==> 32
      intArr4.length ==> 12
      dblArr3(1) ==> 2.5
    }


  }
}