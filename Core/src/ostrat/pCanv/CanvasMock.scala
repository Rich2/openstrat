/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** A class for testing mouse pointer functionality. */
case class CanvasMock(width: Double, height: Double) extends CanvasPlatform
{
   override def clip(pts: Vec2s): Unit = {}
   
   override def getTime: Double = ???
   /** A callback timer with an elapsed time from a given start point. The function is of form:
    *  (elapsedTime(in milliseconds), Startime(in millseconds) => Unit.
    *  The startTime is to be used to call the next frame at then end of the function, if another frame is needed */
   
   override def timeOut(f: () => Unit, millis: Integer): Unit = {}   
   override def polyFill(pf: PolyFill): Unit = {}    
   override def polyDraw(dp: PolyDraw): Unit = {}
   override def polyFillDraw(pfd: PolyFillDraw): Unit = {}
   override def polyOpenDraw(pod: PolyOpenDraw): Unit = {}
   
   override def lineDraw(ld: LineDraw): Unit = {}
   override def arcDraw(ad: ArcDraw): Unit = {}
   override def bezierDraw(bd: BezierDraw): Unit = {}
   override def linesDraw(lsd: LinesDraw): Unit = {}
   override def shapeFill(sf: ShapeFill): Unit = {}
   override def shapeFillDraw(segs: Shape, fillColour: Colour, lineWidth: Double, borderColour: Colour = Colour.Black): Unit = {}
   override def shapeDraw(sd: ShapeDraw): Unit = {}
   override def textGraphic(tg: TextGraphic) = {} 
   override def textOutline(to: TextOutline): Unit = {}  
   override def clear(colour: Colour = Colour.White): Unit = {}  
   override def gcSave(): Unit = {}
   override def gcRestore(): Unit = {} 
   override def saveFile(fileName: String, output: String): Unit = {}
   override def loadFile(fileName: String): EMon[String] = ???
}