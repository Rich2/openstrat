/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pDisp._

/** A plain 2 dimension (mathematical) vector */
class Vec2s(val arr: Array[Double]) extends AnyVal with  DoubleProduct2s[Vec2] with Transable[Vec2s]
{
   override def typeName: Symbol = 'Vec2s   
   override def newElem(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
   def fTrans(f: Vec2 => Vec2): Vec2s = pMap(f)
  // def vec2Map(f: (Double, Double) => (Double, Double)) = xyMap[Vec2, Vec2s](f)(Vec2s.factory)
   def addMap(xOperand: Double, yOperand: Double): Vec2s = pMap(_.addXY(xOperand, yOperand))
   
   //  implicit class Vec2seqImplicit(this: Vec2s) extends Transable[Vec2s]

 //    def fTrans(f: Vec2 => Vec2): Vec2s = this.map(f)

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
   def fill(colour: Colour): FillPoly = FillPoly(colour, this)
   def draw(lineWidth: Double, lineColour: Colour = Black): DrawPoly = DrawPoly(lineWidth, lineColour, this)
   def fillDraw(fillColour: Colour, lineWidth: Double = 1.0, lineColour: Colour = Black): FillDrawPoly =
      FillDrawPoly(this, fillColour, lineWidth, lineColour)
   def fillDrawText(fillColour: Colour, lineWidth: Double, lineColour: Colour, str: String, fontSize: Int, fontColour: Colour = Black) = 
      FillDrawTextPoly(this, fillColour, lineWidth, lineColour, str, fontSize, fontColour)
      
   def fillText(fillColour: Colour, str: String, fontSize: Int, fontColour: Colour = Black) =
      FillTextPoly(this, fillColour, str, fontSize, fontColour)
   def fillTextContrast(fillColour: Colour, str: String, fontSize: Int) =
      FillTextPoly(this, fillColour, str, fontSize, fillColour.contrast)      
     
  def fillTextBWContrast(fillColour: Colour, str: String, fontSize: Int) =
      FillTextPoly(this, fillColour, str, fontSize, fillColour.contrastBW)    
//  def fillTextContrast(fillColour: Colour, str: String, fontSize: Int) =
//      FillTextPoly(this, fillColour, str, fontSize, fillColour.contrast)    
      
   def drawText(lineWidth: Double, lineColour: Colour, str: String, fontSize: Int, fontColour: Colour = Black) = 
      DrawTextPoly(this, lineWidth, lineColour, str, fontSize, fontColour)   
   def fillSubj(evObj: AnyRef, fillColour: Colour): PolySubj = PolySubj.fill(this.polyCentre, this, evObj, fillColour)
   def fillDrawSubj(evObj: AnyRef, fillColour: Colour, lineWidth:  Double, lineColour: Colour = Black): PolySubj =
      PolySubj.fillDraw(this.polyCentre, this, evObj, fillColour, lineWidth, lineColour)
    def drawSubj(evObj: AnyRef, lineWidth:  Double, lineColour: Colour = Black): PolySubj =
      PolySubj.draw(this.polyCentre, this, evObj, lineWidth, lineColour)  
   
   def fillTextSubj(evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 10, textColour: Colour = Black, align: TextAlign = TextCen):
      PolySubj = PolySubj.fillText(this.polyCentre, this, evObj, fillColour, str, fontSize, textColour, align)
   
   def fillContrastTextSubj(evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 10): PolySubj =
      fillTextSubj(evObj, fillColour, str, fontSize, fillColour.contrast)  
   def subj(evObj: AnyRef, elems: CanvEl[_]*): PolySubj = new PolySubj(this.polyCentre, this, evObj, elems)
   def subjSeq(evObj: AnyRef, elems: Seq[CanvEl[_]]): PolySubj = new PolySubj(this.polyCentre, this, evObj, elems)
   def subjAll(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour, textSize: Int, str: String): PolySubj =
         PolySubj(this.polyCentre, this, evObj, Seq(FillDrawPoly(this, fillColour, lineWidth, lineColour), FillText(Vec2Z, str, textSize, lineColour)))
  
   def closedPolygonToLine2s: Line2s =
   {
      val res: Line2s = Line2s(length)
     // println(toString)
      for (i <- 0 until (length -1)) res.setElem(i, Line2(apply(i), apply(i + 1)))      
      res.setLast(Line2(last, head))
     // res.foreach(println)
      res
   }
   
   def ptInPolygon(pt: Vec2): Boolean = {closedPolygonToLine2s.ptInPolygon(pt) }
//      {     
//         val rayIntersections: List[Line2] = by2To1LMap(Line2(_, _)).filter(ls => 
//         )        
//         rayIntersections.length % 2 == 1 //For a convex polygon the ray can only cross one side if inside
//      }

//   implicit class ImplicitVec2SeqSeq(this: Seq[Seq[Vec2]])
//   {
//      def fill(colour: Colour): Seq[FillPoly] = this.map(s => FillPoly(s, colour))
//      //def fillSubj(evObj: AnyRef, colour: Colour): PolySubj = PolySubj.fill(vSeq, evObj, colour)
   //}
   
}

object Vec2s extends Double2sMaker[Vec2, Vec2s]
{
//   def apply(inp: Vec2 *): Vec2s =
//   {
//      val arr = new Array[Double](inp.length * 2)
//      var count = 0
//      while (count < inp.length)
//      {
//         arr(count * 2) = inp(count).x
//         arr(count * 2 + 1) = inp(count).y
//      }
//      new Vec2s(arr)
//   }
   implicit val factory: Int => Vec2s = i => new Vec2s(new Array[Double](i * 2))
   @inline def xy(inp: Double *): Vec2s = doubles(inp: _*)
}

