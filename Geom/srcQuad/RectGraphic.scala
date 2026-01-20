/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb.*

/** A graphic based on a [[Rect]], a rectangle aligned to the X and Y axes. */
trait RectGraphic extends RectangleGraphic, ShapeGraphicOrdinaled
{ override def shape: Rect
}

/** A simple non-compound graphic based on a [[Rect]], a rectangle aligned to the X and Y axes. */
trait RectGraphicSimple extends RectGraphic, RectangleGraphicSimple
{ override def slate(operand: VecPt2): RectGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): RectGraphicSimple
  override def slateFrom(operand: VecPt2): RectGraphicSimple
  override def slateFrom(xOperand: Double, yOperand: Double): RectGraphicSimple
  override def scale(operand: Double): RectGraphicSimple
  override def negX: RectGraphicSimple
  override def negY: RectGraphicSimple
  override def rotate90: RectGraphicSimple
  override def rotate180: RectGraphicSimple
  override def rotate270: RectGraphicSimple
}


/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectDraw extends RectGraphicSimple, RectangleDraw
{ override def slate(operand: VecPt2): RectDraw = RectDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): RectDraw = RectDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def slateFrom(operand: VecPt2): RectDraw = RectDraw(shape.slateFrom(operand), lineWidth, lineColour)
  override def slateFrom(xOperand: Double, yOperand: Double): RectDraw = RectDraw(shape.slateFrom(xOperand, yOperand), lineWidth, lineColour)
  override def slateX(xOperand: Double): RectDraw = RectDraw(shape.slateX(xOperand), lineWidth, lineColour)
  override def slateY(yOperand: Double): RectDraw = RectDraw(shape.slateY(yOperand), lineWidth, lineColour)
  override def scale(operand: Double): RectDraw = RectDraw(shape.scale(operand), lineWidth, lineColour)
  override def negX: RectDraw = RectDraw(shape.negX, lineWidth, lineColour)
  override def negY: RectDraw = RectDraw(shape.negY, lineWidth, lineColour)
  override def rotate90: RectDraw = RectDraw(shape.rotate90, lineWidth, lineColour)
  override def rotate180: RectDraw = RectDraw(shape.rotate180, lineWidth, lineColour)
  override def rotate270: RectDraw = RectDraw(shape.rotate270, lineWidth, lineColour)
  override def prolign(matrix: AxlignMatrix): RectDraw = RectDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def scaleXY(xOperand: Double, yOperand: Double): RectDraw = RectDraw(shape.scaleXY(xOperand, yOperand), lineWidth, lineColour)
}

/** Companion object for the [[RectDraw]] trait, contains a RectFillImp implementation class and an apply method that delegates to it. */
object RectDraw
{ /** Factory method for RectFill. A rectangular Graphic aligned to the axes, filled with a single colour. it delegates to the [[RectDrawGen]] implementation
 * class, but has a return type of RectFill. */
  def apply(rect: Rect, lineWidth: Double = 2, lineColour: Colour = Black): RectDraw = RectDrawGen(rect, lineWidth, lineColour)

  /** Implicit [[Slate2]] type class instance / evidence for [[RectDraw]]. */
  given slate2Ev: Slate2[RectDraw] = new Slate2[RectDraw]
  { override def slate(obj: RectDraw, operand: VecPt2): RectDraw = obj.slate(operand)
    override def slateXY(obj: RectDraw, xOperand: Double, yOperand: Double): RectDraw = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: RectDraw, operand: VecPt2): RectDraw = obj.slateFrom(operand)
    override def slateFromXY(obj: RectDraw, xOperand: Double, yOperand: Double): RectDraw = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: RectDraw, xOperand: Double): RectDraw = obj.slateX(xOperand)
    override def slateY(obj: RectDraw, yOperand: Double): RectDraw = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[RectDraw]]. */
  given scaleEv: Scale[RectDraw] = (obj: RectDraw, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolgn]] type class instance / evidence for [[RectDraw]]. */
  given prolignEv: Prolign[RectDraw] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[RectDraw]]. */
  given scaleXYEv: ScaleXY[RectDraw] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[MirrorAxes]] type class instance / evidence for [[RectDraw]]. */
  given transAxesEv: MirrorAxes[RectDraw] = new MirrorAxes[RectDraw]
  { override def negXT(obj: RectDraw): RectDraw = obj.negX
    override def negYT(obj: RectDraw): RectDraw = obj.negY
    override def rotate90(obj: RectDraw): RectDraw = obj.rotate90
    override def rotate180(obj: RectDraw): RectDraw = obj.rotate180
    override def rotate270(obj: RectDraw): RectDraw = obj.rotate270
  }
  
  /** An implementation class for a [[RectDraw]] that is not specified as a [[SquareDraw]]. */
  case class RectDrawGen(shape: Rect, lineWidth: Double = 2, lineColour: Colour = Black) extends RectDraw
}

