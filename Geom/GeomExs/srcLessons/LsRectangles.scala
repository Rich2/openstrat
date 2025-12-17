/* Copyright 2025 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

object LsRectangles  extends LessonStatic
{ val title = "Rectangles"
  val circ1 = Circle(50)
  val circ2 = Circle(70)
  val l1: List[Circle] = List(circ1, circ2)
  val l2 = l1.slate(50, 70)
  val l3 = l1.draw(2, Black)
  val s1 = Sqlign(400, 600, 0)
  
  val output = RArr(Rectangle(200, 100, 50.degsVec).fill(Green), s1.draw()) ++ Cross.diagRectangles(300, 10).fill(Black) ++ s1.diagRectangles(10).fill(Violet)

  val bodyStr: String = """Rectangles.""".stripMargin
}