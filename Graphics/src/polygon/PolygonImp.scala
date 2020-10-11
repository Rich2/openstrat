/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

/** The implementation class for a general [[Polygon]] as opposed to a specific [[Polygon]] such as a [[Square]] or a [[Rectangle]], is encoded as a
 *  sequence of plain 2 dimension (mathematical) vectors. Minimum length 3. Clockwise is the default. Polygon may be altered to include a centre. */
final class PolygonImp(val arrayUnsafe: Array[Double]) extends Polygon with Vec2sLikeProdDbl2 with AffinePreserve
{ type ThisT = PolygonImp

  /** Temporary value to transition from the current data to one where the centre pt included at the start of the underlying Array. */
  val ptNumOffset: Int = 0

  /** Temporary value to transition from the current data to one where the centre pt included at the start of the underlying Array. */
  def dblsNumOffset: Int = ptNumOffset * 2

  def unsafeFromArray(array: Array[Double]): PolygonImp = new PolygonImp(array)

  @inline override def ptsArray: Array[Double] = arrayUnsafe
  override def typeStr: String = "Polygon"

  override def attribs: Arr[XANumeric] = ???

  override def canEqual(that: Any): Boolean = that match {
    case s: Shape => true
    case _ => false
  }

  override def vertsNum: Int = arrayUnsafe.length / 2 - dblsNumOffset

  override def productArity: Int = 1
  override def productElement(n: Int): Any = arrayUnsafe

  override def xVertGet(index: Int): Double = arrayUnsafe(index * 2 + dblsNumOffset)
  override def yVertGet(index: Int): Double = arrayUnsafe(index * 2 + 1 + dblsNumOffset)
  @inline def x1: Double = arrayUnsafe(0 + dblsNumOffset)
  @inline def y1: Double = arrayUnsafe(1 + dblsNumOffset)
  @inline def v1: Vec2 = x1 vv y1
  override def fTrans(f: Vec2 => Vec2): PolygonImp = new PolygonImp(arrTrans(f))
  def eq(obj: PolygonImp): Boolean = arrayUnsafe.sameElements(obj.arrayUnsafe)
  def minX: Double = foldTailLeft(head.x)((acc, el) => acc.min(el.x))
  def maxX: Double = foldTailLeft(head.x)((acc, el) => acc.max(el.x))
  def minY: Double = foldTailLeft(head.y)((acc, el) => acc.min(el.y))
  def maxY: Double = foldTailLeft(head.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY

  override def xCen: Double = boundingRect.xCen
  override def yCen: Double = boundingRect.yCen

  /** Insert vertex. */
  override def insVert(insertionPoint: Int, newVec: Vec2): PolygonImp =
  { val res = PolygonImp.factory(elemsLen + 1)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    res.unsafeSetElem(insertionPoint, newVec)
    (insertionPoint until elemsLen).foreach(i => res.unsafeSetElem(i + 1, apply(i)))
    res
  }

  /** Insert vertices */
  override def insVerts(insertionPoint: Int, newVecs: Vec2 *): PolygonImp =
  { val res = PolygonImp.factory(elemsLen + newVecs.length)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    newVecs.iForeach((elem, i) => res.unsafeSetElem(insertionPoint + i, elem))
    (insertionPoint until elemsLen).foreach(i => res.unsafeSetElem(i + newVecs.length, apply(i)))
    res
  }

  def distScale(distRatio: Dist): PolygonDist = pMap[Dist2, PolygonDist](_ * distRatio)

  override def foldLeft[B](initial: B)(f: (B, Vec2) => B): B = super.foldLeft(initial)(f)

  override def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)

  @inline override def polygonMap(f: Vec2 => Vec2): PolygonImp = vertsMap(f).toPolygon

  /** Translate geometric transformation on a [[PolygonImp]] returns a [[PolygonImp]]. */
  override def slate(offset: Vec2): PolygonImp = polygonMap(_ + offset)

  /** Translate geometric transformation on a [[PolygonImp]] returns a [[PolygonImp]]. */
  override def slate(xOffset: Double, yOffset: Double): PolygonImp = polygonMap(_.addXY(xOffset, yOffset))

  /** Uniform scaling aginst both X and Y axes transformation on a [[polygonGen]] returning a [[PolygonImp]]. Use the xyScale method for differential
   *  scaling. */
  override def scale(operand: Double): PolygonImp = polygonMap(_ * operand)

  /** Mirror, reflection transformation of a PolygonGen across the X axis, returns a PolygonGen. */
  override def negY: PolygonImp = polygonMap(_.negY)

  /** Mirror, reflection transformation of PolygonGen across the Y axis, returns a PolygonGen. */
  override def negX: PolygonImp = polygonMap(_.negX)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): PolygonImp = polygonMap(_.prolign(matrix))
}

/** Companion object for [[PolygonImp]]. */
object PolygonImp
{ implicit val factory: Int => PolygonImp = i => new PolygonImp(new Array[Double](i * 2))

  def apply(v1: Vec2, v2: Vec2, v3: Vec2, tail: Vec2 *): PolygonImp =
  { val len = (3 + tail.length)
    val res = factory(len)
    res.unsafeSetElems(0, v1, v2, v3)
    res.unsafeSetElemSeq(3, tail)
    res
  }

  implicit val eqImplicit: Eq[PolygonImp] = (p1, p2) => Eq.arrayImplicit[Double].eqv(p1.arrayUnsafe, p2.arrayUnsafe)

  implicit val persistImplicit: ArrProdDbl2Persist[Vec2, PolygonImp] = new ArrProdDbl2Persist[Vec2, PolygonImp]("Polygon")
  { override def fromArray(value: Array[Double]): PolygonImp = new PolygonImp(value)
  }

  /*implicit val polygonsBuildImplicit: ArrBuild[PolygonGen, Polygons] = new ArrArrayDblBuild[PolygonGen, Polygons]
  {
    override type BuffT = PolygonBuff
    def fromArray(array: Array[Array[Double]]): Polygons = new Polygons(array)

    override def newBuff(length: Int):  BuffT = ???
  }*/
}