/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat.*, geom.*, Colour.*

object LsABeziers extends LessonStatic
{ override def title: String = "Beziers"
  override def bodyStr: String = "Beziers"

  def bd(xC1: Double, yC1: Double, xC2: Double, yC2: Double, colour: Colour) = Bezier(0,0, xC1, yC1, xC2, yC2, 500,350).draw(3, colour)
  //This can be more elegantly expressed in dotty
  def fun(a: Int, b: String, c: Double, d: Boolean): Int = a + b.length + c.toInt + (if (d) 1 else 0)
  val pt1 = Pt2(500, - 400)
  val sh1 = ShapeGenOld(LineTail(Pt2Z), LineTail(200, 0), BezierTail(Pt2(300, 300), Pt2(350, 100), pt1), LineTail(100, -200))

  override def output: GraphicElems = RArr(
    bd(-100,200, 300,400, Green),
    bd(-150,-50, 250,350, Violet),
    bd(-250,50, 200,400, Orange),
    bd(-300,100, 200,0, Pink),
    PolyCurveFill(sh1, Yellow),
    TextFixed(pt1.toString, 12, pt1),
  )
}