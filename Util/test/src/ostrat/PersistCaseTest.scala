package ostrat
import utest._

object PersistCaseTest extends TestSuite
{
  case class Ma2(i1: Int, s1: String)
  object Ma2
  { implicit val persist: Persist[Ma2] = Persist2[Int, String, Ma2]("Ma2", m => (m.i1, m.s1), apply)
  }

  case class Mb2(i1: Int, pi: (Int, Int))
  object Mb2
  { implicit val persist: Persist[Mb2] = Persist2[Int, (Int, Int), Mb2]("Mb2", m => (m.i1, m.pi), apply)
  }
  
  case class My3(s1: String, i1: Int, d1: Double)
  object My3
  { implicit val persist: Persist[My3] = Persist3[String, Int, Double, My3]("My3", m => (m.s1, m.i1, m.d1), apply)
  }

  case class My4(a1: Arr[Int], s2: String, l3: List[String], v4: Vector[Int])
  object My4
  { implicit val persist: Persist[My4] = Persist4[Arr[Int], String, List[String], Vector[Int], My4]("My4", m => (m.a1, m.s2, m.l3, m.v4), apply)
  }
  val my4a = My4(Arr(3, 2), "Yes", "AAA" :: Nil, Vector(8))
  deb(my4a.str)
  
  val tests = Tests
  {    
    'Persist2 -
    {
      Ma2(4, "a").str ==> """Ma2(4; "a")"""
      """Ma2(2; "er")""".findType[Ma2] ==> Good(Ma2(2, "er"))
      Mb2(4, (5, 6)).str ==> """Mb2(4; 5, 6)"""
    }

    'Persist3 -
    { My3("Hi", -3, -3.4).str ==> """My3("Hi"; -3; -3.4)"""
    }

    'Persist4 -
    {
      my4a.str ==> """My4(3, 2; "Yes"; "AAA",; 8,)"""
    }
  }
}