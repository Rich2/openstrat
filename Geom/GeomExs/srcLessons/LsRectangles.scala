/* Copyright 2025 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

object LsRectangles  extends LessonStatic
{
  val title = "Rectangles"
  val output = RArr(Rectangle(200, 100, 50.degsVec).fill(Green)) ++ Cross.diagRectangles(300, 10).map(_.fill(Black))

  val bodyStr: String = """Rectangles.""".stripMargin
}