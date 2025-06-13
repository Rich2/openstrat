/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

object LsShapes extends LessonStatic
{ override def title: String = "Shape creation"
  override def bodyStr: String = "Shape creation"

  //Lets copy the values across from the previous lesson.
  val pt1 = Pt2(-200, 200)
  val arcCentre = Pt2(0, 200)
  val pt2 = Pt2(0, 400)
  val pt3 = Pt2(200, 200)
  val pt4 = Pt2(200, -200)
  val ctrl1 = Pt2(150, -125)
  val ctrl2 = Pt2(-175, -250)
  val pt5 = Pt2(-200, -200)

  //But this time we are going to create an intermediate shape.
  val shape1: ShapeGenOld = ShapeGenOld(LineTail(pt1), ArcTail(arcCentre, pt2), ArcTail(arcCentre, pt3), LineTail(pt4), BezierTail(ctrl1, ctrl2, pt5))
  val sf1 = PolyCurveFill(shape1.slate(400, 100), Violet)
  val sf2 = PolyCurveDraw(shape1.clk45, lineWidth = 2)
  val sf3 = sf2.scale(0.5)
  val sf4 = sf3.slate(-250, 200)
  val sf5 = sf4.slateX(-100).copy(colour = Green)
  val sf6 = sf5.negY.copy(colour = Red)

  val stadium = Stadium(200, 100, -200, -200).draw(Green)

  override def output: GraphicElems = RArr(sf1, sf2, sf3, sf4, sf5, sf6, stadium)
}