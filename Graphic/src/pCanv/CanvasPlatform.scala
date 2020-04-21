/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._, Colour._

/** An abstract Canvas interface implemented and to be implemented on various platforms. A concrete implementation will utilise canvas like an HTML
 *  canvas or a Scalafx canvas. This concrete implementation class must (can?) be mixed in with a a particular use trait like CanvSimple or CanvMulti.
 *  The default methods take the origin as the centre of the canvas. Note the Canvas Platform merely passes bare pointer event data to delegate
 *  functions. It does not process them in relation to objects painted on the Canvas.
 *
 *  It is really not a good idea to use this trait, use a sub class of this trait directly in your applications. You do not want to be thinking in
 *  terms of the imperative methods of this application. Use one of the provided classes like CanvasNoPanels or Canvas Panelled or create your own if
 *  the provided classes don't fullfil your needs. */
trait CanvasPlatform extends RectGeom
{
  /** The canvas implementation will call this function when a mouse button is released. Named after Javascript command */
  var mouseUp: (Vec2, MouseButton) => Unit = (v, b) => {}

  /** The canvas implementation will call this function when the mouse button is depressed. Named after Javascript command */
  var mouseDown: (Vec2, MouseButton) => Unit = (v, b) => {}

  var mouseMoved: (Vec2, MouseButton) => Unit = (v, b) => {}
  var mouseDragged: (Vec2, MouseButton) => Unit = (v, b) => {}
  var keyDown: (String) => Unit = (s) => {}
  var keyUp: (String) => Unit = (s) => {}
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
  { def combinedF(elapsed: Integer, startTime: Integer): Unit = { f(elapsed); frame(combinedF, startTime, millis)}
    startFrame(combinedF, millis)
  }
  
  final def polyFill(poly: Polygon, colour: Colour): Unit = oif(poly.length >= 3, pPolyFill(poly, colour))
  def pPolyFill(poly: Polygon, colour: Colour): Unit
  
  final def polyDraw(poly: Polygon, lineWidth: Double, colour: Colour): Unit = oif(poly.length >= 2, pPolyDraw(poly, lineWidth, colour))
  def pPolyDraw(poly: Polygon, lineWidth: Double, colour: Colour): Unit

  def linePathDraw(pod: LinePathDraw): Unit = oif(pod.path.length >= 1, pLinePathDraw(pod))
  def pLinePathDraw(pod: LinePathDraw): Unit
   
  def lineDraw(ld: LineDraw): Unit
  final def lineDraw(pStart: Vec2, pEnd: Vec2, lineWidth: Double = 1.0, colour: Colour = Black): Unit =
    lineDraw(LineDraw(pStart, pEnd, lineWidth, colour))
   
  def cArcDrawOld(ad: CArcDrawOld): Unit
  final def cArcDrawOld(pStart: Vec2, pCen: Vec2, pEnd: Vec2, lineWidth: Double = 1, colour: Colour = Black): Unit =
    cArcDrawOld(CArcDrawOld(pStart, pCen, pEnd, lineWidth, colour))

  def cArcDraw(cad: CArcDraw): Unit
   
  def bezierDraw(bd: BezierDraw): Unit
  final def bezierDraw(pStart: Vec2, pEnd: Vec2, pControl1: Vec2, pControl2: Vec2, lineWidth: Double = 1, colour: Colour = Black): Unit =
    bezierDraw(BezierDraw(pStart, pEnd, pControl1, pControl2, lineWidth, colour))

  def linesDraw(lsd: LinesDraw): Unit
  final def linesDraw(lineWidth: Double, linesColour: Colour, lines: Line2 *): Unit = linesDraw(LinesDraw(Line2s(lines: _*), lineWidth, linesColour))
   
  final def shapeFill(shape: PolyCurve, colour: Colour): Unit = oif(shape.length > 0, pShapeFill(shape, colour))

  def pShapeFill(shape: PolyCurve, colour: Colour): Unit
   
  final def shapeDraw(shape: PolyCurve, lineWidth: Double, colour: Colour): Unit = oif(shape.length > 0, pShapeDraw(shape, lineWidth, colour))
  def pShapeDraw(shape: PolyCurve, lineWidth: Double, colour: Colour): Unit

  def textGraphic(tg: TextGraphic): Unit
  final def textGraphic(str: String, fontSize: Int, posn: Vec2, colour: Colour = Black, align: TextAlign = CenAlign): Unit =
    textGraphic(TextGraphic(str, fontSize, posn, colour, align))
   
  def textOutline(to: TextOutline): Unit
  final def textOutline(str: String, fontSize: Int, posn: Vec2, colour: Colour = Black, lineWidth: Double = 1, align: TextAlign = CenAlign): Unit =
    textOutline(TextOutline(str, fontSize, posn, colour, lineWidth, align))
    
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
  def fromFileFind[A](fileName: String)(implicit ev: Persist[A]): EMon[A] = loadFile(fileName).findType(ev)
  def fromFileFindElse[A](fileName: String, elseValue: => A)(implicit ev: Persist[A]): A = fromFileFind(fileName)(ev).getElse(elseValue)
  
  /** Attempts to find find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls 
   *  procedure (Unit returning function) with that object of type A */
  def fromFileFindForeach[A](fileName: String, f: A => Unit)(implicit ev: Persist[A]): Unit = fromFileFind(fileName)(ev).forGood(f)
  
  def fromFileFindSetting[A](settingStr: String, fileName: String)(implicit ev: Persist[A]): EMon[A] = 
    loadFile(fileName).findSetting(settingStr)(ev)
    
  def fromFileFindSettingElse[A](settingStr: String, fileName: String, elseValue: => A)(implicit ev: Persist[A]): A =
    fromFileFindSetting(settingStr, fileName)(ev).getElse(elseValue)

  def rendElems(elems: Arr[PaintFullElem]): Unit = elems.foreach(_.rendToCanvas(this))
}