/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait ShapeIcon
{ /** Scale the ShapeIcon up and position (translate) it. */
  def scaleSlate(scale: Double, xCen: Double, yCen: Double): Shape

  /** Scale the ShapeIcon up and position (translate) it. */
  def scaleSlate(scale: Double, cen: Vec2): Shape
}

