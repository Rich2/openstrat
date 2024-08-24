/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A [[RectStad]] aligned to the X or Y axes. */
trait RectStad extends RectangleStad
{
  override def rotate90: RectStad = ???
  override def rotate180: RectStad = ???
  override def rotate270: RectStad = ???
}

/** A [[RectStad]] aligned to the X axis. */
/*final class RectStadX private(val x0: Double, val y0: Double, val x2: Double, val y2: Double) extends RectStad
{  override type ThisT = RectStad

//  override def typeStr: String = "Sqlign"

  override def rotate180: RectStadX = ???
  override def fill(fillColour: Colour): ShapeFill = ???
  override def fillInt(intValue: Int): ShapeFill = ???
  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): ShapeCompound = ???
  override def fillActive(fillColour: Colour, pointerID: Any): ShapeCompound = ???
  override def attribs: RArr[XmlAtt] = ???
  override def cenX: Double = (x0 + x2) / 2
  override def cenY: Double = (y0 + y2) / 2
  inline override def cen: Pt2 = Pt2(cenx, cenY)

  override def boundingRect: Rect = ???
  override def negX: RectStadX = new RectStadX(-x0, y0, -x2, y2)
  override def negY: RectStadX = new RectStadX(x0, -y0, x2, -y2)
}*/