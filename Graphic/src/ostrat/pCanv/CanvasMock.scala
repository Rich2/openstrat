/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** A class for testing mouse pointer functionality. */
case class CanvasMock(width: Double, height: Double) extends CanvasPlatform
{
   override def clip(pts: Polygon): Unit = {}
   
   override def getTime: Long = ???
   /** A callback timer with an elapsed time from a given start point. The function is of form:
    *  (elapsedTime(in milliseconds), Startime(in millseconds) => Unit.
    *  The startTime is to be used to call the next frame at then end of the function, if another frame is needed */
   
   override def timeOut(f: () => Unit, millis: Integer): Unit = {}   
   override def pPolyFill(pf: PolyFill): Unit = {}    
   override def pPolyDraw(dp: PolyDraw): Unit = {}
   override def pPolyFillDraw(pfd: PolyFillDraw): Unit = {}
   override def pVec2sDraw(pod: Vec2sDraw): Unit = {}
   
   override def lineDraw(ld: LineDraw): Unit = {}
   override def arcDraw(ad: ArcDraw): Unit = {}
   override def bezierDraw(bd: BezierDraw): Unit = {}
   override def linesDraw(lsd: LinesDraw): Unit = {}
   override def pShapeFill(sf: ShapeFill): Unit = {}
   override def pShapeFillDraw(sfd: ShapeFillDraw): Unit = {}
   override def pShapeDraw(sd: ShapeDraw): Unit = {}
   override def textGraphic(tg: TextGraphic) = {} 
   override def textOutline(to: TextOutline): Unit = {}
   override def dashedLineDraw(dld: DashedLineDraw): Unit = {}
   
   override def clear(colour: Colour = Colour.White): Unit = {}  
   override def gcSave(): Unit = {}
   override def gcRestore(): Unit = {} 
   override def saveFile(fileName: String, output: String): Unit = {}
   override def loadFile(fileName: String): EMon[String] = ???
}