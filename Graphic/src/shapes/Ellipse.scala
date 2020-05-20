/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom


trait Ellipse extends Shape//Affer
{ //type AlignT = EllipseLike
  //override def shear(xScale: Double, yScale: Double): Ellipse
}

case class Ellipselign(xCen: Double, yCen: Double, horrRadius: Double, upRadus: Double) extends Ellipse
{
  override def fill(colour: Colour): GraphicElem = ???
}

