/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** A sequence of plain 2 dimension (mathematical) vectors. This should possibly be renamed Polygon. Clockwise is the default */
class Polygon(val arr: Array[Double]) extends AnyVal with ProductD2s[Vec2] with Transer with Vec2sLike
{ override def typeStr: String = "Polygon"
  override def newElem(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
  def fTrans(f: Vec2 => Vec2): Polygon = new Polygon(arrTrans(f))  
  def eq(obj: Polygon): Boolean = arr.sameElements(obj.arr)

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
   
  def fill(fillColour: Colour, layer: Int = 0): PolyFill = PolyFill(this, fillColour, layer)
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolyDraw = PolyDraw(this, lineWidth, lineColour)
  def slateDraw(offset: Vec2, lineWidth: Double = 2, lineColour: Colour = Black) = PolyDraw(this.slate(offset), lineWidth, lineColour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 1.0, lineColour: Colour = Black): PolyFillDraw =
    PolyFillDraw(this, fillColour, lineWidth, lineColour)

  def fillText(fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, layer: Int = 0): PolyFillText =
    PolyFillText(this, fillColour, str, fontSize, textColour, layer)

  def fillActive(fillColour: Colour, evObj: AnyRef, layer: Int = 0): GraphicElems =
    Arr(PolyFill(this, fillColour, layer), PolyActive(this, evObj, layer))
    
    
  def fillDrawActive(fillColour: Colour, evObj: AnyRef, lineWidth: Double, lineColour: Colour = Black, zOrder: Int = 0): GraphicElems =
    Arr(PolyFillDraw(this, fillColour,lineWidth, lineColour, zOrder), PolyActive(this, evObj, zOrder))
    
  def fillActiveDrawText(fillColour: Colour, evObj: AnyRef, str: String, fontSize: Int = 24, lineWidth: Double, lineColour: Colour = Black,
      zOrder: Int = 0): GraphicElems =
    Arr(PolyFillDrawText(this, fillColour,str, fontSize, lineWidth, lineColour, zOrder), PolyActive(this, evObj, zOrder))
  
  def fillDrawSubj(evObj: AnyRef, fillColour: Colour, lineWidth:  Double, lineColour: Colour = Black, layer: Int = 0): PolySubj =
    PolySubj.fillDraw(this.polyCentre, this, evObj, fillColour, lineWidth, lineColour, layer)
  
  def drawSubj(evObj: AnyRef, lineWidth:  Double, lineColour: Colour = Black): PolySubj =
    PolySubj.draw(this.polyCentre, this, evObj, lineWidth, lineColour)   
  
  def fillTextSubj(evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, align: TextAlign = TextCen):
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
    for (i <- 0 until (length -1)) res.setElem(i, Line2(apply(i), apply(i + 1)))      
    res.setLast(Line2(last, head))     
    res
  }
   
  def ptInPolygon(pt: Vec2): Boolean = {closedPolygonToLine2s.ptInPolygon(pt) } 
   
  /** Insert vertice */
  def insVert(insertionPoint: Int, newVec: Vec2): Polygon =
  { val res = Polygon.factory(length + 1)
    (0 until insertionPoint).foreach(i => res.setElem(i, apply(i)))
    res.setElem(insertionPoint, newVec)
    (insertionPoint until length).foreach(i => res.setElem(i + 1, apply(i)))
    res
  }
   
  /** Insert vertices */
  def insVerts(insertionPoint: Int, newVecs: Vec2 *): Polygon =
  { val res = Polygon.factory(length + newVecs.length)
    (0 until insertionPoint).foreach(i => res.setElem(i, apply(i)))
    newVecs.iForeach((elem, i) => res.setElem(insertionPoint + i, elem))     
    (insertionPoint until length).foreach(i => res.setElem(i + newVecs.length, apply(i)))
    res
  }
  
  def fillSubj(evObj: AnyRef, fillColour: Colour, layer: Int = 0): PolySubj = PolySubj.fill(this.polyCentre, this, evObj, fillColour, layer)
}

object Polygon extends ProductD2sCompanion[Vec2, Polygon]
{ implicit val factory: Int => Polygon = i => new Polygon(new Array[Double](i * 2))
  
  implicit object PolygonPersist extends ProductD2sBuilder[Vec2, Polygon]("Polygon")
  {
    override def fromArray(value: Array[Double]): Polygon = new Polygon(value)
  }
}
