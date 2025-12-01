/* Copyright 2025 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

object LsTriangles  extends LessonStatic
{ val title = "Triangles"

  val equi1 = TriEquiParrX(0, 300, 700, true)
  val equi2 = TriEquiParrX(equi1.v0y, -700, -300, false)
  val rhom = Polygon(equi1.v2, equi1.sd0Cen, equi2.v1)
  val output = RArr(
    TriIsosParrX(600, -200, 200, 0).fill(Violet),
    TriIsosParrX(0, -200, 200, 600).fill(DarkGray),
    equi2.fill(Gold),
    equi1.fill(Blue),
    //rhom.draw()
  ) ++ equi1.vertsTextArrows ++ equi2.vertsTextArrows ++ equi1.sides.iFlatMap { (i, sd) => sd.midPt.textArrowToward(equi1.cenPt, "Sd" + i.str) }

  val bodyStr: String = """Triangles.""".stripMargin
}