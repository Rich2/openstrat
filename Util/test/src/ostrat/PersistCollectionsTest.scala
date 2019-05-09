/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import utest._

object PersistCollectionsTest  extends TestSuite
{ 
  val tests = Tests
  { 
    val l1 = Seq(-1, -2, -30)
    val l1Comma: String = "-1, -2, -30"
    val l2: List[Int] = List(4, 5, 6)
    val l2Comma: String = "4, 5, 6"
    val ss: Seq[Seq[Int]] = Seq(l1, l2)    
    
    'Seq -
    { l1.str ==> "Seq[Int](-1; -2; -30)"
      l1.strSemi ==> "-1; -2; -30"
      l1.strComma ==> l1Comma
      l1.strTyped ==> "Seq[Int](-1; -2; -30)"
      l2.strComma ==> l2Comma
      ss.str ==> "Seq[Seq[Int]](" + l1Comma + "; " + l2Comma + ")"
      //assert(::(4, Nil).str == "Seq(4)")
    }
    
    val a1: Array[String] = Array("3", "4")    
    val a1Res ="Seq[Str](\"3\"; \"4\")"
    
    'Array -
    {
      a1.str ==> a1Res
    }
  }
}