/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** This trait is for Canvas Implementations with a Top left origin and downward y axis. It should not be used by graphical applications. */
trait CanvasTopLeft extends CanvasPlatform
{   
   def tlCen: Vec2 =>  Vec2 = v => Vec2(width / 2 + v.x, height / 2 - v.y)
 
   final override def pPolyFill(fp: PolyFill): Unit = tlPolyFill(fp.fTrans(tlCen))
   final override def pPolyDraw(dp: PolyDraw): Unit = tlPolyDraw(dp.fTrans(tlCen))
   final override def pPolyFillDraw(pfd: PolyFillDraw): Unit = tlPolyFillDraw(pfd.fTrans(tlCen))
   final override def pLinePathDraw(pod: LinePathDraw): Unit = tlLinePathDraw(pod.fTrans(tlCen))
   final override def lineDraw(ld: LineDraw): Unit = tlLineDraw(ld.fTrans(tlCen))
   final override def arcDraw(ad: ArcDraw): Unit = tlArcDraw(ad.fTrans(tlCen))
   final override def bezierDraw(bd: BezierDraw): Unit = tlBezierDraw(bd.fTrans(tlCen))
   final override def linesDraw(lsd: LinesDraw): Unit = tlLinesDraw(lsd.fTrans(tlCen)): Unit
   final override def dashedLineDraw(dld: DashedLineDraw): Unit = tlDashedLineDraw(dld.fTrans(tlCen))
   
   final override def pShapeFill(sf: ShapeFill): Unit = tlShapeFill(sf.fTrans(tlCen))
   final override def pShapeDraw(sd: ShapeDraw): Unit = tlShapeDraw(sd.fTrans(tlCen))
   final override def pShapeFillDraw(sfd: ShapeFillDraw): Unit = tlShapeFillDraw(sfd.fTrans(tlCen))
   
   final override def textGraphic(tg: TextGraphic): Unit = tlTextGraphic(tg.fTrans(tlCen))
   final override def textOutline(tl: TextOutline): Unit = tlTextOutline(tl.fTrans(tlCen))
    
   final override def clip(pts: Polygon): Unit = tlClip(pts.fTrans(tlCen))
   
   protected[this] def tlPolyFill(fp: PolyFill): Unit
   protected[this] def tlPolyDraw(dp: PolyDraw): Unit
   protected[this] def tlPolyFillDraw(pfd: PolyFillDraw): Unit
   protected[this] def tlLinePathDraw(pod: LinePathDraw): Unit
   
   protected[this] def tlLineDraw(ld: LineDraw): Unit
   protected[this] def tlArcDraw(ad: ArcDraw): Unit
   
   protected[this] def tlLinesDraw(lsd: LinesDraw): Unit
   protected[this] def tlDashedLineDraw(dld: DashedLineDraw): Unit

   protected[this] def tlShapeFill(sf: ShapeFill): Unit
   protected[this] def tlShapeFillDraw(sfd: ShapeFillDraw): Unit
   protected[this] def tlShapeDraw(sd: ShapeDraw): Unit
   
   protected[this] def tlBezierDraw(bezierDraw: BezierDraw): Unit 
   
   protected[this] def tlTextGraphic(tg: TextGraphic): Unit
   protected[this] def tlTextOutline(tl: TextOutline): Unit

   protected[this] def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Vec2(x - width / 2, height / 2 - y), mb)
   protected[this] def mouseDownTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDown(Vec2(x - width / 2, height / 2 - y), mb)
   protected[this] def mouseMovedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseMoved(Vec2(x - width / 2, height / 2 - y), mb)
   protected[this] def mouseDraggedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDragged(Vec2(x - width / 2, height / 2 - y), mb)
   
   protected[this] def tlClip(pts: Polygon): Unit
   
}