/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectFill extends RectGraphicSimple, RectangleFill
{ type ThisT <: RectFill
  override def slate(operand: VecPt2): RectFill
  override def slate(xOperand: Double, yOperand: Double): RectFill
  override def slateFrom(operand: VecPt2): RectFill
  override def slateFrom(xOperand: Double, yOperand: Double): RectFill
  override def slateX(xOperand: Double): RectFill
  override def slateY(yOperand: Double): RectFill
  override def scale(operand: Double): RectFill
  override def negX: RectFill
  override def negY: RectFill
  override def rotate90: RectFill
  override def rotate180: RectFill
  override def rotate270: RectFill
  override def prolign(matrix: AxlignMatrix): RectFill
  override def scaleXY(xOperand: Double, yOperand: Double): RectFill = RectFill(shape.scaleXY(xOperand, yOperand), fillFacet)
}

/** Companion object for the RectFill trait, contains a RectFillImp implementation class and an apply method that delegates to it. */
object RectFill
{ /** Factory method for RectFill. A rectangular Graphic aligned to the axes, filled with a single colour. it delegates to the [[RectFillGen]] implementation
   * class, but has a return type of RectFill. */
  def apply(rect: Rect, fillFacet: FillFacet): RectFill = RectFillGen(rect, fillFacet)

  /** Implicit [[Slate2]] type class instance / evidence for [[RectFill]]. */
  given slate2Ev: Slate2[RectFill] = new Slate2[RectFill]
  { override def slate(obj: RectFill, operand: VecPt2): RectFill = obj.slate(operand)
    override def slateXY(obj: RectFill, xOperand: Double, yOperand: Double): RectFill = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: RectFill, operand: VecPt2): RectFill = obj.slateFrom(operand)
    override def slateFromXY(obj: RectFill, xOperand: Double, yOperand: Double): RectFill = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: RectFill, xOperand: Double): RectFill = obj.slateX(xOperand)
    override def slateY(obj: RectFill, yOperand: Double): RectFill = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[RectFill]]. */
  given scaleEv: Scale[RectFill] = (obj: RectFill, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolign]] type class instance / evidence for [[RectFill]]. */
  given prolignEv: Prolign[RectFill] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[MirrorAxes]] type class instance / evidence for [[RectFill]]. */
  given transAxesEv: MirrorAxes[RectFill] = new MirrorAxes[RectFill]
  { override def negXT(obj: RectFill): RectFill = obj.negX
    override def negYT(obj: RectFill): RectFill = obj.negY
    override def rotate90(obj: RectFill): RectFill = obj.rotate90
    override def rotate180(obj: RectFill): RectFill = obj.rotate180
    override def rotate270(obj: RectFill): RectFill = obj.rotate270
  }

  /** Implicit [[ScaleXY]] type class instance / evidence for [[RectFill]]. */
  given scaleXYEv: ScaleXY[RectFill] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** An implementation class for a [[RectFill]] that is not specified as a [[SquareFill]]. */
  case class RectFillGen(shape: Rect, fillFacet: FillFacet) extends RectFill
  { override type ThisT = RectFillGen
    override def slate(operand: VecPt2): RectFillGen = RectFillGen(shape.slate(operand), fillFacet)
    override def slate(xOperand: Double, yOperand: Double): RectFillGen = RectFillGen(shape.slate(xOperand, yOperand), fillFacet)
    override def slateFrom(operand: VecPt2): RectFillGen = RectFillGen(shape.slateFrom(operand), fillFacet)
    override def slateFrom(xOperand: Double, yOperand: Double): RectFillGen = RectFillGen(shape.slateFrom(xOperand, yOperand), fillFacet)
    override def slateX(xOperand: Double): RectFillGen = RectFillGen(shape.slateX(xOperand), fillFacet)
    override def slateY(yOperand: Double): RectFillGen = RectFillGen(shape.slateY(yOperand), fillFacet)
    override def scale(operand: Double): RectFillGen = RectFillGen(shape.scale(operand), fillFacet)
    override def negX: RectFillGen = RectFillGen(shape.negX, fillFacet)
    override def negY: RectFillGen = RectFillGen(shape.negY, fillFacet)
    override def rotate90: RectFillGen = RectFillGen(shape.rotate90, fillFacet)
    override def rotate180: RectFillGen = RectFillGen(shape.rotate180, fillFacet)
    override def rotate270: RectFillGen = RectFillGen(shape.rotate270, fillFacet)
    override def prolign(matrix: AxlignMatrix): RectFillGen = RectFillGen(shape.prolign(matrix), fillFacet)
  }
}

