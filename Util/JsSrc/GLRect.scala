/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import geom._

case class GlRect(x: Double, y: Double, width: Double, height: Double, colour: Colour)

case class GlVert(x: Double, y: Double, colour: Colour)

case class VMain(mems: Seq[Any])
{
   def out(indent: Int = 0): String = ""
}