/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A line segment whose coordinates are specified in [[Length]] units. */
trait LineSegLen2[+VT <: PtLen2] extends LineSegLike[VT], DrawableLen2
{ def xStart: Length
  def yStart: Length
  def xEnd: Length
  def yEnd: Length
  def startPt: VT
  def endPt: VT
  override def slate(operand: VecPtLen2): LineSegLen2[VT]  
  override def slate(xOperand: Length, yOperand: Length): LineSegLen2[VT]

  override def slateX(xOperand: Length): LineSegLen2[VT]
  override def slateY(xOperand: Length): LineSegLen2[VT]
  def scale(operand: Double): LineSegLen2[VT]  
  def mapGeom2 (operand: Length): LSeg

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

object LineSegLen2
{ /** [[SlateXY]] type class instances / evidence for [[PtLen2]]. */
  implicit val slateEv: SlateLen2[LineSegLen2[PtLen2]] = new SlateLen2[LineSegLen2[PtLen2]]
  { override def slateT(obj: LineSegLen2[PtLen2], delta: VecPtLen2): LineSegLen2[PtLen2] = obj.slate(delta)
    override def slateT(obj: LineSegLen2[PtLen2], xDelta: Length, yDelta: Length): LineSegLen2[PtLen2] = obj.slate(xDelta, yDelta)
  }
  
  /** [[Scale]] type class instances / evidence for [[PtLen2]]. */
  implicit val scaleEv: Scale[LineSegLen2[PtLen2]] = (obj, operand) => obj.scale(operand)

  implicit val drawerEv: Drawing[LineSegLen2[PtLen2], LineSegLen2Draw] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}

trait LineSegLen2Arr[+VT <: PtLen2] extends Any, Arr[LineSegLen2[VT]], GeomLen2Elem, DrawableLen2, LengthBased, ArrayDblBacked
{ type ThisT <: LineSegLen2Arr[VT]

  /** Appends the operand [[LineSegLen2Arr]]. */
  def ++(operand: LineSegLen2Arr[?]): ThisT
  
  override def slate(operand: VecPtLen2): LineSegLen2Arr[VT]
  override def slate(xOperand: Length, yOperand: Length): LineSegLen2Arr[VT]
  override def slateX(xOperand: Length): LineSegLen2Arr[VT]
  override def slateY(yOperand: Length): LineSegLen2Arr[VT]
  override def scale(operand: Double): LineSegLen2Arr[VT]
  override def mapGeom2(operand: Length): LineSegArr
  override def draw(lineWidth: Double, lineColour: Colour): LineSegLen2ArrDraw = LineSegLen2ArrDraw(this, lineWidth, lineColour)
}

object LineSegLen2Arr
{ /** [[Drawing]] type class instance / evidence for [[LineSegLen2Arr]]. */  
  implicit def drawerEv[VT <: PtLen2]: Drawing[LineSegLen2Arr[VT], RArr[LineSegLen2Draw]] = (obj, lineWidth, colour) => obj.map(_.draw(lineWidth, colour))
}

case class LineSegLen2Draw(lineSeg: LineSegLen2[?], width: Double, colour: Colour) extends GraphicLen2Simple
{ override def slate(operand: VecPtLen2): LineSegLen2Draw = LineSegLen2Draw(lineSeg.slate(operand), width, colour)
  override def slate(xOperand: Length, yOperand: Length): LineSegLen2Draw = LineSegLen2Draw(lineSeg.slate(xOperand, yOperand), width, colour)
  override def slateX(xOperand: Length): LineSegLen2Draw = LineSegLen2Draw(lineSeg.slateX(xOperand), width, colour)
  override def slateY(yOperand: Length): LineSegLen2Draw = LineSegLen2Draw(lineSeg.slateY(yOperand), width, colour)
  override def scale(operand: Double): LineSegLen2Draw = LineSegLen2Draw(lineSeg.scale(operand), width, colour)
  override def mapGeom2(operand: Length): LineSegDraw = LineSegDraw(lineSeg.mapGeom2(operand), width, colour)
}

object LineSegLen2Draw
{
  implicit val mapGeom2Ev: MapGeom2[LineSegLen2Draw, LineSegDraw] = (obj, operand) => obj.mapGeom2(operand)
}

class LineSegLen2ArrDraw(val lineSegArr: LineSegLen2Arr[PtLen2], lineWidth: Double, colour: Colour) extends GraphicLen2Elem
{ override def slate(operand: VecPtLen2): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.slate(operand), lineWidth, colour)
  override def slate(xOperand: Length, yOperand: Length): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.slate(xOperand, yOperand), lineWidth, colour)
  override def slateX(xOperand: Length): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.slateX(xOperand), lineWidth, colour)
  override def slateY(yOperand: Length): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.slateY(yOperand), lineWidth, colour)
  override def scale(operand: Double): LineSegLen2ArrDraw = LineSegLen2ArrDraw(lineSegArr.scale(operand), lineWidth, colour)
  override def mapGeom2(operand: Length): LineSegArrDraw = LineSegArrDraw(lineSegArr.mapGeom2(operand), lineWidth, colour)
}

object LineSegLen2ArrDraw
{
  def apply(lineSegArr: LineSegLen2Arr[PtLen2], lineWidth: Double, colour: Colour): LineSegLen2ArrDraw = new LineSegLen2ArrDraw(lineSegArr, lineWidth, colour)
  
  implicit val mapGeom2Ev: MapGeom2[LineSegLen2ArrDraw, LineSegArrDraw] = (obj, operand) => obj.mapGeom2(operand)
}