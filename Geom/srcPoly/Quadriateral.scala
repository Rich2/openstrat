/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

import ostrat.geom.QuadriateralGen.uninitialised

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

/** The general case of a quadrilateral */
class QuadriateralGen(val arrayUnsafe: Array[Double]) extends Quadrilateral, AffinePreserve
{ type ThisT = QuadriateralGen
  override def typeStr: String = "QuadrilateralGen"

  override def ptsTrans(f: Pt2 => Pt2): QuadriateralGen =
  { val res = uninitialised
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
  def apply(pt0: Pt2, pt1: Pt2, pt2: Pt2, pt3: Pt2): QuadriateralGen = new QuadriateralGen(SeqLikeDbl2.array(pt0, pt1, pt2, pt3))

  /** Creates a new uninitialised [[QuadriateralGen]]. */
  def uninitialised: QuadriateralGen = new QuadriateralGen(new Array[Double](8))
}

trait QuadGraphic extends PolygonGraphic
{
  override def shape: Quadrilateral
}

trait QuadCompound extends PolygonCompound, QuadGraphic
{
  def quadChilds: RArr[Quadrilateral => GraphicElems] = RArr()
}

class QuadCompoundGen(val shape: Quadrilateral, val facets: RArr[GraphicFacet], quadChilds: RArr[Quadrilateral => GraphicElems], adopted: RArr[Graphic2Elem])
  extends QuadCompound
{
  override def children: RArr[Graphic2Elem] = ???
  override def shearX(operand: Double): QuadCompoundGen = ???
  override def shearY(operand: Double): QuadCompoundGen = ???
}