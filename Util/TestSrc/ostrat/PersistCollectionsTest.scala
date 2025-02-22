/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object PersistCollectionsTest  extends TestSuite
{ 
  val tests = Tests {
    val l1 = List(-1, -2, -30)
    val l1Comma: String = "-1, -2, -30"
    val l2: List[Int] = List(4, 5, 6)
    val l2Comma: String = "4, 5, 6"
    val ll: List[List[Int]] = List(l1, l2)

    test("Depth")
    { l2.showDepth ==> 2
      ll.showDepth ==> 3
    }
    
    test("List1")
    { l1.str ==> "Seq(-1; -2; -30)"
      l1.strSemi.enCurly ==> "-1; -2; -30".enCurly
      l1.strComma ==> l1Comma
      l1.strTyped ==> "Seq[Int](-1; -2; -30)"
      l2.strComma ==> l2Comma
      ll.str ==> "Seq(" + l1Comma + "; " + l2Comma + ")"
      ::(4, Nil).str ==> "Seq(4)"
    }
    val s2 = "Seq(1; 2; 3)"
    
    test("List2")
    { "Seq(1; 2; 3)".asType[List[Int]] ==> Succ(List(1, 2, 3))
      "Seq[1; 2; 3]".asType[List[Int]].isFail ==> true
      "What(1; 2; 3)".asType[List[Int]].isFail ==> true
      "Seq[Int](1; 2; 3)".findType[List[Int]] ==> Succ(List(1, 2, 3))

      s2.findType[List[Double]] ==> Succ(List(1.0, 2, 3))
      s2.findType[List[Int]] ==> Succ(List(1, 2, 3))
      s2.findType[Seq[Int]] ==> Succ(Seq(1, 2, 3))
      s2.findType[Vector[Int]] ==> Succ(Vector(1, 2, 3))
      //"Seq()".findType[Nil.type] ==> Succ(Nil)
    }

    val lts: List[List[Int]] = List(List(1, 2), List(10, 11))

    test("List[List]")
    {
      lts.strSemi ==> "1, 2; 10, 11"
    }
  }
}