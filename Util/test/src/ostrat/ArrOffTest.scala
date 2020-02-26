package ostrat
import utest._

object ArrOffTest extends TestSuite
{
  trait MyTr
  object A1 extends MyTr; object A2 extends MyTr; object A3 extends MyTr; object A4 extends MyTr
  implicit val myRefs: Refs[MyTr] = Refs(A1, A2, A3, A4)
  val ro: RefsOff[MyTr] = new RefsOff[MyTr](0)

  /*val o2 = o1.drop1

  val r2 = o2 match
  { case RefsOff1("a2") => true
    case _ => false
  }

  val r2a = o2 match
  { case RefsOff2("a2", "a3") => true
    case _ => false
  }

  val r3 = o2 match
  { case RefsOff1("a2") => off
    case _ => o1
  }

  val r4 = r3.drop2

  val r5 = r4 match
  { case ArrOff0(_) => true
    case _ => false
  }*/

  val tests = Tests
  {
    "Test1" -
    {
     // assertMatch(ro){ case RefsOff1(A1) => }
      //r2 ==> true
      //r2a ==> true
      //r3.offset ==> 2
      //r4.length ==> 0
      //r5 ==> true
    }
  }
}
