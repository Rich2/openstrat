package ostrat
import utest._

object ArrOffTest  extends TestSuite
{
  implicit val array: Array[String] = Array("a1", "a2", "a3", "a4")
  val o1 = new ArrOff[String](0)

  val r1 = o1 match
  { case ArrOff1("a1", _) => true
    case _ => false
  }

  val o2 = o1.drop1

  val r2 = o2 match
  { case ArrOff1("a2", _) => true
    case _ => false
  }

  val r2a = o2 match
  { case ArrOff2("a2", "a3", _) => true
    case _ => false
  }

  val r3 = o2 match
  { case ArrOff1("a2", off) => off
    case _ => o1
  }

  val r4 = r3.drop2

  val r5 = r4 match
  { case ArrOff0(_) => true
    case _ => false
  }

  val tests = Tests
  {
    'test1
    { r1 ==> true
      r2 ==> true
      r2a ==> true
      r3.offset ==> 2
      r4.length ==> 0
      r5 ==> true
    }
  }
}
