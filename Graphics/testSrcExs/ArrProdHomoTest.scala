/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object ArrProdHomoTest  extends TestSuite
{
  val tests = Tests
  {
    val dbls1 = Dbls(1, 2, 3, 4)
    //val ds1 = dbls1.str
    val mines1 = dbls1.map(d => MyDbl2(d, d * 2))
    val mines2 = dbls1.flatMap(d => MyDbl2s(MyDbl2(d, d + 0.5), MyDbl2(d * 2, d * 2)))
    val str1 = mines2.str

    "test1" -
      { dbls1(3) ==> 4
        mines1(3) ==> MyDbl2(4, 8)
        mines2(0) ==> MyDbl2(1, 1.5)
        MyDbl2(3, 4).isInstanceOf[AnyRef] ==> true
        //str1 ==> "Mines(1, 1.5; 2, 2; 2, 2.5; 4, 4; 3, 3.5; 6, 6; 4, 4.5; 8, 8)"
      }
  }
}