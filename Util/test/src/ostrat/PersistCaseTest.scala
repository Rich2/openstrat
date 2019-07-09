package ostrat
import utest._

object PersistCaseTest extends TestSuite
{
  case class Ma2(i1: Int, s1: String)
  object Ma2
  { implicit val persist: Persist[Ma2] = Persist2[Int, String, Ma2]("Ma2", m => (m.i1, m.s1), apply)
  }
  
  case class My3(s1: String, i1: Int, d1: Double)
  object My3
  { implicit val persist: Persist[My3] = Persist3[String, Int, Double, My3]("My3", m => (m.s1, m.i1, m.d1), apply)
  }
  
  val tests = Tests
  {    
    'persist2 -
    {
      Ma2(4, "a").str ==> """Ma2(4; "a")"""
      """Ma2(2; "er")""".findType[Ma2] ==> Good(Ma2(2, "er"))
    }
  }
}