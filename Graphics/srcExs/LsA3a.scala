/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

object LsA3a
{
  val r1 = Rect(450, 200)//.slate(-200, 200)
  val r1f = r1.fill(Colour.Khaki)
  debvar(r1.rotation)
  val c1 = Circle(100).fill(Green)
  val c1d = c1.scale2.toDraw()
  val circles = Arr(r1f, c1, Circle(100, 0, 200).fill(Violet), Circle(100, 200, 0).fill(SandyBrown), Circle(100, 0, -200).fill(Turquoise), c1d) 
  
  val arr = circles
}

case class LsA3a(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{ repaint(LsA3a.arr)
}