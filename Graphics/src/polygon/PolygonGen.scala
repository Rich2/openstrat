/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

/** A General Polygon as opposed to a specific Polygon such as a Square or a Rectangle is encoded as a sequence of plain 2 dimension (mathematical)
 *  vectors. Minimum length 3. Clockwise is the default */
final class PolygonGen(val arrayUnsafe: Array[Double]) extends Polygon with Vec2sLikeProdDbl2 with AffinePreserve
{ type ThisT = PolygonGen
  def unsafeFromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)
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
  override def fTrans(f: Vec2 => Vec2): PolygonGen = new PolygonGen(arrTrans(f))
  def eq(obj: PolygonGen): Boolean = arrayUnsafe.sameElements(obj.arrayUnsafe)
  def minX: Double = foldTailLeft(head.x)((acc, el) => acc.min(el.x))
  def maxX: Double = foldTailLeft(head.x)((acc, el) => acc.max(el.x))
  def minY: Double = foldTailLeft(head.y)((acc, el) => acc.min(el.y))
  def maxY: Double = foldTailLeft(head.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY

  def boundingWidth: Double = boundingRect.width
  def boundingHeight: Double = boundingRect.height

  def active(id: Any): PolygonActiveOnly = PolygonActiveOnly(this, id)
  def slateDraw(offset: Vec2, lineWidth: Double = 2, lineColour: Colour = Black) = PolygonDraw(this.slate(offset), lineWidth, lineColour)

  def fillText(fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, layer: Int = 0): PolygonFillTextOld =
    PolygonFillTextOld(this, fillColour, str, fontSize, textColour)

  def fillActive(fillColour: Colour, pointerID: Any): PolygonFillActive = PolygonFillActive(this, pointerID, fillColour)//, PolyActiveOnly(this, pointerID))

  def fillDrawActive(fillColour: Colour, pointerID: Any, lineWidth: Double, lineColour: Colour = Black): DisplayElems =
    Arr(PolygonFill(this, fillColour), PolygonDraw(this,lineWidth, lineColour), PolygonActiveOnly(this, pointerID))

  def fillDrawTextActive(fillColour: Colour, pointerID: Any, str: String, fontSize: Int = 24, lineWidth: Double, lineColour: Colour = Black): PolygonAll =
    PolygonAll(this, pointerID, fillColour,str, fontSize, lineWidth, lineColour)

  def fillDrawText(fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2.0, lineColour: Colour = Black): PolygonFillDrawTextOld =
    PolygonFillDrawTextOld(this, fillColour, str, fontSize, lineWidth, lineColour)

  def fillTextActive(fillColour: Colour, pointerEv: Any, str: String, fontSize: Int = 24): PolygonFillTextActive =
    PolygonFillTextActive(this, pointerEv, fillColour,str, fontSize)

  def parentFill(pointerID: Any, fillColour: Colour): PolygonParentOld = PolygonParentOld.fill(this.polyCentre, this, pointerID, fillColour)

  def parentFillDraw(pointerID: Any, fillColour: Colour, lineWidth:  Double, lineColour: Colour = Black): PolygonParentOld =
    PolygonParentOld.fillDraw(this.polyCentre, this, pointerID, fillColour, lineWidth, lineColour)

  def parentFillText(pointerID: Any, fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, align: TextAlign = CenAlign):
  PolygonParentOld = PolygonParentOld.fillText(this.polyCentre, this, pointerID, fillColour, str, fontSize, textColour, align)

  def parentFillContrastText(pointerID: Any, fillColour: Colour, str: String, fontSize: Int = 10): PolygonParentOld =
    parentFillText(pointerID, fillColour, str, fontSize, fillColour.contrast)

  def parentElems(pointerID: Any, elems: Arr[GraphicElem]): PolygonParentOld = new PolygonParentOld(this.polyCentre, this, pointerID, elems)

  def parentAll(pointerID: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour, textSize: Int, str: String): PolygonParentOld =
    PolygonParentOld(this.polyCentre, this, pointerID, Arr(PolygonFill(this, fillColour), PolygonDraw(this, lineWidth, lineColour),
      TextGraphic(str, textSize, this.polyCentre, lineColour)))

  /** Insert vertice */
  def insVert(insertionPoint: Int, newVec: Vec2): PolygonGen =
  { val res = PolygonGen.factory(length + 1)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    res.unsafeSetElem(insertionPoint, newVec)
    (insertionPoint until length).foreach(i => res.unsafeSetElem(i + 1, apply(i)))
    res
  }

  /** Insert vertices */
  def insVerts(insertionPoint: Int, newVecs: Vec2 *): PolygonGen =
  { val res = PolygonGen.factory(length + newVecs.length)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    newVecs.iForeach((elem, i) => res.unsafeSetElem(insertionPoint + i, elem))
    (insertionPoint until length).foreach(i => res.unsafeSetElem(i + newVecs.length, apply(i)))
    res
  }

  def distScale(distRatio: Dist): PolygonDist = pMap[Dist2, PolygonDist](_ * distRatio)

  override def foldLeft[B](initial: B)(f: (B, Vec2) => B): B = super.foldLeft(initial)(f)

  override def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)

  @inline override def polygonMap(f: Vec2 => Vec2): PolygonGen = vertsMap(f).toPolygon

  /** Translate geometric transformation on a [[PolygonGen]] returns a [[PolygonGen]]. */
  override def slate(offset: Vec2): PolygonGen = polygonMap(_ + offset)

  /** Translate geometric transformation on a [[PolygonGen]] returns a [[PolygonGen]]. */
  override def slate(xOffset: Double, yOffset: Double): PolygonGen = polygonMap(_.addXY(xOffset, yOffset))
  
  /** Uniform scaling aginst both X and Y axes transformation on a [[polygonGen]] returning a [[PolygonGen]]. Use the xyScale method for differential
   *  scaling. */
  override def scale(operand: Double): PolygonGen = polygonMap(_ * operand)

  /** Mirror, reflection transformation of a PolygonGen across the line y = yOffset, which is parallel to the X axis, returns a PolygonGen. */
  override def reflectXParallel(yOffset: Double): PolygonGen = polygonMap(_.reflectXOffset(yOffset))

  /** Mirror, reflection transformation of a PolygonGen across the line x = xOffset, which is parallel to the X axis. Returns a PolygonGen. */
  override def reflectYParallel(xOffset: Double): PolygonGen = polygonMap(_.reflectYOffset(xOffset))

  /** Mirror, reflection transformation of a PolygonGen across the X axis, returns a PolygonGen. */
  override def reflectX: PolygonGen = polygonMap(_.reflectX)

  /** Mirror, reflection transformation of PolygonGen across the Y axis, returns a PolygonGen. */
  override def reflectY: PolygonGen = polygonMap(_.reflectY)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): PolygonGen = polygonMap(_.prolign(matrix))
}

