/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait CurveSegTail
{
  def untail(xEnd: Double, yEnd: Double): CurveSeg
}

class CArcTail(val xCen: Double, val yCen: Double, val xEnd: Double, val yEnd: Double, val counter: Int) extends CurveSegTail
{
  override def untail(xEnd: Double, yEnd: Double): CArc = ???
}