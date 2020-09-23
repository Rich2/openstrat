/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A circle based Graphic, may be simple or compound. */
trait CircleGraphic extends EllipseGraphic
{ override def shape: Circle
  def svgStr: String = tagVoidStr("circle", attribs)
  def circleAttribs: Arr[XANumeric] = shape.shapeAttribs
}
