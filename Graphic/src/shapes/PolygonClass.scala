/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black, collection.mutable.ArrayBuffer

/** A General Polygon as opposed to a specific Polygon such as a Square or a Rectangle is encoded as a sequence of plain 2 dimension (mathematical)
 *  vectors. Minimum length 3. Clockwise is the default */
class PolygonClass(val arrayUnsafe: Array[Double]) extends Polygon with Vec2sLikeProdDbl2 with AffineElem
{ type ThisT = PolygonClass
  //type ThisT = PolygonClass
  def unsafeFromArray(array: Array[Double]): PolygonClass = new PolygonClass(array)
  override def typeStr: String = "Polygon"

  override def canEqual(that: Any): Boolean = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???
  
  def x0: Double = arrayUnsafe(0)
  def y0: Double = arrayUnsafe(1)
  def fTrans(f: Vec2 => Vec2): PolygonClass = new PolygonClass(arrTrans(f))
  def eq(obj: PolygonClass): Boolean = arrayUnsafe.sameElements(obj.arrayUnsafe)
  def minX: Double = foldTailLeft(head.x)((acc, el) => acc.min(el.x))
  def maxX: Double = foldTailLeft(head.x)((acc, el) => acc.max(el.x))
  def minY: Double = foldTailLeft(head.y)((acc, el) => acc.min(el.y))
  def maxY: Double = foldTailLeft(head.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY
  
  def boundingWidth: Double = boundingRect.width
  def boundingHeight: Double = boundingRect.height
  def polyCentre: Vec2 = boundingRect.cen

  def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)
  def active(id: Any): PolygonActiveOnly = PolygonActiveOnly(this, id)
  def slateDraw(offset: Vec2, lineWidth: Double = 2, lineColour: Colour = Black) = PolygonDraw(this.slate(offset), lineWidth, lineColour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 1.0, lineColour: Colour = Black): PolygonFillDraw =
    PolygonFillDraw(this, fillColour, lineWidth, lineColour)

  def fillText(fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, layer: Int = 0): PolygonFillText =
    PolygonFillText(this, fillColour, str, fontSize, textColour)

  def fillActive(fillColour: Colour, pointerID: Any): PolygonFillActive = PolygonFillActive(this, pointerID, fillColour)//, PolyActiveOnly(this, pointerID))

  def fillDrawActive(fillColour: Colour, pointerID: Any, lineWidth: Double, lineColour: Colour = Black): GraphicElemFulls =
    Arr(PolygonFillDraw(this, fillColour,lineWidth, lineColour), PolygonActiveOnly(this, pointerID))

  def fillDrawTextActive(fillColour: Colour, pointerID: Any, str: String, fontSize: Int = 24, lineWidth: Double, lineColour: Colour = Black): PolygonAll =
    PolygonAll(this, pointerID, fillColour,str, fontSize, lineWidth, lineColour)

  def fillDrawText(fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2.0, lineColour: Colour = Black): PolygonFillDrawText =
    PolygonFillDrawText(this, fillColour, str, fontSize, lineWidth, lineColour)

  def fillTextActive(fillColour: Colour, pointerEv: Any, str: String, fontSize: Int = 24): PolygonFillTextActive =
    PolygonFillTextActive(this, pointerEv, fillColour,str, fontSize)

  def parentFill(pointerID: Any, fillColour: Colour): PolygonParentFull = PolygonParentFull.fill(this.polyCentre, this, pointerID, fillColour)

  def parentFillDraw(pointerID: Any, fillColour: Colour, lineWidth:  Double, lineColour: Colour = Black): PolygonParentFull =
    PolygonParentFull.fillDraw(this.polyCentre, this, pointerID, fillColour, lineWidth, lineColour)

  def parentFillText(pointerID: Any, fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, align: TextAlign = CenAlign):
  PolygonParentFull = PolygonParentFull.fillText(this.polyCentre, this, pointerID, fillColour, str, fontSize, textColour, align)

  def parentFillContrastText(pointerID: Any, fillColour: Colour, str: String, fontSize: Int = 10): PolygonParentFull =
    parentFillText(pointerID, fillColour, str, fontSize, fillColour.contrast)

  def parentElems(pointerID: Any, elems: Arr[GraphicFullElem]): PolygonParentFull = new PolygonParentFull(this.polyCentre, this, pointerID, elems)

