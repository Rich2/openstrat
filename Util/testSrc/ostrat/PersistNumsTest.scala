/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object PersistNumsTest  extends TestSuite
{
  val tests = Tests {
    "Ints" -
    {
      5.str ==> "5"
      "5".findType[Int] ==> Good(5)
      "228".asInt ==> Good(228)
      "-228".intAtStsIndex(0) ==> Good(-228)
      (-86).str ==> "-86"
      (-86).strComma ==> "-86"
      (-86).strTyped ==> "Int(-86)"
      "7".findType[Int] ==> Good(7)
      "7".findType[Double] ==> Good(7)
    }

    "Doubles" -
     {
       23.4.str ==> "23.4"
       8.0.str ==> "8"
       9.12345.show(ShowStandard, 0, 0) ==> "9"
       9.12345.show(ShowStandard, 1, 0) ==> "9.1"
       9.12345.show(ShowStandard, 4, 0) ==> "9.1234"
       4.3234.show(ShowStandard, 6, 6) ==> "4.323400"
       234.568.show(ShowStandard, -1, 4) ==> "234.5680"
       (8.0).strTyped ==> "DFloat(8)"
       (-6.00).str ==> "-6"
     }
  }
}
