/* Copyright 2018-23 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

object LsA3a
{
  val r1: Rectangle = Rect(200, 150)
  val r1f: PolygonFill = r1.fill(Colour.Khaki)
  val r2: Rectangle = r1.slateXY(-200, 150)
  
  val rf2 = r2.fill(Colour.Gray)
  val c1: CircleFill = Circle(100).fill(Green)
  val c1d: CircleDraw = c1.scale2.toDraw()

  val circles: RArr[ShapeGraphicSimple with CanvElem] = RArr(r1f, rf2, Circle(100, 0, 200).fill(Violet), Circle(100, 200, 0).fill(SandyBrown),
    Circle(100, 0, -200).fill(Turquoise), c1d)
}

case class LsA3a(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{ repaint(LsA3a.circles)
}