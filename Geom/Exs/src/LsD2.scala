/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._

/** D Series lessons deal with persistence */
case class LsD2(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D2")
{
  val tStr = """2.0;
     "Hello";
      7;
      Vec2(2.3; 3.2);
      "Goodbye" """
  val t1 = tStr.intAtStsIndex(2)
  /** Gives the same result as t1 as the Int value ar index 2 is unique. */
  val t2 = tStr.findType[Int]//Does the same as r1
  val t3 = tStr.findType[String]
  val t4 = tStr.typeAtStsIndex[String](0)//Indexs start at 0
  val t5 = tStr.typeAtStsIndex[String](1)
  val t6 = tStr.typeAtStsIndex[String](2)//Because Indexs start at 0. There is no element 2 of type String.
  val t7 = tStr.findType[Pt2]
  val t8 = tStr.dblAtStsIndex(0)// findDouble //This fails because 7 can be both an Int and a Double.
  val topStrs = Arr(t1, t2, t3, t4, t5, t6, t7, t8).map(_.toString)
  val topBlock = MText(200, topStrs)
  
  val arr = Array(4, 5, 6)
  val as = arr.str
//  val r1 = as.findType[Seq[Int]]//The default constructor for a Seq is List
  val r2 = as.findType[List[Int]]
  val r3 = as.findType[Vector[Int]]
//  val a4 = as.findType[Array[Int]]
 // val r4 = a4//toString method on Array not very helpful
  //val r5 = a4.map(_(1))
//  val r6: EMon[Int] = a4.map[Int](arr => arr(2))//This is the long explicit result.
  
 // val strs = Arr(/*r1,*/ r2, r3, r4, r5).map(_.toString)
//  val bottomBlock  = MText(-100, strs)
  
  repaint(topBlock)// ++ bottomBlock)
}