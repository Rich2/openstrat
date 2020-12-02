/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait EArcDraw extends CurveSegDraw
{

}

object EArcDraw
{
  case class EArcDrawImp(arc: EArc, colour: Colour, lineWidth: Double)
}