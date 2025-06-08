/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

/** Lesson Polygons. */
object LsPolygons extends LessonStatic
{ override def title: String = "Polygons"
  override def bodyStr: String = "Polygons"

  val sq0 = Sqlign(100).fill(Red)
  val sq1 = Square(100 / 2.sqrt, 45.degsVec).fill(Pink)
  val sq2: RectangleFill = Sqlign(100, -100, 100).fill(Orange)
  val sq3: RectangleFill = Square(100, 20.degsVec, -200, 100).fill(Colour.Sienna)
  val rg: RectangleFill = Rect(200, 100, 100, 50).fill(Green)
  val rd: RectangleDraw = Rect(200, 100, 100, 160).draw()
  val pr: Polygon = Polygon.dbls(100,-100, 385,-100, 385,-200, 100,-200)
  val ls: LSeg2 = LSeg2(100, -220, 500, -310)
  val prr = pr.reflect(ls)

  def output = RArr(
    Triangle(-100,0, 0,-200, -300,-400).fill(Violet),
    rg, rd, sq0, sq1, sq2, sq3, pr.draw(), ls.draw(lineColour = DarkGreen), prr.draw(lineColour = Brown),
  )
}