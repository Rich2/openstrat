/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

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
      ShapeGenOld(LineTail(pt1), ArcTail(arcCentre, pt2), ArcTail(arcCentre, pt3), LineTail(pt4), BezierTail(ctrl1, ctrl2, pt5)).fill(Pink),
      TextGraphic("pt1", 16, pt1),
      TextGraphic("arcCentre", 16, arcCentre),
      TextGraphic("pt2", 16, pt2),
      TextGraphic("pt3", 16, pt3),
      TextGraphic("pt4", 16, pt4),
      TextGraphic("ctrl1", 16, ctrl1),
      TextGraphic("ctrl2", 16, ctrl2),
      TextGraphic("pt5", 16, pt5),
      )   
}