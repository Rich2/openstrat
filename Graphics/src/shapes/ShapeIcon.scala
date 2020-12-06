/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait ShapeIcon
{ type ShapeT <: Shape
  /** Scale the ShapeIcon up and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  def reify(scale: Double, xCen: Double, yCen: Double): ShapeT

  /** Scale the ShapeIcon up and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  def reify(scale: Double, cen: Pt2 = Pt2Z): ShapeT
  
  def fill(colour: Colour): ShapeGraphicIcon
}

trait ShapeGraphicIcon
{
  /** Scale the ShapeGraphicIcon up and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning)
   * it. */
  def reify(scale: Double, xCen: Double, yCen: Double): ShapeGraphic

  /** Scale the ShapeGraphicIcon up and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning)
   * it. */
  def reify(scale: Double, cen: Pt2): ShapeGraphic
}

trait ShapeFillIcon extends ShapeGraphicIcon
{
  def fillColour: Colour
  /** Scale the ShapeFillIcon up and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning)
   * it. */
  def reify(scale: Double, xCen: Double, yCen: Double): ShapeFill

  /** Scale the ShapeFillIcon up and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning)
   * it. */
  def reify(scale: Double, cen: Pt2): ShapeFill
}
