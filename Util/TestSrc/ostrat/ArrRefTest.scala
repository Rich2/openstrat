/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object ArrRefTest extends TestSuite
{
  trait MyT { def i: Int }
  case class MyA(i: Int) extends MyT
  case class MyB(i: Int) extends MyT

  object MyT
  {
    implicit val eqTImplicit: EqT[MyT] = (m1, m2) => (m1, m2)match
    { case (MyA(i1), MyA(i2)) => i1 == i2
      case (MyB(i1), MyB(i2)) => i1 == i2
      case _ => false
    }
  }

  val tests = Tests {
    val myAArr1: Arr[MyA] = Arr(MyA(1), MyA(2), MyA(3))
    val myBArr1: Arr[MyB] = Arr(MyB(4), MyB(5))
    val myTArr1: Arr[MyT] = myAArr1 ++ myBArr1
    val ints1 = myTArr1.map(_.i * 10)
    val ints2: IntArr = IntArr(5, 6, 7)
    val myTArr2: Arr[MyT] = ints2.flatMap(i => Arr(MyA(i), MyB(i)))
    val myRefs1 = ints2.map(MyA(_))
    val refs2: Arr[MyA] = ints2.map(MyA(_))
    val ints3: IntArr = refs2.map(_.i)

    test("map")
    { myAArr1(2) ==> MyA(3)
      myTArr1.ssLength ==> 5
      ints1(4) ==> 50
      myTArr2.ssLength ==> 6
      myTArr2(0) ==> MyA(5)
      myTArr2(5) ==> MyB(7)
      myRefs1(1) ==> MyA(6)
      refs2(2) ==> MyA(7)
      ints3(2) ==> 7
    }
    val a1 = MyA(2)
    val b1 = MyB(3)
    val b2 = MyB(7)
    val b3 = MyB(7)
    val arr1: Arr[MyT] = Arr(a1, b1, b2)
    val arr2: Arr[MyT] = Arr(a1, b1, b2)
    val arr3: Arr[MyT] = Arr(a1, b1, b3)
    val arr4: Arr[MyT] = Arr(a1, b2, b3)
    test("Equality")
    {
      (arr1 === arr2) ==> true
      (arr1 === arr3) ==> true
      (arr1 === arr4) ==> false
    }

    val rs1 = "Seq[Int](5; 6; 7)"

    test("Persist")
    {
      //ints2.str ==> rs1
      //rs1.findType[Ints] ==> Good(ints2)
    }
  }
}
