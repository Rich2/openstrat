/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pgui._, Colour._

case class LsA9(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A9")
{
  def bd(c1: Pt2, c2: Pt2, colour: Colour) = Bezier(Pt2Z, c1, c2, 500 pp 350).draw(3, colour)
  //This can be more elegantly expressed in dotty
  def fun(a: Int, b: String, c: Double, d: Boolean): Int = a + b.length + c.toInt + (if (d) 1 else 0)
  val pt1 = 500 pp - 400
  val sh1 = ShapeGenOld(LineTail(Pt2Z), LineTail(200 pp 0), BezierTail(300 pp 300, 350 pp 100, pt1), LineTail(100 pp -200))
  
  def stuff = RArr(
         bd(-100 pp 200, 300 pp 400, Green),
         bd(-150 pp -50, 250 pp 350, Violet),
         bd(-250 pp 50, 200 pp 400, Orange),
         bd(-300 pp 100, 200 pp 0, Pink),
         PolyCurveFill(sh1, Yellow),
         TextGraphic(pt1.toString, 12, pt1),
         )
   repaint(stuff)
}