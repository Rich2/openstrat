/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black, Double.{NegativeInfinity => NegInf, PositiveInfinity => PosInf}

/** Shape is an Array[Double] based collection for a sequence of CurveSegs, similar to a Polygon which is an Array[Double based collection of just
 *   LineSegs. It Uses 6 Doubles for each CurveSeg. The first Double of each curveSeg is set to Negative Infinity for a LineSeg positive infinity for
 *   an ArcSeg, but represents the x component of the first control point for a BezierSeg. */
class Shape(val arr: Array[Double]) extends AnyVal with DoubleProduct6s[CurveSeg] with Transable[Shape] //with Stringer
{ def typeSym = 'Shape
  //def str: String = persistD3
   override def typeName: Symbol = 'CurvedSeg
   override def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): CurveSeg = new CurveSeg(d1, d2, d3, d4, d5, d6)
   
   def fTrans(f: Vec2 => Vec2): Shape =
   {       
      val newArr = new Array[Double](length * 6)
      def setMiddle(offset: Int): Unit =
      {
         val newMiddle: Vec2 = f(arr(offset + 2) vv arr(offset + 3))
         newArr(offset + 2) = newMiddle.x
         newArr(offset + 3) = newMiddle.y
      }
      
      def setEnd(offset: Int): Unit =
      {
         val newEnd: Vec2 = f(arr(offset + 4) vv arr(offset + 5))
         newArr(offset + 4) = newEnd.x
         newArr(offset + 5) = newEnd.y
      }      
      
      (0 until length).foreach{index =>
         val offset = index * 6
         arr(offset) match
         {
           case NegInf => { newArr(offset) = NegInf; setEnd(offset) }
           case PosInf => { newArr(offset) = PosInf; setMiddle(offset); setEnd(offset) }   
            case d =>
            {
               val newControl1: Vec2 = f(arr(offset) vv arr(offset + 1)) 
               newArr(offset) = newControl1.x
               newArr(offset + 1) = newControl1.y
               setMiddle(offset)
               setEnd(offset)
            }
         }
      }
      new Shape(newArr)
   }
   
   def fill(colour: Colour): ShapeFill = ShapeFill(this, colour)
   def draw(lineWidth: Double, lineColour: Colour = Black) = ShapeDraw(this,lineWidth, lineColour)
   def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) = ShapeFillDraw(this, fillColour, lineWidth, lineColour)
   def fillDrawClick(evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): List[GraphicElem[_]] =
      List(ShapeFillDraw(this, fillColour, lineWidth, lineColour),
            ClickShape(this, evObj))
       
   def fillSlateable(colour: Colour, evObj: AnyRef, posn: Vec2 = Vec2Z): NoScaleShape =
      NoScaleShape(posn, this, evObj, List(ShapeFill(this, colour)))      
   def fillScale(colour: Colour, factor: Double): ShapeFill = ShapeFill(this.scale(factor), colour)
   def fillScaleSlate(colour: Colour, factor: Double, offset: Vec2): ShapeFill = ShapeFill(this.scale(factor).slate(offset), colour)
   
   /** Not sure if this method should be a member of Transable */
   def boundingRect =
   {
      //val t = Arc()
      var minX, maxX, minY, maxY = 0.0
      var i = 0
      this.foreach {ss =>
         val v = ss.pEnd
         if (i == 0)
         {
            minX = v.x
            maxX = v.x
            minY = v.y
            maxY = v.y
         }
         else
         {
            minX = minX.min(v.x)
            maxX = maxX.max(v.x)
            minY = minY.min(v.y)
            maxY = maxY.max(v.y)
         }
         i += 1
      }
      if (i == 0) throw new Exception("boundingRect method called on empty Vec2 collection") else {}
      BoundingRect(minX, maxX, minY, maxY)               
   }
   def ptInShape: Vec2 => Boolean = pt =>  pMap[Vec2, Polygon](_.pEnd).ptInPolygon(pt) 
   
   /** Not sure if this is useful */   
   def segForeach(fLineSeg: CurveSeg => Unit, fArcSeg: CurveSeg => Unit, fBezierSeg: CurveSeg => Unit): Unit =
      foreach(_.segDo(fLineSeg, fArcSeg, fBezierSeg))         
}

object Shape extends Double6sMaker[CurveSeg, Shape]
{
   implicit val factory: Int => Shape = i => new Shape(new Array[Double](i * 6))
   
}
