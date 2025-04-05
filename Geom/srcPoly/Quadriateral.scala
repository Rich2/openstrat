/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A 4 sided [[Polygon]]. */
trait Quadrilateral extends Polygon4Plus
{ type ThisT <: Quadrilateral
  final override def numVerts: Int = 4

  def diag1: LineSeg = LineSeg(v2, v0)

  def diag2: LineSeg = LineSeg(v3, v1)

  @inline def diags: LineSegArr = LineSegArr(diag1, diag2)

  override def slate(operand: VecPt2): Quadrilateral = QuadriateralGen(arraySlate(operand))
  override def slate(xOperand: Double, yOperand: Double): Quadrilateral = QuadriateralGen(arraySlateXY(xOperand, yOperand))
  override def scale(offset: Double): Quadrilateral = QuadriateralGen(arrayScale(offset))
  
}

class QuadriateralGen(val arrayUnsafe: Array[Double]) extends Quadrilateral, AffinePreserve
{ type ThisT = QuadriateralGen
  override def typeStr: String = "QuadrilateralGen"  

  override def ptsTrans(f: Pt2 => Pt2): QuadriateralGen = ???

  override def fromArray(array: Array[Double]): QuadriateralGen = new QuadriateralGen(array)
}

object QuadriateralGen
{ /** Factory apply method to construct [[Quadrilateral]] from an [[Array]] of [[Double]]s. */
  def apply(array: Array[Double]): QuadriateralGen = new QuadriateralGen(array)
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