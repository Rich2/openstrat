/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA4(canv: CanvasPlatform) extends CanvasSimple("Lesson A4")
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
      TextGraphic(pt1, "pt1", 16),
      TextGraphic(arcCentre, "arcCentre", 16),
      TextGraphic(pt2, "pt2", 16),
      TextGraphic(pt3, "pt3", 16),
      TextGraphic(pt4, "pt4", 16),
      TextGraphic(ctrl1, "ctrl1", 16),
      TextGraphic(ctrl2, "ctrl2", 16),
      TextGraphic(pt5, "pt5", 16),
      )   
}