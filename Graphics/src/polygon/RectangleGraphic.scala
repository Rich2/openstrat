/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait RectangleGraphic extends ShapeGraphicOld with Rectangular
{ override def shape: Rectangle
  def xAttrib = NumericAttrib("x", xCen)
  def yAttrib = NumericAttrib("y", yCen)
  def widthAttrib = NumericAttrib("width", width)
  def heightAttrib = NumericAttrib("height", height)
}
