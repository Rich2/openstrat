/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait Coloured extends AnyRef
{ def colour: Colour
  def contrast: Colour = colour.contrast
  def colourContrast2(other: Colour): Colour = colour.contrast2(other)
  def contrastBW = colour.contrastBW
}