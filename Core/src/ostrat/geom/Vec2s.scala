/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A sequence of plain 2 dimension (mathematical) vectors. This should possibly be renamed Polygon. Clockwise is the default */
class Vec2s(val arr: Array[Double]) extends AnyVal with DoubleProduct2s[Vec2] with Transable[Vec2s]
{
   override def typeName: Symbol = 'Vec2s   
   override def newElem(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
   def fTrans(f: Vec2 => Vec2): Vec2s = pMap(f)
   def addMap(xOperand: Double, yOperand: Double): Vec2s = pMap(_.addXY(xOperand, yOperand))
   
   /** Creates a bounding rectangle for a collection of 2d points */
   def boundingRect: BoundingRect =
   {
      var minX, maxX = head1
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
   import Colour.Black
   def fill(colour: Colour): PolyFill = PolyFill(this, colour)
   def draw(lineWidth: Double, lineColour: Colour = Black): PolyDraw = PolyDraw(this, lineWidth, lineColour)
   def fillDraw(fillColour: Colour, lineWidth: Double = 1.0, lineColour: Colour = Black): PolyFillDraw =
      PolyFillDraw(this, fillColour, lineWidth, lineColour)
   
   def fillSubj(evObj: AnyRef, fillColour: Colour): PolySubj = PolySubj.fill(this.polyCentre, this, evObj, fillColour)
   def fillDrawSubj(evObj: AnyRef, fillColour: Colour, lineWidth:  Double, lineColour: Colour = Black): PolySubj =
      PolySubj.fillDraw(this.polyCentre, this, evObj, fillColour, lineWidth, lineColour)
    def drawSubj(evObj: AnyRef, lineWidth:  Double, lineColour: Colour = Black): PolySubj =
      PolySubj.draw(this.polyCentre, this, evObj, lineWidth, lineColour)   
   def fillTextSubj(evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, align: TextAlign = TextCen):
      PolySubj = PolySubj.fillText(this.polyCentre, this, evObj, fillColour, str, fontSize, textColour, align)
    
   
   def fillContrastTextSubj(evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 10): PolySubj =
      fillTextSubj(evObj, fillColour, str, fontSize, fillColour.contrast)  
   def subj(evObj: AnyRef, elems: PaintElem[_]*): PolySubj = new PolySubj(this.polyCentre, this, evObj, elems.toList)
   def subjSeq(evObj: AnyRef, elems: List[PaintElem[_]]): PolySubj = new PolySubj(this.polyCentre, this, evObj, elems)
   def subjAll(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour, textSize: Int, str: String): PolySubj =
         PolySubj(this.polyCentre, this, evObj, List(PolyFillDraw(this, fillColour, lineWidth, lineColour),
               TextGraphic(this.polyCentre, str, textSize, lineColour)))
  
   def closedPolygonToLine2s: Line2s =
   {
      val res: Line2s = Line2s(length)   
      for (i <- 0 until (length -1)) res.setElem(i, Line2(apply(i), apply(i + 1)))      
      res.setLast(Line2(last, head))     
      res
   }
   
   def ptInPolygon(pt: Vec2): Boolean = {closedPolygonToLine2s.ptInPolygon(pt) } 
   
   /** Insert vertice */
   def insVert(insertionPoint: Int, newVec: Vec2): Vec2s =
   { val res = Vec2s.factory(length + 1)
     (0 until insertionPoint).foreach(i => res.setElem(i, apply(i)))
     res.setElem(insertionPoint, newVec)
     (insertionPoint until length).foreach(i => res.setElem(i + 1, apply(i)))
     res
   }
   
   /** Insert vertices */
   def insVerts(insertionPoint: Int, newVecs: Vec2 *): Vec2s =
   { val res = Vec2s.factory(length + newVecs.length)
     (0 until insertionPoint).foreach(i => res.setElem(i, apply(i)))
     newVecs.iForeach((elem, i) => res.setElem(insertionPoint + i, elem))     
     (insertionPoint until length).foreach(i => res.setElem(i + newVecs.length, apply(i)))
     res
   }
}

object Vec2s extends Double2sMaker[Vec2, Vec2s]
{
   implicit val factory: Int => Vec2s = i => new Vec2s(new Array[Double](i * 2))  
}
