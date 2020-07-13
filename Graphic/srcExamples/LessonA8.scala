/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA8(canv: CanvasPlatform) extends CanvasNoPanels("lesson A7")
{
  //Lets copy the values across form the previous lesson.
  val pt1 = -200 vv 200
  val arcCentre = 0 vv 200
  val pt2 = 0 vv 400
  val pt3 = 200 vv 200
  val pt4 = 200 vv -200
  val ctrl1 = 150 vv -125
  val ctrl2 = -175 vv -250
  val pt5 = -200 vv -200

  //But this time we are going to create an intermediate shape.
  val shape1: PolyCurve = PolyCurve(LineTail(pt1), ArcTail(arcCentre, pt2), ArcTail(arcCentre, pt3), LineTail(pt4), BezierTail(ctrl1, ctrl2, pt5))
  val sf1 = PolyCurveFill(shape1.slate(400 vv 100), Violet)
  val sf2 = PolyCurveDraw(shape1.clk45, 2)
  val sf3 = sf2.scale(0.5)
  val sf4 = sf3.slate(-250, 200)
  val sf5 = sf4.slateX(-100).copy(colour = Green)  
  val rect = Rectangle(200, 100, -400 vv 100)
  canv.polyFill(rect, Orange)//Not sure why this code is in here.
  val sf6 = sf5.negY.copy(colour = Red)
  repaints(sf1, sf2, sf3, sf4, sf5, sf6)
}