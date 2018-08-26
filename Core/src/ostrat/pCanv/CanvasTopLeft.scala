/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._
import Colour.Black

/** This trait is for Canvas Implementations with a Top left origin and downward y axis. It should not be used by graphical applications. */
trait CanvasTopLeft extends CanvasPlatform
{
   protected def tlx: Double = width / 2
   protected def tly: Double = height / 2
   def tlCen: Vec2 =>  Vec2 = v => Vec2(tlx + v.x, tly - v.y)
   def fTl(pts: Vec2s): Vec2s = pts.fTrans(tlCen)
   
   override def polyFill(fp: PolyFill): Unit = tlPolyFill(fp.fTrans(tlCen))
   override def polyDraw(dp: PolyDraw): Unit = tlPolyDraw(dp.fTrans(tlCen))   
   override def polyFillDraw(pfd: PolyFillDraw): Unit = tlPolyFillDraw(pfd.fTrans(tlCen))   
   
   override def lineDraw(ld: LineDraw): Unit = tlLineDraw(ld.fTrans(tlCen))
   override def arcDraw(ad: ArcDraw): Unit = tlArcDraw(ad.fTrans(tlCen))
   override def bezierDraw(bd: BezierDraw): Unit = tlBezierDraw(bd.fTrans(tlCen))
   override def linesDraw(lineSegs: Line2s, lineWidth: Double, linesColour: Colour): Unit =
      tlLinesDraw(lineSegs.fTrans(tlCen), lineWidth, linesColour): Unit
   
   override def shapeFill(segs: List[CurveSeg], colour: Colour): Unit = tlShapeFill(segs.fTrans(tlCen), colour)
   override def shapeFillDraw(segs: List[CurveSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): Unit =
      tlShapeFillDraw(segs.fTrans(tlCen), fillColour, lineWidth, lineColour)
   override def shapeDraw(segs: List[CurveSeg], lineWidth: Double, lineColour: Colour): Unit =
      tlShapeDraw(segs.fTrans(tlCen), lineWidth, lineColour)   
   
   override def textGraphic(tg: TextGraphic): Unit = tlTextGraphic(tg.fTrans(tlCen))
   override def textOutline(tl: TextOutline): Unit = tlTextOutline(tl.fTrans(tlCen))
    
//   override def textFill(x: Double, y: Double, text: String,  fontSize: Int, colour: Colour): Unit =
//      tlTextFill(tlx + x, tly - y, text, fontSize, colour)
//   override def textDraw(x: Double, y: Double, text: String,  fontSize: Int, colour: Colour): Unit =
//      tlTextDraw(tlx + x, tly - y, text, fontSize, colour)
   override def clip(pts: Vec2s): Unit = tlClip(fTl(pts))   
   
   protected def tlPolyFill(fp: PolyFill): Unit
   protected def tlPolyDraw(dp: PolyDraw): Unit
   protected def tlPolyFillDraw(pfd: PolyFillDraw): Unit
   
   protected def tlLineDraw(ld: LineDraw): Unit
   protected def tlArcDraw(ad: ArcDraw): Unit
   
   protected def tlLinesDraw(lineSegs: Line2s, lineWidth: Double, linesColour: Colour): Unit

   protected def tlShapeFill(segs: List[CurveSeg], colour: Colour): Unit
   protected def tlShapeFillDraw(segs: List[CurveSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit
   protected def tlShapeDraw(segs: List[CurveSeg], lineWidth: Double, lineColour: Colour): Unit
   
   protected def tlBezierDraw(bezierDraw: BezierDraw): Unit 
   
   protected def tlTextGraphic(tg: TextGraphic): Unit
   protected def tlTextOutline(tl: TextOutline): Unit
//   protected def tlCircleFill(x: Double, y: Double, radius: Double, colour: Colour): Unit
   protected def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Vec2(x - width / 2, height / 2 - y), mb)
   protected def mouseDownTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDown(Vec2(x - width / 2, height / 2 - y), mb)
   protected def mouseMovedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseMoved(Vec2(x - width / 2, height / 2 - y), mb)
   protected def mouseDraggedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDragged(Vec2(x - width / 2, height / 2 - y), mb)
   protected def tlClip(pts: Vec2s): Unit
   
}