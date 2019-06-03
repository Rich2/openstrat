package ostrat
import utest._

object PersistCaseTest extends TestSuite
{
  case class My2(i1: Int, s1: String)
  object My2
  { implicit object My2Persist extends Persist2[Int, String, My2]("My2", m => (m.i1, m.s1), apply)
  }
  
  val m2a = "My2(2; \"er\")"
  
  case class My3(s1: String, i1: Int, d1: Double)
  object My3
  { implicit object My3Persist extends Persist3[String, Int, Double, My3]("My3", m => (m.s1, m.i1, m.d1), apply)
  }
  
  val tests = Tests
  {    
    'persist2 -
    {
      m2a.findType[My2] ==> Good(My2(2, "er")) 
    }
  }
}