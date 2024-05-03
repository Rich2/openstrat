/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utiljvm._, geom._

extension(thisCompound: RectCompound)
{
  def svgFile(fileName: String): Unit = homeWrite("Temp", fileName, thisCompound.htmlSvg.out())
}
