/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must
 *  implement the single method fTrans(f: Vec2 => Vec2): T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T.  */
trait AffineElem extends TransSimer with TransElem
{ type AlignT <: AffineElem
  def fTrans(f: Vec2 => Vec2): AlignT
  def slate(offset: Vec2): AlignT = fTrans(_ + offset)
  def scale(operand: Double): AlignT = fTrans(_ * operand)

  /** A generalised shear transformation. I think this is correct. */
  def shear(xScale: Double, yScale: Double): AffineElem = ??? // fTrans(v => v.x * yScale vv v.y * xScale)

  def mirrorXOffset(yOffset: Double): AlignT = fTrans(_.mirrorXOffset(yOffset))
  def mirrorYOffset(xOffset: Double): AlignT = fTrans(_.mirrorYOffset(xOffset))
  def rotateRadians(radians: Double): AlignT = fTrans(_.rotateRadians(radians))
  def mirror(v1: Vec2, v2: Vec2): AlignT = fTrans(_.mirror(v1, v2))

  override def mirror(line: Line2): AlignT = fTrans((_.mirror(line)))

  override def mirrorX: AlignT = fTrans(_.mirrorX)

  override def mirrorY: AlignT = fTrans(_.mirrorY)

  override def prolign(matrix: ProlignMatrix): AlignT = fTrans(_.prolignTrans(matrix))

  /** Rotates 90 degrees or Pi / 2 radians anticlockwise. */
  override def rotate90: AlignT = fTrans(_.rotate90)

  /** Rotates 180 degrees or + Pi radians. */
  override def rotate180: AlignT = fTrans(_.rotate180)

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: AlignT = fTrans(_.rotate270)
}