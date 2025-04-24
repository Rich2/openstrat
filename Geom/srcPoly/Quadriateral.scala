/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 4 sided [[Polygon]]. */
trait Quadrilateral extends Polygon4Plus
{ type ThisT <: Quadrilateral
  override def numVerts: Int = 4
  override def numElems: Int = 4
  def diag1: LineSeg = LineSeg(v2, v0)

  def diag2: LineSeg = LineSeg(v3, v1)

  @inline def diags: LineSegArr = LineSegArr(diag1, diag2)

  def vertsTrans(f: Pt2 => Pt2): Quadrilateral = Quadrilateral(f(v0), f(v1), f(v2), f(v3))

  override def slate(operand: VecPt2): Quadrilateral = vertsTrans(_.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Quadrilateral = vertsTrans(_.slate(xOperand, yOperand))
  override def slateX(xOperand: Double): Quadrilateral = vertsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): Quadrilateral = vertsTrans(_.slateY(yOperand))
  override def scale(offset: Double): Quadrilateral = vertsTrans(_.scale(offset))
  override def negX: Quadrilateral = vertsTrans(_.negX)
  override def negY: Quadrilateral = vertsTrans(_.negY)
  override def prolign(matrix: AxlignMatrix): Quadrilateral = vertsTrans(_.prolign(matrix))
  override def rotate90: Quadrilateral = vertsTrans(_.rotate90)
  override def rotate180: Quadrilateral = vertsTrans(_.rotate90)
  override def rotate270: Quadrilateral = vertsTrans(_.rotate90)
  override def rotate(rotation: AngleVec): Quadrilateral = vertsTrans(_.rotate(rotation))
  override def reflect(lineLike: LineLike): Quadrilateral = vertsTrans(_.reflect(lineLike))
  override def scaleXY(xOperand: Double, yOperand: Double): Quadrilateral = vertsTrans(_.scaleXY(xOperand, yOperand))

  override def sides: LineSegArr = LineSegArr(side0, side1, side2, side3)
}

object Quadrilateral
{ /** Factory apply method to create [[Quadrilateral]] from its 4 vertices. */
  def apply (vt0: Pt2, vt1: Pt2, vt2: Pt2, vt3: Pt2): Quadrilateral = new QuadrilateralGen(Array(vt0.x, vt0.y, vt1.x, vt1.y, vt2.x, vt2.y, vt3.x, vt3.y))

  /** Factory apply method to create [[Quadrilateral]] from the X and Y components of the 4 vertices. */
  def apply(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Quadrilateral =
    new QuadrilateralGen(Array(x0, y0, x1, y1, x2, y2, x3, y3))
    
  /** Implicit [[Slate]] type class instance for [[Quadrilateral]] */  
  implicit val slateEv: Slate[Quadrilateral] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance for [[Quadrilateral]] */
  implicit val slateXYEv: SlateXY[Quadrilateral] = (obj, xOperand, yOperand) => obj.slate(xOperand, yOperand)

  /** Implicit [[Scale]] type class instance for [[Quadrilateral]] */
  implicit val scaleEv: Scale[Quadrilateral] = (obj, operand) => obj.scale(operand)
}

/** The general case of a quadrilateral */
class QuadrilateralGen(val arrayUnsafe: Array[Double]) extends PolygonLike[Pt2], Quadrilateral
{ type ThisT = QuadrilateralGen
  override def typeStr: String = "QuadrilateralGen"

  override def slate(operand: VecPt2): QuadrilateralGen = ???
  override def slate(xOperand: Double, yOperand: Double): QuadrilateralGen = ???

  def ptsTrans(f: Pt2 => Pt2): QuadrilateralGen =
  { val res = QuadrilateralGen.uninitialised
    iForeach{ (i, el) => res.setElemUnsafe(i, f(el)) }
    res
  }

  override def v1x: Double = ???

  override def v1y: Double = ???

  override def v2x: Double = ???

  override def v2y: Double = ???

  override def v3x: Double = ???

  override def v3y: Double = ???

  override def xVertsArray: Array[Double] = ???
  override def yVertsArray: Array[Double] = ???

  /** Accesses the specifying sequence element by a 0 based index. For [[Sequ]]s this will an alternative name for apply. */
  override def elem(index: Int): Pt2 = ???

  /** Sets / mutates an element in the Arr at the given index. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  override def setElemUnsafe(index: Int, newElem: Pt2): Unit = ???

  override def v0x: Double = arrayUnsafe(0)
  override def v0y: Double = arrayUnsafe(1)
  override def v0: Pt2 = Pt2(arrayUnsafe(0), arrayUnsafe(1))
  override def vLastX: Double = arrayUnsafe(numVerts - 2)
  override def vLastY: Double = arrayUnsafe(numVerts - 1)
  override def vLast: Pt2 = Pt2(vLastX, vLastY)
  override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
  override def sides: LineSegArr = ??? // new LineSegArr(arrayForSides)
}

/** Companion object for [[QuadrilateralGen]], the general case of a [[Quadrilateral]], contains factory methods. */
object QuadrilateralGen
{ /** Apply factory method to construct [[Quadrilateral]] from an [[Array]] of [[Double]]s. */
  def apply(array: Array[Double]): QuadrilateralGen = new QuadrilateralGen(array)

  /** Apply factory method to construct [[Quadrilateral]] from its 4 vertices. */
  def apply(pt0: Pt2, pt1: Pt2, pt2: Pt2, pt3: Pt2): QuadrilateralGen = new QuadrilateralGen(SlImutDbl2.array(pt0, pt1, pt2, pt3))

  /** Creates a new uninitialised [[QuadrilateralGen]]. */
  def uninitialised: QuadrilateralGen = new QuadrilateralGen(new Array[Double](8))
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