/** Companion object for [[PolygonGen]]. */
object PolygonGen //extends ProductD2sCompanion[Vec2, Polygon]
{ implicit val factory: Int => PolygonGen = i => new PolygonGen(new Array[Double](i * 2))

  def apply(v1: Vec2, v2: Vec2, v3: Vec2, tail: Vec2 *): PolygonGen =
  { val len = (3 + tail.length)
    val res = factory(len)
    res.unsafeSetElems(0, v1, v2, v3)
    res.unsafeSetElemSeq(3, tail)
    res
  }

  implicit val eqImplicit: Eq[PolygonGen] = (p1, p2) => Eq.arrayImplicit[Double].eqv(p1.arrayUnsafe, p2.arrayUnsafe)

  implicit val persistImplicit: ArrProdDbl2Persist[Vec2, PolygonGen] = new ArrProdDbl2Persist[Vec2, PolygonGen]("Polygon")
  { override def fromArray(value: Array[Double]): PolygonGen = new PolygonGen(value)
  }

  /*implicit val polygonsBuildImplicit: ArrBuild[PolygonGen, Polygons] = new ArrArrayDblBuild[PolygonGen, Polygons]
  {
    override type BuffT = PolygonBuff
    def fromArray(array: Array[Array[Double]]): Polygons = new Polygons(array)

    override def newBuff(length: Int):  BuffT = ???
  }*/
}