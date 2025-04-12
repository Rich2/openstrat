/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 4 sided [[Polygon]]. */
trait Quadrilateral extends Polygon4Plus
{ type ThisT <: Quadrilateral
  final override def numVerts: Int = 4

  def diag1: LineSeg = LineSeg(v2, v0)

  def diag2: LineSeg = LineSeg(v3, v1)

  @inline def diags: LineSegArr = LineSegArr(diag1, diag2)

  override def slate(operand: VecPt2): Quadrilateral = QuadriateralGen(arraySlate(operand))
  override def slate(xOperand: Double, yOperand: Double): Quadrilateral = QuadriateralGen(arraySlateXY(xOperand, yOperand))
  override def slateX(xOperand: Double): Quadrilateral = QuadriateralGen(arraySlateX(xOperand))
  override def slateY(yOperand: Double): Quadrilateral = QuadriateralGen(arraySlateY(yOperand))
  override def scale(offset: Double): Quadrilateral = QuadriateralGen(arrayScale(offset))
  override def negX: Quadrilateral = QuadriateralGen(arrayNegX)
  override def negY: Quadrilateral = QuadriateralGen(arrayNegY)
  override def prolign(matrix: ProlignMatrix): Quadrilateral = QuadriateralGen(arrayProlign(matrix))
  override def rotate90: Quadrilateral = QuadriateralGen(arrayRotate90)
  override def rotate180: Quadrilateral = QuadriateralGen(arrayRotate90)
  override def rotate270: Quadrilateral = QuadriateralGen(arrayRotate90)
  override def rotate(rotation: AngleVec): Quadrilateral = QuadriateralGen(arrayRotate(rotation))
  override def reflect(lineLike: LineLike): Quadrilateral = QuadriateralGen(arrayReflect(lineLike))
  override def scaleXY(xOperand: Double, yOperand: Double): Quadrilateral = QuadriateralGen(arrayScaleXY(xOperand, yOperand))
}

object Quadrilateral
{ /** Factory apply method to create [[Quadrilateral]] from its 4 vertices. */
  def apply (vt0: Pt2, vt1: Pt2, vt2: Pt2, vt3: Pt2): Quadrilateral = new QuadriateralGen(Array(vt0.x, vt0.y, vt1.x, vt1.y, vt2.x, vt2.y, vt3.x, vt3.y))

  /** Factory apply method to create [[Quadrilateral]] from the X and Y components of the 4 vertices. */
  def apply(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Quadrilateral =
    new QuadriateralGen(Array(x0, y0, x1, y1, x2, y2, x3, y3))
    
  /** Implicit [[Slate]] type class instance for [[Quadrilateral]] */  
  implicit val slateEv: Slate[Quadrilateral] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance for [[Quadrilateral]] */
  implicit val slateXYEv: SlateXY[Quadrilateral] = (obj, xOperand, yOperand) => obj.slate(xOperand, yOperand)

  /** Implicit [[Scale]] type class instance for [[Quadrilateral]] */
  implicit val scaleEv: Scale[Quadrilateral] = (obj, operand) => obj.scale(operand)
}

/** The general case of a quadrilateral */
class QuadriateralGen(val arrayUnsafe: Array[Double]) extends Quadrilateral, AffinePreserve
{ type ThisT = QuadriateralGen
  override def typeStr: String = "QuadrilateralGen"

  override def ptsTrans(f: Pt2 => Pt2): QuadriateralGen =
  { val res = QuadriateralGen.uninitialised
    iForeach{ (i, el) => res.setElemUnsafe(i, f(el)) }
    res
  }

  override def fromArray(array: Array[Double]): QuadriateralGen = new QuadriateralGen(array)
}

/** Companion object for [[QuadriateralGen]], the general case of a [[Quadrilateral]], contains factory methods. */
object QuadriateralGen
{ /** Apply factory method to construct [[Quadrilateral]] from an [[Array]] of [[Double]]s. */
  def apply(array: Array[Double]): QuadriateralGen = new QuadriateralGen(array)

  /** Apply factory method to construct [[Quadrilateral]] from its 4 vertices. */
  def apply(pt0: Pt2, pt1: Pt2, pt2: Pt2, pt3: Pt2): QuadriateralGen = new QuadriateralGen(SlImutDbl2.array(pt0, pt1, pt2, pt3))

  /** Creates a new uninitialised [[QuadriateralGen]]. */
  def uninitialised: QuadriateralGen = new QuadriateralGen(new Array[Double](8))
}

/** 2-dimensional graphic based ona quadrilateral */
trait QuadGraphic extends PolygonGraphic
{ override def shape: Quadrilateral
}

/** Compound graphic based on a quadrilateral. */
trait QuadCompound extends PolygonCompound, QuadGraphic
{ /** Graphic child elements of a quadrilateral. */
  def quadChilds: RArr[Quadrilateral => GraphicElems] = RArr()

  /** A sequence of graphics that have been attached to this quadrilateral. */
  def adopted: GraphicElems = RArr()

  override def slate(operand: VecPt2): QuadCompound = QuadCompoundGen(shape.slate(operand), facets, quadChilds, adopted)
  override def slate(xOperand: Double, yOperand: Double): QuadCompound = QuadCompoundGen(shape.slate(xOperand, yOperand), facets, quadChilds, adopted)
  override def slateX(xOperand: Double): QuadCompound = QuadCompoundGen(shape.slateX(xOperand), facets, quadChilds, adopted)
  override def slateY(yOperand: Double): QuadCompound = QuadCompoundGen(shape.slateY(yOperand), facets, quadChilds, adopted)
  override def scale(operand: Double): QuadCompound = QuadCompoundGen(shape.scale(operand), facets, quadChilds, adopted)
}

object QuadCompound
{ /** Implicit [[Slate]] type class instance / evidence for [[QuadCompound]]. */
  val slateEv: Slate[QuadCompound] = (obj, operand) => obj.slate(operand)
}

/** The implementation for the general case of a compound graphic based on a quadrilateral. */
class QuadCompoundGen(val shape: Quadrilateral, val facets: RArr[GraphicFacet], quadChilds: RArr[Quadrilateral => GraphicElems], adopted: RArr[Graphic2Elem])
  extends QuadCompound
{ override def children: RArr[Graphic2Elem] = ???
  override def shearX(operand: Double): QuadCompoundGen = ???
  override def shearY(operand: Double): QuadCompoundGen = ???
}