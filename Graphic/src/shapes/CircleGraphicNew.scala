/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Display capability. */
trait DispCap

case class FillCap(colour: Colour) extends DispCap
case class DrawCap(colour: Colour) extends DispCap
case class HoverCap(colour: Colour) extends DispCap

case class CircleGraphicNew(circle: Circle, caps: Arr[DispCap])
{

}
