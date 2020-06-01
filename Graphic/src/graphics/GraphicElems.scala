/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A GraphicElem is either an element that can be rendered to a display (or printed) or is an active element in display or both. */
trait GraphicElem extends TransElem
{/** Translate geometric transformation. */
  def slate(offset: Vec2): GraphicElem
 
 /** Translate geometric transformation. */
 def slate(xOffset: Double, yOffset: Double): GraphicElem
  
  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): GraphicElem
  
  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): GraphicElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): GraphicElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorX: GraphicElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorY: GraphicElem

  def prolign(matrix: ProlignMatrix): GraphicElem

  /** Rotates 90 degrees rotate-clockwise or + Pi/2 */
  def rotate90: GraphicElem

  def rotate180: GraphicElem
}

object GraphicElem
{
  implicit val slateImplicit: Slate[GraphicElem] = (obj: GraphicElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[GraphicElem] = (obj: GraphicElem, operand: Double) => obj.scale(operand)

  implicit val mirrorAxisImplicit: MirrorAxis[GraphicElem] = new MirrorAxis[GraphicElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: GraphicElem, yOffset: Double): GraphicElem = obj.mirrorXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: GraphicElem, xOffset: Double): GraphicElem = obj.mirrorYOffset(xOffset)
  }

  implicit val prolignImplicit: Prolign[GraphicElem] = (obj, matrix) => obj.prolign(matrix)
}
/** This trait is slated for removal. */
trait GraphicElemOld extends TransSimer with GraphicElem
{ type AlignT <: GraphicElemOld

  override def slate(offset: Vec2): AlignT

  override def slate(xOffset: Double, yOffset: Double): AlignT

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): AlignT

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): AlignT
}

/** The base trait for all objects on a canvas / panel. The objects are re-composed for each frame. The Canvas objects must be re-composed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicFullElem extends GraphicElem with AffineElem
{ type AlignT <: GraphicFullElem

  override def slate(offset: Vec2): AlignT

  override def slate(xOffset: Double, yOffset: Double): AlignT

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): AlignT

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def mirrorYOffset(xOffset: Double): AlignT
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBounded extends GraphicElemOld
{ type AlignT <: GraphicBounded
  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def width: Double = boundingRect.width
  
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedFull extends GraphicBounded with GraphicFullElem
{ type AlignT <: GraphicBoundedFull
  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
 // def boundingRect: BoundingRect
  //def width: Double = boundingRect.width
}

/** Base trait for all child (non Parent) Graphic elements that output to the display. */
trait PaintFullElem extends PaintElemOld with GraphicFullElem
{ type AlignT <: PaintFullElem
  //override def fTrans(f: Vec2 => Vec2): PaintFullElem

}

trait FilledElem extends PaintElem
{ def fillColour: Colour
}

trait FillElem extends FilledElem

trait Fillable extends TransElem
{ def fill(colour: Colour): FillElem
}