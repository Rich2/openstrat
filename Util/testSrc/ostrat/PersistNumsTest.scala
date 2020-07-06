package ostrat
import utest._

object PersistNumsTest  extends TestSuite
{
  val tests = Tests
  {
    "Ints" -
    {
      5.str ==> "5"
      "5".findType[Int] ==> Good(5)
      "228".findInt ==> Good(228)
    /*  "-228".findInt ==> Good(-228)
      (-86).str ==> "-86"
      (-86).strComma ==> "-86"
      (-86).strTyped ==> "Int(-86)"*/
      "7".findType[Int] ==> Good(7)
      "7".findType[Double] ==> Good(7)
    }

    "Doubles" -
     {
       23.4.str ==> "23.4"
       val d: Double = 8
       d.strTyped ==> "DFloat(8)"
       (-6.00).str ==> "-6"
     }
  }
}
