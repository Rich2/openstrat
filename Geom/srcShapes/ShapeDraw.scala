/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** Base [[ShapeDraw]] trait for multiple geometries. */
trait ShapeGeomlessDraw
{
  /** The line width of this draw graphic */
  def lineWidth: Double

  /** The line colour of this draw graphic. */
  def lineColour: Colour
}

/** A simple no compound graphic that draws a shape. The line has a single width and colour. */
trait ShapeDraw extends ShapeGeomlessDraw, ShapeGraphicSimple
{ def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(lineWidth)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(lineColour)
  override def nonShapeAttribs: RArr[XAtt] = RArr(strokeWidthAttrib, strokeAttrib)
}

object ShapeDraw
{
  def apply(shape: Shape, colour: Colour, lineWidth: Double): ShapeDraw = ???
}

trait ShapeLen2Draw extends ShapeGeomlessDraw, ShapeLen2GraphicSimple
{ override def slate(operand: VecPtLen2): ShapeLen2Draw
  override def slate(xOperand: Length, yOperand: Length): ShapeLen2Draw
  override def slateX(xOperand: Length): ShapeLen2Draw
  override def slateY(yOperand: Length): ShapeLen2Draw
  override def scale(operand: Double): ShapeLen2Draw
}

object ShapeLen2Draw
{ /** Implicit [[SlateLen2]] type class instance / evidence for [[ShapeLen2Draw]]. */
  implicit val slateLen2Ev: SlateLen2[ShapeLen2Draw] = new SlateLen2[ShapeLen2Draw]
  { override def slateT(obj: ShapeLen2Draw, delta: VecPtLen2): ShapeLen2Draw = obj.slate(delta)
    override def slateXY(obj: ShapeLen2Draw, xDelta: Length, yDelta: Length): ShapeLen2Draw = obj.slate(xDelta, yDelta)
    override def slateX(obj: ShapeLen2Draw, xDelta: Length): ShapeLen2Draw = obj.slateX(xDelta)
    override def slateY(obj: ShapeLen2Draw, yDelta: Length): ShapeLen2Draw = obj.slateY(yDelta)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[ShapeLen2Draw]]. */
  implicit val scaleEv: Scale[ShapeLen2Draw] = (obj: ShapeLen2Draw, operand: Double) => obj.scale(operand)
}