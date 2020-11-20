/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCanv
import geom._

/** This trait is for Canvas Implementations with a Top left origin and downward y axis. It should not be used directly by graphical applications. */
trait CanvasTopLeft extends CanvasPlatform
{
  def tlCen: Pt2 => Pt2 = v => Pt2(width / 2 + v.x, height / 2 - v.y)
  def matrix: ProlignMatrix = ProlignMatrix.mirrorY.slate(width / 2, height / 2)
 
  final override def pPolyFill(pf: PolygonFill): Unit = tlPolyFill(pf.negY.slate(width / 2, height / 2))
  final override def pPolyDraw(pd: PolygonDraw): Unit = tlPolyDraw(pd.negY.slate(width / 2, height / 2))
  final override def pLinePathDraw(pod: LinePathDraw): Unit = tlLinePathDraw(pod.fTrans(tlCen))
  final override def lineDraw(ld: LineSegDraw): Unit = tlLineDraw(ld.fTrans(tlCen))

  final override def cArcDrawOld(ad: CArcDrawOld): Unit = tlCArcDrawOld(ad.fTrans(tlCen))
  final override def cArcDraw(cad: CArcDraw): Unit = tlCArcDraw(cad.negY.slate(width / 2, height / 2))
  final override def cArcDraw3(cad: CArcDraw3): Unit = tlCArcDraw3(cad.negY.slate(width / 2, height / 2))

  final override def bezierDraw(bd: BezierDraw): Unit = tlBezierDraw(bd.fTrans(tlCen))
  final override def lineSegsDraw(lsd: LinesDraw): Unit = tlLinesDraw(lsd.fTrans(tlCen)): Unit
  final override def dashedLineDraw(dld: DashedLineDraw): Unit = tlDashedLineDraw(dld.fTrans(tlCen))

  final override def pShapeGenFill(sgf: ShapeGenFill): Unit = tlShapeFill(sgf.negY.slate(width / 2, height / 2))

  final override def pShapeGenDraw(sgd: ShapeGenDraw): Unit = tlShapeDraw(sgd.negY.slate(width / 2, height / 2))
 
  final override def circleFill(cf: CircleFill): Unit = tlCircleFill(cf.negY.slate(width / 2, height / 2))

  final override def circleFillRadial(circle: Circle, fill: FillRadial): Unit =
    tlCircleFillRadial(circle.negY.slate(width / 2, height / 2), fill)

  final override def circleDraw(cd: CircleDraw): Unit = tlCircleDraw(cd.negY.slate(width / 2, height / 2))

  final override def ellipseFill(ef: EllipseFill): Unit = tlEllipseFill(ef.negY.slate(width / 2, height / 2))

  final override def ellipseDraw(ellipse: Ellipse, colour: Colour, lineWidth: Double): Unit =
    tlEllipseDraw(ellipse.negY.slate(width / 2, height / 2), lineWidth, colour)

  final override def textGraphic(tg: TextGraphic): Unit = tlTextGraphic(tg.fTrans(tlCen))
  final override def textOutline(tl: TextOutline): Unit = tlTextOutline(tl.fTrans(tlCen))

  final override def clip(pts: Polygon): Unit = tlClip(pts.fTrans(tlCen))

  protected[this] def tlPolyFill(pf: PolygonFill): Unit
  protected[this] def tlPolyDraw(pd: PolygonDraw): Unit
  protected[this] def tlLinePathDraw(pod: LinePathDraw): Unit

  protected[this] def tlLineDraw(ld: LineSegDraw): Unit

  protected[this] def tlCArcDrawOld(ad: CArcDrawOld): Unit
  protected[this] def tlCArcDraw(tld: CArcDraw): Unit
  protected[this] def tlCArcDraw3(tld: CArcDraw3): Unit

  protected[this] def tlLinesDraw(lsd: LinesDraw): Unit
  protected[this] def tlDashedLineDraw(dld: DashedLineDraw): Unit

  protected[this] def tlShapeFill(sgf: ShapeGenFill): Unit

  protected[this] def tlShapeDraw(sgd: ShapeGenDraw): Unit

  protected[this] def tlCircleFill(cf: CircleFill): Unit
  protected[this] def tlCircleFillRadial(circle: Circle, fill: FillRadial): Unit
  protected[this] def tlCircleDraw(cd: CircleDraw): Unit
  
  protected[this] def tlEllipseFill(ef: EllipseFill): Unit
  protected[this] def tlEllipseDraw(ellipse: Ellipse, lineWidth: Double, lineColour: Colour): Unit
  protected[this] def tlBezierDraw(bezierDraw: BezierDraw): Unit
   
  protected[this] def tlTextGraphic(tg: TextGraphic): Unit
  protected[this] def tlTextOutline(tl: TextOutline): Unit

  protected[this] def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Pt2(x - width / 2, height / 2 - y), mb)
  protected[this] def mouseDownTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDown(Pt2(x - width / 2, height / 2 - y), mb)
  protected[this] def mouseMovedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseMoved(Pt2(x - width / 2, height / 2 - y), mb)
  protected[this] def mouseDraggedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDragged(Pt2(x - width / 2, height / 2 - y), mb)
   
  protected[this] def tlClip(pts: Polygon): Unit
}