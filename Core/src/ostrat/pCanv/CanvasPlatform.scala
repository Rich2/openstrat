/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._
import Colour._

/** An abstract Canvas trait. A concrete implementation will utilise  canvas like an HTML canvas or a Scalafx canvas. This concrete implementation
 *  class must (can?) be mixed in with a a particular use trait like CanvSimple or CanvMulti .The default methods take the origin as the centre of the
 *  canvas. Note the Canvas Platform merely passes bare pointer event data to delegate functions. It does not process them in relation to objects
 *  painted on the Canvas. */
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
   def clip(pts: Polygon): Unit
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
   
   final def polyFill(colour: Colour, verts: Vec2 *): Unit = polyFill(verts.toPolygon.fill(colour))
   def polyFill(pf: PolyFill): Unit
   final def polyDraw(lineWidth: Double, lineColour: Colour, verts: Vec2 *): Unit = polyDraw(verts.toPolygon.draw(lineWidth, lineColour)) 
   def polyDraw(dp: PolyDraw): Unit
   def polyFillDraw(pfd: PolyFillDraw): Unit
   def polyDrawText(pts: Polygon, lineWidth: Double, borderColour: Colour, str: String, fontSize: Int, fontColour: Colour = Black): Unit =
   {
      //polyDraw(lineWidth, borderColour, pts: Polygon)
      textGraphic(pts.polyCentre, str, fontSize, fontColour) 
   }
   def polyOpenDraw(pod: PolyOpenDraw): Unit
   def polyOpenDraw(lineWidth: Double, colour: Colour, pStart: Vec2, pEnds: Vec2 *): Unit = ???
   
   def lineDraw(ld: LineDraw): Unit
   def lineDraw(pStart: Vec2, pEnd: Vec2, lineWidth: Double = 1.0, colour: Colour = Black): Unit = lineDraw(LineDraw(pStart, pEnd, lineWidth, colour))
   
   def arcDraw(ad: ArcDraw): Unit
   def arcDraw(pStart: Vec2, pCen: Vec2, pEnd: Vec2, lineWidth: Double = 1, colour: Colour = Black): Unit =
      arcDraw(ArcDraw(pStart, pCen, pEnd, lineWidth, colour))
   
   def bezierDraw(bd: BezierDraw): Unit
   def bezierDraw(pStart: Vec2, pEnd: Vec2, pControl1: Vec2, pControl2: Vec2, lineWidth: Double = 1, colour: Colour = Black): Unit =
      bezierDraw(BezierDraw(pStart, pEnd, pControl1, pControl2, lineWidth, colour))

   def linesDraw(lsd: LinesDraw): Unit
   final def linesDraw(lineWidth: Double, linesColour: Colour, lines: Line2 *): Unit = linesDraw(LinesDraw(Line2s(lines: _*), lineWidth, linesColour))
   
   def shapeFill(sf: ShapeFill): Unit
   final def shapeFill(fillColour: Colour, segs: CurveSeg *): Unit = shapeFill(ShapeFill(Shape(segs: _*), fillColour)) 
   
   def shapeDraw(sd: ShapeDraw): Unit
   final def shapeDraw(lineWidth: Double, borderColour: Colour, segs: CurveSeg *): Unit = 
     shapeDraw(ShapeDraw(Shape(segs: _*), lineWidth, borderColour))
   def shapeFillDraw(segs: Shape, fillColour: Colour, lineWidth: Double, borderColour: Colour = Black): Unit   
   
   
   def textGraphic(tg: TextGraphic): Unit
   final def textGraphic(posn: Vec2, text: String, fontSize: Int, colour: Colour = Black, align: TextAlign = TextCen): Unit =
      textGraphic(TextGraphic(posn, text, fontSize, colour, align))
   def textOutline(to: TextOutline): Unit
   final def textOutline(posn: Vec2, text: String, fontSize: Int, colour: Colour, lineWidth: Double, align: TextAlign = TextCen): Unit =
      textOutline(TextOutline(posn, text, fontSize, colour, lineWidth, align))
      
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

   def clear(colour: Colour = White): Unit   
   def gcSave(): Unit
   def gcRestore(): Unit 
   def saveFile(fileName: String, output: String): Unit
   def loadFile(fileName: String): EMon[String]
   def fromFileFind[A: Persist](fileName: String): EMon[A] = loadFile(fileName).findType
   def fromFileFindElse[A: Persist](fileName: String, elseValue: => A): A = fromFileFind(fileName).getElse(elseValue)
   /** Attempts to find find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls 
    *  procedure (Unit returning function) with that object of type A */
   def fromFileFindForeach[A: Persist](fileName: String, f: A => Unit): Unit = fromFileFind(fileName).foreach(f) 
    
   def rendElems(elems: List[PaintElem[_]]): Unit = elems.foreach(rendElem) 
   def rendElem(el: PaintElem[_]): Unit = el match
   {
      case fp: PolyFill => polyFill(fp)//verts, fillColour)
      case dp: PolyDraw => polyDraw(dp)// (verts, lineWidth, lineColour) => polyDraw(verts, lineWidth, lineColour)
      case pfd: PolyFillDraw => polyFillDraw(pfd)
      case lsd: LinesDraw => linesDraw(lsd)
      case ld: LineDraw => lineDraw(ld)
      case sf: ShapeFill => shapeFill(sf)
      case sd: ShapeDraw => shapeDraw(sd)
      case ShapeFillDraw(segs, fillColour, lineWidth, lineColour) => shapeFillDraw(segs, fillColour, lineWidth, lineColour) 
      case ad: ArcDraw => arcDraw(ad)
      case bd: BezierDraw => bezierDraw(bd)
      case TextGraphic(posn, text, fontSize, colour, align) => textGraphic(posn, text, fontSize, colour, align)
   }    
}
