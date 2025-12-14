/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb.*

/** The new ShapeSegs trait will prioritise easy and simplicity of functionality over efficiency. A generalised implementation of a [[Shape]]. A closed sequence
 *  of curve segments. Use [[ShapeGen]] for a general implementation of this class, */
trait ShapeSegs extends Shape
{ /** The backing array of [[ShapeSeg]]s. End users should rarely need to access this field. */
  def unsafeArray: Array[CurveSeg]

  /** The [[ShapeSeg]]s that make up this Shape. */
  def segs: RArr[CurveSeg] = new RArr(unsafeArray)

  override def boundingRect: Rect = segs.foldLeft(segs(0).boundingRect)((acc, el) => acc || el.boundingRect)
  override def boundingWidth: Double = boundingRect.width
  override def boundingHeight: Double = boundingRect.height

  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): ShapeDraw = ???

  override def attribs: RArr[XAtt] = ???

  override def slate(operand: VecPt2): ShapeGen = new ShapeGen(unsafeArray.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): ShapeGen = new ShapeGen(unsafeArray.slate(xOperand, yOperand))
  override def slateX(xOperand: Double): ShapeGen = new ShapeGen(unsafeArray.slateX(xOperand))
  override def slateY(yOperand: Double): ShapeGen = new ShapeGen(unsafeArray.slateY(yOperand))
  override def scale(operand: Double): ShapeGen = new ShapeGen(unsafeArray.scale(operand))
  override def prolign(matrix: AxlignMatrix): ShapeGen = ???
  override def rotate(rotation: AngleVec): ShapeSegs = ???
  override def mirror(lineLike: LineLike): ShapeGen = ???
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGen = ???
  override def shearX(operand: Double): ShapeGen = ???
  override def shearY(operand: Double): ShapeGen = ???
  override def fill(fillfacet: FillFacet): ShapeFill = ???
  override def fillInt(intValue: Int): ShapeFill = ???
  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): ShapeCompound = ???
  override def fillActive(fillColour: Colour, pointerID: Any): ShapeCompound = ???
  
  override def fillActiveText(fillColour: Colour, pointerEv: Any, str: String, fontRatio: Double, fontColour: Colour, align: TextAlign,
                              baseLine: BaseLine, minSize: Double): ShapeCompound = ???

  /** Determines if the parameter point lies inside this [[Circle]]. */
  override def ptInside(pt: Pt2): Boolean = ???

  override def negX: ShapeSegs = ???
  override def negY: ShapeSegs = ???
  override def rotate90: ShapeSegs = ???
  override def rotate180: ShapeSegs = ???
  override def rotate270: ShapeSegs = ???
}

/** Companion object of the ShapeSegs class contains implicit instances for 2D geometric transformations. */
object ShapeSegs
{
  /** Throws on 0 length var args. */
  def apply(curveTails: CurveTailOld*): ShapeSegs =
  { val array: Array[CurveSeg] = new Array[CurveSeg](curveTails.length)
    curveTails.iForeach { (ct, i) =>
      ct match {
        //case lt: LineTail =>
        case _ =>
      }
    }
    new ShapeGen(array)
  }

  /** Implicit [[Slate2]] type class instance / evidence for [[ShapeSegs]]. */
  given slate2Ev: Slate2[ShapeSegs] = new Slate2[ShapeSegs]
  { override def slate(obj: ShapeSegs, operand: VecPt2): ShapeSegs = obj.slate(operand)
    override def slateXY(obj: ShapeSegs, xOperand: Double, yOperand: Double): ShapeSegs = obj.slate(xOperand, yOperand)
    override def slateX(obj: ShapeSegs, xOperand: Double): ShapeSegs = obj.slateX(xOperand)
    override def slateY(obj: ShapeSegs, yOperand: Double): ShapeSegs = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[ShapeSegs]]. */
  given scaleEv: Scale[ShapeSegs] = (obj: ShapeSegs, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[ShapeSegs]]. */
  given rotateEv: Rotate[ShapeSegs] = (obj: ShapeSegs, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[ShapeSegs]]. */
  given prolignEv: Prolign[ShapeSegs] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[ShapeSegs]]. */
  given scaleXYEv: ScaleXY[ShapeSegs] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Mirror]] type class instance / evidence for [[ShapeSegs]]. */
  given ReflectEv: Mirror[ShapeSegs] = (obj, lineLike) => obj.mirror(lineLike)
  
  /** Implicit [[TransAxes]] type class instance / evidence for [[ShapeSegs]]. */
  given transAxesEv: TransAxes[ShapeSegs] = new TransAxes[ShapeSegs]
  { override def negXT(obj: ShapeSegs): ShapeSegs = obj.negX
    override def negYT(obj: ShapeSegs): ShapeSegs = obj.negY
    override def rotate90(obj: ShapeSegs): ShapeSegs = obj.rotate90
    override def rotate180(obj: ShapeSegs): ShapeSegs = obj.rotate180
    override def rotate270(obj: ShapeSegs): ShapeSegs = obj.rotate270
  }
  
  /** Implicit [[Shear]] type class instance / evidence for [[ShapeSegs]]. */
  given shearEv: Shear[ShapeSegs] = new Shear[ShapeSegs]
  { override def shearXT(obj: ShapeSegs, yFactor: Double): ShapeSegs = obj.shearX(yFactor)
    override def shearYT(obj: ShapeSegs, xFactor: Double): ShapeSegs = obj.shearY(xFactor)
  }
}