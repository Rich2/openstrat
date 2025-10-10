/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utiljvm.*, geom.*

extension(thisCompound: RectCompound)
{
  def svgFile(dirPathStr: String, fileName: String): Unit = fileWrite(dirPathStr / fileName, thisCompound.htmlSvg.out)
  def svgFile(fileName: String): Unit = homeWrite("Temp", fileName, thisCompound.htmlSvg.out)
}