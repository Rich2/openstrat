/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
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

    test("Depth"){
      l2.showDepth ==> 2
      ll.showDepth ==> 3
    }
    
    test("List1")
    { l1.str ==> "Seq(-1; -2; -30)"
      l1.strSemi ==> "-1; -2; -30"
      l1.strComma ==> l1Comma
      l1.strTyped ==> "Seq[Int](-1; -2; -30)"
      l2.strComma ==> l2Comma
      ll.str ==> "Seq(" + l1Comma + "; " + l2Comma + ")"
      ::(4, Nil).str ==> "Seq(4)"
    }
    val s2 = "Seq(1; 2; 3)"
    
    test("List2")
    { "Seq(1; 2; 3)".asType[List[Int]] ==> Good(List(1, 2, 3))
      "Seq[1; 2; 3]".asType[List[Int]].isBad ==> true
      "What(1; 2; 3)".asType[List[Int]].isBad ==> true
      "Seq[Int](1; 2; 3)".findType[List[Int]] ==> Good(List(1, 2, 3))

      s2.findType[List[Double]] ==> Good(List(1.0, 2, 3))
      s2.findType[List[Int]] ==> Good(List(1, 2, 3))
      s2.findType[Seq[Int]] ==> Good(Seq(1, 2, 3))
      s2.findType[Vector[Int]] ==> Good(Vector(1, 2, 3))
      //"Seq()".findType[Nil.type] ==> Good(Nil)
    }
    /*
    val a1: Array[String] = Array("3", "4")    
    val a1Res ="Seq[Str](\"3\"; \"4\")"
    
    'Array -
    {
      a1.str ==> a1Res
    }

    'Arr -
    {
      Arr(-1, -2, -3).str ==> """Seq[Int](-1; -2; -3)"""
    }*/
  }
}