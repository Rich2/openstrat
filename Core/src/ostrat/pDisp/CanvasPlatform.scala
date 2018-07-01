/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDisp
import geom._
import Colour.Black

/** An abstract Canvas trait. A concrete implementation will utilise  canvas like an HTML canvas or a Scalafx canvas. This concrete implementation
 *  class must be mixed in with a a particular use trait like CanvSimple or CanvMulti .The default methods take the origin as the centre of the
 *  canvas. */
trait CanvasPlatform extends RectGeom
{
   /** The canvas implementation will call this function when a mouse button is released. Named after Javascript command */
   var mouseUp: (Vec2, MouseButton) => Unit = (v, b) => {}
   /** The canvas implementation will call this function when the mouse button is depressed. Named after Javascript command */
   var mouseDown: (Vec2, MouseButton) => Unit = (v, b) => {}
   var mouseMoved: (Vec2, MouseButton) => Unit = (v, b) => {}
   var mouseDragged: (Vec2, MouseButton) => Unit = (v, b) => {}
   var onScroll: Boolean => Unit = b => {}
   var resize: () => Unit = () => {}
   def clip(pts: Vec2s): Unit
   /** Returns the time in milliseconds */
   def getTime: Double
   /** A callback timer with an elapsed time from a given start point. The function is of form:
    *  (elapsedTime(in milliseconds), Startime(in millseconds) => Unit.
    *  The startTime is to be used to call the next frame at then end of the function, if another frame is needed */
   def frame(f: (Double, Double) => Unit, startTime: Double, frameLength: Integer = 15): Unit =
      timeOut(() => f(getTime - startTime, startTime), frameLength)   
   def frameZero(f: (Double, Double) => Unit, frameLength: Integer = 15): Unit = frame(f, getTime)
   /** A call back timer. Takes the delay in milliseconds */
   def timeOut(f: () => Unit, millis: Integer): Unit  
   var textMin: Int = 10
   final def fillPoly(colour: Colour, pts: Vec2s): Unit = fillPolya(FillPoly(colour, pts))
   def fillPolya(pf: FillPoly): Unit
   def polyDraw(pts: Vec2s, lineWidth: Double, linerColour: Colour = Black): Unit
   def polyFillDraw(pts: Vec2s, fillColour: Colour, lineWidth: Double, borderColour: Colour = Colour.Black): Unit
   def polyFillDrawText(pts: Vec2s, fillColour: Colour, lineWidth: Double, borderColour: Colour, str: String, fontSize: Int,
         fontColour: Colour = Black): Unit =
   {
      polyFillDraw(pts: Vec2s, fillColour, lineWidth, borderColour)
      textFill(pts.polyCentre, str, fontSize, fontColour) 
   }
   def polyFillText(pts: Vec2s, fillColour: Colour, str: String, fontSize: Int, fontColour: Colour = Black): Unit =
   {
      fillPoly(fillColour, pts)//: Vec2s, fillColour)
      textFill(pts.polyCentre, str, fontSize, fontColour) 
   }
   
   def polyDrawText(pts: Vec2s, lineWidth: Double, borderColour: Colour, str: String, fontSize: Int, fontColour: Colour = Black): Unit =
   {
      polyDraw(pts: Vec2s, lineWidth, borderColour)
      textFill(pts.polyCentre, str, fontSize, fontColour) 
   }
   
   def arcDraw(arc: Arc, lineWidth: Double, lineColour: Colour): Unit
   def lineSegsDraw(lineSegs: Seq[Line2], lineWidth: Double, linesColour: Colour): Unit
   def shapeFill(segs: Seq[ShapeSeg], fillColour: Colour): Unit
   def shapeFillDraw(segs: Seq[ShapeSeg], fillColour: Colour, lineWidth: Double, borderColour: Colour = Colour.Black): Unit
   def shapeDraw(segs: Seq[ShapeSeg], lineWidth: Double, borderColour: Colour = Colour.Black): Unit   
   def textFill(posn: Vec2, text: String, fontSize: Int, colour: Colour = Colour.Black, align: TextAlign = TextCen): Unit 
   def textDraw(posn: Vec2, text: String,  fontSize: Int, colour: Colour = Colour.Black): Unit  
//   def textFill(x: Double, y: Double, text: String,  fontSize: Int, colour: Colour): Unit
//   def textDraw(x: Double, y: Double, text: String,  fontSize: Int, colour: Colour): Unit
   
   def toBL(input: Vec2): Vec2 = Vec2(input.x, height - input.y)      
   
   def animSeq(anims: Seq[DispPhase]): Unit = anims match
   {
      case Seq() => 
      case Seq(DispStill(f), _*) => f()   
      case Seq(DispAnim(fAnim, secs), tail @ _*) =>
      {
         val start = getTime
         def func(): Unit =  
         {            
            val curr = getTime
            val elapsed = (curr - start) / 1000
            fAnim(elapsed)
            if (elapsed < secs) timeOut(() => func(), 30) else animSeq(tail)
         }         
         timeOut(() => func(), 30)
      }
   }

   def clear(colour: Colour = Colour.White): Unit   
   def gcSave(): Unit
   def gcRestore(): Unit 
   def saveFile(fileName: String, output: String): Unit
   def loadFile(fileName: String): EMon[String]
   def fromFileFind[A: Persist](fileName: String): EMon[A] = loadFile(fileName).findType
   def fromFileFindElse[A: Persist](fileName: String, elseValue: => A): A = fromFileFind(fileName).getElse(elseValue)
   /** Attempts to find find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls 
    *  procedure (Unit returning function) with that object of type A */
   def fromFileFindForeach[A: Persist](fileName: String, f: A => Unit): Unit = fromFileFind(fileName).foreach(f) 
    
   def rendElems(elems: Seq[CanvEl[_]]): Unit = elems.foreach(rendElem) 
   def rendElem(el: CanvEl[_]): Unit = el match
   {
      case fp: FillPoly => fillPolya(fp)//verts, fillColour)
      case DrawPoly(verts, lineWidth, lineColour) => polyDraw(verts, lineWidth, lineColour)
      case FillDrawPoly(verts, fillColour, lineWidth, lineColour) => polyFillDraw(verts, fillColour, lineWidth, lineColour)
      case PolyLineDraw(lines, lineWidth, lineColour) => lineSegsDraw(lines, lineWidth, lineColour)
      case LineDraw(line, lineWidth, lineColour) => lineSegsDraw(Seq(line), lineWidth, lineColour)
      case FillShape(segs, fillColour) => shapeFill(segs, fillColour)
      case DrawShape(segs, lineWidth, lineColour)  => shapeDraw(segs, lineWidth, lineColour)
      case FillDrawShape(segs, fillColour, lineWidth, lineColour) => shapeFillDraw(segs, fillColour, lineWidth, lineColour) 
      case DrawArc(arc, lineWidth, lineColour) => arcDraw(arc, lineWidth, lineColour)
      case FillText(posn, text, fontSize, colour, align) => textFill(posn, text, fontSize, colour, align)
      case FillDrawTextPoly(verts, fillColour, lineWidth, lineColour, str, fontSize, fontColour) =>
         polyFillDrawText(verts, fillColour, lineWidth, lineColour, str, fontSize, fontColour)
      case FillTextPoly(verts, fillColour, str, fontSize, fontColour) => polyFillText(verts, fillColour, str, fontSize, fontColour)   
      case DrawTextPoly(verts, lineWidth, lineColour, str, fontSize, fontColour) =>
         polyDrawText(verts, lineWidth, lineColour, str, fontSize, fontColour)   
   }    
}
