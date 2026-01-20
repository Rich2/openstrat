/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

/** 2-dimensional graphic based on a [[Square]] */
trait SquareGraphic extends RectangleGraphic
{ override def shape: Square
  override def slate(operand: VecPt2): SquareGraphic
  override def slate(xOperand: Double, yOperand: Double): SquareGraphic
  override def slateFrom(operand: VecPt2): SquareGraphic
  override def slateFrom(xOperand: Double, yOperand: Double): SquareGraphic
  override def slateX(xOperand: Double): SquareGraphic
  override def slateY(yOperand: Double): SquareGraphic
  override def scale(operand: Double): SquareGraphic
  override def negY: SquareGraphic
  override def negX: SquareGraphic
}

/** Simple [[Square]] based graphic. */
trait SquareGraphicSimple extends SquareGraphic, RectangleGraphicSimple
{ override def slate(operand: VecPt2): SquareGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): SquareGraphicSimple
  override def slateFrom(operand: VecPt2): SquareGraphicSimple
  override def slateFrom(xOperand: Double, yOperand: Double): SquareGraphicSimple
  override def slateX(xOperand: Double): SquareGraphicSimple
  override def slateY(yOperand: Double): SquareGraphicSimple
  override def scale(operand: Double): SquareGraphicSimple
  override def negX: SquareGraphicSimple
  override def negY: SquareGraphicSimple
  override def rotate90: SquareGraphicSimple
  override def rotate180: SquareGraphicSimple
  override def rotate270: SquareGraphicSimple
  override def prolign(matrix: AxlignMatrix): SquareGraphicSimple
  override def rotate(rotation: AngleVec): SquareGraphicSimple
  override def mirror(lineLike: LineLike): SquareGraphicSimple
}

/** Graphic that draws a [[Square]]. */
trait SquareDraw extends RectangleDraw, SquareGraphicSimple
{ override def slate(operand: VecPt2): SquareDraw = SquareDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): SquareDraw = SquareDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def slateFrom(operand: VecPt2): SquareDraw = SquareDraw(shape.slateFrom(operand), lineWidth, lineColour)
  override def slateFrom(xOperand: Double, yOperand: Double): SquareDraw = SquareDraw(shape.slateFrom(xOperand, yOperand), lineWidth, lineColour)
  override def slateX(xOperand: Double): SquareDraw = SquareDraw(shape.slateX(xOperand), lineWidth, lineColour)
  override def slateY(yOperand: Double): SquareDraw = SquareDraw(shape.slateY(yOperand), lineWidth, lineColour)
  override def scale(operand: Double): SquareDraw = SquareDraw(shape.scale(operand), lineWidth, lineColour)
  override def negX: SquareDraw = SquareDraw(shape.negX, lineWidth, lineColour)
  override def negY: SquareDraw = SquareDraw(shape.negY, lineWidth, lineColour)
  override def rotate90: SquareDraw = SquareDraw(shape.rotate90, lineWidth, lineColour)
  override def rotate180: SquareDraw = SquareDraw(shape.rotate180, lineWidth, lineColour)
  override def rotate270: SquareDraw = SquareDraw(shape.rotate270, lineWidth, lineColour)
  override def prolign(matrix: AxlignMatrix): SquareDraw = SquareDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def rotate(rotation: AngleVec): SquareDraw = SquareDraw(shape.rotate(rotation), lineWidth, lineColour)
  override def mirror(lineLike: LineLike): SquareDraw = SquareDraw(shape.mirror(lineLike), lineWidth, lineColour) 
}

/** Companion object for SquareDraw contains factory method and implementation class. */
object SquareDraw
{ /** Factory apply method for [[Square]] draw graphic. */
  def apply(shape: Square, lineWidth: Double = 2, lineColour: Colour = Black): SquareDraw = SquareDrawGen(shape, lineWidth, lineColour)

