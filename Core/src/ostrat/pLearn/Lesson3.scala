/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pLearn
import geom._, pCanv._, Colour._

case class Lesson3 (canv: CanvasPlatform) extends CanvasSimple
{
  val pt1 = -200 vv 200
  val arcCentre = 0 vv 200
  val pt2 = 0 vv 400
  val pt3 = 200 vv 200
  val pt4 = 200 vv -200
  val ctrl1 = 150 vv -125
  val ctrl2 = -175 vv -250
  val pt5 = -200 vv -200
  //A shape is just a closed sequence of curve segments */
  canv.shapeFill(Pink,
      LineSeg(pt1), ArcSeg(arcCentre, pt2), ArcSeg(arcCentre, pt3), LineSeg(pt4), BezierSeg(ctrl1, ctrl2, pt5))
  canv.textGraphic(pt1, "pt1", 16)
  canv.textGraphic(arcCentre, "arcCentre", 16)
  canv.textGraphic(pt2, "pt2", 16)
  canv.textGraphic(pt3, "pt3", 16)
  canv.textGraphic(pt4, "pt4", 16)
  canv.textGraphic(ctrl1, "ctrl1", 16)
  canv.textGraphic(ctrl2, "ctrl2", 16)
  canv.textGraphic(pt5, "pt5", 16)
}