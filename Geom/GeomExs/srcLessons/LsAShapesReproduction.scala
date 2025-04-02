/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

object LsAShapesReproduction extends LessonStatic
{ override def title: String = "Shapes Reproduction"
  override def bodyStr: String = "Shapes Reproduction"

  val pt1 = Pt2(-200, 200)
  val arcCentre = Pt2(0, 200)
  val pt2 = Pt2(0, 400)
  val pt3 = Pt2(200, 200)
  val pt4 = Pt2(200, -200)
  val ctrl1 = Pt2(150, -125)
  val ctrl2 = Pt2(-175, -250)
  val pt5 = Pt2(-200, -200)

  override def output: GraphicElems = RArr(
      //A shape is just a closed sequence of curve segments */
      ShapeGenOld(LineTail(pt1), ArcTail(arcCentre, pt2), ArcTail(arcCentre, pt3), LineTail(pt4), BezierTail(ctrl1, ctrl2, pt5)).fill(Pink),
      TextFixed("pt1", 16, pt1),
      TextFixed("arcCentre", 16, arcCentre),
      TextFixed("pt2", 16, pt2),
      TextFixed("pt3", 16, pt3),
      TextFixed("pt4", 16, pt4),
      TextFixed("ctrl1", 16, ctrl1),
      TextFixed("ctrl2", 16, ctrl2),
      TextFixed("pt5", 16, pt5),
      )   
}