/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** Lesson A3. Unfinished. */
case class LsA3(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{
  val x1 = 100
  val y1 = 200
  val c0: Ellipse = Circle(60)
  val d0 = c0.slateX(50)
  val e0: Ellipse = c0.scaleXY(2, 3)
  val a1 = List(c0, d0).scaleXY(2, 3)
  
  val c1 = Circle(200).fill(Green)

  val c1d = c1.scale2.toDraw()
  val circles = Arr(c1, Circle(100, 0, 200).fill(Violet), Circle(100, 200, 0).fill(SandyBrown), Circle(100, 0, -200).fill(Turquoise), c1d)
  val crosses =  Pt2s(0 pp 0, -100 pp 0, 100 pp 0, 0 pp 100).flatMap(v => Cross(1, v))

  val el1 = Ellipse(200, 100, -300 pp 0).fill(Red)
  val el2 = Ellipse(50, 100, 150 pp 200).fill(Colour.BurlyWood)
  val el3 = el2.slateX(100).toDraw(2)

  val elTopRight = el1.trBoundTo(canv.topRight)
  val els = Arr(el1, el2, el3, elTopRight)

  repaint(circles ++ crosses ++ els +- c1d)
}