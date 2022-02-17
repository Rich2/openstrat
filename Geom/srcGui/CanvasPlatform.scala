/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._, Colour._

/** An abstract Canvas interface implemented and to be implemented on various platforms. A concrete implementation will utilise canvas like an HTML
 * canvas or a Scalafx canvas. This concrete implementation class must (can?) be mixed in with a a particular use trait like CanvSimple or CanvMulti.
 * The default methods take the origin as the centre of the canvas. Note the Canvas Platform merely passes bare pointer event data to delegate
 * functions. It does not process them in relation to objects painted on the Canvas.
 *
 * It is really not a good idea to use this trait, use a sub class of this trait directly in your applications. You do not want to be thinking in
 * terms of the imperative methods of this application. Use one of the provided classes like CanvasNoPanels or Canvas Panelled or create your own if
 * the provided classes don't fulfill your needs. */
trait CanvasPlatform extends RectCenlign
{
  /** The canvas implementation will call this function when a mouse button is released. Named after Javascript command. */
  var mouseUp: (Pt2, MouseButton) => Unit = (v, b) => {}

  /** The canvas implementation will call this function when the mouse button is depressed. Named after Javascript command. */
  var mouseDown: (Pt2, MouseButton) => Unit = (v, b) => {}

  var mouseMoved: (Pt2, MouseButton) => Unit = (v, b) => {}
  var mouseDragged: (Pt2, MouseButton) => Unit = (v, b) => {}
  var keyDown: (String) => Unit = (s) => {}
  var keyUp: (String) => Unit = (s) => {}
  var onScroll: Boolean => Unit = b => {}
  var resize: () => Unit = () => {}
  def clip(pts: Polygon): Unit

  /** Returns the system (Unix) time in milliseconds. */
  def getTime: Long

  /** A callback timer with an elapsed time from a given start point. Although are in a general purpose form, the most common usage is for animations
   * where things move dependent on how much time has passed. The function is of form: (elapsedTime(in milliseconds), Startime
   * (in milliseconds) => Unit. The startTime is to be used to call the next frame at then end of the function, if another frame is needed. */
  def frame(f: (Integer, Integer) => Unit, startTime: Integer, frameLength: Integer = 15): Unit =
    timeOut(() => f(getTime.toInt - startTime, startTime), frameLength)

  /** The initial frame although are in a general purpose form, the most common usage is for animations where things move dependent on how much time
   * has passed. */
  def startFrame(f: (Integer, Integer) => Unit, frameLength: Integer = 15): Unit = frame(f, getTime.toInt)

  /** A call back timer. Takes the delay in milliseconds */
  def timeOut(f: () => Unit, millis: Integer): Unit
  
  def startFramePermanent(f: Integer => Unit, millis: Integer = 15): Unit =
  { def combinedF(elapsed: Integer, startTime: Integer): Unit = { f(elapsed); frame(combinedF, startTime, millis)}
    startFrame(combinedF, millis)
  }
  
  final def polygonFill(pf: PolygonFill): Unit = onlyIf(pf.vertsNum >= 3, pPolyFill(pf))
  protected def pPolyFill(pf: PolygonFill): Unit
  
  final def polygonDraw(pd: PolygonDraw): Unit = onlyIf(pd.vertsNum >= 2, pPolyDraw(pd))
  protected def pPolyDraw(pd: PolygonDraw): Unit

  def linePathDraw(pod: LinePathDraw): Unit = onlyIf(pod.path.dataLength >= 1, pLinePathDraw(pod))
  protected def pLinePathDraw(pod: LinePathDraw): Unit
   
  def lineSegDraw(ld: LineSegDraw): Unit

  /** Draws a circular arc on the canvas. */
  def cArcDraw(cad: CArcDraw): Unit

  /** Draws an elliptical arc on the canvas. */
  def eArcDraw(ead: EArcDraw): Unit

  def bezierDraw(bd: BezierDraw): Unit

  def lineSegsDraw(lsd: LinesDraw): Unit
   
  final def shapeGenFill(sgf: ShapeGenFillOld): Unit = onlyIf(sgf.shape.dataLength > 0, pShapeGenFill(sgf))
  protected def pShapeGenFill(sgf: ShapeGenFillOld): Unit
   
  final def shapeGenDraw(sgd: ShapeGenDrawOld): Unit = onlyIf(sgd.shape.dataLength > 0, pShapeGenDraw(sgd))
  protected def pShapeGenDraw(sgd: ShapeGenDrawOld): Unit

  /** Side effecting procedure that fills a circle. Implemented by the target platform. */
  def circleFill(cf: CircleFill): Unit

  def circleFillRadial(circle: Circle, fill: FillRadial): Unit

  /** Side effecting procedure that draws a circle on the screen. Implemented by the target platform. */
  def circleDraw(cd: CircleDraw): Unit

  /** Side effecting procedure that fills an ellipse on the screen. Implemented by the target platform. */
  def ellipseFill(ef: EllipseFill): Unit

  /** Side effecting procedure that draws an ellipse on the screen. Implemented by the target platform. */
  def ellipseDraw(ed: EllipseDraw): Unit
  
  def textGraphic(tg: TextGraphic): Unit
   
  def textOutline(to: TextOutline): Unit

  /** To be removed from CanvasPlatform. */
  def dashedLineDraw(dld: DashedLineDraw): Unit   
      
  def toBL(input: Pt2): Pt2 = Pt2(input.x, height - input.y)
   
  def animSeq(anims: Seq[DispPhase]): Unit = anims match
  {
    case Seq() =>     
    case Seq(DispStill(f), _*) => f()   
    
    case Seq(DispAnim(fAnim, secs), tail @ _*) =>
    { val start = getTime
      def func(): Unit =  
      { val curr = getTime
        val elapsed = (curr - start) / 1000
        fAnim(elapsed.toDouble)
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
  def fromFileFind[A](fileName: String)(implicit ev: Unshow[A]): EMon[A] = loadFile(fileName).findType(ev)
  def fromFileFindElse[A](fileName: String, elseValue: => A)(implicit ev: Persist[A]): A = fromFileFind(fileName)(ev).getElse(elseValue)
  
  /** Attempts to find find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls 
   *  procedure (Unit returning function) with that object of type A */
  def fromFileFindForeach[A](fileName: String, f: A => Unit)(implicit ev: Persist[A]): Unit = fromFileFind(fileName)(ev).forGood(f)
  
  def fromFileFindSetting[A](settingStr: String, fileName: String)(implicit ev: Persist[A]): EMon[A] =
    loadFile(fileName).findSetting(settingStr)(ev)
    
  def fromFileFindSettingElse[A](settingStr: String, fileName: String, elseValue: => A)(implicit ev: PersistDec[A]): A =
    fromFileFindSetting(settingStr, fileName)(ev).getElse(elseValue)

  def rendElems(elems: Arr[GraphicElem]): Unit = elems.foreach(_.rendToCanvas(this))
}