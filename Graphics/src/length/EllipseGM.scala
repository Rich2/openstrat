/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** An ellipse defined in mega metres or millions of Kilometres. Not sure whether this class justifies its weight in code. Might be useful for
 * Solar System app. */
case class EllipseGM(xCen: GMetres, yCen: GMetres, xAxes1: GMetres, yAxes1: GMetres, radius2: GMetres)
{
  def toScalar(scale: Length): Ellipse = Ellipse.cenAxes1Radius2(xCen / scale, yCen /scale, xAxes1 / scale, yAxes1 / scale, radius2 / scale)
}
