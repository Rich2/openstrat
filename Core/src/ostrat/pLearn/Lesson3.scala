/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pLearn
import geom._, pCanv._, Colour._

case class Lesson3 (canv: CanvasPlatform) extends CanvasSimple
{
  canv.shapeFill(Red, LineSeg(-200, 200), LineSeg(200, 200), LineSeg(200, -200), LineSeg(-200, -200))

}