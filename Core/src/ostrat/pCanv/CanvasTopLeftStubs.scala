/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** This trait provides stub methods to allow development on a Canvas with incomplete functionality. Override the methods as desired but remove this
 *  trait form the inheritance hierarchy once full functionality has been implemented */
trait CanvasTopLeftStubs extends CanvasTopLeft
{
   override def clip(pts: Vec2s): Unit = {}
   override def getTime: Double = 0
   override protected def tlPolyFill(fp: PolyFill): Unit = {}
   override protected def tlPolyDraw(dp: PolyDraw): Unit = {}
   override protected def tlPolyFillDraw(fdp: PolyFillDraw): Unit = {}
   override protected def tlLinesDraw(lineSegs: Line2s, lineWidth: Double, linesColour: Colour): Unit = {}

   override protected def tlShapeFill(segs: List[CurveSeg], colour: Colour): Unit = {}
   override protected def tlShapeFillDraw(segs: List[CurveSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit = {}
   override protected def tlShapeDraw(segs: List[CurveSeg], lineWidth: Double, lineColour: Colour): Unit = {}
   override protected def tlArcDraw(ad: ArcDraw): Unit = {}
   
   override protected def tlTextGraphic(x: Double, y: Double, text: String, fontSize: Int, textColour: Colour, align: TextAlign): Unit = {}
   override protected def tlTextDraw(x: Double, y: Double, text: String, fontSize: Int, lineColour: Colour): Unit = {}
//   override protected def tlCircleFill(x: Double, y: Double, radius: Double, colour: Colour): Unit = {}
   //override protected def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Vec2(x - width / 2, height / 2 - y), mb)
   
   override protected def tlClip(pts: Vec2s): Unit = {}
   def clear(colour: Colour = Colour.White): Unit = {} 
   def gcSave(): Unit = {} 
   def gcRestore(): Unit = {}
   def timeOut(f: () => Unit, millis: Integer): Unit = {}
}