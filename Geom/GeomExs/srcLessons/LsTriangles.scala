/* Copyright 2025 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

object LsTriangles  extends LessonStatic
{ val title = "Triangles"

  val equi1 = TriEquiXlign(0, 300, 700, true)
  val equi2 = TriEquiXlign(equi1.v0y, -700, -300, false)
  val rhom = Polygon(equi1.v2, equi1.sd0Cen, equi2.v1)
  val c1 = Circle(200, 0, -250)
  val output = RArr(
    TriIsosXlign(600, -200, 200, 0).fill(Violet),
    TriIsosXlign(0, -200, 200, 600).fill(DarkGray),
    equi2.fill(Gold),
    equi1.fill(Blue),
    c1.triEquiUp.draw(),
    c1.triEquiDown.draw(2, DarkBlue),
    c1.draw(),

    //rhom.draw()
  ) ++ equi1.vertsTextArrows() ++ equi2.vertsTextArrows() ++ equi1.sidesTextArrows ++ equi2.sidesTextArrows

  val bodyStr: String = """Triangles.""".stripMargin
}