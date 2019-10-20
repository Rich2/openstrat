/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** A Polygon is encoded as a sequence of plain 2 dimension (mathematical) vectors. Minimum length 3.. Clockwise is the default */
class Polygon(val array: Array[Double]) extends AnyVal with Transer with Vec2sLike
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
   
  def fill(fillColour: Colour): PolyFill = PolyFill(this, fillColour)
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolyDraw = PolyDraw(this, lineWidth, lineColour)
  def slateDraw(offset: Vec2, lineWidth: Double = 2, lineColour: Colour = Black) = PolyDraw(this.slate(offset), lineWidth, lineColour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 1.0, lineColour: Colour = Black): PolyFillDraw =
    PolyFillDraw(this, fillColour, lineWidth, lineColour)

  def fillText(fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, layer: Int = 0): PolyFillText =
    PolyFillText(this, fillColour, str, fontSize, textColour)

  def fillActive(fillColour: Colour, evObj: AnyRef): GraphicElems = Arr(PolyFill(this, fillColour), PolyActive(this, evObj))
    
  def fillDrawActive(fillColour: Colour, evObj: AnyRef, lineWidth: Double, lineColour: Colour = Black): GraphicElems =
    Arr(PolyFillDraw(this, fillColour,lineWidth, lineColour), PolyActive(this, evObj))
    
  def fillActiveDrawText(fillColour: Colour, evObj: AnyRef, str: String, fontSize: Int = 24, lineWidth: Double, lineColour: Colour = Black,
      zOrder: Int = 0): GraphicElems =
    Arr(PolyFillDrawText(this, fillColour,str, fontSize, lineWidth, lineColour), PolyActive(this, evObj))
  
  def fillDrawSubj(evObj: AnyRef, fillColour: Colour, lineWidth:  Double, lineColour: Colour = Black): PolySubj =
    PolySubj.fillDraw(this.polyCentre, this, evObj, fillColour, lineWidth, lineColour)
  
  def drawSubj(evObj: AnyRef, lineWidth:  Double, lineColour: Colour = Black): PolySubj =
    PolySubj.draw(this.polyCentre, this, evObj, lineWidth, lineColour)   
  
  def fillTextSubj(evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, align: TextAlign = CenAlign):
    PolySubj = PolySubj.fillText(this.polyCentre, this, evObj, fillColour, str, fontSize, textColour, align)    
   
  def fillContrastTextSubj(evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 10): PolySubj =
    fillTextSubj(evObj, fillColour, str, fontSize, fillColour.contrast)  
  def subj(evObj: AnyRef, elems: PaintElem*): PolySubj = new PolySubj(this.polyCentre, this, evObj, elems.toArr)
  def subjSeq(evObj: AnyRef, elems: Arr[PaintElem]): PolySubj = new PolySubj(this.polyCentre, this, evObj, elems)
  def subjAll(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour, textSize: Int, str: String): PolySubj =
    PolySubj(this.polyCentre, this, evObj, Arr(PolyFillDraw(this, fillColour, lineWidth, lineColour),
        TextGraphic(str, textSize, this.polyCentre, lineColour)))
  
  def closedPolygonToLine2s: Line2s =
  { val res: Line2s = Line2s(length)   
    for (i <- 0 until (length -1)) res.unsafeSetElem(i, Line2(apply(i), apply(i + 1)))      
    res.unsafeSetLast(Line2(last, head))
    res
  }
   
  def ptInPolygon(pt: Vec2): Boolean = {closedPolygonToLine2s.ptInPolygon(pt) } 
   
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
  
  def fillSubj(evObj: AnyRef, fillColour: Colour): PolySubj = PolySubj.fill(this.polyCentre, this, evObj, fillColour)

  def distScale(distRatio: Dist): DPolygon = pMap[Dist2, DPolygon](_ * distRatio)
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
  
  implicit val persistImplicit: ProductD2sBuilder[Vec2, Polygon] = new ProductD2sBuilder[Vec2, Polygon]("Polygon")
  { override def fromArray(value: Array[Double]): Polygon = new Polygon(value)
  }
}

/* A polygon using distances. */
class DPolygon(val array: Array[Double]) extends AnyVal with ProductD2s[Dist2]
{ type ThisT = DPolygon
  def unsafeFromArray(array: Array[Double]): DPolygon = new DPolygon(array)
  override def typeStr: String = "DPolygon"
  override def elemBuilder(d1: Double, d2: Double): Dist2 = new Dist2(d1, d2)
}

/** The companion object for DPolygon. Provides an implicit builder. */
object DPolygon extends ProductD2sCompanion[Dist2, DPolygon]
{ implicit val factory: Int => DPolygon = i => new DPolygon(new Array[Double](i * 2))
}