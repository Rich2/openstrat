/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black

case class CircleFillOld(circle: CircleOld, colour: Colour) extends TransSimerUser with GraphicElem
{ override type SimerT = CircleFillOld
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleFillOld = CircleFillOld(transer, colour)
  def shear(xScale: Double, yScale: Double): AffineElem = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillOld(this)

  override def mirrorYOffset(xOffset: Double): CircleFillOld = ???

  override def mirrorXOffset(yOffset: Double): CircleFillOld = ???

  override def mirrorX: CircleFillOld = CircleFillOld(circle.mirrorX, colour)

  override def mirrorY: CircleFillOld = ???

  override def prolign(matrix: ProlignMatrix): CircleFillOld = ???

  override def rotate90: CircleFillOld = ???

  override def rotate180: CircleFillOld = ???

  override def rotate270: CircleFillOld = ???
}

case class CircleDrawOld(circle: CircleOld, lineWidth: Double, colour: Colour) extends TransSimerUser with GraphicElem
{ override type SimerT = CircleDrawOld
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleDrawOld = CircleDrawOld(transer, lineWidth, colour)
  def shear(xScale: Double, yScale: Double): AffineElem = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDrawOld(this)

  override def mirrorYOffset(xOffset: Double): CircleDrawOld = ???

  override def mirrorXOffset(yOffset: Double): CircleDrawOld = ???

  override def mirrorX: CircleDrawOld = ???

  override def mirrorY: CircleDrawOld = ???

  override def prolign(matrix: ProlignMatrix): CircleDrawOld = ???

  override def rotate90: CircleDrawOld = ???

  override def rotate180: CircleDrawOld = ???

  override def rotate270: CircleDrawOld = ???
}

case class CircleFillDrawOld(circle: CircleOld, fillColour: Colour, lineWidth: Double = 2.0, lineColour: Colour = Black) extends TransSimerUser
  with GraphicElem
{ override type SimerT = CircleFillDrawOld
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleFillDrawOld = CircleFillDrawOld(transer, fillColour, lineWidth, lineColour)
  override def shear(xScale: Double, yScale: Double): AffineElem = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillDrawOld(this)

  override def mirrorYOffset(xOffset: Double): CircleFillDrawOld = ???

  override def mirrorXOffset(yOffset: Double): CircleFillDrawOld = ???

  override def mirrorX: CircleFillDrawOld = ???

  override def mirrorY: CircleFillDrawOld = ???

  override def prolign(matrix: ProlignMatrix): CircleFillDrawOld = ???

  override def rotate90: CircleFillDrawOld = ???

  override def rotate180: CircleFillDrawOld = ???

  override def rotate270: CircleFillDrawOld = ???
}