/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb._

/** The implementation class for a general [[Polygon]] as opposed to a specific [[Polygon]] such as a [[Square]] or a [[Rectangle]], is encoded as a
 *  sequence of plain 2 dimension (mathematical) vectors. Minimum length 3. Clockwise is the default. Polygon may be altered to include a centre. */
final class PolygonGen(val unsafeArray: Array[Double]) extends Polygon with Pt2SeqLike with AffinePreserve with SeqSpecDbl2[Pt2]
{ override type ThisT = PolygonGen

  override def fromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)
  override def typeStr: String = "Polygon"
  override def vertsNum: Int = unsafeArray.length / 2
  override def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)

  override def vertsTrans(f: Pt2 => Pt2): PolygonGen = new PolygonGen(arrTrans(f))

  /** A method to perform all the [[ProlignPreserve]] transformations with a function from PT2 => PT2. */
  @inline override def ptsTrans(f: Pt2 => Pt2): PolygonGen = vertsTrans(f)

  override def attribs: RArr[XmlAtt] = ???

  override def canEqual(that: Any): Boolean = that match {
    case s: Shape => true
    case _ => false
  }

  def eq(obj: PolygonGen): Boolean = unsafeArray.sameElements(obj.unsafeArray)
  def minX: Double = ssTailFold(v0.x)((acc, el) => acc.min(el.x))
  def maxX: Double = ssTailFold(v0.x)((acc, el) => acc.max(el.x))
  def minY: Double = ssTailFold(v0.y)((acc, el) => acc.min(el.y))
  def maxY: Double = ssTailFold(v0.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY

  /** Insert vertex. */
  override def insVert(insertionPoint: Int, newVec: Pt2): PolygonGen =
  { val res = PolygonGen.uninitialised(ssLength + 1)
    (0 until insertionPoint).foreach(i => res.setElemUnsafe(i, ssIndex(i)))
    res.setElemUnsafe(insertionPoint, newVec)
    (insertionPoint until ssLength).foreach(i => res.setElemUnsafe(i + 1, ssIndex(i)))
    res
  }

  /** Insert vertices */
  override def insVerts(insertionPoint: Int, newVecs: Pt2 *): PolygonGen =
  { val res = PolygonGen.uninitialised(ssLength + newVecs.length)
    (0 until insertionPoint).foreach(i => res.setElemUnsafe(i, ssIndex(i)))
    newVecs.iForeach((i, elem) => res.setElemUnsafe(insertionPoint + i, elem))
    (insertionPoint until ssLength).foreach(i => res.setElemUnsafe(i + newVecs.length, ssIndex(i)))
    res
  }

  def distScale(distRatio: Length): PolygonM2 = map[PtM2, PolygonM2](p => p.toMetres(distRatio))

  /** Translate geometric transformation on a Polygon returns a Polygon. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slateXY(xDelta: Double, yDelta: Double): PolygonGen = map(_.addXY(xDelta, yDelta))

  /** Translate geometric transformation on a Polygon returns a Polygon. The return type of this method will be narrowed further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(offset: Vec2Like): PolygonGen = map(_.slate(offset))

  /** Uniform scaling against both X and Y axes transformation on a polygon returning a Polygon. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): PolygonGen = map(_.scale(operand))

  /** Mirror, reflection transformation of a Polygon across the X axis, returns a Polygon. */
  override def negY: PolygonGen = map(_.negY)

  /** Mirror, reflection transformation of Polygon across the Y axis, returns a Polygon. */
  override def negX: PolygonGen = map(_.negX)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): PolygonGen = map(_.prolign(matrix))

  override def rotate90: PolygonGen = map(_.rotate90)

  override def rotate180: PolygonGen = map(_.rotate180)

  override def rotate270: PolygonGen = map(_.rotate270)

  /** Rotation 2D geometric transformation on a Polygon, taking the rotation as a scalar measured in radians, returns a Polygon. The Return type will
   * be narrowed in some but not all sub traits / classes. */
  override def rotate(angle: AngleVec): PolygonGen = map(_.rotate(angle))

  /** Reflect 2D geometric transformation across a line, line segment or ray on a polygon, returns a Polygon. The Return type will be narrowed in sub
   * traits / classes. */
  override def reflect(lineLike: LineLike): PolygonGen = map(_.reflect(lineLike))

  /** XY scaling 2D geometric transformation on a Polygon returns a Polygon. This allows different scaling factors across X and Y dimensions. The
   * return type will be narrowed in some, but not all descendant Polygon types. */
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonGen = map(_.xyScale(xOperand, yOperand))

  /** Shear 2D geometric transformation along the X Axis on a Polygon, returns a Polygon. The return type will be narrowed in some but not all sub
   * classes and traits. */
  override def shearX(operand: Double): PolygonGen = map(_.xShear(operand))

  /** Shear 2D geometric transformation along the Y Axis on a Polygon, returns a Polygon. The return type will be narrowed in sub classes and
   * traits. */
  override def shearY(operand: Double): PolygonGen = map(_.xShear(operand))

}

/** Companion object for [[PolygonGen]]. */
object PolygonGen extends CompanionSeqLikeDbl2[Pt2, PolygonGen]
{ override def fromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)

  implicit val eqImplicit: EqT[PolygonGen] = (p1, p2) => EqT.arrayImplicit[Double].eqT(p1.unsafeArray, p2.unsafeArray)

  implicit val buildArrMapEv: BuilderArrMap[PolygonGen, PolygonGenArr] = new BuilderMapArrArrayDbl[PolygonGen, PolygonGenArr]
  { override type BuffT = PolygonGenBuff
    override def fromArrayArrayDbl(array: Array[Array[Double]]): PolygonGenArr = new PolygonGenArr(array)
    override def newBuff(length: Int): PolygonGenBuff = PolygonGenBuff(length)
  }

  /** [[Show]] type class instance / evidence for [[PolygonGen]] */
  implicit lazy val showEv: ShowSeqSpec[Pt2, PolygonGen] = ShowSeqSpec[Pt2, PolygonGen]("Polygon")

  /** [[Unshow]] type class instance / evidence for [[PolygonGen]] */
  implicit lazy val unshowEv: UnshowSeqLike[Pt2, PolygonGen] = UnshowSeqLike[Pt2, PolygonGen]("Polygon")
}