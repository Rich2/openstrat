/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LsA7(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A7")
{
  val pt1 = -200 pp 200
  val arcCentre = 0 pp 200
  val pt2 = 0 pp 400
  val pt3 = 200 pp 200
  val pt4 = 200 pp -200
  val ctrl1 = 150 pp -125
  val ctrl2 = -175 pp -250
  val pt5 = -200 pp -200
  
  repaints(
      //A shape is just a closed sequence of curve segments */
      PolyCurve(LineTail(pt1), ArcTail(arcCentre, pt2), ArcTail(arcCentre, pt3), LineTail(pt4), BezierTail(ctrl1, ctrl2, pt5)).fill(Pink),
      TextGraphic("pt1", pt1, 16),
      TextGraphic("arcCentre", arcCentre, 16),
      TextGraphic("pt2", pt2, 16),
      TextGraphic("pt3", pt3, 16),
      TextGraphic("pt4", pt4, 16),
      TextGraphic("ctrl1", ctrl1, 16),
      TextGraphic("ctrl2", ctrl2, 16),
      TextGraphic("pt5", pt5, 16),
      )   
}