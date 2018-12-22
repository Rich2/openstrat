/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._
import Colour._

/** An abstract Canvas trait. A concrete implementation will utilise canvas like an HTML canvas or a Scalafx canvas. This concrete implementation
 *  class must (can?) be mixed in with a a particular use trait like CanvSimple or CanvMulti. The default methods take the origin as the centre of the
 *  canvas. Note the Canvas Platform merely passes bare pointer event data to delegate functions. It does not process them in relation to objects
 *  painted on the Canvas. */
trait CanvasPlatform extends RectGeom
{ /** The canvas implementation will call this function when a mouse button is released. Named after Javascript command */
  var mouseUp: (Vec2, MouseButton) => Unit = (v, b) => {}
  /** The canvas implementation will call this function when the mouse button is depressed. Named after Javascript command */
  var mouseDown: (Vec2, MouseButton) => Unit = (v, b) => {}
  var mouseMoved: (Vec2, MouseButton) => Unit = (v, b) => {}
  var mouseDragged: (Vec2, MouseButton) => Unit = (v, b) => {}
  var keyReleased: () => Unit = () => {}
  var onScroll: Boolean => Unit = b => {}
  var resize: () => Unit = () => {}
  def clip(pts: Polygon): Unit
  /** Returns the system (Unix) time in milliseconds. */
  def getTime: Long
  /** A callback timer with an elapsed time from a given start point. Although are in a general purpose form, the most common usage is for animations
   *   where things move dependent on how much time has passed. The function is of form: (elapsedTime(in milliseconds), Startime (in
   *   milliseconds) => Unit. The startTime is to be used to call the next frame at then end of the function, if another frame is needed. */
  def frame(f: (Integer, Integer) => Unit, startTime: Integer, frameLength: Integer = 15): Unit =
    timeOut(() => f(getTime.toInt - startTime, startTime), frameLength)
  /** The initial frame although are in a general purpose form, the most common usage is for animations where things move dependent on how much time
   *   has passed. */
  def startFrame(f: (Integer, Integer) => Unit, frameLength: Integer = 15): Unit = frame(f, getTime.toInt)
  /** A call back timer. Takes the delay in milliseconds */
  def timeOut(f: () => Unit, millis: Integer): Unit
  
  def startFramePermanent(f: Integer => Unit, millis: Integer = 15): Unit =
  {
    def combinedF(elapsed: Integer, startTime: Integer): Unit = { f(elapsed); frame(combinedF, startTime, millis)}
    startFrame(combinedF, millis)
  }
  
  var textMin: Int = 10
   
  final def polyFill(colour: Colour, verts: Vec2 *): Unit = polyFill(verts.toPolygon.fill(colour))
  def polyFill(pf: PolyFill): Unit
  final def polyDraw(lineWidth: Double, lineColour: Colour, verts: Vec2 *): Unit = polyDraw(verts.toPolygon.draw(lineWidth, lineColour))
  def polyDraw(dp: PolyDraw): Unit
  def polyFillDraw(pfd: PolyFillDraw): Unit
  def polyDrawText(pts: Polygon, lineWidth: Double, borderColour: Colour, str: String, fontSize: Int, fontColour: Colour = Black): Unit =
    textGraphic(str, fontSize, pts.polyCentre, fontColour)
   
  def vec2sDraw(pod: Vec2sDraw): Unit
  final def vec2sDraw(lineWidth: Double, colour: Colour, pStart: Vec2, pEnds: Vec2 *): Unit =
    vec2sDraw(Vec2sDraw(LineSegs(pStart, pEnds :_*), lineWidth, colour))
   
  def lineDraw(ld: LineDraw): Unit
  final def lineDraw(pStart: Vec2, pEnd: Vec2, lineWidth: Double = 1.0, colour: Colour = Black): Unit =
    lineDraw(LineDraw(pStart, pEnd, lineWidth, colour))
   
  def arcDraw(ad: ArcDraw): Unit
  final def arcDraw(pStart: Vec2, pCen: Vec2, pEnd: Vec2, lineWidth: Double = 1, colour: Colour = Black): Unit =
    arcDraw(ArcDraw(pStart, pCen, pEnd, lineWidth, colour))
   
  def bezierDraw(bd: BezierDraw): Unit
  final def bezierDraw(pStart: Vec2, pEnd: Vec2, pControl1: Vec2, pControl2: Vec2, lineWidth: Double = 1, colour: Colour = Black): Unit =
    bezierDraw(BezierDraw(pStart, pEnd, pControl1, pControl2, lineWidth, colour))

