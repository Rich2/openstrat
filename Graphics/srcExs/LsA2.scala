/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** Lesson A2. */
case class LsA2(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A2")
{
  val sq0 = Sqlign(100).fill(Red)
  val sq1 = Square(100 / 2.sqrt, 45.degs).fill(Pink)
  val sq2 = Sqlign(100, -100, 100).fill(Orange)
  val sq3 = Square(100, 20.degs, -200, 100).fill(Colour.Sienna)
  val rg: RectangleFill = Rect(200, 100, 100, 50).fill(Green)
  val rd: RectangleDraw = Rect(200, 100, 100, 160).draw()
  val pr: Polygon = Polygon(100 pp -100, 385 pp -100, 385 pp -200, 100 pp -200)
  val ls = LineSeg(100, -220, 500, -310)
  val prr = pr.reflect(ls)
  repaints(
    Triangle(-100 pp 0, 0 pp -200, -300 pp -400).fill(Violet),
    rg, rd, sq0, sq1, sq2, sq3, pr.draw(), ls.draw(DarkGreen), prr.draw(Brown),
  )
}