  /** Implicit [[Slate2]] type class instance / evidence for [[SquareDraw]]. */
  given slate2Ev: Slate2[SquareDraw] = new Slate2[SquareDraw]
  { override def slate(obj: SquareDraw, operand: VecPt2): SquareDraw = obj.slate(operand)
    override def slateXY(obj: SquareDraw, xOperand: Double, yOperand: Double): SquareDraw = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: SquareDraw, operand: VecPt2): SquareDraw = obj.slateFrom(operand)
    override def slateFromXY(obj: SquareDraw, xOperand: Double, yOperand: Double): SquareDraw = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: SquareDraw, xOperand: Double): SquareDraw = obj.slateX(xOperand)
    override def slateY(obj: SquareDraw, yOperand: Double): SquareDraw = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[SquareDraw]]. */
  given scaleEv: Scale[SquareDraw] = (obj: SquareDraw, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolgn]] type class instance / evidence for [[SquareDraw]]. */
  given prolignEv: Prolign[SquareDraw] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Rotate]] type class instance / evidence for [[SquareDraw]]. */
  given rotateEv: Rotate[SquareDraw] = (obj: SquareDraw, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[MirrorAxes]] type class instance / evidence for [[SquareDraw]]. */
  given transAxesEv: MirrorAxes[SquareDraw] = new MirrorAxes[SquareDraw]
  { override def negXT(obj: SquareDraw): SquareDraw = obj.negX
    override def negYT(obj: SquareDraw): SquareDraw = obj.negY
    override def rotate90(obj: SquareDraw): SquareDraw = obj.rotate90
    override def rotate180(obj: SquareDraw): SquareDraw = obj.rotate180
    override def rotate270(obj: SquareDraw): SquareDraw = obj.rotate270
  }
  /** Immutable Graphic element that defines and draws a Polygon. */
  case class SquareDrawGen(shape: Square, lineWidth: Double = 2, lineColour: Colour = Black) extends SquareDraw
}

/** A [[Square]] fill graphic. */
trait SquareFill extends SquareGraphicSimple, RectangleFill
{ override def slate(operand: VecPt2): SquareFill
  override def slate(xOperand: Double, yOperand: Double): SquareFill
  override def slateFrom(operand: VecPt2): SquareFill
  override def slateFrom(xOperand: Double, yOperand: Double): SquareFill
  override def slateX(xOperand: Double): SquareFill
  override def slateY(yOperand: Double): SquareFill
  override def scale(operand: Double): SquareFill
  override def negX: SquareFill
  override def negY: SquareFill
  override def rotate90: SquareFill
  override def rotate180: SquareFill
  override def rotate270: SquareFill
  override def prolign(matrix: AxlignMatrix): SquareFill
  override def rotate(rotation: AngleVec): SquareFill = SquareFill(shape.rotate(rotation), fillFacet)
  override def mirror(lineLike: LineLike): SquareFill = SquareFill(shape.mirror(lineLike), fillFacet)
}

object SquareFill
{ /** Factory apply method for the general case of [[SquareFill]]. Use [[SqlignFill]] for one aligned to the X and Y axes. */
  def apply(shape: Square, fillFacet: FillFacet): SquareFillGen = SquareFillGen(shape, fillFacet)

  /** Implicit [[Slate2]] type class instance / evidence for [[SquareFill]]. */
  given slate2Ev: Slate2[SquareFill] = new Slate2[SquareFill]
  { override def slate(obj: SquareFill, operand: VecPt2): SquareFill = obj.slate(operand)
    override def slateXY(obj: SquareFill, xOperand: Double, yOperand: Double): SquareFill = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: SquareFill, operand: VecPt2): SquareFill = obj.slateFrom(operand)
    override def slateFromXY(obj: SquareFill, xOperand: Double, yOperand: Double): SquareFill = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: SquareFill, xOperand: Double): SquareFill = obj.slateX(xOperand)
    override def slateY(obj: SquareFill, yOperand: Double): SquareFill = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[SquareFill]]. */
  given scaleEv: Scale[SquareFill] = (obj: SquareFill, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[SquareFill]]. */
  given rotateEv: Rotate[SquareFill] = (obj: SquareFill, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[SquareFill]]. */
  given prolignEv: Prolign[SquareFill] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[MirrorAxes]] type class instance / evidence for [[SquareFill]]. */
  given transAxesEv: MirrorAxes[SquareFill] = new MirrorAxes[SquareFill]
  { override def negYT(obj: SquareFill): SquareFill = obj.negY
    override def negXT(obj: SquareFill): SquareFill = obj.negX
    override def rotate90(obj: SquareFill): SquareFill = obj.rotate90
    override def rotate180(obj: SquareFill): SquareFill = obj.rotate180
    override def rotate270(obj: SquareFill): SquareFill = obj.rotate270
  }
  
  /** Implementation for the general case of [[Square]] as opposed to the specific case of [[Squaren]]. */
  case class SquareFillGen(shape: Square, fillFacet: FillFacet) extends SquareFill
  { override type ThisT = SquareFillGen
    override def slate(operand: VecPt2): SquareFillGen = SquareFillGen(shape.slate(operand), fillFacet)
    override def slate(xOperand: Double, yOperand: Double): SquareFillGen = SquareFillGen(shape.slate(xOperand, yOperand), fillFacet)
    override def slateFrom(operand: VecPt2): SquareFillGen = SquareFillGen(shape.slateFrom(operand), fillFacet)
    override def slateFrom(xOperand: Double, yOperand: Double): SquareFillGen = SquareFillGen(shape.slateFrom(xOperand, yOperand), fillFacet)
    override def slateX(xOperand: Double): SquareFillGen = SquareFillGen(shape.slateX(xOperand), fillFacet)
    override def slateY(yOperand: Double): SquareFillGen = SquareFillGen(shape.slateY(yOperand), fillFacet)
    override def scale(operand: Double): SquareFillGen = SquareFillGen(shape.scale(operand), fillFacet)
    override def negX: SquareFillGen = SquareFillGen(shape.negX, fillFacet)
    override def negY: SquareFillGen = SquareFillGen(shape.negY, fillFacet)
    override def rotate90: SquareFillGen = SquareFillGen(shape.rotate90, fillFacet)
    override def rotate180: SquareFillGen = SquareFillGen(shape.rotate180, fillFacet)
    override def rotate270: SquareFillGen = SquareFillGen(shape.rotate180, fillFacet)
    override def prolign(matrix: AxlignMatrix): SquareFillGen = SquareFillGen(shape.prolign(matrix), fillFacet)
  }
}

