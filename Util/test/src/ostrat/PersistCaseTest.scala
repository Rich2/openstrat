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
  
  case class Ma3(s1: String, i1: Int, d1: Double)
  object Ma3
  { implicit val persist: Persist[Ma3] = Persist3[String, Int, Double, Ma3]("Ma3", m => (m.s1, m.i1, m.d1), apply)
  }

  case class Mb3(i1: Int = 1, i2: Int = 2, i3: Int = 3)
  object Mb3
  { implicit val persist: Persist[Mb3] = Persist3[Int, Int, Int, Mb3]("Mb3", m => (m.i1, m.i2, m.i3), apply, Some(3), Some(2), Some(1))
  }

  case class My4(a1: Arr[Int], s2: String, l3: List[String], v4: Vector[Int])
  object My4
  { implicit val persist: Persist[My4] = Persist4[Arr[Int], String, List[String], Vector[Int], My4]("My4", m => (m.a1, m.s2, m.l3, m.v4), apply)
  }
  val my4a = My4(Arr(3, 2), "Yes", "AAA" :: Nil, Vector(8))
  
  val tests = Tests
  {    
    'Persist2 -
    {
      Ma2(4, "a").str ==> """Ma2(4; "a")"""
      """Ma2(2; "er")""".findType[Ma2] ==> Good(Ma2(2, "er"))
      Mb2(4, (5, 6)).str ==> """Mb2(4; 5, 6)"""
    }

    val p3r1 = "Mb3(2; 3)"
    val p3r2 = "Mb3(2)"
    val p3r3 = "Mb3()"
    'Persist3 -
    { Ma3("Hi", -3, -3.4).str ==> """Ma3("Hi"; -3; -3.4)"""
      Mb3(2, 3, 4).str ==> """Mb3(2; 3; 4)"""
      Mb3(2, 3, 3).str ==> p3r1
      p3r1.findType[Mb3] ==> Good(Mb3(2, 3, 3))
      Mb3(2, 2, 3).str ==> p3r2
      p3r2.findType[Mb3] ==> Good(Mb3(2, 2, 3))
      Mb3(1, 2, 3).str ==> p3r3
      p3r3.findType[Mb3] ==> Good(Mb3(1, 2, 3))
    }

    'Persist4 -
    {
      my4a.str ==> """My4(3, 2; "Yes"; "AAA",; 8,)"""
    }
  }
}