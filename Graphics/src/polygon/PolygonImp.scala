/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

/** The implementation class for a general [[Polygon]] as opposed to a specific [[Polygon]] such as a [[Square]] or a [[Rectangle]], is encoded as a
 *  sequence of plain 2 dimension (mathematical) vectors. Minimum length 3. Clockwise is the default. Polygon may be altered to include a centre. */
final class PolygonImp(val arrayUnsafe: Array[Double]) extends Polygon with Vec2sLikeProdDbl2 with AffinePreserve
{ type ThisT = PolygonImp
  def unsafeFromArray(array: Array[Double]): PolygonImp = new PolygonImp(array)

  @inline override def ptsArray: Array[Double] = arrayUnsafe
  override def typeStr: String = "Polygon"

  override def attribs: Arr[XANumeric] = ???

  override def canEqual(that: Any): Boolean = that match {
    case s: Shape => true
    case _ => false
  }

  override def productArity: Int = 1
  override def productElement(n: Int): Any = arrayUnsafe

  override def xGet(index: Int): Double = arrayUnsafe(index * 2)
  override def yGet(index: Int): Double = arrayUnsafe(index * 2 + 1)
  @inline def x0: Double = arrayUnsafe(0)
  @inline def y0: Double = arrayUnsafe(1)
  @inline def v0: Vec2 = x0 vv y0
  override def fTrans(f: Vec2 => Vec2): PolygonImp = new PolygonImp(arrTrans(f))
  def eq(obj: PolygonImp): Boolean = arrayUnsafe.sameElements(obj.arrayUnsafe)
  def minX: Double = foldTailLeft(head.x)((acc, el) => acc.min(el.x))
  def maxX: Double = foldTailLeft(head.x)((acc, el) => acc.max(el.x))
  def minY: Double = foldTailLeft(head.y)((acc, el) => acc.min(el.y))
  def maxY: Double = foldTailLeft(head.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY

  def active(id: Any): PolygonActiveOnly = PolygonActiveOnly(this, id)

  def fillDrawActive(fillColour: Colour, pointerID: Any, lineWidth: Double, lineColour: Colour = Black): GraphicElems =
    Arr(PolygonFill(this, fillColour), PolygonDraw(this,lineWidth, lineColour), PolygonActiveOnly(this, pointerID))

  def fillDrawTextActiveOld(fillColour: Colour, pointerID: Any, str: String, fontSize: Int = 24, lineWidth: Double, lineColour: Colour = Black):
    PolygonAll = PolygonAll(this, pointerID, fillColour,str, fontSize, lineWidth, lineColour)

  def fillDrawText(fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2.0, lineColour: Colour = Black): PolygonFillDrawTextOld =
    PolygonFillDrawTextOld(this, fillColour, str, fontSize, lineWidth, lineColour)

  def fillTextActive(fillColour: Colour, pointerEv: Any, str: String, fontSize: Int = 24): PolygonFillTextActive =
    PolygonFillTextActive(this, pointerEv, fillColour,str, fontSize)

  def parentFill(pointerID: Any, fillColour: Colour): PolygonParentOld = PolygonParentOld.fill(this.polyCentre, this, pointerID, fillColour)

  def parentFillDraw(pointerID: Any, fillColour: Colour, lineWidth:  Double, lineColour: Colour = Black): PolygonParentOld =
    PolygonParentOld.fillDraw(this.polyCentre, this, pointerID, fillColour, lineWidth, lineColour)

  def parentFillTextOld(pointerID: Any, fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, align: TextAlign = CenAlign):
    PolygonParentOld = PolygonParentOld.fillText(this.polyCentre, this, pointerID, fillColour, str, fontSize, textColour, align)

  def parentElems(pointerID: Any, elems: Arr[GraphicElem]): PolygonParentOld = new PolygonParentOld(this.polyCentre, this, pointerID, elems)

  def parentAll(pointerID: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour, textSize: Int, str: String): PolygonParentOld =
    PolygonParentOld(this.polyCentre, this, pointerID, Arr(PolygonFill(this, fillColour), PolygonDraw(this, lineWidth, lineColour),
      TextGraphic(str, textSize, this.polyCentre, lineColour)))

  /** Insert vertice */
  def insVert(insertionPoint: Int, newVec: Vec2): PolygonImp =
  { val res = PolygonImp.factory(length + 1)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    res.unsafeSetElem(insertionPoint, newVec)
    (insertionPoint until length).foreach(i => res.unsafeSetElem(i + 1, apply(i)))
    res
  }

  /** Insert vertices */
  def insVerts(insertionPoint: Int, newVecs: Vec2 *): PolygonImp =
  { val res = PolygonImp.factory(length + newVecs.length)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    newVecs.iForeach((elem, i) => res.unsafeSetElem(insertionPoint + i, elem))
    (insertionPoint until length).foreach(i => res.unsafeSetElem(i + newVecs.length, apply(i)))
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
object PolygonImp //extends ProductD2sCompanion[Vec2, Polygon]
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