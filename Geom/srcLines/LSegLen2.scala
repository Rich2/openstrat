/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A line segment whose coordinates are specified in [[Length]] units. */
trait LSegLen2[+VT <: PtLen2] extends LSegBase[VT], DrawableLen2
{ def xStart: Length
  def yStart: Length
  def xEnd: Length
  def yEnd: Length
  def startPt: VT
  def endPt: VT
  override def slate(operand: VecPtLen2): LSegLen2[VT]  
  override def slate(xOperand: Length, yOperand: Length): LSegLen2[VT]
  override def slateX(xOperand: Length): LSegLen2[VT]
  override def slateY(xOperand: Length): LSegLen2[VT]
  def scale(operand: Double): LSegLen2[VT]  
  def mapGeom2 (operand: Length): LSeg2

  override def draw(lineWidth: Double, lineColour: Colour): LineSegLen2Draw = LineSegLen2Draw(this, lineWidth, lineColour)

  def xStartFemtometresNum: Double
  def yStartFemtometresNum: Double
  def xEndFemtometresNum: Double
  def yEndFemtometresNum: Double
  def xStartPicometresNum: Double
  def yStartPicometresNum: Double
  def xEndPicometresNum: Double
  def yEndPicometresNum: Double
  def xStartMetresNum: Double
  def yStartMetresNum: Double
  def xEndMetresNum: Double
  def yEndMetresNum: Double
  def xStartKilometresNum: Double
  def yStartKilometresNum: Double
  def xEndKilometresNum: Double
  def yEndKilometresNum: Double
}

object LSegLen2
{ /** [[SlateXY]] type class instances / evidence for [[PtLen2]]. */
  given slateEv: SlateLen2[LSegLen2[PtLen2]] = new SlateLen2[LSegLen2[PtLen2]]
  { override def slateT(obj: LSegLen2[PtLen2], delta: VecPtLen2): LSegLen2[PtLen2] = obj.slate(delta)
    override def slateXY(obj: LSegLen2[PtLen2], xDelta: Length, yDelta: Length): LSegLen2[PtLen2] = obj.slate(xDelta, yDelta)
    override def slateX(obj: LSegLen2[PtLen2], xDelta: Length): LSegLen2[PtLen2] = obj.slateX(xDelta)
    override def slateY(obj: LSegLen2[PtLen2], yDelta: Length): LSegLen2[PtLen2] = obj.slateY(yDelta)
  }
  
  /** [[Scale]] type class instance / evidence for [[PtLen2]]. */
  given scaleEv: Scale[LSegLen2[PtLen2]] = (obj, operand) => obj.scale(operand)

  /** [[Drawing]] type class instance / evidence for [[PtLen2]]. */
  given drawingEv: Drawing[LSegLen2[PtLen2], LineSegLen2Draw] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}

trait LineSegLen2Arr[+VT <: PtLen2] extends Any, Arr[LSegLen2[VT]], GeomLen2Elem, DrawableLen2, LengthBased, ArrayDblBacked
{ type ThisT <: LineSegLen2Arr[VT]

  /** Appends the operand [[LineSegLen2Arr]]. */
  def ++(operand: LineSegLen2Arr[?]): ThisT
  
  override def slate(operand: VecPtLen2): LineSegLen2Arr[VT]
  override def slate(xOperand: Length, yOperand: Length): LineSegLen2Arr[VT]
  override def slateX(xOperand: Length): LineSegLen2Arr[VT]
  override def slateY(yOperand: Length): LineSegLen2Arr[VT]
  override def scale(operand: Double): LineSegLen2Arr[VT]
  override def mapGeom2(operand: Length): LSeg2Arr
  override def draw(lineWidth: Double, lineColour: Colour): LineSegLen2ArrDraw = LineSegLen2ArrDraw(this, lineWidth, lineColour)
}

object LineSegLen2Arr
{ /** [[Drawing]] type class instance / evidence for [[LineSegLen2Arr]]. */  
  given drawerEv[VT <: PtLen2]: Drawing[LineSegLen2Arr[VT], RArr[LineSegLen2Draw]] = (obj, lineWidth, colour) => obj.map(_.draw(lineWidth, colour))
}

case class LineSegLen2Draw(lineSeg: LSegLen2[?], width: Double, colour: Colour) extends GraphicLen2Simple
{ override def slate(operand: VecPtLen2): LineSegLen2Draw = LineSegLen2Draw(lineSeg.slate(operand), width, colour)
  override def slate(xOperand: Length, yOperand: Length): LineSegLen2Draw = LineSegLen2Draw(lineSeg.slate(xOperand, yOperand), width, colour)
  override def slateX(xOperand: Length): LineSegLen2Draw = LineSegLen2Draw(lineSeg.slateX(xOperand), width, colour)
  override def slateY(yOperand: Length): LineSegLen2Draw = LineSegLen2Draw(lineSeg.slateY(yOperand), width, colour)
  override def scale(operand: Double): LineSegLen2Draw = LineSegLen2Draw(lineSeg.scale(operand), width, colour)
  override def mapGeom2(operand: Length): LSeg2Draw = LSeg2Draw(lineSeg.mapGeom2(operand), width, colour)
}

object LineSegLen2Draw
{
  given mapGeom2Ev: MapGeom2[LineSegLen2Draw, LSeg2Draw] = (obj, operand) => obj.mapGeom2(operand)
}

class LineSegLen2ArrDraw(val lineSegArr: LineSegLen2Arr[PtLen2], lineWidth: Double, colour: Colour) extends GraphicLen2Elem
{ override def slate(operand: VecPtLen2): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.slate(operand), lineWidth, colour)
  override def slate(xOperand: Length, yOperand: Length): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.slate(xOperand, yOperand), lineWidth, colour)
  override def slateX(xOperand: Length): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.slateX(xOperand), lineWidth, colour)
  override def slateY(yOperand: Length): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.slateY(yOperand), lineWidth, colour)
  override def scale(operand: Double): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.scale(operand), lineWidth, colour)
  override def mapGeom2(operand: Length): LSeg2ArrDraw = LSeg2ArrDraw(lineSegArr.mapGeom2(operand), lineWidth, colour)
}

object LineSegLen2ArrDraw
{
  def apply(lineSegArr: LineSegLen2Arr[PtLen2], lineWidth: Double, colour: Colour): LineSegLen2ArrDraw = new LineSegLen2ArrDraw(lineSegArr, lineWidth, colour)
  
  given mapGeom2Ev: MapGeom2[LineSegLen2ArrDraw, LSeg2ArrDraw] = (obj, operand) => obj.mapGeom2(operand)
}