/** A compound graphic for squares. */
trait SquareCompound extends RectangleCompound, SquareGraphic
{ override def slate(operand: VecPt2): SquareCompound = SquareCompound(shape.slate(operand), facets, children.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): SquareCompound =
    SquareCompound(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))

  override def slateFrom(operand: VecPt2): SquareCompound = SquareCompound(shape.slateFrom(operand), facets, children.slateFrom(operand))

  override def slateFrom(xOperand: Double, yOperand: Double): SquareCompound =
    SquareCompound(shape.slateFrom(xOperand, yOperand), facets, children.slateFrom(xOperand, yOperand))

  override def slateX(xOperand: Double): SquareCompound = SquareCompound(shape.slateX(xOperand), facets, children.slateX(xOperand))
  override def slateY(yOperand: Double): SquareCompound = SquareCompound(shape.slateY(yOperand), facets, children.slateY(yOperand))
  override def scale(operand: Double): SquareCompound = SquareCompound(shape.scale(operand), facets, children.scale(operand))
  override def negX: SquareCompound = SquareCompound(shape.negX, facets, children.negX)
  override def negY: SquareCompound = SquareCompound(shape.negY, facets, children.negY)
  override def prolign(matrix: AxlignMatrix): SquareCompound = SquareCompound(shape.prolign(matrix), facets, children.prolign(matrix))
  override def rotate90: SquareCompound = SquareCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: SquareCompound = SquareCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: SquareCompound = SquareCompound(shape.rotate270, facets, children.rotate270)
  override def rotate(rotation: AngleVec): SquareCompound = SquareCompound(shape.rotate(rotation), facets, children.rotate(rotation))
  override def mirror(lineLike: LineLike): SquareCompound = SquareCompound(shape.mirror(lineLike), facets, children.mirror(lineLike))
}

/** Companion object for SquareCompound. Contains the [[SquareCompound.SquareCompoundGen]] implementation class for the general case of Squares and an apply
 * factor method that delegates to it. */
object SquareCompound
{ /** Factory apply method to construct a [[SquareCompound]]. */
  def apply(shape: Square, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) : SquareCompound =
    new SquareCompoundGen(shape, facets, children)

  /** Implementation class for the general case of [[SquareCompound]]. */
  case class SquareCompoundGen(shape: Square, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends SquareCompound, AxisFree
  { override type ThisT = SquareCompoundGen
    override def slate(operand: VecPt2): SquareCompoundGen = SquareCompoundGen(shape.slate(operand), facets, children.slate(operand))

    override def slate(xOperand: Double, yOperand: Double): SquareCompoundGen =
      SquareCompoundGen(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))

    override def slateFrom(operand: VecPt2): SquareCompoundGen = SquareCompoundGen(shape.slateFrom(operand), facets, children.slateFrom(operand))

    override def slateFrom(xOperand: Double, yOperand: Double): SquareCompoundGen =
      SquareCompoundGen(shape.slateFrom(xOperand, yOperand), facets, children.slateFrom(xOperand, yOperand))

    override def slateX(xOperand: Double): SquareCompoundGen = SquareCompoundGen(shape.slateX(xOperand), facets, children.slateX(xOperand))
    override def slateY(yOperand: Double): SquareCompoundGen = SquareCompoundGen(shape.slateY(yOperand), facets, children.slateY(yOperand))
    override def scale(operand: Double): SquareCompoundGen = SquareCompoundGen(shape.scale(operand), facets, children.scale(operand))
    override def prolign(matrix: AxlignMatrix): SquareCompoundGen = SquareCompoundGen(shape.prolign(matrix), facets, children.prolign(matrix))
    override def rotate(rotation: AngleVec): SquareCompoundGen = SquareCompoundGen(shape.rotate(rotation), facets, children.rotate(rotation))
    override def mirror(lineLike: LineLike): SquareCompoundGen = SquareCompoundGen(shape.mirror(lineLike), facets, children.mirror(lineLike))
  }
}