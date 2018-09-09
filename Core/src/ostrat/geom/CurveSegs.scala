/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Array based collection for CurveSegs. Uses 6 Doubles for each CurveSeg. It doesn't inherit from DoubleProduct6s, because CurveSeg is not a 
 *  DoubleProduct6 */
class CurveSegs(val arr: Array[Double]) extends AnyVal with DoubleProduct6s[CurveSeg] with Transable[CurveSegs]
{
   def length: Int = arr.length / 6
//   def fSeg[A](index: Int, fLine((Vec2): A =
//   {
//      val offset = index * 6
//      arr(offset) match
//      {
//         case d if d.isNaN => LineSeg(arr(offset + 4), arr(offset + 5))
//         case d if d.isInfinity => ArcSeg(arr(offset + 2), arr(offset + 3), arr(offset + 4), arr(offset + 5))
//         case d => BezierSeg(d, arr(offset + 1), arr(offset + 2), arr(offset + 3), arr(offset + 4), arr(offset + 5))
//      }
//   }   
   
   def fTrans(f: Vec2 => Vec2): CurveSegs =
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
            case d if d.isNaN => setEnd(offset)
            case d if d.isInfinity => { setMiddle(offset); setEnd(offset) }   
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
      new CurveSegs(newArr)
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
      def ptInShape: Vec2 => Boolean = pt =>  pMap[Vec2, Vec2s](_.pEnd).ptInPolygon(pt)      
}

object CurveSegs extends Double6sMaker[CurveSeg, CurveSegs]
{
   implicit val factory: Int => CurveSegs = i => new CurveSegs(new Array[Double](i * 6))
   
   //@inline def apply(inp: CurveSeg *): CurveSegs = make(inp)
//   def make(inp: Seq[CurveSeg]): CurveSegs =
//   {
//      val arr = new Array[Double](inp.length * 6)
//      inp.iForeach{ (el, i) =>
//         val offset = i * 6      
//         el match      
//         {
//         case ls: LineSeg =>
//            {
//               arr(offset) = Double.NaN
//               arr(offset + 4) = ls.xEnd
//               arr(offset + 5) = ls.yEnd
//            }
//         case as: ArcSeg =>
//            {
//              arr(offset) = Double.PositiveInfinity
//              arr(offset + 2) = as.xCen
//              arr(offset + 3) = as.yCen
//              arr(offset + 4) = as.xEnd
//              arr(offset + 5) = as.yEnd
//         
//            }
//         case bs: BezierSeg =>
//            {
//              arr(offset) = bs.xC1
//              arr(offset + 1) = bs.yC1 
//              arr(offset + 2) = bs.xC2
//              arr(offset + 3) = bs.yC2
//              arr(offset + 4) = bs.xEnd
//              arr(offset + 5) = bs.yEnd
//            }
//         }            
//      }
//      new CurveSegs(arr)
//   }
}
