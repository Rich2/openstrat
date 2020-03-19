/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** This trait provides stub methods to allow development on a Canvas with incomplete functionality. Override the methods as desired but remove this
 *  trait form the inheritance hierarchy once full functionality has been implemented */
trait CanvasTopLeftStubs extends CanvasTopLeft
{ override def getTime: Long = 0
  override protected def tlPolyFill(poly: Polygon, colour: Colour): Unit = {}
  override protected def tlPolyDraw(poly: Polygon, lineWidth: Double, colour: Colour): Unit = {}
  override protected def tlLinesDraw(lsd: LinesDraw): Unit = {}

  override protected def tlShapeFill(shape: Shape, colour: Colour): Unit = {}
  override protected def tlShapeDraw(shape: Shape, lineWidth: Double, colour: Colour): Unit = {}
  override protected def tlArcDraw(ad: ArcDraw): Unit = {}
   
  override protected def tlTextGraphic(tg: TextGraphic): Unit = {}
  override protected def tlTextOutline(to: TextOutline): Unit = {}
//   override protected def tlCircleFill(x: Double, y: Double, radius: Double, colour: Colour): Unit = {}
   //override protected def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Vec2(x - width / 2, height / 2 - y), mb)
   
   override protected def tlClip(pts: Polygon): Unit = {}
   def clear(colour: Colour = Colour.White): Unit = {} 
   def gcSave(): Unit = {} 
   def gcRestore(): Unit = {}
   def timeOut(f: () => Unit, millis: Integer): Unit = {}
}