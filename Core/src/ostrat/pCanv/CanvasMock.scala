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
   override def arcDraw(arc: Arc, lineWidth: Double, lineColour: Colour): Unit = {}
   override def bezierDraw(bd: BezierDraw): Unit = {}
   override def linesDraw(lineSegs: Line2s, lineWidth: Double, linesColour: Colour): Unit = {}
   override def shapeFill(segs: List[ShapeSeg], fillColour: Colour): Unit = {}
   override def shapeFillDraw(segs: List[ShapeSeg], fillColour: Colour, lineWidth: Double, borderColour: Colour = Colour.Black): Unit = {}
   override def shapeDraw(segs: List[ShapeSeg], lineWidth: Double, borderColour: Colour = Colour.Black): Unit = {}
   override def textGraphic(posn: Vec2, text: String, fontSize: Int, colour: Colour = Colour.Black, align: TextAlign = TextCen): Unit = {} 
   override def textOutline(posn: Vec2, text: String,  fontSize: Int, colour: Colour = Colour.Black): Unit = {}  
   override def clear(colour: Colour = Colour.White): Unit = {}  
   override def gcSave(): Unit = {}
   override def gcRestore(): Unit = {} 
   override def saveFile(fileName: String, output: String): Unit = {}
   override def loadFile(fileName: String): EMon[String] = ???
}