/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA6(canv: CanvasPlatform) extends CanvasSimple("Lesson A6")
{
  val pt1 = -200 vv 200
  val arcCentre = 0 vv 200
  val pt2 = 0 vv 400
  val pt3 = 200 vv 200
  val pt4 = 200 vv -200
  val ctrl1 = 150 vv -125
  val ctrl2 = -175 vv -250
  val pt5 = -200 vv -200  
  
  repaints(
      //A shape is just a closed sequence of curve segments */
      Shape(LineSeg(pt1), ArcSeg(arcCentre, pt2), ArcSeg(arcCentre, pt3), LineSeg(pt4), BezierSeg(ctrl1, ctrl2, pt5)).fill(Pink),
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