  def linesDraw(lsd: LinesDraw): Unit
  final def linesDraw(lineWidth: Double, linesColour: Colour, lines: Line2 *): Unit = linesDraw(LinesDraw(Line2s(lines: _*), lineWidth, linesColour))
   
  def shapeFill(sf: ShapeFill): Unit
  final def shapeFill(fillColour: Colour, segs: CurveSeg *): Unit = shapeFill(ShapeFill(Shape(segs: _*), fillColour)) 
   
  def shapeDraw(sd: ShapeDraw): Unit
  final def shapeDraw(lineWidth: Double, borderColour: Colour, segs: CurveSeg *): Unit =
    shapeDraw(ShapeDraw(Shape(segs: _*), lineWidth, borderColour))
   
  def shapeFillDraw(sfd: ShapeFillDraw): Unit
  final def shapeFillDraw(fillColour: Colour, lineWidth: Double, borderColour: Colour, segs: CurveSeg*): Unit =
    shapeFillDraw(ShapeFillDraw(Shape(segs: _*), fillColour, lineWidth, borderColour, 1))
   
  def textGraphic(tg: TextGraphic): Unit
  final def textGraphic(str: String, fontSize: Int, posn: Vec2, colour: Colour = Black, align: TextAlign = TextCen): Unit =
    textGraphic(TextGraphic(str, fontSize, posn, colour, align))
   
  def textOutline(to: TextOutline): Unit
  final def textOutline(posn: Vec2, text: String, fontSize: Int, colour: Colour, lineWidth: Double, align: TextAlign = TextCen): Unit =
    textOutline(TextOutline(posn, text, fontSize, colour, lineWidth, align))
    
  def dashedLineDraw(dld: DashedLineDraw): Unit   
      
  def toBL(input: Vec2): Vec2 = Vec2(input.x, height - input.y)      
   
  def animSeq(anims: Seq[DispPhase]): Unit = anims match
  {
    case Seq() =>     
    case Seq(DispStill(f), _*) => f()   
    
    case Seq(DispAnim(fAnim, secs), tail @ _*) =>
    { val start = getTime
      def func(): Unit =  
      { val curr = getTime
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
  def fromFileFind[A](fileName: String)(implicit ev: Persist[A]): EMon[A] = loadFile(fileName).eFindType(ev)
  def fromFileFindElse[A](fileName: String, elseValue: => A)(implicit ev: Persist[A]): A = fromFileFind(fileName)(ev).getElse(elseValue)
  
  /** Attempts to find find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls 
   *  procedure (Unit returning function) with that object of type A */
  def fromFileFindForeach[A](fileName: String, f: A => Unit)(implicit ev: Persist[A]): Unit = fromFileFind(fileName)(ev).foreach(f)
  
  def fromFileFindSetting[A](settingSym: Symbol, fileName: String)(implicit ev: Persist[A]): EMon[A] = 
    loadFile(fileName).eFindSett(settingSym)(ev)
    
  def fromFileFindSettingElse[A](settingSym: Symbol, fileName: String, elseValue: => A)(implicit ev: Persist[A]): A =
    fromFileFindSetting(settingSym, fileName)(ev).getElse(elseValue)
    
  def rendElems(elems: List[PaintElem[_]]): Unit = elems.foreach(rendElem) 
  
  def rendElem(el: PaintElem[_]): Unit = el match
  {
    case fp: PolyFill => polyFill(fp)//verts, fillColour)
    case dp: PolyDraw => polyDraw(dp)// (verts, lineWidth, lineColour) => polyDraw(verts, lineWidth, lineColour)
    case pfd: PolyFillDraw => polyFillDraw(pfd)
    case lsd: LinesDraw => linesDraw(lsd)
    case ld: LineDraw => lineDraw(ld)
    case dld: DashedLineDraw => dashedLineDraw(dld)
    case sf: ShapeFill => shapeFill(sf)
    case sd: ShapeDraw => shapeDraw(sd)
    case sfd: ShapeFillDraw => shapeFillDraw(sfd) 
    case ad: ArcDraw => arcDraw(ad)
    case bd: BezierDraw => bezierDraw(bd)
    case tg: TextGraphic => textGraphic(tg)
    case to: TextOutline => textOutline(to)
  }    
}
