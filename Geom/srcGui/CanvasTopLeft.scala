/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._

/** This trait is for Canvas Implementations with a Top left origin and downward y axis. It should not be used directly by graphical applications. */
trait CanvasTopLeft extends CanvasPlatform
{
  def tlCen: Pt2 => Pt2 = v => Pt2(width / 2 + v.x, height / 2 - v.y)
  def matrix: ProlignMatrix = ProlignMatrix.mirrorY.slate(width / 2, height / 2)
 
  final override def pPolyFill(pf: PolygonFill): Unit = tlPolyFill(pf.negY.slateXY(width / 2, height / 2))
  final override def pPolyDraw(pd: PolygonDraw): Unit = tlPolyDraw(pd.negY.slateXY(width / 2, height / 2))
  final override def pLinePathDraw(pod: LinePathDraw): Unit = tlLinePathDraw(pod.ptsTrans(tlCen))
  final override def lineSegDraw(ld: LineSegDraw): Unit = tlLineDraw(ld.ptsTrans(tlCen))
  final override def cArcDraw(cad: CArcDraw): Unit = tlCArcDraw(cad.negY.slateXY(width / 2, height / 2))
  final override def eArcDraw(ead: EArcDraw): Unit = tlEArcDraw(ead.negY.slateXY(width / 2, height / 2))

  final override def bezierDraw(bd: BezierDraw): Unit = tlBezierDraw(bd.ptsTrans(tlCen))
  final override def lineSegsDraw(lsd: LinesDraw): Unit = tlLinesDraw(lsd.ptsTrans(tlCen)): Unit
  final override def dashedLineDraw(dld: DashedLineDraw): Unit = tlDashedLineDraw(dld.ptsTrans(tlCen))

  final override def pShapeGenFill(sgf: ShapeGenFillOld): Unit = tlShapeFill(sgf.negY.slateXY(width / 2, height / 2))

  final override def pShapeGenDraw(sgd: ShapeGenDrawOld): Unit = tlShapeDraw(sgd.negY.slateXY(width / 2, height / 2))

  final override def circleFill(cf: CircleFill): Unit = tlCircleFill(cf.negY.slateXY(width / 2, height / 2))

  final override def circleFillRadial(circle: Circle, fill: FillRadial): Unit =
    tlCircleFillRadial(circle.negY.slateXY(width / 2, height / 2), fill)

  final override def circleDraw(cd: CircleDraw): Unit = tlCircleDraw(cd.negY.slateXY(width / 2, height / 2))

  final override def ellipseFill(ef: EllipseFill): Unit = tlEllipseFill(ef.negY.slateXY(width / 2, height / 2))

  final override def ellipseDraw(ed: EllipseDraw): Unit = tlEllipseDraw(ed.negY.slateXY(width / 2, height / 2))

  final override def textGraphic(tg: TextGraphic): Unit = tlTextGraphic(tg.negY.slateXY(width / 2, height / 2))
  final override def textOutline(tl: TextOutline): Unit = tlTextOutline(tl.ptsTrans(tlCen))

  final override def clip(pts: Polygon): Unit = tlClip(pts.map(tlCen))

  protected def tlPolyFill(pf: PolygonFill): Unit
  protected def tlPolyDraw(pd: PolygonDraw): Unit
  protected def tlLinePathDraw(pod: LinePathDraw): Unit

  protected def tlLineDraw(ld: LineSegDraw): Unit

  protected def tlCArcDraw(cad: CArcDraw): Unit
  protected def tlEArcDraw(ead: EArcDraw): Unit

  protected def tlLinesDraw(lsd: LinesDraw): Unit
  protected def tlDashedLineDraw(dld: DashedLineDraw): Unit

  protected def tlShapeFill(sgf: ShapeGenFillOld): Unit

  protected def tlShapeDraw(sgd: ShapeGenDrawOld): Unit

  protected def tlCircleFill(cf: CircleFill): Unit
  protected def tlCircleFillRadial(circle: Circle, fill: FillRadial): Unit
  protected def tlCircleDraw(cd: CircleDraw): Unit
  
  protected def tlEllipseFill(ef: EllipseFill): Unit
  protected def tlEllipseDraw(ed: EllipseDraw): Unit
  protected def tlBezierDraw(bezierDraw: BezierDraw): Unit
   
  protected def tlTextGraphic(tg: TextGraphic): Unit
  protected def tlTextOutline(tl: TextOutline): Unit

  protected def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Pt2(x - width / 2, height / 2 - y), mb)
  protected def mouseDownTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDown(Pt2(x - width / 2, height / 2 - y), mb)
  protected def mouseMovedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseMoved(Pt2(x - width / 2, height / 2 - y), mb)
  protected def mouseDraggedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDragged(Pt2(x - width / 2, height / 2 - y), mb)
   
  protected def tlClip(pts: Polygon): Unit
}