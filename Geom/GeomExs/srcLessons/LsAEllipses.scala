/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsAEllipses extends LessonGraphics
{ override def title: String = "Ellipses"
  override def bodyStr: String = "Ellipses"
  override def canv: CanvasPlatform => Any = LsAEllipses(_)
}

/** Lesson A3. Unfinished. */
case class LsAEllipses(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{ val x1 = 100
  val y1 = 200
  val c0: Ellipse = Circle(30)
  val d0 = c0.slateX(50)
  val e0: Ellipse = c0.scaleXY(2, 3)
  val a1 = RArr(c0, d0).scaleXY(2, 3)
  
  val c1 = Circle(100).fill(Green)

  val c1d = c1.scale2.toDraw()
  val circles: RArr[CircleGraphicSimple & CanvElem] =
    RArr(c1, Circle.d(100, 0, 200).fill(Violet), Circle.d(100, 200, 0).fill(SandyBrown), Circle.d(100, 0, -200).fill(Turquoise), c1d)
  val crosses =  Pt2Arr.dbls(0,0, -100,0, 100,0, 0,100).flatMap(Cross.diag(_)).draw()

  val el1: EllipseFill = Ellipse(200, 100, -300, 0).fill(Red)
  val el2 = Ellipse(50, 100, 150, 200).fill(Colour.BurlyWood)
  val el3 = el2.slateX(100).toDraw(2)

  val elTopRight = el1.boundsTRTo(canv.topRight)
  val els = RArr(el1, el2, el3, elTopRight)

  repaint(circles +% crosses ++ els +% c1d)
}