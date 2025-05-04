/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb.*

/** The implementation class for a general [[Polygon]] as opposed to a specific [[Polygon]] such as a [[Square]] or a [[Rectangle]], is encoded as a sequence of
 * plain 2 dimension (mathematical) vectors. Minimum length 3. Clockwise is the default. Polygon may be altered to include a centre. */
final class PolygonGen(val arrayUnsafe: Array[Double]) extends Polygon, Pt2SeqLike, AffinePreserve, SeqSpecDbl2[Pt2], PolygonDbl2[Pt2], Pt2SeqSpec
{ override type ThisT = PolygonGen

  override def fromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)
  override def typeStr: String = "Polygon"
  override def numVerts: Int = arrayUnsafe.length / 2
  override def fill(fillfacet: FillFacet): PolygonFill = PolygonFill(this, fillfacet)
  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)

  def vertsTrans(f: Pt2 => Pt2): PolygonGen = new PolygonGen(arrTrans(f))

  /** A method to perform all the [[ProlignPreserve]] transformations with a function from PT2 => PT2. */
  @inline override def ptsTrans(f: Pt2 => Pt2): PolygonGen = new PolygonGen(arrTrans(f))

  override def attribs: RArr[XmlAtt] = ???

  override def canEqual(that: Any): Boolean = that match
  { case s: Shape => true
    case _ => false
  }

  def eq(obj: PolygonGen): Boolean = arrayUnsafe.sameElements(obj.arrayUnsafe)
  def minX: Double = tailFold(v0.x)((acc, el) => acc.min(el.x))
  def maxX: Double = tailFold(v0.x)((acc, el) => acc.max(el.x))
  def minY: Double = tailFold(v0.y)((acc, el) => acc.min(el.y))
  def maxY: Double = tailFold(v0.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY

  /** Insert vertex. */
  override def insVert(insertionPoint: Int, newVec: Pt2): PolygonGen =
  { val res = PolygonGen.uninitialised(numElems + 1)
    (0 until insertionPoint).foreach(i => res.setElemUnsafe(i, elem(i)))
    res.setElemUnsafe(insertionPoint, newVec)
    (insertionPoint until numElems).foreach(i => res.setElemUnsafe(i + 1, elem(i)))
    res
  }

  /** Insert vertices */
  override def insVerts(insertionPoint: Int, newPts: Pt2 *): PolygonGen =
  { val res = PolygonGen.uninitialised(numElems + newPts.length)
    (0 until insertionPoint).foreach(i => res.setElemUnsafe(i, elem(i)))
    newPts.iForeach((i, elem) => res.setElemUnsafe(insertionPoint + i, elem))
    (insertionPoint until numElems).foreach(i => res.setElemUnsafe(i + newPts.length, elem(i)))
    res
  }

  def distScale(distRatio: Metres): PolygonM2Gen = map[PtM2, PolygonM2Gen](p => p.toMetres(distRatio))

  override def slate(xOperand: Double, yOperand: Double): PolygonGen = map(_.slate(xOperand, yOperand))
  override def slate(operand: VecPt2): PolygonGen = map(_.slate(operand))
  override def scale(operand: Double): PolygonGen = map(_.scale(operand))
  override def negX: PolygonGen = map(_.negX)
  override def negY: PolygonGen = map(_.negY)
  override def prolign(matrix: AxlignMatrix): PolygonGen = map(_.prolign(matrix))
  override def rotate90: PolygonGen = map(_.rotate90)
  override def rotate180: PolygonGen = map(_.rotate180)
  override def rotate270: PolygonGen = map(_.rotate270)
  override def rotate(rotation: AngleVec): PolygonGen = map(_.rotate(rotation))
  override def reflect(lineLike: LineLike): PolygonGen = map(_.reflect(lineLike))
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonGen = map(_.xyScale(xOperand, yOperand))
  override def shearX(operand: Double): PolygonGen = map(_.xShear(operand))
  override def shearY(operand: Double): PolygonGen = map(_.xShear(operand))
  override def verts: Pt2Arr = new Pt2Arr(arrayUnsafe)

  override def v0x: Double = arrayUnsafe(0)
  override def v0y: Double = arrayUnsafe(1)
  override def v0: Pt2 = Pt2(arrayUnsafe(0), arrayUnsafe(1))
  override def vLastX: Double = arrayUnsafe(numVerts * 2 - 2)
  override def vLastY: Double = arrayUnsafe(numVerts * 2 - 1)
  override def vLast: Pt2 = Pt2(vLastX, vLastY)
  override def side0: LSeg2 = LSeg2(v0x, v0y, arrayUnsafe(2), arrayUnsafe(3))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
  override def sides: LSeg2Arr = new LSeg2Arr(arrayForSides)
}

/** Companion object for [[PolygonGen]]. */
object PolygonGen extends CompanionSlDbl2[Pt2, PolygonGen]
{ /** Factory apply method to construct a [[PolygonGen]] from an [[Array]] of [[Double]]s */
  def apply(array: Array[Double]): PolygonGen = new PolygonGen(array)
  
  override def fromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)

  implicit val eqImplicit: EqT[PolygonGen] = (p1, p2) => EqT.arrayEv[Double].eqT(p1.arrayUnsafe, p2.arrayUnsafe)

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