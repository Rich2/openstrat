package ostrat
import utest._

object ArrRefTest extends TestSuite
{
  val tests = Tests
  {
    trait MyT { def i: Int }
    case class MyA(i: Int) extends MyT
    case class MyB(i: Int) extends MyT

    val myAArr1: Refs[MyA] = Refs(MyA(1), MyA(2), MyA(3))
    val myBArr1: Refs[MyB] = Refs(MyB(4), MyB(5))
    val myTArr1: Refs[MyT] = myAArr1 -+ myBArr1
    val ints1 = myTArr1.map(_.i * 10)
    val ints2 = Ints(5, 6, 7)
    val myTArr2: Refs[MyT] = ints2.flatMap(i => Refs(MyA(i), MyB(i)))
    val myRefs1 = ints2.map(MyA(_))
    val refs2: Refs[MyA] = ints2.map(MyA(_))
    val ints3: Ints = refs2.map(_.i)

    'test1
    { myAArr1(2) ==> MyA(3)
      myTArr1.length ==> 5
      ints1(4) ==> 50
      myTArr2.length ==> 6
      myTArr2(0) ==> MyA(5)
      myTArr2(5) ==> MyB(7)
      myRefs1(1) ==> MyA(6)
      refs2(2) ==> MyA(7)
      ints3(2) ==> 7
    }

    'Persist
    {
      //ints2.str ==> ???
    }
  }
}