  def parentAll(pointerID: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour, textSize: Int, str: String): PolygonParentFull =
    PolygonParentFull(this.polyCentre, this, pointerID, Arr(PolygonFillDraw(this, fillColour, lineWidth, lineColour),
      TextGraphic(str, textSize, this.polyCentre, lineColour)))

  def closedPolygonToLine2s: Line2s =
  { val res: Line2s = Line2s(length)
    for (i <- 0 until (length -1)) res.unsafeSetElem(i, Line2(apply(i), apply(i + 1)))
    res.unsafeSetLast(Line2(last, head))
    res
  }

  /** Determines if the parenter point lies inside this Polygon. */
  def ptInPolygon(pt: Vec2): Boolean = closedPolygonToLine2s.ptInPolygon(pt)

  /** Insert vertice */
  def insVert(insertionPoint: Int, newVec: Vec2): PolygonClass =
  { val res = PolygonClass.factory(length + 1)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    res.unsafeSetElem(insertionPoint, newVec)
    (insertionPoint until length).foreach(i => res.unsafeSetElem(i + 1, apply(i)))
    res
  }

  /** Insert vertices */
  def insVerts(insertionPoint: Int, newVecs: Vec2 *): PolygonClass =
  { val res = PolygonClass.factory(length + newVecs.length)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    newVecs.iForeach((elem, i) => res.unsafeSetElem(insertionPoint + i, elem))
    (insertionPoint until length).foreach(i => res.unsafeSetElem(i + newVecs.length, apply(i)))
    res
  }

  def distScale(distRatio: Dist): PolygonDist = pMap[Dist2, PolygonDist](_ * distRatio)

  override def mirrorX: PolygonClass = ???

  override def foldLeft[B](initial: B)(f: (B, Vec2) => B): B = super.foldLeft(initial)(f)
}

object PolygonClass //extends ProductD2sCompanion[Vec2, Polygon]
{ implicit val factory: Int => PolygonClass = i => new PolygonClass(new Array[Double](i * 2))

  def apply(v1: Vec2, v2: Vec2, v3: Vec2, tail: Vec2 *): PolygonClass =
  { val len = (3 + tail.length)
    val res = factory(len)
    res.unsafeSetElems(0, v1, v2, v3)
    res.unsafeSetElemSeq(3, tail)
    res
  }

  implicit val eqImplicit: Eq[PolygonClass] = (p1, p2) => Eq.arrayImplicit[Double].eqv(p1.arrayUnsafe, p2.arrayUnsafe)

  implicit val persistImplicit: ArrProdDbl2Persist[Vec2, PolygonClass] = new ArrProdDbl2Persist[Vec2, PolygonClass]("Polygon")
  { override def fromArray(value: Array[Double]): PolygonClass = new PolygonClass(value)
  }

  implicit val polygonsBuildImplicit: ArrBuild[PolygonClass, Polygons] = new ArrArrayDblBuild[PolygonClass, Polygons]
  {
    override type BuffT = PolygonBuff
    def fromArray(array: Array[Array[Double]]): Polygons = new Polygons(array)

    override def newBuff(length: Int):  BuffT = ???
  }
}

final class Polygons(val array: Array[Array[Double]]) extends AnyVal with ArrArrayDbl[PolygonClass]
{ override type ThisT = Polygons
  override def unsafeFromArrayArray(aad: Array[Array[Double]]): Polygons = new Polygons(aad)
  def apply(index: Int): PolygonClass = new PolygonClass(array(index))
}

object Polygons
{
  def apply(input: PolygonClass*): Polygons =
  {
    val array: Array[Array[Double]] = new Array[Array[Double]](input.length)
    var count = 0

    while (count < input.length)
    { array(count) = input(count).arrayUnsafe
      count += 1
    }
    new Polygons(array)
  }

  implicit val eqImplicit: Eq[Polygons] = ArrArrayDblEq[PolygonClass, Polygons]
}

class PolygonBuff(val unsafeBuff: ArrayBuffer[Array[Double]]) extends AnyVal with ArrayDoubleBuff[PolygonClass]
{ def apply(index: Int): PolygonClass = new PolygonClass(unsafeBuff(index))
}