/** This is a compound graphic based on a Rect shape. A rectangle aligned to the X and Y axes.  */
trait RectCompound extends RectGraphic, RectangleCompound
{ override def shape: Rect
  override def mainSvgElem: SvgRect = SvgRect(attribs)

  override def slate(operand: VecPt2): RectCompound = RectCompound(shape.slate(operand), facets, children.slate(operand))
  
  override def slate(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))

  override def slateFrom(operand: VecPt2): RectCompound = RectCompound(shape.slateFrom(operand), facets, children.slateFrom(operand))

  override def slateFrom(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.slateFrom(xOperand, yOperand), facets, children.slateFrom(xOperand, yOperand)) 

  override def slateX(xOperand: Double): RectCompound = RectCompound(shape.slateX(xOperand), facets, children.slateX(xOperand))
  override def slateY(yOperand: Double): RectCompound = RectCompound(shape.slateY(yOperand), facets, children.slateY(yOperand))  
  override def scale(operand: Double): RectCompound = RectCompound(shape.scale(operand), facets, children.scale(operand))
  override def negX: RectCompound = RectCompound(shape.negX, facets, children.negX)
  override def negY: RectCompound = RectCompound(shape.negY, facets, children.negY)
  override def rotate90: RectCompound = RectCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: RectCompound = RectCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: RectCompound = RectCompound(shape.rotate270, facets, children.rotate270)
  override def prolign(matrix: AxlignMatrix): RectCompound = RectCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def scaleXY(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.scaleXY(xOperand, yOperand), facets, children.scaleXY(xOperand, yOperand) )

  override def addChildren(newChildren: Arr[Graphic2Elem]): RectCompound = RectCompound(shape, facets, children ++ newChildren)

  def htmlSvg: SvgSvgRel =
  { val atts = RArr(WidthAtt(shape.width), HeightAtt(shape.height), CentreBlockAtt)
    val svgElems = children.flatMap(_.svgElems)
    new SvgSvgRel(shape.left, -shape.top, shape.width, shape.height, svgElems, atts)
  }
}

/** Companion object for the RectCompound trait, contains implicit instances for 2D geometric transformation type classes. */
object RectCompound
{ /** Factory apply method for creating a compound [[Rect]] based graphic. */
  def apply(shape: Rect, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()): RectCompound =
    RectCompoundGen(shape, facets, children)

  /** [[Slate2]] type class instance / evidence for [[RectCompound]]. */
  given slate2Ev: Slate2[RectCompound] = new Slate2[RectCompound]
  { override def slate(obj: RectCompound, operand: VecPt2): RectCompound = obj.slate(operand)
    override def slateXY(obj: RectCompound, xOperand: Double, yOperand: Double): RectCompound = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: RectCompound, operand: VecPt2): RectCompound = obj.slateFrom(operand)
    override def slateFromXY(obj: RectCompound, xOperand: Double, yOperand: Double): RectCompound = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: RectCompound, xOperand: Double): RectCompound = obj.slateX(xOperand)
    override def slateY(obj: RectCompound, yOperand: Double): RectCompound = obj.slateY(yOperand)
  }
  /** [[Scale]] type class instance / evidence for [[RectCompound]]. */
  given scaleEv: Scale[RectCompound] = (obj: RectCompound, operand: Double) => obj.scale(operand)

  /** [[ScaleXY]] type class instance / evidence for [[RectCompound]]. */
  given scaleXYEv: ScaleXY[RectCompound] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  
  /** [[Prolign]] type class instance / evidence for [[RectCompound]]. */
  given prolignEv: Prolign[RectCompound] = (obj, matrix) => obj.prolign(matrix)

  /** [[TransAxed]] type class instance / evidence for [[RectCompound]]. */
  given transAxesEv: MirrorAxes[RectCompound] = new MirrorAxes[RectCompound]
  { override def negYT(obj: RectCompound): RectCompound = obj.negY
    override def negXT(obj: RectCompound): RectCompound = obj.negX
    override def rotate90(obj: RectCompound): RectCompound = obj.rotate90
    override def rotate180(obj: RectCompound): RectCompound = obj.rotate180
    override def rotate270(obj: RectCompound): RectCompound = obj.rotate270
  }

  /** Implementation class for the general case of a [[RectCompound]], as opposed to the special case of [[SqlignCompound]]. */
  case class RectCompoundGen(shape: Rect, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends RectCompound
}