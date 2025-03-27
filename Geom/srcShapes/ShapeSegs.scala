/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb._

/** The new ShapeSegs trait will prioritise easy and simplicity of functionality over efficiency. A generalised implementation of a [[Shape]]. A closed sequence
 *  of curve segments. Use [[ShapeGen]] for a general implementation of this class, */
trait ShapeSegs extends Shape
{
  /** The backing array of [[ShapeSeg]]s. End users should rarely need to access this field. */
  def unsafeArray: Array[CurveSeg]

  /** The [[ShapeSeg]]s that make up this Shape. */
  def segs: RArr[CurveSeg] = new RArr(unsafeArray)

  override def boundingRect: Rect = segs.foldLeft(segs(0).boundingRect)((acc, el) => acc || el.boundingRect)
  override def boundingWidth: Double = boundingRect.width
  override def boundingHeight: Double = boundingRect.height

  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): ShapeDraw = ???

  override def attribs: RArr[XmlAtt] = ???

  override def slateXY(xOperand: Double, yOperand: Double): ShapeGen = new ShapeGen(unsafeArray.SlateXY(xOperand, yOperand))

  /** Uniform scaling 2D geometric transformation on a ShapeGen returns a Shape. The Return type will be narrowed in sub traits / classes. Use the
   * xyScale method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): ShapeGen = new ShapeGen(unsafeArray.scale(operand))

  /** 2D Transformation using a [[ProlignMatrix]] on a Shape, returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def prolign(matrix: ProlignMatrix): ShapeGen = ???

  /** Rotation 2D geometric transformation on a ShapeGen taking the rotation as a scalar measured in radians, returns a Shape. The Return type will be
   * narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): ShapeSegs = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a Shape, returns a Shape. The Return type will be narrowed in sub
   * traits / classes. */
  override def reflect(lineLike: LineLike): ShapeGen = ???

  /** XY scaling 2D geometric transformation on a ShapeGen returns a Shape. This allows different scaling factors across X and Y dimensions. The return
   * type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGen = ???

  /** Shear 2D geometric transformation along the X Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def shearX(operand: Double): ShapeGen = ???

  /** Shear 2D geometric transformation along the Y Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def shearY(operand: Double): ShapeGen = ???

  override def fill(fillfacet: FillFacet): ShapeFill = ???

  override def fillInt(intValue: Int): ShapeFill = ???

  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): ShapeCompound = ???

  override def fillActive(fillColour: Colour, pointerID: Any): ShapeCompound = ???

  /** [[ShapeCompound]] graphic with a [[FillFacet]], a [[TextFacet]] and a [[ShapeActive]] child. */
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
  def apply(curveTails: CurveTailOld*): ShapeSegs = {
    val array: Array[CurveSeg] = new Array[CurveSeg](curveTails.length)
    curveTails.iForeach { (ct, i) =>
      ct match {
        //case lt: LineTail =>
        case _ =>
      }
    }
    new ShapeGen(array)

  }

  implicit val slateImplicit: SlateXY[ShapeSegs] = (obj: ShapeSegs, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[ShapeSegs] = (obj: ShapeSegs, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeSegs] = (obj: ShapeSegs, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[ShapeSegs] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[ShapeSegs] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val ReflectImplicit: Reflect[ShapeSegs] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val transAxesImplicit: TransAxes[ShapeSegs] = new TransAxes[ShapeSegs] {
    override def negYT(obj: ShapeSegs): ShapeSegs = obj.negY

    override def negXT(obj: ShapeSegs): ShapeSegs = obj.negX

    override def rotate90(obj: ShapeSegs): ShapeSegs = obj.rotate90

    override def rotate180(obj: ShapeSegs): ShapeSegs = obj.rotate180

    override def rotate270(obj: ShapeSegs): ShapeSegs = obj.rotate270
  }

  implicit val shearImplicit: Shear[ShapeSegs] = new Shear[ShapeSegs] {
    override def shearXT(obj: ShapeSegs, yFactor: Double): ShapeSegs = obj.shearX(yFactor)

    override def shearYT(obj: ShapeSegs, xFactor: Double): ShapeSegs = obj.shearY(xFactor)
  }
}