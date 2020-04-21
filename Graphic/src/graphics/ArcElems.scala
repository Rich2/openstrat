/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Circular Arc Draw PaintElem. */
final case class CArcDraw(xStart: Double, yStart: Double, xCen: Double, yCen: Double, deltaRadians: Double, lineWidth: Double = 2.0,
                          colour: Colour = Black) //extends TransSimer
{

}