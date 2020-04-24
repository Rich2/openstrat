/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black, collection.mutable.ArrayBuffer

/** A Polygon is encoded as a sequence of plain 2 dimension (mathematical) vectors. Minimum length 3. Clockwise is the default */
class Polygon(val array: Array[Double]) extends AnyVal with TranserAll with Vec2sLike
{ type ThisT = Polygon
  def unsafeFromArray(array: Array[Double]): Polygon = new Polygon(array)
  override def typeStr: String = "Polygon"
  override def elemBuilder(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
  def fTrans(f: Vec2 => Vec2): Polygon = new Polygon(arrTrans(f))  
  def eq(obj: Polygon): Boolean = array.sameElements(obj.array)
  def minX: Double = foldTailLeft(head.x)((acc, el) => acc.min(el.x))
  def maxX: Double = foldTailLeft(head.x)((acc, el) => acc.max(el.x))
  def minY: Double = foldTailLeft(head.y)((acc, el) => acc.min(el.y))
  def maxY: Double = foldTailLeft(head.y)((acc, el) => acc.max(el.y))
  def width: Double = maxX - minX
  def height: Double = maxY - minY
  /** Creates a bounding rectangle for a collection of 2d points */
  def boundingRect: BoundingRect =
  { var minX, maxX = head1
    var minY, maxY = head2      
    foreachTail{v =>
      minX = minX.min(v.x)
      maxX = maxX.max(v.x)
      minY = minY.min(v.y)
      maxY = maxY.max(v.y)
    }         
    BoundingRect(minX, maxX, minY, maxY)               
  }

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

  def parentElems(pointerID: Any, elems: Arr[PaintFullElem]): PolygonParentFull = new PolygonParentFull(this.polyCentre, this, pointerID, elems)

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
  def insVert(insertionPoint: Int, newVec: Vec2): Polygon =
  { val res = Polygon.factory(length + 1)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    res.unsafeSetElem(insertionPoint, newVec)
    (insertionPoint until length).foreach(i => res.unsafeSetElem(i + 1, apply(i)))
    res
  }
   
  /** Insert vertices */
  def insVerts(insertionPoint: Int, newVecs: Vec2 *): Polygon =
  { val res = Polygon.factory(length + newVecs.length)
    (0 until insertionPoint).foreach(i => res.unsafeSetElem(i, apply(i)))
    newVecs.iForeach((elem, i) => res.unsafeSetElem(insertionPoint + i, elem))     
    (insertionPoint until length).foreach(i => res.unsafeSetElem(i + newVecs.length, apply(i)))
    res
  }

  def distScale(distRatio: Dist): PolygonDist = pMap[Dist2, PolygonDist](_ * distRatio)
}

object Polygon //extends ProductD2sCompanion[Vec2, Polygon]
{ implicit val factory: Int => Polygon = i => new Polygon(new Array[Double](i * 2))

  def apply(v1: Vec2, v2: Vec2, v3: Vec2, tail: Vec2 *): Polygon =
  { val len = (3 + tail.length)
    val res = factory(len)
    res.unsafeSetElems(0, v1, v2, v3)
    res.unsafeSetElemSeq(3, tail)
    res
  }

  implicit val eqImplicit: Eq[Polygon] = (p1, p2) => Eq.arrayImplicit[Double].eqv(p1.array, p2.array)

  implicit val persistImplicit: ArrProdDbl2Persist[Vec2, Polygon] = new ArrProdDbl2Persist[Vec2, Polygon]("Polygon")
  { override def fromArray(value: Array[Double]): Polygon = new Polygon(value)
  }

  implicit val polygonsBuildImplicit: ArrBuild[Polygon, Polygons] = new ArrArrayDblBuild[Polygon, Polygons]
  {
    override type BuffT = PolygonBuff
    def fromArray(array: Array[Array[Double]]): Polygons = new Polygons(array)

    override def newBuff(length: Int):  BuffT = ???
  }
}

final class Polygons(val array: Array[Array[Double]]) extends AnyVal with ArrArrayDbl[Polygon]
{ override type ThisT = Polygons
  override def unsafeFromArrayArray(aad: Array[Array[Double]]): Polygons = new Polygons(aad)
  def apply(index: Int): Polygon = new Polygon(array(index))
}

object Polygons
{
  def apply(input: Polygon*): Polygons =
  {
    val array: Array[Array[Double]] = new Array[Array[Double]](input.length)
    var count = 0

    while (count < input.length)
    { array(count) = input(count).array
      count += 1
    }
    new Polygons(array)
  }

  implicit val eqImplicit: Eq[Polygons] = ArrArrayDblEq[Polygon, Polygons]
}

class PolygonBuff(val unsafeBuff: ArrayBuffer[Array[Double]]) extends AnyVal with ArrayDoubleBuff[Polygon]
{ def apply(index: Int): Polygon = new Polygon(unsafeBuff(index))
}
