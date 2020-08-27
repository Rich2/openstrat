/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait RectangleGraphic extends ShapeGraphicOld with Rectangular
{ override def shape: Rectangle
  def xAttrib = XANumeric("x", xCen)
  def yAttrib = XANumeric("y", yCen)
  def widthAttrib = XANumeric("width", width)
  def heightAttrib = XANumeric("height", height)
}
